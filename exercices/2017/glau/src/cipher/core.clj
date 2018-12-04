(ns cipher.core)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn to-int
  "takes a lowercase letter character and returns its position in the alphabet: a = 0, b = 1, etc."
  [letter-char]
  (let [ascii-a (int \a)]
    (- (int letter-char) ascii-a)))

(defn to-char [n]
  (let [ascii-a (int \a)]
    (char (+ n ascii-a)))
  )

#_(defn shift [char n]
  (to-char (mod (+ (to-int char) n) 26))
  )

(defn shift [char n]
  (-> (to-int char)
      (+ n)
      (mod 26)
      to-char)
  )

(defn ceasar-encrypt [string k]
  (apply str (mapv #(shift % k) string))
  )

(defn ceasar-decrypt [string k]
  (apply str (mapv #(shift % (- k)) string))
  )

(defn get-letters [string]
  (apply str (filterv #(Character/isLetter %) (clojure.string/lower-case string)))
  )

(defn encrypt [string k]
  (ceasar-encrypt (get-letters string) k)
  )

(defn count-letters [char string]
  (count (filterv #(= % char) string))
  )

(def alphabet (map to-char (range 26)))

(def encr1 "radyjgtxhpsncpbxrvtctgpaejgedhtegdvgpbbxcvapcvjpvtrdbqxcxcv
iwtpeegdprwpqxaxinpcsxcitgprixktstktadebtciduphrgxeixcvapcvjp
vtlxiwpctuuxrxtcipcsgdqjhixcugphigjrijgtudgbjaixiwgtpstsegdvg
pbbxcvo")

(zipmap alphabet (mapv #(count-letters % encr1) alphabet))

(defn counter-letters [string]
  (zipmap alphabet (mapv #(count-letters % string) alphabet))
  )

(defn ordered-counter [hashhh]
  (sort-by val > hashhh)
  )

(defn mastered-decrypt [text char]
  (let [frequent-text (take 3 (ordered-counter(counter-letters text)))
        letters (map first frequent-text)
        keys (map #(- (to-int char) (to-int %)) letters)]
  (mapv #(ceasar-decrypt text %) keys))
  )