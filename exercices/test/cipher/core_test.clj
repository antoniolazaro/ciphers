(ns cipher.core-test
  (:require [clojure.test :refer :all]
            [cipher.core :as core]
            [midje.sweet :refer :all]))

(fact "It's equals"
      2 => 2)

(fact "Plus values"
      (core/pluxValues 1 2) => 103)

(fact "Times 10 and plus Y"
      (core/times10PlusY 2 5) => 25)

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
         \d    3)

 (fact "takes a number between 0 and 25 (inclusive) and returns the corresponding lowercase letter."
      (tabular
        (core/to-char ?int) => ?result
        ?int ?result
        0     \a
        1     \b
        2     \c
        3     \d)))

(facts "shifting the alphabet by a given number of positions to the left"
      (fact "character a is d when shift position is 3"
            (core/shift \a 3) => \d)
      (fact "character d is a when shift position is -3"
            (core/shift \d -3) => \a)
      (fact "takes any char in alphabet and shift 3 positions"
            (tabular
              (core/shift ?letter, 3) => ?result
              ?letter ?result
              \a      \d
              \b      \e
              \x      \a
              \y      \b
              \z      \c)))

(fact "Encrypting word sequence using caesar-encrypt"
      (fact "Word apple is ujjfy when encrypted with shift 20 positions"
       (core/caesar-encrypt "apple" 20) => "ujjfy"))

(facts "Decrypting word sequence using caesar-decrypt"
       (fact "Word ujjfy is apple when decrypted with shift 20 positions"
             (core/caesar-decrypt "ujjfy" 20) => "apple")
       (fact "Word gtxyts is apple when decrypted with shift 5 positions"
             (core/caesar-decrypt "gtxyts" 5) => "boston")
       (fact "Word gtxyts is apple when decrypted with shift 10 positions"
             (core/caesar-decrypt "mvytebolbsnqo" 10) => "clojurebridge"))

(facts "Takes a string with any symbols in it, and returns a string of only letters in it, all letters converted to lowercase"
  (fact "Get \"Hello, friend!\" and return \"hellofriend\""
        (core/get-letters "Hello, friend!") => "hellofriend"))

(fact "Get encrypted text using caesar-encrypt"
      (fact "Encrypt \"Hello, friend!\" end get return \"mjqqtkwnjsi\" when key equals 5"
        (core/encrypt-text "Hello, friend!" 5) => "mjqqtkwnjsi")
      (fact "Decrypt \"mjqqtkwnjsi\" end get return \"Hello, friend!\" when key equals 5"
        (core/decrypt-text "mjqqtkwnjsi" 5) => "hellofriend"))