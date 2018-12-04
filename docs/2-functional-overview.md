# Visão Geral de Clojure.

## Princípios de linguagens funcionais

Clojure é uma linguagem funcional. Isso significa que no lugar de 
atualizar variáveis em memória, programas funcionam através de composição de funções. Funções em uma linguagem funcional são tratadas como os outros elementos da linguagem (números, strings, etc.), e.g. elas podem ser armazenadas em uma variável, passadas para outras funções, retornadas de outras funções e até criadas em tempo de execução. Isso é geralmente referenciado como *funções sendo cidadãos de primeira classe na linguagem*. 

Variáveis e estruturas de dados em Clojure são imutáveis por padrão, e.g. elas nunca mudam localmente. Toda vez que uma modificação é necessária um novo objeto é criado. Por exemplo, se você adiciona um elemento a uma lista, uma nova lista é criada com o novo elemento adicionado e a antiga lista permanece a mesma. 

Isso pode parecer uma abordagem ineficiente, mas por trás há um compartilhamento muito eficiente das partes que não mudaram e que são invisíveis para o programador. Vetores de Clojure são em particular eficientes nesse sentido. 

O benefício da imutabilidade consiste na facilidade de saber exatamente o que está acontecendo em um programa já que todo objeto referenciado é sempre o mesmo desde o momento de sua criação. Isso também facilita a inserção de concorrência uma vez que há muito menos necessidade de acompanhar atualizações simultâneas, consequentemente muito menos necessidade de bloquear objetos. Na verdade, um dos principais objetivos de design de Clojure era facilitar programação concorrente com o mínimo de bloqueio. 

## Clojure e Java

Clojure é compilado em bytecode Java e executado em Java Virtual Machine (JVM). Existe uma maneira conveniente de chamar métodos Java e usar bibliotecas Java em Clojure. Isso significa que Clojure pode usar qualquer funcionalidade Java predefinida.

Como Clojure é implementado sobre Java, seus objetos (números, strings, listas, etc.) são implementados como tipos de dado Java. Injelizmente, isso também significa que as mensagens de erro de Clojure são exceções Java e referem-se aos tipos de dados Java. Isso as tornam menos compreensíveis para aqueles que não estão familiarizados com Java. Nós vamos tentar ajudá-la a navegar pelas mensagens de erro caso elas se tornem confusas. 

## Sintaxe Clojure 

Clojure é uma linguagem na família Lisp. Linguagens Lisp são funcionais e dinamicamente tipadas com uma sintaxe simples e uniforme. Em linguagens Lisp existem palavras-chave (como o `def` de Clojure para definir uma variável) e nenhuma operação pré-definida: mesmo operações aritiméticas como `+`, `-`, etc. são funções em Clojure.

A sintaxe Clojure segue a notação de prefixo: uma expressão Clojure é envolvida com parênteses, o primeiro elemento entre os parênteses é o nome da função e os que seguem são os argumentos da função:
```clojure
(+ 2 5)
```
é a chamada da função `+` com os argumentos `2` e `5`, que retorna `7`. 

Muitas funções em Clojure recebem um número variável de argumentos: 
```clojure
(+ 2 5 3)
```
passa três argumentos para `+` e retorna `10`. 

Como funções em Clojure são cidadãos de primeira classe, elas podem ser passadas para outras funções. Por exemplo,
```clojure
(filter odd? [2 3 -1 8])
```
é uma chamada para a função `filter` que recebe uma função que retorna valores true/false e uma coleção de elementos, e retorna uma nova coleção contendo apenas os elementos para os quais a função retornou `true`. Neste caso, apenas os elementos `3` e `-1` estarão na coleção resultante. 

Todas as linguagens possuem suas próprias convenções de nomenclatura e formatação. Clojure (e outras linguagens Lisp) usa as seguintes:

* Variáveis com mais de uma palavra usam traços, não underscores ou camel-casing: `encontra-menor-chave`.
* Nomes de funções tendem a ser verbos, não substantivos: `take`, `reduce`, etc.
* Nomes de funções que retornam valores booleanos (true/false) terminam com um ponto de interrogação: `odd?`, `number?`. 
* Parênteses fecham na última linha da expressão aninhada, como na seguinte definição:
```clojure
(defn quadrado
  [x]
  (* x x))
```

Comentários começam com `;` e vão até o final da linha:
```clojure
(def n 10) ; n é o número de elementos
```
Tradicionalmente nós usamos `;;` para começar um comentário que ocupa uma linha inteira.

Para mais informações sobre o estilo Clojure consulte [O Guia de Estilo Clojure](https://github.com/rodriguescelio/clojure-style-guide/blob/pt-BR/README.md) (e você pode querer revisitar este link depois de concluir essa lição). 

## ClojureDocs

Clojure possui uma excelente documentação que foi montada pelos programadores Clojure: [ClojureDocs: community-powered documentation and examples repository](https://clojuredocs.org/)

Maior parte das funções que você irá utilizar estão listadas no [Guia de referência rápida do ClojureDocs](https://clojuredocs.org/quickref). A lição provê links para as funções utilizadas em cada subseção.

A documentação para cada função vem com uma lista de exemplos, funções relacionadas no fundo da página e até alguns comentários e discussões. Exemplos possuem casos de exemplos mais comuns e alguns casos mais específicos (se aplicáveis).

Nós recomendamos que você tire um tempo para olhar para os primeiros exemplos - e talvez até tentá-los! É bem comum ter várias abas do navegador abertas com descrições de diferentes funções.

ClojureDocs são muito úteis, por favor utilize-os!

## Sobre essa lição

Esta lição foi projetada para ser uma introdução independente ao Clojure utilizando alguns exemplos divertidos de criptografia. Ele irá passar pelas funções mais utilizadas em Clojure, te dará chance de escrever suas próprias funções através de uma série de exercícios sugeridos, e finalmente te dará um exemplo de encriptação para tentar quebrar (i.e. tentar achar a chave e tentar decriptografar) utilizando as ferramentas que você aprendeu.

Apesar dos exemplos serem apenas exemplos de "brincadeira", e não uma encriptação da vida real, as funcionalidades de Clojure e abordagens que você aprenderá serão úteis para qualquer análise e processamento de dados.

Nós te encorajamos a:

1. Utilize esta lição como um guia para o seu aprendizado, não um conjunto rígido de passos. Leve quanto tempo for necessário para explorar o material, você não precisa finalizá-lo ou alcançar algum sprint particular.
2. Experimente as coisas. Clojure REPL é uma boa maneira de brincar com alguns exemplos.
3. Faça perguntas. Mentores estão aí para responder suas perguntar ou explicar resultados que possam parecer confusos ou discutir diferentes abordagens para o seu problema.
4. Você provavelmente irá querer trabalhar em pares, especialmente nas partes posteriores da lição.

**Próximo:** [Tipos de dados e funções em Clojure](3-functions.md)
<br />
**Anterior:** [Setup do Projeto](1-setup.md)
