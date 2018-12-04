(ns cipher.core
  (:require [clojure.string :as str])
  (import [java.lang Character]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))


(defn to-int [valor]
  (let [asca (int \a)]
    (- (int valor) asca)))

(defn to-char [valor]
  (let [asca (int \a)]
    (char (+ valor asca))))

(defn shift [letra valor]
  (to-char (mod (+ (to-int letra) valor) 26)))
; (-> (to-int letra)
;     (+ n)
;     (mod 26)
;     (to-char))

(defn caesar-encrypt [palavra valor]
  (apply str (mapv #(shift % valor) palavra)))


(defn caesar-decrypt [palavra valor]
  (apply str (mapv #(shift % (- valor)) palavra)))


(defn get-letters [palavra]
   (->> (filterv #(Character/isLetter %) palavra)
        (apply str)
        (str/lower-case)))

(defn enc-letters [palavra valor]
  (caesar-encrypt(get-letters palavra) 5))

(defn count-letters [letra palavra]
  (count (filterv #(= letra %) palavra)))

(def alphabet (map to-char (range 26)))

(def encr1 "radyjgtxhpsncpbxrvtctgpaejgedhtegdvgpbbxcvapcvjpvtrdbqxcxcv
iwtpeegdprwpqxaxinpcsxcitgprixktstktadebtciduphrgxeixcvapcvjp
vtlxiwpctuuxrxtcipcsgdqjhixcugphigjrijgtudgbjaixiwgtpstsegdvg
pbbxcvo")

(defn ocurrency []
  (zipmap alphabet (map #(count-letters % encr1) alphabet)))

(defn highest []
  (take 3(sort-by second > (ocurrency))))

(defn abs [n]
  (max n (- n)))

(defn findKey [v1 v2]
  (prn (abs(- (to-int v1) (to-int v2)))))

(defn findKey2 [v1 v2 key]
  (prn(to-char (mod (+ (to-int v1) (to-int v2) key) 26))))