;from core_test.clj by Larissa Gaulia

(ns cipher.core-test
  (:require [cipher.core :as core]
            [midje.sweet :refer :all]))

(fact "this will fail"
      1 => 1)

(facts "takes a lowercase letter character and returns its position in the alphabet: a = 0, b = 1, etc"
       (fact "character a is the first letter, in position 0"
             (core/to-int \a) => 0)
       (fact "character b is the second letter, in position 1"
             (core/to-int \b) => 1))
       (fact "coerces float to int"
            (core/to-int 97.3) => 0)

(facts "takes a position and returns the alphabet letter"
       (tabular
         (core/to-char ?position) => ?result
         ?position ?result
         0    \a
         1    \b
         2    \c
         3    \d))

(fact "ceaser cipher letter"
      (fact "a to d"
            (core/shift \a 3) => \d)
      (fact "b to v"
            (core/shift \b 20) => \v)
      (fact "z to c"
            (core/shift \z 3) => \c))

(fact "caesar cipher word"
      (fact "apple"
            (apply str (mapv #(core/shift % 20) "apple")) => "ujjfy"))

(fact "caesar-encrypt"
      (fact "encrypts any word with special chars"
            (core/caesar-encrypt "apple" 20) => "ujjfy")
      (fact "encrypts any word without special chars"
            (core/caesar-encrypt-no-special-char "Hello, friend!" 5) => "mjqqtkwnjsi"))

(fact "caesar-decrypt"
      (fact "decrypts any word"
            (core/caesar-decrypt "ujjfy" 20) => "apple")
      (fact "decrypts no spacial chars words"
            (core/caesar-decrypt (core/caesar-encrypt-no-special-char "Hello, friend!" 5) 5) => "hellofriend"))

(fact "get-letters"
      (fact "Hello, friend! to hellofriend"
            (core/get-letters "Hello, friend!") => "hellofriend"))

(fact "takes a char string and counts the number of letters"
      (fact "3 letters a in ..."
            (core/count-letters \a "aadvark") => 3)
      (fact "0 letters x in ..."
            (core/count-letters \x "aadvark") => 0))

(def encr1 "radyjgtxhpsncpbxrvtctgpaejgedhtegdvgpbbxcvapcvjpvtrdbqxcxcv
iwtpeegdprwpqxaxinpcsxcitgprixktstktadebtciduphrgxeixcvapcvjp
vtlxiwpctuuxrxtcipcsgdqjhixcugphigjrijgtudgbjaixiwgtpstsegdvg
pbbxcvo")

(fact "constructs a frequency hashmap"
      (core/construct-hashmap encr1) => {\a 7, \b 8, \c 16, \d 10, \e 8, \f 0, \g 16, \h 5, \i 13, \j 8, \k 2, \l 1, \m 0, \n 2, \o 1, \p 19, \q 3, \r 8, \s 6, \t 17, \u 5, \v 11, \w 4, \x 17, \y 1, \z 0})

(fact "sorts a hashmap by its values"
      (core/sort-map (core/construct-hashmap encr1)) => (list [\p 19] [\t 17] [\x 17] [\c 16] [\g 16] [\i 13] [\v 11] [\d 10] [\b 8] [\e 8] [\j 8] [\r 8] [\a 7] [\s 6] [\h 5] [\u 5] [\w 4] [\q 3] [\k 2] [\n 2] [\l 1] [\o 1] [\y 1] [\f 0] [\m 0] [\z 0]))

(fact "take first 3 elements of map"
      (core/take-three (core/sort-map (core/construct-hashmap encr1))) => (list [\p 19] [\t 17] [\x 17]))

(fact "gets potential key"
      (core/get-potential-key \e \p) => 11)

(fact "decrypts encr1"
      (core/caesar-decrypt encr1 (core/get-potential-key \a \p)) => "clojureisadynamicgeneralpurposeprogramminglanguagecombiningctheapproachabilityandinteractivedevelopmentofascriptinglanguacgewithanefficientandrobustinfrastructureformultithreadedprogrcammingz")

(def encr2 "ahixblmaxmabgzpbmayxtmaxklmatmixkvaxlbgmaxlhnetgwlbgzlmaxmngxpbmahnmmaxphkwltgwgxoxklmhiltmtee")
(fact "decrypts encr2"
      (core/caesar-decrypt encr2 (core/get-potential-key \a \t)) => "hopeisthethingwithfeathersthatperchesinthesoulandsingsthetunewithoutthewordsandneverstopsatall")

(fact "encrypt-letter"
      (core/encrypt-letter  \c \w) => \y)
