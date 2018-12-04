(ns cipher.core-test
  (:require [clojure.test :refer :all]
            [cipher.core :as core]
            [midje.sweet :refer :all]
            [clojure.string :as str])
  (import [java.lang Character]))

(fact
  (tabular
    (core/to-int ?char) => ?result
      ?char ?result
      \a 0
      \b 1
      \c 2))

(fact
  (tabular
    (core/to-char ?int) => ?result
      ?int ?result
      0 \a
      1 \b
      2 \c))

(fact
  (tabular
    (core/shift ?char ?int) => ?result
      ?char ?int ?result
      \a 3 \d
      \b 20 \v))

(fact
    (core/encrypt "abc" 2) => "cde")

(fact
  (core/caesar-decrypt "ujjfy" 20) => "apple")

(fact
  (core/get-letters "Hello, friend!") => "hellofriend")

(fact
  (core/count-letters \a "aadvark") => 3)

(fact
  (core/take-three "radyjgtxhpsncpbxrvtctgpaejgedhtegdvgpbbxcvapcvjpvtrdbqxcxcv
iwtpeegdprwpqxaxinpcsxcitgprixktstktadebtciduphrgxeixcvapcvjp
vtlxiwpctuuxrxtcipcsgdqjhixcugphigjrijgtudgbjaixiwgtpstsegdvg
pbbxcvo"))

(fact
  (core/key-getter \d \a) => 3)

(fact
  (core/decrypt "radyjgtxhpsncpbxrvtctgpaejgedhtegdvgpbbxcvapcvjpvtrdbqxcxcv
iwtpeegdprwpqxaxinpcsxcitgprixktstktadebtciduphrgxeixcvapcvjp
vtlxiwpctuuxrxtcipcsgdqjhixcugphigjrijgtudgbjaixiwgtpstsegdvg
pbbxcvo") => [\a \b \c \d \e \f \g \h \i \j \k \l \m \n \o \p \q \r \s \t \u \v \w \x \y \z])

(fact
  (core/decrypt "ahixblmaxmabgzpbmayxtmaxklmatmixkvaxlbgmaxlhnetgwlbgzlmaxm
  ngxpbmahnmmaxphkwltgwgxoxklmhiltmtee") => [\a \b \c \d \e \f \g \h \i \j \k \l \m \n \o \p \q \r \s \t \u \v \w \x \y \z])
