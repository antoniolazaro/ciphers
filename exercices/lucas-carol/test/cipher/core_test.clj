(ns cipher.core-test
  (:require [clojure.test :refer :all]
            [cipher.core :as core]
            [midje.sweet :refer :all]))

(fact "this will fail"
      2 => 2)

(facts "takes a lowercase letter character and returns its position in the alphabet: a = 0, b = 1, etc"
       (fact "character a is the first letter, in position 0"
             (core/to-int \a) => 0)
       (fact "character b is the second letter, in position 1"
             (core/to-int \b) => 1)

       (tabular
         (core/to-int ?char) => ?result
         ?char ?result
         \a 0
         \b 1
         \c 2
         \d 3)
       )

(facts "takes a number and returns the letter by position in the alphabet: 0 = a, 1 = b, etc"
       (fact "position 0 returns a"
             (core/to-char 0) => \a)
       )

(facts "cipher a letter"
       (tabular
         (core/shift ?char ?key) => ?result
         ?char ?key ?result
         \a 3 \d
         \z 1 \a)
       )

(facts "cipher a text"
       (tabular
         (core/caesar-encrypt ?char ?key) => ?result
         ?char ?key ?result
         "abc" 7 "hij"
         "apple" 20 "ujjfy"
         "Hello, friend!" 5 "mjqqtkwnjsi"
         )
       )

(facts "decipher a text"
       (tabular
         (core/caesar-decrypt ?text ?key) => ?result
         ?text ?key ?result
         "hij" 7 "abc"
         "ujjfy" 20 "apple"
         "gtxyts" 5 "boston"
         "mvytebolbsnqo" 10 "clojurebridge"

         )
       )

(def bigWord "radyjgtxhpsncpbxrvtctgpaejgedhtegdvgpbbxcvapcvjpvtrdbqxcxcviwtpeegdprwpqxaxinpcsxcitgprixktstktadebtciduphrgxeixcvapcvjpvtlxiwpctuuxrxtcipcsgdqjhixcugphigjrijgtudgbjaixiwgtpstsegdvgpbbxcvo")
(def bigWordCount
  {\a 7, \b 8, \c 16, \d 10, \e 8, \f 0, \g 16, \h 5, \i 13, \j 8, \k 2, \l 1, \m 0,
   \n 2, \o 1, \p 19, \q 3, \r 8, \s 6, \t 17, \u 5, \v 11, \w 4, \x 17, \y 1, \z 0})

(def aadvarkCount
  {\a 3, \b 0, \c 0, \d 1, \e 0, \f 0, \g 0, \h 0, \i 0, \j 0, \k 1, \l 0, \m 0,
   \n 0, \o 0, \p 0, \q 0, \r 1, \s 0, \t 0, \u 0, \v 1, \w 0, \x 0, \y 0, \z 0})

(fact "remove non-letter characters and make it lowercase"
      (core/get-letters "Hello, friend!") => "hellofriend"
      )

(facts "count letters"
       (fact "one letter in a text"
             (core/count-letters \a "aadvark") => 3
             )
       (fact "all letters in a text"
             (tabular
               (core/count-all-letters ?text) => ?result
               ?text ?result
               "aadvark" aadvarkCount
               bigWord bigWordCount
               )
             )
       )
