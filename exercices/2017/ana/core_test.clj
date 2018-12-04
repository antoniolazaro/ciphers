(ns cipher.core-test
  (:require [cipher.core :as core]
            [midje.sweet :refer :all]))

;(fact "this will pass"
;      2 => 2)

(fact "to-int"
    (tabular
      (core/to-int ?char) => ?result
        ?char ?result
        \a    0
        \b    1
        \c    2
        \d    3
        \e    4))

(fact "to-char"
    (tabular
      (core/to-char ?pos) => ?result
        ?pos ?result
        0    \a
        1    \b
        2    \c
        3    \d
        9    \j
      ))

(fact "shift"
    (tabular
      (core/shift ?char ?n) => ?result
        ?char  ?n  ?result
        \a     2   \c
        \f     3   \i
        \b     -1  \a
        \z     3   \c
      ))

(fact "caesar-encrypt"
      (core/caesar-encrypt "apple" 20) => "ujjfy"
      (core/caesar-encrypt "Hello, friend!" 5) => "mjqqtkwnjsi")

(fact "caesar-decrypt"
      (core/caesar-decrypt "ujjfy" 20) => "apple")

(fact "count-letters"
      (core/count-letter "aaakfjg" \a) => 3)

(fact "count-letters"
      (core/count-letters core/encr1) => {\a 7, \b 8, \c 16, \d 10, \e 8, \f 0, \g 16, \h 5, \i 13, \j 8, \k 2, \l 1, \m 0, \n 2, \o 1, \p 19, \q 3, \r 8, \s 6, \t 17, \u 5, \v 11, \w 4, \x 17, \y 1, \z 0})

(fact "count-highest"
      (core/count-highest-letters core/encr1) => '([\p 19] [\t 17] [\x 17]))

(fact "get-key"
      (core/get-key \p \e) => 11
      (core/get-key \c \z) => 3
      )

(fact "break-encryption"
      (core/break-caesar core/encr1 \a) => "clojureisadynamicgeneralpurposeprogramminglanguagecombiningctheapproachabilityandinteractivedevelopmentofascriptinglanguacgewithanefficientandrobustinfrastructureformultithreadedprogrcammingz"
      (core/break-caesar core/encr2 \t) => "hopeisthethingwithfeathersthatperchesinthesoulandsingsthetunewithoutthewordsandneverstopsatall")