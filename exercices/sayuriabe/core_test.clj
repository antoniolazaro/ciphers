(ns cipher.core-test
  (:require [midje.sweet :refer :all]
            [cipher.core :as core]))

(fact "this will fail"
      1 => 1)

(facts "takes a lowercase letter character and returns its position in the alphabet: a = 0, b = 1, etc"
       (tabular
         (core/to-int ?char) => ?result
         ?char ?result
         \a    0
         \b    1
         \c    2
         \d    3))

(facts "takes a lowercase letter character and returns its position in the alphabet: a = 0, b = 1, etc"
       (tabular
         (core/to-char ?int) => ?result
         ?int ?result
         0  \a
         1  \b
         2  \c
         3  \d    ))

(fact "shift"
      (core/shift \a 3) => \d
      (core/shift \b 20) => \v)

(fact "test map"
      (core/caesar-encrypt "apple" 20) => "ujjfy")

(fact "decrypt"
      (core/caesar-decrypt "ujjfy" 20) => "apple")

(fact "get letters"
      (core/get-letters "Hello, friend!") => "hellofriend")

(fact "enc-letters"
      (core/enc-letters "Hello, friend!" 5) => "mjqqtkwnjsi")

(fact "count letters"
      (core/count-letters \a "aadvark") => 3)

(fact "ocorrencias"
      (core/ocurrency) = > {\a 7, \b 8, \c 16, \d 10, \e 8, \f 0, \g 16, \h 5, \i 13, \j 8, \k 2, \l 1, \m 0, \n 2, \o 1, \p 19, \q 3, \r 8, \s 6, \t 17, \u 5, \v 11, \w 4, \x 17, \y 1, \z 0})

(fact "maior"
      (core/highest) => '([\p 19] [\t 17] [\x 17]))

(fact "key"
      (core/findKey \p \e))


(fact "key"
      (core/findKey2 \e \p 11))

