(ns cipher.core-test
  (:require [cipher.core :as core]
            [midje.sweet :refer :all]))

(fact "this will fail"
      1 => 1)

(fact "def test"
      core/answer => 42)

(fact "func test"
      (core/f 10 20) => 130)

(fact "teste exercicio 1"
      (core/g 1 2) => 12)

(fact "teste exercicio 2"
      (core/to-char 3) => \d)

(fact "teste exercício 3"
      (tabular
        (core/shift ?char ?number) => ?result
        ?char ?number ?result
        \a    3        \d
        \b    20       \v
        \z    3        \c))

(fact "teste exercício 4"
      (core/to-negative [1 2 -3]) => [-1 -2 3])

(fact "teste exercicio 5"
      (core/caesar-encrypt "ana" 1) => "bob")

(fact "teste exercicio 6"
      (core/caesar-decrypt "ujjfy" 20) => "apple")

(fact "teste exercicio 7"
      (core/just-letters "Hello ??o+u*") => "Helloou")

(fact "teste exercicio 8"
      (core/count-letters \a "ana") => 2)

(def encr1 "radyjgtxhpsncpbxrvtctgpaejgedhtegdvgpbbxcvapcvjpvtrdbqxcxcv
iwtpeegdprwpqxaxinpcsxcitgprixktstktadebtciduphrgxeixcvapcvjp
vtlxiwpctuuxrxtcipcsgdqjhixcugphigjrijgtudgbjaixiwgtpstsegdvg
pbbxcvo")

(fact "teste exercicio 9"
        (core/map-count encr1) => {\a 7, \b 8, \c 16, \d 10, \e 8, \f 0, \g 16, \h 5, \i 13, \j 8, \k 2, \l 1, \m 0,
                                   \n 2, \o 1, \p 19, \q 3, \r 8, \s 6, \t 17, \u 5, \v 11, \w 4, \x 17, \y 1, \z 0})

(fact "teste exercicio 10"
      (core/sort-hashmap {\a 7, \b 8, \c 16, \d 10, \e 8, \f 0, \g 16, \h 5, \i 13, \j 8, \k 2, \l 1, \m 0, \n 2, \o 1,
                          \p 19, \q 3, \r 8, \s 6, \t 17, \u 5, \v 11, \w 4, \x 17, \y 1, \z 0})
      => [[\p 19] [\t 17] [\x 17] [\c 16] [\g 16] [\i 13] [\v 11] [\d 10] [\b 8] [\e 8] [\j 8] [\r 8] [\a 7] [\s 6]
          [\h 5] [\u 5] [\w 4] [\q 3] [\k 2] [\n 2] [\l 1] [\o 1] [\y 1] [\f 0] [\m 0] [\z 0]])

(fact "teste exercicio 11"
      (core/take-occuring {\a 7, \b 8, \c 16, \d 10, \e 8, \f 0, \g 16, \h 5, \i 13, \j 8, \k 2, \l 1, \m 0, \n 2, \o 1,
                           \p 19, \q 3, \r 8, \s 6, \t 17, \u 5, \v 11, \w 4, \x 17, \y 1, \z 0})
      => [[\p 19] [\t 17] [\x 17]])

(fact "teste exercicio 12"
      (core/formula encr1 \p \e) => "clojureisadynamicgeneralpurposeprogramminglanguagecombiningctheapproachabilityandinteractivedevelopmentofascriptinglanguacgewithanefficientandrobustinfrastructureformultithreadedprogrcammingz")


