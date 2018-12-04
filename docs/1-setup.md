# Setup do Projeto

1) Criar um novo projeto clojure com [leiningen](https://leiningen.org)

Criar um diretório para o projeto e nele executar no terminal:
`$ lein new cipher`

2) Abrir/Importar o projeto no [IntelliJ] https://www.jetbrains.com/idea/download/

3) Editar o arquivo project.clj para adicionar o [`midje`](https://github.com/marick/Midje) como dependência do projeto. E adicionar o plugin `lein-midje` para executarmos os testes.

``` clojure
(defproject cipher "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-midje "3.2.1"]]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [midje "1.9.0"]])

```

4) Atualizar dependencias no IntelliJ (para que o editor consiga fazer autocomplete e o highlight corretamente do código)

![screen shot 2017-12-05 at 19 01 52](https://user-images.githubusercontent.com/1187561/33630613-252c3468-d9ef-11e7-8544-64096a70f20d.png)

5) Editar o arquivo `core_test.clj` e adicionar o midje na lista de required namespaces.

``` clojure
(:require [cipher.core :as core]
          [midje.sweet :refer :all])
```

> Obs. Note que mudamos aqui também o require do namespace **cipher.core** de `[cipher.core :refer :all]` para `[cipher.core :as core]`.
Com isso criamos um _alias_ para este namespace, esta é uma boa prática pois evita o conflito de funções além de ajudar na leitura e organização do código.

6) No diretório do seu projeto, execute no terminal

```$ lein midje :autotest```

> O autotest vai executar os testes a cada mudança no código :) 

7) Adicionar um novo teste no arquivo core_test.clj com o Midje (e remover o teste do clojure.test gerado)

``` clojure
(fact "this will fail"
  1 => 2)

```

No seu terminal veja que o novo teste já foi executado e deve ter um resultado como:
```
FAIL "this will fail" at (core_test.clj:7)
Expected:
2
Actual:
1
```

> **Faça esse teste passar :)**

