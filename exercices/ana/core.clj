(ns cipher.core
  (:require [clojure.string :as string])
  (import [java.lang Character]))

; write caesar cipher

(defn to-int [letter-char]
  "takes a lowercase letter character and returns its position in the alphabet: a = 0, b = 1, etc."
  (let [ascii-a (int \a)]
    (- (int letter-char) ascii-a)))


(defn to-char [position]
  "takes an integer and returns the lowercase letter char in that int position: 0 = a, 1 = b, etc."
  (let [ascii-a (int \a)]
    (char (+ position ascii-a))))


(defn shift [letter n]
  "takes a letter and an int and returns this letter shifted by n"
  (-> (to-int letter)
      (+ n)
      (mod 26)
      (to-char)))


(defn get-letters [phrase]
  "takes a phrase and returns it lowercase and only with letters: Hello, friend! = hellofriend"
  (apply str (filterv #(Character/isLetter %) (string/lower-case phrase))))


(defn caesar-encrypt [word key]
  "takes a word and an int and returns word shifted by int"
  (apply str (mapv #(shift % key) (get-letters word))))


(defn caesar-decrypt [word key]
  "takes an encrypted work and returns word decrypted with key"
  (apply str (mapv #(shift % (* -1 key)) word)))



; break caesar cipher
; most frequent letters in english language are: e, t, a (try those first)


(def encr1 "radyjgtxhpsncpbxrvtctgpaejgedhtegdvgpbbxcvapcvjpvtrdbqxcxcv
iwtpeegdprwpqxaxinpcsxcitgprixktstktadebtciduphrgxeixcvapcvjp
vtlxiwpctuuxrxtcipcsgdqjhixcugphigjrijgtudgbjaixiwgtpstsegdvg
pbbxcvo")

(def encr2 "ahixblmaxmabgzpbmayxtmaxklmatmixkvaxlbgmaxlhnetgwlbgzlmaxmngxpbmahnmmaxphkwltgwgxoxklmhiltmtee")


(defn count-letter [word letter]
  (count (filterv #(= letter %) word)))


(defn count-letters [phrase]
  (let [alphabet (map to-char (range 26))]
    (zipmap alphabet (mapv #(count-letter phrase %) alphabet))))


(defn count-highest-letters [phrase]
  (take 3 (sort-by val > (count-letters phrase))))


(defn get-key [encrypted-char decrypted-char]
  (mod (- (to-int encrypted-char) (to-int decrypted-char)) 26))


(defn break-caesar [phrase decrypted-char]
  (caesar-decrypt phrase (get-key (first(first(count-highest-letters phrase))) decrypted-char)))

