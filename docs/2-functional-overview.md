# Visão Geral de Clojure.

## Principles of functional languages

Clojure is a functional language. This means that rather 
than updating variables in memory, programs work by function composition. Functions in a functional language are treated as other elements of the language (numbers, strings, etc.), i.e. they can be stored in a variable, passed to other functions, returned from other functions, and even created at run time. This is often refers to as *functions being first class citizens in the language*. 

Variables and data structures in Clojure are immutable by default, i.e. they never change in place. Every time a modification is needed, a new object is created. For instance, if you add an element to a list, a new list is created with the new element added, and the old list stays the same. 

This may seem like an inefficient approach, but underneath there is a very efficient sharing of the parts that didn't change, but it's invisible to the programmer. Clojure vectors are in particular efficient in this sense. 

The benefit of immutability is that it is easier to know exactly what's happening in a program since every object that a program references is always the same as when it was originally created. This also makes it easier to incorporate concurrency since there is much less of a need to keep track of simultaneous updates, so much less of a need to lock objects. In fact, one of the main design goals of Clojure was the ease of concurrent programming with minimal locking. 

## Clojure and Java

Clojure compiles into Java bytecode and runs on Java Virtual Machine (JVM). There is a convenient way to call Java methods and use Java libraries in Clojure. This means that Clojure can use any predefined Java functionality. 

Since Clojure is implemented on top of Java, Clojure objects (numbers, strings, lists, etc.) are implemented as Java datatypes. 
Unfortunately, this also means that Clojure error messages are Java exceptions and refer to Java datatypes. This makes them less understandable to those not familiar with Java. We will try to help you navigate through error messages if they become confusing. 

## Clojure Syntax 

Clojure is a language in the Lisp family. Lisp languages are functional, dynamically typed languages with a simple uniform syntax. In Lisp languages there are a handful of keywords (such as Clojure `def` for defining a variable) and no predefined operations: even arithmetic operations, such as `+`, `-`, etc. are functions in Clojure. 

Clojure syntax follows prefix notation: a Clojure expression is surrounded with parentheses, the first element in the parentheses is a function name, and what follows is the function arguments:
```clojure
(+ 2 5)
```
is the call to a function `+` with the arguments `2` and `5`, which returns `7`. 

Many functions in Clojure take a variable number of arguments: 
```clojure
(+ 2 5 3)
```
passes three arguments to `+`, and returns `10`. 

Since functions in Clojure are first-class citizens, they can be passed to other functions. For instance,
```clojure
(filter odd? [2 3 -1 8])
```
is a call to a function `filter` that takes a function that returns true/false values and a collection of elements, and returns a new collection that only contains the elements for which the function returned `true`. In this case, only the elements `3` and `-1` will be in the resulting collection. 

All languages have their own naming and formatting conventions. Clojure (and other Lisp languages) use the following:

* Multi-word variable names use dashes, not underscores or camel-casing: `find-min-key`.
* Function names tend to be verbs, not nouns: `take`, `reduce`, etc.
* Names of functions that return boolean (true/false) values end with a question mark: `odd?`, `number?`. 
* Parentheses all close on the last line of a nested expression, as in the follwoing function definition: 
```clojure
(defn square
  [x]
  (* x x))
```

Comments start with `;` and go until the end of the line:
```clojure
(def n 10) ; n is the number of elements
```
Traditionally we use `;;` to start a comment that takes the 
entire line. 

For more on Clojure style please see [The Clojure Style Guide](https://github.com/bbatsov/clojure-style-guide) (and you might want to revisit this link after you are done with this lesson). 

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
