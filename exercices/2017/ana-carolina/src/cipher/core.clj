(ns cipher.core)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(def answer 42)

(defn f
  [x y]
  (+ x y 100))

;;Exercício 1: Write a function g that takes two numbers x and y and returns x times 10 plus y.

(defn g
  [x y]
 (+ (* x 10) y))

(defn to-int
  "takes a lowercase letter character and returns its position in the alphabet: a = 0, b = 1, etc."
  [letter-char]
  (let [ascii-a (int \a)]
    (- (int letter-char) ascii-a)))

;;Exercício 2: Write a reverse conversion function to-char, i.e. a function that takes a number
;; between 0 and 25 (inclusive) and returns the corresponding lowercase letter. For instance,
;; (to-char 3) should return \d.

(defn to-char
  [number]
  (let [number-char (+ number (int \a))]
    (char number-char)))

;;Exercicio 3:Write a function shift according to the description above. Some examples for how it should work:
;
;(shift \a 3) ; result \d
;(shift \b 20) ; result \v
;(shift \z 3) ; result \c

(defn shift
  [letter-char number]
  (let [letter-char-to-int (+ (to-int letter-char) number)
        letter-int (mod letter-char-to-int 26)]
    (to-char letter-int)))

;Exercício 4: Use mapv and an anonymous function to take the opposite of each number in a given vector. For instance,
;if the vector is [2 -1 0 3], the result would be [-2 1 0 -3].

(defn to-negative
  [numbers]
  (mapv #(* % -1) numbers))

;Exercício 5: Below is the start of a function that encrypts a word w with a key k. Fill in the body of the function
;and test it on some examples.

(defn caesar-encrypt
  "encrypting a word w with a key k using Caesar cipher"
  [w k]
  (apply str (mapv #(shift % k) w)))

;Exercício 6: Based on the function caesar-encrypt, write a function caesar-decrypt that takes an encrypted word
;(all lower-case, no spaces or other symbols) and a key and returns its decryption. Recall how we can use the same
;shift function for decryption.

(defn shift-decrypt
  [letter-char number]
  (let [letter-char-to-int (- (to-int letter-char) number)
        letter-int (mod letter-char-to-int 26)]
    (to-char letter-int)))

(defn caesar-decrypt
  "decrypting a word w with a key k using Caesar cipher"
  [w k]
  (apply str (mapv #(shift-decrypt % k) w)))

;Exercicio 7:  write a function get-letters that takes a string with any symbols in it, and returns a string of of
;only letters in it, all letters converted to lowercase, as in the example below:

(defn just-letters
  [letters]
  (apply str(filterv #(Character/isLetter %) letters)))

;Exercicio 8: Look at your previous function that uses filterv, and then write a call to filterv to filter out all
;characters other than \a in the word "aadvark". Once you have accomplished this task, make it into a function
;count-letters that works as in the example above. Try it on a different word and with a different character.

(defn count-letters
  [char letters]
  (count (filterv #(= % char) letters)))

;Exercicio 9: Construct a hashmap of the number of occurrences of each letter in the string encr1. Here is a sequence of
;steps that accomplishes the task:

(def alphabet (map to-char (range 26)))

(defn map-count
  [text]
  (zipmap alphabet (mapv #(count-letters % text) alphabet)))

;;Exercicio 10: write an expression that sorts the hashmap. The result should be:

(defn sort-hashmap
  [hashmap]
  (sort-by second > hashmap))

;Exercicio 11: Write a function that, given a hashmap of characters and their counts, returns the highest occurring
;elements and their counts ([\p 19] [\t 17] [\x 17]).

(defn take-occuring
  [hashmap]
  (take 3 (sort-hashmap hashmap)))

;Exercicio 12: Write the above computation as a formula (or, better yet, a function that takes two characters returns
;                                                            a potential key).
(defn formula
  [text x y]
  (caesar-decrypt text (- (to-int y) (to-int x))))
















