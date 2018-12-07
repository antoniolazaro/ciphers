# Cifra de Vigenère

## Resumo da cifra
A cifra de Vigenère resolve duas fraquezas principais da cifra de César: a de César tem apenas 26 chaves, e todas as ocorrências de uma letra são sempre encodadas na mesma letra no texto encriptado.

Como funciona: nós escolhemos uma chave que é uma palavra. Vamos usar a palavra "cipher". A frase que gostaríamos de encriptar é "Welcome to ClojureBridge!", que se torna "welcometoclojurebridge" depois de remover caracteres especiais e de converter tudo para minúsculo.

Agora imagine pegar a frase que você gostaria de encriptar e então escrever a palavra-chave quantas vezes forem necessárias para atingir o mesmo comprimento:

```
welcometoclojurebridge
cipherciphercipherciph
```
Vamos pegar `w` (a primeira letra do texto que estamos encriptando) e `c` (a letra da palavra-chave logo abaixo). Então `w` é encriptada pegando seu valor inteiro, `22`, o valor inteiro de `c`, `2`, somando os dois e pegando o resultado módulo 26, se ele for maior que 25. O resultado será `24`, que corresponde à letra `y`.

A encriptação do `e` é a soma do seu valor `4` com o valor de `i` (a letra abaixo de `e`) módulo 26, então ela é `4 + 8 = 12`, que é `m`.

Se você continuar o processo, você vai obter o seguinte resultado (a terceira linha):
```
welcometoclojurebridge
cipherciphercipherciph
ymajsdgbdjpflcglfiklvl 
```
Note que as duas letras `e` em "welcome" são encodadas de maneira diferente: como `m` no primeiro caso e `g` no segundo. Isso esconde padrões no texto e não permite análise de frequência como nós fizemos com a cifra de César, já que a ocorrência da mesma letra em um texto cifrado pode vir de letras diferentes do texto original. 

**Exercício** Escreva uma função `encrypt-letter` que, dados dois caracteres, retorna o primeiro encriptado com o segundo. Por exemplo, dados `\w` e `\c`, ela retorna `\y`. Não se esqueça de testá-la com valores para os quais a adição resulte em um número maior que 25 (encriptar `\u` com `\i`, por exemplo, resulta em `\c`).

## Encriptando e decriptando com a cifra de Vigenère
*Funções relevantes:* [cycle](https://clojuredocs.org/clojure.core/cycle) (nova), [map](https://clojuredocs.org/clojure.core/map), [mapv](https://clojuredocs.org/clojure.core/mapv) (revisitada). 

Agora nós vamos usar `mapv` para encriptar textos mais longos.

Para isso, consideremos um jeito um pouco diferente de como usar `mapv` (ou `map`): nós podemos usá-las com múltiplas sequências. O exemplo mais simples é:
```clojure
(mapv + [6 7 8] [3 2 1]) ; [9 9 9]
(mapv - [6 7 8] [3 2 1]) ; [3 5 7]
```
Aqui `mapv` aplica a função dada (`+` no primeiro exemplo e `-` no segundo) aos primeiros elementos dos vetores, depois aos segundos, etc.

Claro, nós podemos usá-la com uma função anônima também; a função deve ter dois argumentos:
```clojure
(mapv #(if (< %1 %2) %1 %2) [2 4 5] [3 6 1]) ; [2 4 1]
```
Nesse caso a função anônima retorna o menor dos seus dois argumentos. Então, dados `2` e `3`, ela retorna `2`, que é o primeiro elemento do resultado. Depois, dados `4` e `6`, ela retorna `4`, etc.

**Exercício:** Use `mapv` para computar a soma dos quadrados dos elementos dos vetores dados. Por exemplo, passando `[1 2]` e `[3 1]`, ela deveria retornar o vetor `[10 5]`.

Aqui uma funcionalidade legal: se uma das sequências passadas é maior do que a outra, o resultado tem o mesmo tamanho da sequência menor, e os elementos restantes são ignorados:
```clojure
(mapv + [6 7 8] (range)) ; [6 8 10]
```
Isso nos permite usar uma função `cycle` para gerar uma sequência infinita de chaves repetidas e então "adicionar" ela ao texto a ser encriptado. `cycle` recebe uma sequência e cria uma sequência lazy infinita ao repetir a sequência recebida. Por exemplo,  
```clojure
(def cycle1 (cycle [1 2 3])) ; infinite cycle
(take 10 cycle1) ; (1 2 3 1 2 3 1 2 3 1)
(def cycle2 (cycle "word")) 
crypto-solutions=> (take 10 cycle2) ; (\w \o \r \d \w \o \r \d \w \o)
```

**Exercício:** Use a `cycle` com a chave `"cipher"`, `mapv` (ou `map`), e a função `encrypt-letter`que você escreveu mais cedo para encriptar a string `"welcometoclojurebridge"`.

**Exercício:** Quando você conseguir fazer ela funcionar, escreva uma função `vigenere-encrypt` que recebe um texto para encriptar e uma chave e faz a encriptação. Assim como na cifra de César, você precisa primeiro usar a `get-letters` para retirar outros símbolos e converter as letras para minúsculo. 

**Exercício:** Agora escreva uma função que recebe uma string encriptada e uma chave e faz a decriptação. Note que você vai fazer uma subtração módulo 26 ao invés de uma adição.
<br />
Garanta que quando você encriptar uma string com uma chave, você possa obtê-la de volta (com todas as letras minúsculas e sem pontuação) ao decriptá-la com a mesma chave.

**Exercício:** Poste um texto encriptado (com uma chave) no slack. Tente decriptar os textos de outras pessoas.
 
## Quebrando a cifra de Vigenère
Parece que a cifra de Vigenère não permite análise de frequência já que a mesma letra pode ser encriptada de muitos jeitos diferentes. Na verdade, porém, é possível adivinhar uma chave através de um processo que é só um pouquinho mais complicado que quebrar uma cifra de César. Ele também precisa de um texto maior. 

Suponha que você saiba o tamanho da chave. Digamos, a chave tem 5 letras. Isso significa que se eu pegar letras do texto encriptado nas posições 0, 5, 10, 15, etc., elas são todas encriptadas com a mesma letra da chave, então a todas elas são adicionadas o mesmo valor. O mesmo vale para letras nas posições 1, 6, 11, 16, etc.

Isso significa que você pode fazer uma análise de frequência em cada um desses grupos de letras, e determinar uma possível chave de uma letra para cada grupo assim como você fez para a cifra de César. Juntar essas chaves de uma letra forma a palavra-chave que você está tentando adivinhar.

Mas como saber o tamanho da chave??? Acontece que se você pegar letras da string encriptada a uma distância igual ao tamanho da chave (como fizemos acima), suas frequências são muito desiguais, muito como as frequências das letras no alfabeto inglês. Porém, se você pegar qualquer outra distância, as frequências tendem a ser mais uniformemente distrubuídas (como ruído aleatório).

Com essas ideias em mente, você pode agora tentá-las no exemplo ao final dessa seção. Abaixo nós explicamos como várias funções de Clojure podem ser usadas para essa tarefa. 

### Parte 1: determine o tamanho da chave

*Funções relevantes:* [reduce](https://clojuredocs.org/clojure.core/reduce), [take-nth](https://clojuredocs.org/clojure.core/take-nth), [rest](https://clojuredocs.org/clojure.core/rest), [assoc](https://clojuredocs.org/clojure.core/assoc) (revisitada).

Primeiramente, você precisa usar a função `take-nth` para criar subsquências de elementos com distância de n passos entre eles:
```clojure
(take-nth 3 [1 2 3 4 5 6 7]) ; (1 4 7)
(take-nth 3 (rest [1 2 3 4 5 6 7])) ; (2 5)
```
A função `rest` te permite pegar todo n-ésimo elemento (todo terceiro, no exemplo) começando do segundo. Se você quiser pular os dois primeiros, você pode usar `(rest (rest s))`, onde `s` é uma sequência.

Outro jeito de começar em uma posição diferente da primeira é usando `drop`. Por exemplo, se você quiser começar no terceiro elemento, chame `drop` com o parâmentro 2 para tirar os dois primeiros elementos.

Quando você tiver pegado os elementos em toda n-ésima posição, até o tamanho potencial da chave, você precisa ver se essas frequências combinam com as frequências do alfabeto inglês.

Você pode usar as funções que você escreveu para quebrar a cifra de César para computar um hashmap que mostra quantas vezes cada letra aparece. Isso não são frequências, entretanto, apenas somas. Para transformá-las em porcentagens, você precisa dividir cada soma pelo número total de elementos.

A função `count` the dá o número de elementos em cada subsequência. Agora você pode usar a função `reduce` para separar o hashmap e juntá-lo de novo com os novos valores.

Leia a descição da reduce acima, depois dê uma olhada nos exemplos aqui.

Em sua forma simples, a `reduce` é usada para computar um único resultado a partir de uma sequência. Por exemplo, ela pode ser usada para adicionar elementos de uma sequência:
```clojure
(reduce + [1 3 5]) ; 9
``` 
Pode ser um pouco mais complicado se você quiser um ponto de início. Por exemplo, eu posso querer subtrair todos os meus elementos de 10.
```clojure
(reduce - 10 [1 3 5]) ; 1
```
10 aqui é usado como um ponto de início, ou uma *seed* da qual o resultado cresce. A operação é então aplicada ao 10 e ao primeiro elemento do vetor, então ao resultado disso e ao segundo elemento do vetor, e assim por diante. 

No nosso caso, queremos reduzir um hashmap ao alterar cada valor (enquanto mantendo a mesma chave) e associando-o ao novo hashmap. Aqui está um exemplo que é muito similar, mas que incrementa o valor, ao invés de dividí-lo pelo número total de caracteres:
```clojure
(reduce #(assoc %1 (first %2) (inc (second %2))) {} {"a" 2, "b" 5}) ; {"a" 3, "b" 6}
```
Aqui nós começamos com um hashmap vazio como seed. Então para cada par de chave/valor no hashmap passado (referenciado como `%2` na função anônima) nós mantemos a chave (que é `(first %2)`) e incrementamos o valor correspondente (em `(inc (second %2))`). Então nós usamos `assoc` para adicionar o valor obtido ao hashmap resultante.

Esse é um uso muito complexo da `reduce`, então estude-o cuidadosamente e tire suas dúvidas se você tiver alguma.

**Exercício:** modifique o exemplo acima para dividir cada valor do dado hashmap pelo número total de letras na subsequência, ao invés de incrementá-lo.

Agora você pode usar essas funções para tentar diferentes tamanhos de chaves e ver qual delas dá frequências de letras mais similares à língua inglesa: [Relative frequencies of letters in the English language (wikipedia)] (https://en.wikipedia.org/wiki/Letter_frequency#Relative_frequencies_of_letters_in_the_English_language). Uma vez que você esteja razoavelmente confiante de que você achou o tamanho certo da chave, você pode tentar determinar suas letras.

### Parte 2: análise de frequência nas subsequências

Quando você obter subsequências de todas as N-ésimas letras, onde N é o provável tamanho da chave, você pode usar as funções que você escreveu para quebrar a cifra de César para determinar cada letra da chave separadamente.

O único problema é que você não vai saber se você está acertando até você adivinhar a chave inteira, então você terá que brincar um pouco de adivinhação juntando as letras.

### Parte 3: desafio de Vigenère

Aqui está um exemplo para você tentar quebrar. A chave tem entre dois e oito caracteres. É uma palavra e está relacionada ao tópico deste workshop.

Seja paciente, isso pode levar tempo. Tente automatizar seu processo o tanto quanto possível. Nós também recomendamos que vocês trabalhem em duplas.
```
rzsrppgeamjllagcpwxismxxcalecwygluetcaguepwwlznpclepcsgcpkgbac
ltcifstvntybwsepwutzkinweettwgqwjpnweefbwgazgvciebtvyalvyjlowh
smhdacdpcqrtobzttlwpznepnpacpqfspxwcomfiazgvciebtvyalvyjlowhhp
arstwsewlwplwkptgexmfiznudmwddymguepwutzkisqywwmntwxjdrzsbxqfv
wifvfiytdwoxyoldsmjpnkgbatahsuwceascopwgyinpwzscvazthikhzuwitu
whcmxtczwsewshlusotvyvciutepwjdvskjijapqywmcjzpkdpdayjtlwsxqkh
ttwspalgzgwgfakwzxhtceshyietonggsmjhsmopdxghepmbzckajiopclwsep
wecmkxomfitidbplsaznxgpmvdxjqecmkxomfimijpnsgqlus
```

## Terminamos?
Esse foi um longo dia! Porém, eu recomendo que você agora tire uns minutos e volte para a página de overview para ler a descrição de Clojure e programação funcional novamente. Agora muito do que que está lá deve fazer mais sentido com a experiência de hoje.

### Agradecemos por comparecer ao ClojureBridge!!!

**Voltar ao começo:** [Setup do Projeto](1-setup.md)
<br />
**Anterior:** [Quebrando a cifra de César](5-frequency.md)
