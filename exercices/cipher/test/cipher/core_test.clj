(ns cipher.core-test
  (:require [clojure.test :refer :all]
            [cipher.core :as core]
            [midje.sweet :refer :all]))

(facts "takes a lowercase letter character and returns its position in the alphabet: a = 0, b = 1, etc"
       (tabular
         (core/to-int ?char) => ?result
         ?char ?result
         \a    0
         \b    1
         \c    2
         \d    3))

(facts "takes a number and returns the corresponding lowercase letter"
       (tabular
         (core/to-char ?int) => ?result
         ?int ?result
         0    \a
         1    \b
         2    \c
         25   \z))

(facts "shifts a letter by a given number"
       (tabular
         (core/shift ?char ?number) => ?mask-char
         ?char ?number ?mask-char
         \a    3       \d
         \b    20      \v
         \z    3       \c))

(fact "encrypts a word with a key using Caesar cipher"
      (core/caesar-encrypt "apple" 20) => "ujjfy")

(facts "decrypts a secret word according to the key"
       (tabular
         (core/caesar-decrypt ?word ?key) => ?decrypted-word
         ?word           ?key ?decrypted-word
         "ujjfy"         20   "apple"
         "gtxyts"        5    "boston"
         "mvytebolbsnqo" 10   "clojurebridge"
         "mjqqtkwnjsi"   5    "hellofriend"))

(fact "gets only letters and make it lower case"
      (core/get-letters "Hello, friend!") => "hellofriend")

(fact "encrypts complete texts with a key"
      (core/encrypt-text "Hello, friend!" 5) => "mjqqtkwnjsi")

(facts "counts the number of occurrences of a letter in a text"
      (tabular
        (core/count-letters ?letter ?text) => ?count-result
        ?letter ?text     ?count-result
        \a      "aadvark" 3
        \x      "aadvark" 0))

(fact "creates a hashmap with alphabets letters' frequency in a text"
      (core/count-each-letter core/encr1)
      => {\a 7, \b 8, \c 16, \d 10, \e 8, \f 0, \g 16, \h 5, \i 13, \j 8, \k 2,
          \l 1, \m 0, \n 2, \o 1, \p 19, \q 3,
          \r 8, \s 6, \t 17, \u 5, \v 11, \w 4, \x 17, \y 1, \z 0})

(fact "desc sort hash by second value"
      (let [frequencies {\a 7, \b 8, \c 16, \d 10, \e 8, \f 0, \g 16,
                         \h 5, \i 13, \j 8, \k 2, \l 1, \m 0, \n 2, \o 1, \p 19, \q 3,
                         \r 8, \s 6, \t 17, \u 5, \v 11, \w 4, \x 17, \y 1, \z 0}]
        (core/sort-frequencies frequencies) => [[\p 19] [\t 17] [\x 17] [\c 16] [\g 16] [\i 13] [\v 11] [\d 10] [\b 8]
                                                [\e 8] [\j 8] [\r 8] [\a 7] [\s 6] [\h 5] [\u 5] [\w 4] [\q 3] [\k 2]
                                                [\n 2] [\l 1] [\o 1] [\y 1] [\f 0] [\m 0] [\z 0]]))

(fact "takes the three highest occurring elements"
      (let [frequencies {\a 7, \b 8, \c 16, \d 10, \e 8, \f 0, \g 16,
                         \h 5, \i 13, \j 8, \k 2, \l 1, \m 0, \n 2, \o 1, \p 19, \q 3,
                         \r 8, \s 6, \t 17, \u 5, \v 11, \w 4, \x 17, \y 1, \z 0}]
        (core/highest-occurring-elements frequencies) => [[\p 19] [\t 17] [\x 17]]))

(fact "calculates a key given two corresponding letters"
      (core/calculate-potential-key \d \a) => 3)

(fact "decrypts something that makes sense"
      (core/try-decryption core/encr1 \t \e)
      => "clojureisadynamicgeneralpurposeprogramminglanguagecombiningctheapproachabilityandinteractivedevelopmentofascriptinglanguacgewithanefficientandrobustinfrastructureformultithreadedprogrcammingz")