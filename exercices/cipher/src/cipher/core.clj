(ns cipher.core)

(def ascii-a (int \a))
(def alphabet-size 26)

(def encr1 "radyjgtxhpsncpbxrvtctgpaejgedhtegdvgpbbxcvapcvjpvtrdbqxcxcv
iwtpeegdprwpqxaxinpcsxcitgprixktstktadebtciduphrgxeixcvapcvjp
vtlxiwpctuuxrxtcipcsgdqjhixcugphigjrijgtudgbjaixiwgtpstsegdvg
pbbxcvo")

(defn to-int [char]
  "returns a lowercase letter's position in the alphabet"
  (- (int char) ascii-a))

(defn to-char [number]
  "takes a number and returns the corresponding lowercase letter"
  (char (+ number ascii-a)))

(defn shift [char number]
  "shifts a letter by a given number"
  (-> (to-int char)
      (+ number)
      (mod alphabet-size)
      (to-char)))

(defn caesar-encrypt [word key]
  "encrypts a word with a key using Caesar cipher"
  (->> word
       (mapv #(shift % key))
       (apply str)))

(defn caesar-decrypt [word key]
  "decrypts a word according to a key using Caeser cipher"
  (->> word
       (mapv #(shift % (- key)))
       (apply str)))

(defn get-letters [text]
  "gets only letters and make it lower case"
  (->> text
       (clojure.string/lower-case)
       (filterv #(Character/isLetter %))
       (apply str)))

(defn encrypt-text [text key]
  "encrypts complete texts with a key"
  (-> text
      (get-letters)
      (caesar-encrypt key)))

(defn count-letters [letter text]
  "counts the number of occurrences of a letter in a text"
  (->> text
       (clojure.string/lower-case)
       (filterv #(= letter %))
       (count)))

(def alphabet (map to-char (range alphabet-size)))

(defn count-each-letter [text]
  "creates a hashmap with alphabets letters' frequency in a text"
  (zipmap alphabet
          (mapv #(count-letters % text) alphabet)))

(defn sort-frequencies [frequencies-hash]
  "desc sort hash by second value"
  (sort-by second > frequencies-hash))

(defn highest-occurring-elements [frequencies]
  "takes the three highest occurring elements"
  (->> (sort-frequencies frequencies)
       (take 3)))

(defn calculate-potential-key [mask-letter letter]
  "calculates a key given two corresponding letters"
  (-> (- (to-int mask-letter) (to-int letter))
      (mod alphabet-size)))

(defn try-decryption [encrypted-text mask-letter letter]
  "decrypts something that makes sense"
  (->> letter
       (calculate-potential-key mask-letter)
       (caesar-decrypt encrypted-text)))
