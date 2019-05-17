# Mapas Clojure e análise de frequência

## Análise de frequência para quebrar cifras

A língua inglesa, como a maioria dos idiomas, tem uma distribuição uniforme de letras. A letra mais frequente em Inglês
é o `e` que, na média, constitui aproximadamente 13% dos textos, seguida das letras `t` (cerca de 9%) e `a` (8%). A
letra menos frequente é o `z`, com menos de 0.1% de ocorrência.
Uma distribuição completa das letras em Inglês pode ser encontrada aqui: [Relative frequencies of letters in the English language (wikipedia)](https://en.wikipedia.org/wiki/Letter_frequency#Relative_frequencies_of_letters_in_the_English_language). Abra esse link numa nova aba, pois ele pode ser necessário mais tarde.

A distribuição não uniforme de letras e o fato de que cada letra gera sempre a mesma letra correspondente na versão
criptografada permitem que a a cifra de Caesar seja quebrada (isto é, que a chave seja encontrada) de maneira muito mais
rápida do que simplesmente tentar todas as chaves possíveis e verificar se o resultado contém palavras que façam
sentido. Essa estratégia é chamada de *análise de frequência*.

Note que precisamos de um texto em Inglês relativamente longo para que a análise de frequência funcione: apenas uma palavra não é suficiente.

Nosso exemplo usará o seguinte texto criptografado usando a cifra de Caesar com uma chave desconhecida:

```clojure
"radyjgtxhpsncpbxrvtctgpaejgedhtegdvgpbbxcvapcvjpvtrdbqxcxcv
iwtpeegdprwpqxaxinpcsxcitgprixktstktadebtciduphrgxeixcvapcvjp
vtlxiwpctuuxrxtcipcsgdqjhixcugphigjrijgtudgbjaixiwgtpstsegdvg
pbbxcvo"
```

## Hashmaps Clojure

*Funções relevantes* [assoc](https://clojuredocs.org/clojure.core/assoc), [zipmap](https://clojuredocs.org/clojure.core/zipmap)

Uma maneira conveniente de guardar as frequências é um tipo de coleção Clojure chamada *hashmap*, também conhecida como *map*, simplesmente. Tome cuidado para não confundir com a função *map*.

Um mapa é uma sequência de pares "chave-valor". São escritos usando chaves; por exemplo, `{"maçã" 2, "banana" 3}` é um mapa com uma chave `"maçã"` relacionada com um valor `2`, e uma chave "banana" relacionada com um valor `3`.

Chaves e valores podem ser qualquer coisa: números, strings, vectors, etc. É comum utilizar chaves de um tipo Clojure especial chamado *keyword*, dado que eles podem acelerar consultas em mapas grandes, mas isso não é necessário. Keywords em Clojure são escritas assim: `:maçã`.

Como estamos computando frequências de caracteres, nossas chaves serão caracteres e os valores serão contadores ou
percentuais.

Aqui estão algumas funcionalidades de mapas:

1. `{}` é um mapa vazio.
2. Você pode adicionar elementos à um mapa usando a função `assoc`, passando uma chave e um valor. Se uma chave passada
   pro `assoc` já estiver presente, o valor é substituído pelo novo.
```clojure
(assoc {} "a" 3) ; resultado: {"a" 3}
(assoc {"a" 3} "x" 2) ; resultado: {"a" 3, "x" 2}
(assoc {"a" 3, "x" 2} "a" 5) ; resultado: {"a" 5, "x" 2}
```
3. O principal caso de uso de mapas é quando precisamos consultar um valor para uma chave específica. Não existe uma função especial para fazer isso: o próprio mapa serve como uma função que, quando passada uma chave, retorna o respectivo valor. Se a chave não for encontrada, retorna `nil`:
```clojure
({"a" 3, "x" 2} "a") ; resultado: 3
({"a" 3, "x" 2} \a) ; resultado: nil (\a é diferente de "a")
({"a" 3, "x" 2} "b") ; resultado: nil
({} "qualquer") ; resultado: nil
```
4. Uma outra maneira de criar um mapa é a partir de dois vetores: um com chaves e um com os valores correspondentes. A função que faz isso se chama `zipmap`:
```clojure
(zipmap [\a \b] [2 5]) ; resultado: {\a 2, \b 5}
```

**Exercício:** Pratique com mapas. Por exemplo, crie um pequeno mapa de animais e seus respectivos sons ("pato"/"quack"), e depois consulte o som a partir do animal.

## Contando o número de ocorrências de um caractere
*Funções relevantes:* [count](https://clojuredocs.org/clojure.core/count), [first](https://clojuredocs.org/clojure.core/first), [second](https://clojuredocs.org/clojure.core/second), [=](https://clojuredocs.org/clojure.core/=).

Para computar as frequências de letras, precisamos determinar quantas vezes cada letra aparece na string criptografada. Precisamos trabalhar também com vetores que são pares de chave e valor.

As funções que precisamos para trabalhar com vetores são:

1. `count` retorna o número de elementos de um vetor: `(count ["maçã" "banana"])` retorna `2`.
2. `first` retorna o primeiro elemento de um vetor: `(first ["maçã" "banana"])` retorna `"maçã"`.
3. `second` retorna o segundo elemento de um vetor: `(second ["maçã" "banana"])` retorna `"banana"`, assim como `(second ["maçã" "banana" "kiwi"])`

Note que essas funçoẽs também funcionam com qualquer sequência (vamos explorar elas mais tarde).

Agora estamos de volta a escrever funções Clojure. Precisamos escrever uma função `count-letters` que, dado um caractere e uma string, reornaria o número de ocorrências desse caractere na string. Por exemplo:
```clojure
(count-letters \a "aadvark") ; 3
(count-letters \x "aadvark") ; 0
```

Uma maneira (que não é a mais eficiente, mas é simples) é filtrar todos os outros caracteres and retornar a quantidade de caracteres restantes. Em Clojure, `=` é uma função que pode ser usada em quaisquer dois valores (sejam números, strings, vetores, etc). Por exemplo, `(= (/ 6 2) 3)` retorna `true` (note que `(/6 2)` é o resultado de dividir 6 por 2), e `(= 97 "97")` retorna `false`, dado que uma string não é igual a um número.

Aqui precisamos usar a função `filterv` com uma função anônima que checa se o seu parâmetro é igual ao caractere em questão, e então precisamos retornar a quantidade restante.

**Exercício:** Veja sua função anterior que usa `filterv` e então escreva uma chamada de `filterv` para tirar os
caracteres que não sejam `\a` na palavra `"aadvark"`. Após isso, defina uma função `count-letters` que funcione como especificado no exemplo acima. Tente usá-la em uma palavra diferente e com um caractere diferente.

## Sequências preguiçosas e intervalos
*Funções relevantes:* [range](https://clojuredocs.org/clojure.core/range), [take](https://clojuredocs.org/clojure.core/take), [drop](https://clojuredocs.org/clojure.core/drop)

Dados são comumente organizados de forma sequencial: o primeiro elemento, depois o segundo, etc. A maioria das funções Clojure predefinidas funcionam com dados sequenciais, processando elementos um depois do outro.
Até agora a única coleção de dados sequencial que vimos são vetores. Entretanto, diversos dados sequenciais em Clojure são descritos através de sequências. Uma característica interessante de Clojure é que as sequências são *preguiçosas*, isto é, elas podem ser conceitualmente inifinitas, realizando apenas a porção que de fato está sendo utilizada.

Um exemplo de sequência preguiçosa é um intervalo. Um intervalo é uma sequência de números inteiros, e é gerada por uma função `range`. Por exemplo, `(range 2 5)` retorna a sequência `(2 3 4)` (o limite superior não é inclusivo). `(range 10)` retorna uma sequência de inteiros de 0 a 9.

Você pode tentar os exemplos acima no REPL. O que você não deveria tentar no REPL, entretanto, é chamar `range` sem parâmetros: `(range)`. Isso retorna uma sequência de inteiros de 0 ao inifito! Porém, você pode guardar essa sequência em uma variável, já que isso não vai realizá-la:
```clojure
(def inf (range))
```
Agora você pode pegar quantos elementos quiser usando a função `take`:
```clojure
(take 20 inf) ; inteiros de 0 a 19
(take 100 inf) ; inteiros de 0 a 99
```
`take` tem uma função complementar chamada `drop`, que descarta os `n` primeiros elemntos. Usar `drop` em um
sequência infinita produz uma outra sequência inifita.

Agora voltando para as definições de `map` e `filter`, você pode notar que as duas retornam sequências preguiçosas. Por exemplo, você pode definir uma sequência infinita de números pares aplicando um filtro `even?` em uma sequência infinita com todos os números.
```clojure
(def evens (filter even? (range)))
(0 2 4 6 8 10 12 14 16 18) ; even integers from 0 to 18
```

Note também que `mapv` e `filterv` não são preguiçosas (essas funções são chamadas "ansiosas"), então substituir
`filter` por `filterv` no exemplo acima levaria a uma avaliação inifita.

Sinta-se livre para explorar sequências preguiçosas procurando por [sequence functions no clojuredocs](https://clojuredocs.org/quickref).


Nós vamos utilizar um intervalo finito para computar frequências de letras, mas vamos voltar para sequências preguiçosas potencialmente inifinitas na próxima seção, sobre a cifra de Vinegere.

Por enquanto precisamos criar uma sequência de 26 letras. Em vez de digitá-las, vams criar um intervalo dos 26 primeiros inteiros e aplicar a função `to-char` que escrevemos previamente.
```clojure
(def alphabet (map to-char (range 26)))
```
Você pode usar `map` ou `mapv` aqui, dado que utilizaremos a sequência finita inteira de qualquer forma.

Verifique que `alphabet` de fato contém todos os caracteres em order.

## Criando um hashmap de quantidade de letras
Agora estamos prontos pra construir a função que recebe uma string e retorna um hashmap com o número de vezes que cada letra aparece na string.

Vamos revisitar o text criptografado que estamos tentando quebrar e  salvá-lo em uma variável:
```clojure
(def encr1 "radyjgtxhpsncpbxrvtctgpaejgedhtegdvgpbbxcvapcvjpvtrdbqxcxcv
iwtpeegdprwpqxaxinpcsxcitgprixktstktadebtciduphrgxeixcvapcvjp
vtlxiwpctuuxrxtcipcsgdqjhixcugphigjrijgtudgbjaixiwgtpstsegdvg
pbbxcvo")
```

Assumimos que você tem uma função `count-letters` e a variável `alphabet` definidas, como discutido acima.

Você talvez queira revistar os exemplos de `mapv` e `zipmap` antes de começar a trabalhar nos próximos passos.

**Exercício:** construa um hashmap do número de ocorrências de cada letra na string `encr1`. Aqui está a sequencia de passos que resolvem o problema:

1. Use `map` (ou `mapv`) e `count-letters` para encontrar o número de ocorrências de cada letra contido em `alphabet` na string `encr1`. Se isso parecer complicado, acompanhe a solução apresentada abaixo.
2. Use `zipmap` em `alphabet` e o resutlado do primeiro passo para "zipar" os contadores com o alfabeto, criando um novo hashmap.

Se tudo correr corretamente, você deve terminar com um mapa como esse:
```clojure
{\a 7, \b 8, \c 16, \d 10, \e 8, \f 0, \g 16, \h 5, \i 13, \j 8, \k 2, \l 1, \m 0, \n 2, \o 1, \p 19, \q 3, \r 8, \s 6, \t 17, \u 5, \v 11, \w 4, \x 17, \y 1, \z 0}
```

**Solução do primeiro passo do exercício anterior:**
Precisamos "mapear" o alfabeto, computando os contadores para cada letra. Logo, vamos passar cada letra para a função anônima, uma por uma, como o parâmetro `%`:
```clojure
(map #(count-letters % encr1) alphabet)
```

**Exercício:** Transforme sua solução para o exercício anterior em uma função que recebe uma string e retorna as
frequências. Passe a string `encr1` para ela para testar se o resultado está correto.

## Ordenação em Clojure
*Funções relevantes:* [sort-by](https://clojuredocs.org/clojure.core/sort-by)

Agora que temos o hashmap com os contadores, precisamos achar a letra que ocorre mais frequentemente. É esperado que entre elas estejam as três letras mais comuns no idioma Inglês: `e, t, a`. Isso reduz suficientemente as possibilidades de chaves para tentarmos gerar encontrar uma que produz um text reconhecível em Inglês.

Podemos simplesmente olhar para o hasmap e encontrar as letras mais frequentes, mas é mais conveniente ordená-lo pela frequência. A função que faz isso é `sort-by`: ela recebe qualquer sequência e uma função que diz como os elementos devem ser ordenados. Por exemplo, dada uma sequência de pares de números, podemos ordenar pelo primeiro elemento do par:
```clojure
(sort-by first [[1 2] [2 2] [2 3]]) ; resultado: ([1 2] [2 2] [2 3])
```
Melhor, podemos especificar que queremos ordem crescente ou descrescente:
```clojure
(sort-by first > [[1 2] [2 2] [2 3]]) ; resultado: ([2 2] [2 3] [1 2])
```

Um hashmap é uma coleção de pares chave/valor, logo se comporta da mesma maneira que vetores de vetores. A única
diferença é que não podemos ter a mesma chave com valores diferentes em um hasmap:
```clojure
(sort-by first > {1 2, 4 2, 2 3}) ; resultado: ([4 2] [2 3] [1 2])
```
Precisamos ordenar nosso hashmap pelo segundo elemento de cada par (o número de ocorrências de uma letra é o valor, não a chave). Note que precisamos ordenar em ordem decrescente, como no exemplo acima.

**Exercício:** escreva uma expressão que ordene o hashmap.
O resultado deve ser:
```clojure
([\p 19] [\t 17] [\x 17] [\c 16] [\g 16] [\i 13] [\v 11] [\d 10] [\b 8] [\e 8] [\j 8] [\r 8] [\a 7] [\s 6] [\h 5] [\u 5] [\w 4] [\q 3] [\k 2] [\n 2] [\l 1] [\o 1] [\y 1] [\f 0] [\m 0] [\z 0])
```

Podemos também pegar os três primeiros elementos desse mapa usando `take`.

**Exercício:* escreva uma função que, dado um hashmap de caracteres e suas frequências, retorna os três elementos mais frequentes e suas frequências: `([\p 19] [\t 17] [\x 17])`

## Computando a chave da cifra

A única tarefa restante agora é adivinhar os valores das chaves e tentá-los. As três letras mais comuns no idioma Inglês são `e, t, a`, e há uma grande probablilidade de uma delas correspoder ao `\p` na mensagem criptografada.

Vamos supor que `\p` seja o valor criptografado de `\e` (isso pode ou não ser verdade). Nós obtemos `\p` a partir de `\e` convertendo os dois para seus valores numéricos, somando a chave e determinando o resto da divisão por 26.

**Exercício:** Escreva a computação acima como uma função que recebe dois caracteres e retorna uma possível chave.

**Exercício:** Tente descriptografar o texto com a chave que você computou. Se não funcionar, tente outras opções
prováveis (`\p` pode também significar `\t` ou `\a`). Uma dessas opções deve gerar a chave correta.


**Exercício:** Agora tente essa estratégia na string "ahixblmaxmabgzpbmayxtmaxklmatmixkvaxlbgmaxlhnetgwlbgzlmaxmngxpbmahnmmaxphkwltgwgxoxklmhiltmtee".

**Exercício:** Tente também fazer upload dos seus exemplos mais longos (mas não a chave) para o Slack e tente descobrir a chave dos outros exemplos que encontrados lá. Garanta que eles são longos o suficiente para que seja feita análise de frequência. Use apenas textos escritos em inglês.

## Próximos passos

Obviamente, a cifra de Caesar não é segura. Entretanto, exsite uma cifra mais complexa e melhor, que extende a ideia de um "deslocamento" do alfabeto: a cifra de Vigenere, explicada na próxima seção.

**Próxima:** [Cifra de Vigenere](6-vigenere.md)
<br />
**Anterior:** [Cifra de Caesar](4-caesar.md)
