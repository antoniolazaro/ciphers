(ns cipher.core-test
  (:require [clojure.test :refer :all]
            [cipher.core :refer :all])
  (:require [cipher.core :as core]
            [midje.sweet :refer :all]))


;(deftest a-test
;   (fact "you shall pass"
;        (+ 1 1) => 2))
;
;(defn func [x y] (* x y))
;(fact (func 1 3) => 3)

;(defn to-int
;  "takes a lowercase letter character and returns its position in the alphabet: a = 0, b = 1, etc."
;  [letter-char]
;  (let [ascii-a (int \a)]
;    (- (int letter-char) ascii-a)))
;
;
;(defn to-char
;  "takes a position in the alphabet: a = 0, b = 1, etc. and returns its lowercase letter character"
;  [num]
;  (let [ascii-a (int \a)]
;    (char(+ num ascii-a))))
;



;(fact (shift \d -3) => \a)
;(fact (shift \b 20) => \v)
;(fact (shift \z 3) => \c)

;(defn neg_vector
;  [vector]
;  (mapv #(- 0 %) vector)
;  )
;
;(fact (neg_vector [2 -1 0 3]) => [-2 1 0 -3])

(defn shift
  "shifts a letter by a given number, eg (shift \\a 3) result \\d"
  [letter-char num]
  (to-char (mod (+ (to-int letter-char) num) 26)))

(defn caesar-encrypt
  "encrypt words with Caesar cipher"
  [word num]
  (apply str (mapv #(shift % num) word)))


;(fact (caesar-encrypt "elppa" 20) => "yfjju")

(defn caesar-decrypt
  "decrypt words with Caesar cipher"
  [word num]
  (apply str (mapv #(shift % (- 0 num)) word)))

;(fact (caesar-decrypt "yfjju" 20) => "elppa")
;(fact (caesar-decrypt "ujjfy" 20) => "apple")
;(fact (caesar-decrypt "gtxyts" 5) => "boston")
;(fact (caesar-decrypt "mvytebolbsnqo" 10) => "clojurebridge")

(defn get-letters
  "takes a string with any symbols in it, and returns a string of of only letters in it, all letters converted to lowercase"
  [string]
  (apply str (filterv #(Character/isLetter %) (clojure.string/lower-case string))))

;(fact (get-letters "Hello, friend!") => "hellofriend")
;(fact (get-letters "!@#$B%*()a") => "ba")

(defn encryption
  "decryption with Caesar cipher on entire strings of text"
  [string num]
  (caesar-encrypt (get-letters string) num)
  )

;(fact (encryption "Hello, friend!" 5) => "mjqqtkwnjsi")

(defn count-letters
  "function that, given a character and a string, would return the number of occurrences of this character in the string"
  [letter string]
  (count (filterv #(= letter %) string)))

;(fact (count-letters \a "aadvark") => 3)
;(fact (count-letters \x "aadvark") => 0)
;(fact (count-letters \d "aadvark") => 1)

(def alphabet (map to-char (range 26)))

(def encr1 "radyjgtxhpsncpbxrvtctgpaejgedhtegdvgpbbxcvapcvjpvtrdbqxcxcv
iwtpeegdprwpqxaxinpcsxcitgprixktstktadebtciduphrgxeixcvapcvjp
vtlxiwpctuuxrxtcipcsgdqjhixcugphigjrijgtudgbjaixiwgtpstsegdvg
pbbxcvo")

(defn hashmap
  "returns a hashmap of the number of times each letter appears in the string"
  [string]
  (sort-by second >(zipmap alphabet (map #(count-letters % string) alphabet))))

(defn take3-hashmap
  (take 3 hashmap))

(defn potential key
  "a function that takes two characters returns a potential key"
  [char1 char2]
  (mod (- (int char1) (int char2)) 26))
