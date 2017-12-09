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
             (core/to-int \b) => 1))

(facts "takes a lowercase letter character and returns its position in the alphabet: a = 0, b = 1, etc"
       (tabular
         (core/to-int ?char) => ?result
         ?char ?result
         \a    0
         \b    1
         \c    2
         \d    3))

(facts "takes a number between 0 and 25 (inclusive) and returns the corresponding lowercase letter"
       (tabular
         (core/to-char ?n) => ?result
         ?n ?result
          3    \d
          4    \e
          5    \f
          6    \g))

(facts "takes a letter and a number and return another letter"
       (tabular
         (core/shift ?char ?n) => ?result
         ?char ?n ?result
         \a     3    \d
         \b     20   \v
         \z     3    \c
         \c     -2   \a))

(facts "encrypt a word by shifting the alphabet by some number"
       (tabular
         (core/ceasar-encrypt ?string ?k) => ?result
         ?string ?k ?result
         "apple" 20  "ujjfy"
         "apple"  3  "dssoh"))

(facts "decrypt a word by shifting the alphabet by some number"
       (tabular
         (core/ceasar-decrypt ?string ?k) => ?result
         ?string ?k ?result
         "ujjfy" 20  "apple"
         "dssoh"  3  "apple"
         "gtxyts" 5  "boston"
         "mvytebolbsnqo"  10  "clojurebridge"))

(fact "takes a string with any symbols in it, and returns a string of of only letters in it"
      (tabular
        (core/get-letters ?string) => ?result
        ?string  ?result
        "Hello, friend!"  "hellofriend"
        "Hi! How are you?"  "hihowareyou"))

(fact "encrypt a string"
      (tabular
        (core/encrypt ?string ?key) => ?result
        ?string ?key ?result
        "Hello, friend!" 5 "mjqqtkwnjsi"
        "Hi! How are you?" 6 "nonucgxkeua"))

(fact "count letter"
      (tabular
        (core/count-letters ?char ?string) => ?result
        ?char ?string ?result
        \a     "aadvark" 3
        \e  "eitaleleieee" 6))

(fact "count ALL letters"
      (tabular
        (core/counter-letters ?string) => ?result
        ?string ?result
        "radyjgtxhpsncpbxrvtctgpaejgedhtegdvgpbbxcvapcvjpvtrdbqxcxcv
iwtpeegdprwpqxaxinpcsxcitgprixktstktadebtciduphrgxeixcvapcvjp
vtlxiwpctuuxrxtcipcsgdqjhixcugphigjrijgtudgbjaixiwgtpstsegdvg
pbbxcvo" {\a 7, \b 8, \c 16, \d 10, \e 8, \f 0, \g 16, \h 5, \i 13, \j 8, \k 2,
                  \l 1, \m 0, \n 2, \o 1, \p 19, \q 3, \r 8, \s 6, \t 17, \u 5,
                  \v 11, \w 4, \x 17, \y 1, \z 0}
        "abracadabra" {\a 5, \b 2, \c 1, \d 1, \e 0, \f 0, \g 0, \h 0, \i 0, \j 0,
                       \k 0, \l 0, \m 0, \n 0, \o 0, \p 0, \q 0, \r 2, \s 0, \t 0,
                       \u 0, \v 0, \w 0, \x 0, \y 0, \z 0}))

(fact "order counter"
      (let [hashhh {\a 7, \b 8, \c 16, \d 10, \e 8, \f 0, \g 16, \h 5, \i 13, \j 8,
                    \k 2, \l 1, \m 0, \n 2, \o 1, \p 19, \q 3, \r 8, \s 6, \t 17,
                    \u 5, \v 11, \w 4, \x 17, \y 1, \z 0}]
        (core/ordered-counter hashhh) => [[\p 19] [\t 17] [\x 17] [\c 16] [\g 16] [\i 13]
                                          [\v 11] [\d 10] [\b 8] [\e 8] [\j 8] [\r 8] [\a 7]
                                          [\s 6] [\h 5] [\u 5] [\w 4] [\q 3] [\k 2] [\n 2]
                                          [\l 1] [\o 1] [\y 1] [\f 0] [\m 0] [\z 0]]
        ))

(fact "decrypt text"
      (let [text "ahixblmaxmabgzpbmayxtmaxklmatmixkvaxlbgmaxlhnetgwlbgzlmaxmngxpbmahnmmaxphkwltgwgxoxklmhiltmtee"]
        (core/mastered-decrypt text \t)
        => (contains "hopeisthethingwithfeathersthatperchesinthesoulandsingsthetunewithoutthewordsandneverstopsatall")))