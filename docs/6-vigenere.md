# Cifra de Vigenère

## Resumo da cifra
_Vigenere cipher addresses two main weaknesses of Caesar cipher: Caesar cipher has only 26 keys, and all occurrences of a letter are always encoded by the same letter in the encrypted text._
A cifra de Vigenère resolve duas fraquezas principais da cifra de César: a de César tem apenas 26 chaves, e todas as ocorrências de uma letra são sempre encodadas pela mesma letra no texto encriptado.

_Here is how it works: we pick a key which is a word. Let's use the word "cipher". The phrase we would like to encrypt is "Welcome to ClojureBridge!", which turns into "welcometoclojurebridge" after removing non-letters and converting to all lowercase._
Como funciona: nós escolhemos uma chave que é uma palavra. Vamos usar a palavra "cipher". A frase que gostaríamos de encriptar é "Welcome to ClojureBridge!", que se torna "welcometoclojurebridge" depois de remover caracteres especiais e de converter tudo para minúsculo.

_Now imagine taking the phrase that you would like to encrypt and then writing the key word under it as many time as needed to get the same length:_
Agora imagine pegar a frase que você gostaria de encriptar e então escrever a palavra-chave quantas vezes forem necessárias para atingir o mesmo comprimento:

```
welcometoclojurebridge
cipherciphercipherciph
```
_Let's take `w` (the first letter of the text we are encrypting) and `c` (the letter of the keyword right under it). Then `w` is encrypted as taking its integer value, `22`, adding the integer value of `c`, `2`, adding them together, and taking the result modulo 26 if it is greater than 25. The result will be `24`, which corresponds to the letter `y`._
Vamos pegar `w` (a primeira letra do texto que estamos encriptando) e `c` (a letra da palavra-chave logo abaixo). Então `w` é encriptada pegando seu valor inteiro, `22`, o valor inteiro de `c`, `2`, somando os dois e pegando o resultado módulo 26, se ele for maior que 25. O resultado será `24`, que corresponde à letra `y`.

_The encryption of `e` is the sum of its value `4` and the value of `i` (the letter written under `e`) modulo 26, so it's `4 + 8 = 12`, which is `m`._
A encriptação do `e` é a soma do seu valor `4` com o valor de `i` (a letra abaixo de `e`) módulo 26, então ela é `4 + 8 = 12`, que é `m`.

_If you continue the process, you will get the following result (the third line below):_
Se você continuar o processo, você vai obter o seguinte resultado (a terceira linha):
```
welcometoclojurebridge
cipherciphercipherciph
ymajsdgbdjpflcglfiklvl 
```
_Note that the two letters `e` in "welcome" are encoded differently: as `m` in the first case, and as `g` in the second. This hides patterns in the text and doesn't allow frequency analysis the way we did it for Caesar cipher since the same occurrence of a letter in ciphertext may've come from different letters in the original text._
Note que as duas letras `e` em "welcome" são encodadas de maneira diferente: como `m` no primeiro caso e `g` no segundo. Isso esconde padrões no texto e não permite análise de frequência como nós fizemos com a cifra de César, já que a ocorrência da mesma letra em um texto cifrado pode vir de letras diferentes do texto original. 

_**Exercise** Write a function `encrypt-letter` that, given two characters, returns the first one encrypted with the second one. For instance, given `\w` and `\c`, it returns `\y`. Make sure to test it on examples in which addition results in a number higher than 25 (encrypting `\u` with `\i`, for instance, gives `\c`)._
**Exercício** Escreva uma função `encrypt-letter` que, dados dois caracteres, retorna o primeiro encriptado com o segundo. Por exemplo, dados `\w` e `\c`, ela retorna `\i`. Não se esqueça de testá-la com valores para os quais a adição resulte em um número maior que 25 (encriptar `\u` com `\i`, por exemplo, resulta em `\c`).

_## Encrypting and decrypting with Vigenere cipher_
## Encriptando e decriptando com a cifra de Vigenère
_*Relevant functions:* [cycle](https://clojuredocs.org/clojure.core/cycle) (new), [map](https://clojuredocs.org/clojure.core/map), [mapv](https://clojuredocs.org/clojure.core/mapv) revisited._
*Funções relevantes:* [cycle](https://clojuredocs.org/clojure.core/cycle) (nova), [map](https://clojuredocs.org/clojure.core/map), [mapv](https://clojuredocs.org/clojure.core/mapv) revisitada. 

_Now we are going to use `mapv` to encrypt longer texts._
Agora nós vamos usar `mapv` para encriptar textos mais longos.

_To do that, we consider a slightly different example of using `mapv` (or `map`): we can use it with multiple sequences. The simplest example is:_
Para isso, consideremos um jeito um pouco diferente de como usar `mapv` (ou `map`): nós podemos usá-las com múltiplas sequências. O exemplo mais simples é:
```clojure
(mapv + [6 7 8] [3 2 1]) ; [9 9 9]
(mapv - [6 7 8] [3 2 1]) ; [3 5 7]
```
_Here `mapv` applies the given function (`+` in the first example, and `-` in the second case) to the first elements of the two given vectors, then to the second ones, etc._
Aqui `mapv` aplica a função dada (`+` no primeiro exemplo e `-` no segundo) aos primeiros elementos dos vetores, depois aos segundos, etc.

_Of course, we can use it with anonymous functions as well; the function must have two arguments:_
Claro, nós podemos usá-la com uma função anônima também; a função deve ter dois argumentos:
```clojure
(mapv #(if (< %1 %2) %1 %2) [2 4 5] [3 6 1]) ; [2 4 1]
```
_In this case the anonymous function returns the smaller of its two arguments. Thus, given `2` and `3`, it returns `2`, which is the first element of the result. Then given `4` and `6`, it returns `4`, etc._
Nesse caso a função anônima retorna o menor dos seus dois argumentos. Então, dados `2` e `3`, ela retorna `2`, que é o primeiro elemento do resultado. Depois, dados `4` e `6`, ela retorna `4`, etc.

_**Exercise:** Use `mapv` to compute the sum of squares of the elements of the given vectors. For instance, given `[1 2]` and `[3 1]`, it would return the vector `[10 5]`._
**Exercício:** Use `mapv` para computar a soma dos quadrados dos elementos dos vetores dados. Por exemplo, passando `[1 2]` e `[3 1]`, ela retornaria o vetor `[10 5]`.

_Here is a cool feature: if one the given sequences is longer than the other, the result is as long as the shorter sequence, and the remaining elements are ignored:_
Aqui uma funcionalidade legal: se uma das sequências passadas é maior do que a outra, o resultado tem o mesmo tamanho da sequência menor, e os elementos restantes são ignorados:
```clojure
(mapv + [6 7 8] (range)) ; [6 8 10]
```
_This allows us to use a `cycle` function to generate an infinite sequence of repeated keyword and then "add" it to text to be encrypted. `cycle` takes a sequence and creates an infinite lazy sequence by repeating the given sequence. For instance,_
Isso nos permite usar uma função `cycle` para gerar uma sequência infinita de chaves repetidas e então "adicionar" ela ao texto a ser encriptado. `cycle` recebe uma sequência e cria uma sequência lazy infinita ao repetir a sequência recebida. Por exemplo,  
```clojure
(def cycle1 (cycle [1 2 3])) ; infinite cycle
(take 10 cycle1) ; (1 2 3 1 2 3 1 2 3 1)
(def cycle2 (cycle "word")) 
crypto-solutions=> (take 10 cycle2) ; (\w \o \r \d \w \o \r \d \w \o)
```

_**Exercise:** Use the cycle of a keyword "`cipher"`, `mapv` (or `map`), and the `encrypt-letter` function that you wrote earlier to encrypt the string `"welcometoclojurebridge"`._
**Exercício:** Use a `cycle` com a chave `"cipher"`, `mapv` (ou `map`), e a função `encrypt-letter`que você escreveu mais cedo para encriptar a string `"welcometoclojurebridge"`._

_**Exercise:** Once you get it to work, write a function `vigenere-encrypt` that takes a text to encrypt and a keyword and performs the encryption. Just like in the Caesar cipher, you need to first use `get-letters` to get rid of other symbols and convert the letters to lowercase._
**Exercício:** Quando você conseguir fazer ela funcionar, escreva uma função `vigenere-encrypt` que recebe um texto para encriptar e uma chave e faz a encriptação. Assim como na cifra de César, você precisa primeiro usar a `get-letters` para retirar outros símbolos e converter as letras para minúsculo. 

_**Exercise:** Now write a function that takes an encrypted string and a keyword and performs the decryption. Note that you will be doing subtraction modulo 26 instead of the addition. 
<br />
Make sure that when you encrypt a string with a keyword, you can get it back (except in all lowercase letters and without punctuation) by decrypting it with the same keyword._
**Exercício:** Agora escreva uma função que recebe uma string encriptada e uma chave e faz a decriptação. Note que você vai fazer uma subtração módulo 26 ao invés de uma adição.
<br />
Garanta que quando você encriptar uma string com uma chave, você possa obtê-la de volta (com todas as letras minúsculas e sem pontuação) ao decriptá-la com a mesma chave.

_**Exercise:** Post an encryption (with a key) on slack. Try decrypting other people's encryptions._
**Exercício:** Poste um texto encriptado (com uma chave) no slack. Tente decriptar os textos de outras pessoas.
 
_## Breaking Vigenere cipher_
## Quebrando a cifra de Vigenère
_It seems like a Vigenere cipher doesn't allow frequency analysis since the same letter may be encrypted in multiple different ways. However, one can actually guess a key by a process that's just slightly more complicated than breaking a Caesar cipher. It also requires a longer text._
Parece que a cifra de Vigenère não permite análise de frequência já que a mesma letra pode ser encriptada de muitos jeitos diferentes. Na verdade, porém, é possível adivinhar uma chave através de um processo que é só um pouquinho mais complicado que quebrar uma cifra de César. Ele também precisa de um texto maior. 

_Suppose you know the length of the key. Let's say, the key is 5 letters long. That means that if I take letters in the encrypted text at positions 0, 5, 10, 15, etc., they are all encrypted with the same letter of the key, so they are all shifted by the same amount. The same is true for letters at the positions 1, 6, 11, 16, etc._
Suponha que você saiba o tamanho da chave. Digamos, a chave tem 5 letras. Isso significa que se eu pegar letras do texto encriptado nas posições 0, 5, 10, 15, etc., elas são todas encriptadas com a mesma letra da chave, então a todas elas são adicionadas o mesmo valor. O mesmo vale para letras nas posições 1, 6, 11, 16, etc.

_This means that you can do frequency analysis on each of these groups of letters, and determine a likely one-letter key  for each group exactly the same as you determined it for the Caesar cipher. Putting these one-letter keys together forms the keyword that you are trying to guess._
Isso significa que você pode fazer uma análise de frequência em cada um desses grupos de letras, e determinar uma possível chave de uma letra para cada grupo assim como você fez para a cifra de César. Juntar essas chaves de uma letra forma a palavra-chave que você está tentando adivinhar.

_But how do you know the length of the key??? It turns out that if you take letters in the encrypted string at the distance that equals to the length of the keyword (as we have done above), their frequencies are very uneven, much like the frequencies of letters in the English alphabet. However, if you take any other distance, frequencies tend to be more uniformly distributed (closer to random noise)._
Mas como saber o tamanho da chave??? Acontece que se você pegar letras da string encriptada a uma distância igual ao tamanho da chave (como fizemos acima), suas frequências são muito desiguais, muito como as frequências das letras no alfabeto inglês. Porém, se você pegar qualquer outra distância, as frequências tendem a ser mais uniformemente distrubuídas (como ruído aleatório).

_Armed with these ideas, you can now try it on the example given at the end of this section. Below we explain how various Clojure functions can be used for this task._
Com essas ideias em mente, você pode agora tentá-las no exemplo ao final dessa seção. Abaixo nós explicamos como várias funções de CLojure podem ser usadas para essa tarefa. 

_### Part 1: determine the length of the key_
### Parte 1: determine o tamanho da chave

_*Relevant functions:* [reduce](https://clojuredocs.org/clojure.core/reduce), [take-nth](https://clojuredocs.org/clojure.core/take-nth), [rest](https://clojuredocs.org/clojure.core/rest), [assoc](https://clojuredocs.org/clojure.core/assoc) (revisited)._
*Funções relevantes:* [reduce](https://clojuredocs.org/clojure.core/reduce), [take-nth](https://clojuredocs.org/clojure.core/take-nth), [rest](https://clojuredocs.org/clojure.core/rest), [assoc](https://clojuredocs.org/clojure.core/assoc) (revisitada).

_First of all, you need to use the `take-nth` function to make subsequences n steps apart. Here is how it works:_
Primeiramente, você precisa usar a função `take-nth` para criar subsquências de elementos com distância de n passos para frente:
```clojure
(take-nth 3 [1 2 3 4 5 6 7]) ; (1 4 7)
(take-nth 3 (rest [1 2 3 4 5 6 7])) ; (2 5)
```
_`rest` function allows you to take the every n-th element (every third in this example) starting at the second one. 
If you want to skip the first two, you can use `(rest (rest s))`, where `s` is a sequence._
A função `rest` te permite pegar todo n-ésimo elemento (todo terceiro, no exemplo) começando do segundo. Se você quiser pular os dois primeiros, você pode usar `(rest (rest s))`, onde `s` é uma sequência.

_Another way to start at a position other than the first one is to use `drop`. For instance, if you want to start at the third element, call `drop` with a parameter 2 to drop the first two elements._
Outro jeito de começar em uma posição diferente da primeira é usando `drop`. Por exemplo, se você quiser começar no terceiro elemento, chame `drop` com o parâmentro 2 para tirar os dois primeiros elementos.

_Once you have pulled the elements at every n-th position, upto a potential length of the key, you need to see if these frequencies match English frequencies._
Quando você tiver pegado os elementos em toda n-ésima posição, até o tamanho potencial da chave, você precisa ver se essas frequências combinam com as frequências inglesas.

_You can use the functions that you wrote for breaking Caesar cipher to compute a hashmap that shows how many times each letter occurs. They are not frequencies, however, just counts. In order to turn them into percentages, you need to divide each count by the total number of elements._
Você pode usar as funções que você escreveu para quebrar a cifra de César para computar um hashmap que mostra quantas vezes cada letra aparece. Isso não são frequências, entretando, apenas somas. Para transformá-las em porcentagens, você precisa dividir cada soma pelo número total de elementos.

_The function `count` gives you the number of elements in each subsequence. Now you can use the function `reduce` to take apart the hashmap and put it back together with the new values._
A função `count` the dá o número de elementos em cada subsequência. Agora você pode usar a função `reduce` para separar o hashmap e juntá-lo de novo com os novos valores.

_Read the description of reduce above, then take a look at the examples here._
Leia a descição da reduce acima, depois dê uma olhada nos exemplos aqui.

_In its simple form, `reduce` is used to compute a single result from a sequence. For instance, it can be used to add elements in a sequence together:_
Em sua forma simples, `reduce` é usado para computar um único resultado a partir de uma sequência. Por exemplo, ele pode ser usado para adicionar elementos de uma sequência:
```clojure
(reduce + [1 3 5]) ; 9
``` 
_It can be a bit more complicated if you want to have a starting point for your computation. For instance, I may want to subtract all my elements from 10:_
Pode ser um pouco mais complicado se você quiser um ponto de início. Por exemplo, eu posso querer subtrair todos os meus elementos de 10.
```clojure
(reduce - 10 [1 3 5]) ; 1
```
_10 here is used as a starting point, or a *seed* from which the result grows. The operation is then applied first to 10 and the first element, then to the result of it and the second element, and so on._
10 aqui é usado como um ponto de início, ou uma *seed* da qual o resultado cresce. A operação é então aplicada ao 10 e ao primeiro elemento do vetor, então ao resultado disso e ao segundo elemento do vetor, e assim por diante. 

_In our case we want to reduce a hashmap by changing each value (while keeping the same key) and associating it into the new hashmap. Here is an example that is very similar, but it increments the value, instead of dividing it by the total number of characters:_
No nosso caso, queremos reduzir um hashmap ao alterar cada valor (enquanto mantendo a mesma chave) e associando-o ao novo hashmap. Aqui está um exemplo que é muito similar, mas incrementa o valor, ao invés de dividí-lo pelo número total de caracteres:
```clojure
(reduce #(assoc %1 (first %2) (inc (second %2))) {} {"a" 2, "b" 5}) ; {"a" 3, "b" 6}
```
_Here we start with an empty hashmap as a seed. Then for each key/value pair in the hashmap (referred to as `%2` in the anonymous function) we keep the key (which is `(first %2)`) and increment the corresponding value (done by `(inc (second %2))`). Then we use `assoc` to add it to the new resulting hashmap._
Aqui nós começamos com um hashmap vazio como seed. Então para cada par de chave/valor no hashmap passado (referenciado como `%2` na função anônima) nós mantemos a chave (que é `(first %2)`) e incrementamos o valor correspondente (em `(inc (second %2))`). Então nós usamos `assoc` para adicionar o valor obtido ao hashmap resultante.

_This is a very complex use of `reduce`, so study it carefully and ask questions if you have any._
Esse é um uso muito complexo da `reduce`, então estude-o cuidadosamente e tire suas dúvidas se você tiver alguma.

_**Exercise:** modify the example above to divide each value of the given hashmap by the total number of letters in the subsequence, instead of incrementing it._
**Exercício:** modifique o exemplo acima para dividir cada valor do dado hashmap pelo número total de letras na subsequência, ao invés de incrementá-lo.

_Now you can use these functions to try different key lengths and see which of them gives letter frequencies more similar to English: [Relative frequencies of letters in the English language (wikipedia)](https://en.wikipedia.org/wiki/Letter_frequency#Relative_frequencies_of_letters_in_the_English_language) Once you are reasonably sure that you got the right key length, you can try to determine its letters._
Agora você pode usar essas funções para tentar diferentes tamanhos de chaves e ver qual delas dá frequências de letras mais similares ao inglês: [Relative frequencies of letters in the English language (wikipedia)] (https://en.wikipedia.org/wiki/Letter_frequency#Relative_frequencies_of_letters_in_the_English_language). Uma vez que você esteja razoavelmente certa de que você achou o tamanho certo da chave, você pode tentar determinar suas letras.

_### Part 2: frequency analysis on the subsequences_
### Parte 2: análise de frequência nas subsequências

_Once you have obtained subsequencies of every N-th letter, where N is the likely length of the key, you can use the functions you wrote for breaking Caesar cipher to determine each letter of the key separately._
Quando você obter subsequências de todas as N-ésimas letras, onde N é o provável tamanho da chave, você pode usar as funções que você escrever para quebrar a cifra de César para determinar cada letra da chave separadamente.

_The only problem is that you will not know if you are right until you guess the entire key, so you would have to play a bit of a guessing game putting letters together._
O único problema é que você não vai saber se você está certa até você adivinhar a chave inteira, então você terá que brincar um pouco de adivinhação juntando as letras.

_### Part 3: Vigenere challenge_
### Parte 3: desafio de Vigenère

_Here is an example for you to try to break. The key is between two and eight characters long. It is a word and is related to the topic of this workshop._
Aqui está um exemplo para você tentar quebrar. A chave tem entre dois e oito caracteres. É uma palavra e está relacionada ao tópico deste workshop.

_Be patient, this may take time. Try to automate you process as much as possible. We also recommend that you work in pairs._
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

_## Are we done?_
## Terminamos?
_This was a long day! However, I do recommend that now you take a few minutes and go back to the overview page and read the description of Clojure and functional programming again. Hopefully a lot of this now would be something that you can relate to today's experience._
Esse foi um longo dia! Porém, eu recomendo que você agora tire uns minutos e volte para a página de overview para ler a descrição de Clojure e programação funcional de novo. Agora muito do que que está lá deve fazer mais sentido com a experiência de hoje.

_#### Thanks for attending ClojureBridge!!!_
### Obrigada por comparecer ao ClojureBridge!!!

**Voltar ao começo:** [Setup do Projeto](1-setup.md)
<br />
**Anterior:** [Quebrando a cifra de César](5-frequency.md)
