(ns cipher.core-test
  (:require [cipher.core :as core]
            [midje.sweet :refer :all]))


(fact "this will not fail anymore"
      1 => 1)

(facts "takes a lowercase letter character and returns its position in the alphabet: a = 0, b = 1, etc"
       (tabular
         (core/to-int ?char) => ?result
         ?char ?result
         \a    0
         \b    1
         \c    2
         \d    3
         \e    4
         \f    5))

(facts "takes a number between 0 and 25 (inclusive) and returns the corresponding lowercase letter"
       (tabular
         (core/to-char ?char) => ?result
         ?char ?result
         0    \a
         1    \b
         2    \c
         3    \d
         4    \e
         5    \f))