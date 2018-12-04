;from core.clj by Larissa Gaulia

(ns cipher.core)

(defn abs
  [x y]
  (if (< x y) (- y x) (- x y)))


(defn to-int
  "takes a lowercase letter character and returns its position in the alphabet: a = 0, b = 1, etc."
  [letter-char]
  (let [ascii-a (int \a)]
    (- (int letter-char) ascii-a)))

(defn to-char
  "takes a position in the alphabet and returns its lowercase char"
  [position]
  (let [int-a (int \a)]
    (char (+ (int position) int-a))))

(defn shift
  "makes the caeser cipher"
  [letter shifter]
  (to-char (mod(+ (to-int letter) shifter) 26)))

(defn caesar-encrypt
  "encrypting a word w with a key k using Caesar cipher"
  [w k]
  (apply str (mapv #(shift % k) w)))

(defn caesar-decrypt
  "decrypting a word w with a key k using Caesar cipher"
  [w k]
  (apply str (mapv #(shift % (- 0 k)) w)))

(defn get-letters
  "returns a string without any special chars"
  [string]
  (apply str (filterv  #(Character/isLetter %) (clojure.string/lower-case string))))

(defn caesar-encrypt-no-special-char
  "eliminates special chars, uses the caesar-encrypt function"
  [string n]
  (caesar-encrypt (get-letters string) n))


(defn count-letters
  "count occurrences of given letter"
  [letter string]
  (count (filterv #(= letter %) string)))


(def alphabet (map to-char (range 26)))

(defn construct-hashmap
  "takes a string and constructs frequency map"
  [string]
  (zipmap alphabet (map #(count-letters % string) alphabet)))

(defn sort-map
  "self explanatory"
  [mymap]
  (sort-by second > mymap))

(defn take-three
  "takes first three elements of list"
  [list]
  (take 3 list))

(defn get-potential-key
  [x y]
  (mod (abs (int x) (int y)) 26))

(defn encrypt-letter
  [x y]
  (to-char (mod (+ (to-int x) (to-int y)) 26)))
