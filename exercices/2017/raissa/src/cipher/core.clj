(ns cipher.core)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn g [x y] (+ (* x 10) y))

(defn to-int
  "takes a lowercase letter character and returns its position in the alphabet: a = 0, b = 1, etc."
  [letter-char]
  (let [ascii-a (int \a)]
    (- (int letter-char) ascii-a)))

(defn to-char
  "takes a number between 0 and 25 (inclusive) and returns the corresponding lowercase letter"
  [int-char]
  (let [ascii-a (int \a)]
    (char (+ int-char ascii-a))))

(defn shift
  "calculates shift"
  [shift-letter n]
  (let [int-val (to-int shift-letter)]
    (to-char (mod (+ int-val n) 26))))

(defn caesar-encrypt
  "encrypting a word w with a key k using Caesar cipher"
  [w k]
  (apply str (mapv #(shift % k) w))
  )

(defn invert
  [x]
  (- x (* 2 x)))

(defn caesar-decrypt
  "decrypting a word w with a key k using Caesar cipher"
  [w k]
  (apply str (mapv #(shift % (invert k)) w))
  )
(defn get-letters
  [s]
  (apply str
         (filterv #(Character/isLetter %)
                  (clojure.string/lower-case s))))

(defn count-letters
  [l w]
  (count (filterv #(= % l) w)))

(def alphabet (map to-char (range 26)))

(def encr1 "radyjgtxhpsncpbxrvtctgpaejgedhtegdvgpbbxcvapcvjpvtrdbqxcxcv
iwtpeegdprwpqxaxinpcsxcitgprixktstktadebtciduphrgxeixcvapcvjp
vtlxiwpctuuxrxtcipcsgdqjhixcugphigjrijgtudgbjaixiwgtpstsegdvg
pbbxcvo")

(defn frequency-calc
  [phrase]
  (sort-seq
    (zipmap alphabet
            (map #(count-letters % phrase) alphabet)))
  )

(defn sort-seq
  [seqToSort]
  (sort-by second > seqToSort)
  )

(defn calc-enckey
  [a b]
  (mod (- (int a) (int b)) 26)
  )

(defn encrypt-letter
  [a b]
  (to-char (mod (+ (to-int a) (to-int b)) 26)))

(defn square-sum
  [v1 v2]
  (mapv #(+ (* %1 %1) (* %2 %2)) v1 v2))

(def encrypt-key (cycle "cipher"))

(defn vigenere-encrypt
  [text key]
  (apply str
         (mapv #(encrypt-letter %1 %2) (get-letters text) (cycle key)))
  )