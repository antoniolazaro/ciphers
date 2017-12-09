(ns cipher.learning-test
  (:require [clojure.test :refer :all]
            [cipher.learning :as learning]
            [midje.sweet :refer :all]))

(fact "filtering odd letters"
      (filterv odd? [6 7 -1 0 5]) => [7 -1 5])

(fact "filtering with anonymous functions"
      (filterv #(< % 5) [3 6 5 8 0]) => [3 0])

(fact "filtering letters"
      (apply str (filterv #(Character/isLetter %) "How are you?")) => "Howareyou")
