(ns cipher.core)

(defn to-int
  "takes a lowercase letter character and returns its position in the alphabet: a = 0, b = 1, etc."
  [letter-char]
  (let [ascii-a (int \a)]
    (- (int letter-char) ascii-a)))

(defn to-char
  "takes a lowercase letter character and returns its position in the alphabet: a = 0, b = 1, etc."
  [number]
  (let [ascii-a (int \a)]
    (if (or(< number 0)(> number 25))
      "Hey, listen!"
      (char (+ number ascii-a))
      )
    )
  )

(defn shift
  [letter-char key]
  (let [number (to-int letter-char)]
    (let [ciphered (+ number key)]
      (to-char (mod ciphered 26))
      )
    )
  )

(defn get-letters
  [text]
  (->> (clojure.string/lower-case text)
       (filterv #(Character/isLetter %))
       (apply str)
       )
  )

(defn caesar-encrypt
  [text key]
  (apply str (mapv #(shift % key) (get-letters text)))
)

(defn caesar-decrypt
  [text key]
  (caesar-encrypt text (- 0 key))
  )


(defn count-letters
  [letter text]
  (->> (clojure.string/lower-case text)
       (filterv #(= letter %))
       (count)
       )
  )

(def alphabet (mapv to-char (range 26)))

(defn count-all-letters
  [text]
  (->> (mapv #(count-letters % text) alphabet)
       (zipmap alphabet)
       )
  )