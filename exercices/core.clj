(ns cipher.core
  (:require [clojure.string :as str])
  (import [java.lang Character]))

(defn to-int
  "takes a lowercase letter character and returns its position in the alphabet: a = 0, b = 1, etc."
  [letter-char]
  (let [ascii-a (int \a)]
    (- (int letter-char) ascii-a)))

(defn to-char
  [int-number]
  (let [ascii-a (int \a)]
    (char (+ int-number ascii-a))))

(defn shift
  [letter-char int-shift]
  (to-char(mod(+ int-shift (to-int letter-char)) 26)))

(defn encrypt
  [word int-number]
  (apply str (mapv #(shift % int-number) word)))

(defn caesar-decrypt
  [word int-number]
  (apply str (mapv #(shift % (- int-number)) word)))

(defn get-letters
  [word]
  (str/lower-case (apply str (filterv #(Character/isLetter %) word))))

(defn caesar-encrypt
  [word number]
  (encrypt (get-letters word) number))

(defn count-letters
  [chara word]
  (count (filterv #(= chara %) word)))

(def alphabet (map to-char (range 26)))

(def encr1 "radyjgtxhpsncpbxrvtctgpaejgedhtegdvgpbbxcvapcvjpvtrdbqxcxcv
iwtpeegdprwpqxaxinpcsxcitgprixktstktadebtciduphrgxeixcvapcvjp
vtlxiwpctuuxrxtcipcsgdqjhixcugphigjrijgtudgbjaixiwgtpstsegdvg
pbbxcvo")

(defn take-three
  [text]
  (take 3 (sort-by second > (zipmap alphabet (mapv #(count-letters % text) alphabet)))))

(defn abs [n] (max n (- n)))

(defn key-getter
  [enc-char plain-char]
  (abs (- (to-int enc-char) (to-int plain-char))))

(defn decrypt
  [text]
  (map #(println (caesar-decrypt text (key-getter (first (first (take-three text))) %))) alphabet))

