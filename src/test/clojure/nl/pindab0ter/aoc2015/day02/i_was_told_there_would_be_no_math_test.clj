(ns nl.pindab0ter.aoc2015.day02.i-was-told-there-would-be-no-math-test
  (:require
    [clojure.test :refer :all]
    [nl.pindab0ter.aoc2015.day02.i-was-told-there-would-be-no-math :refer :all]))


(deftest parse-test
  (is '((2 3 4) (1 1 10) (parse "2x3x4\n1x1x10"))))


(deftest wrapping-paper-test
  (are [expected input]
    (= expected (wrapping-paper (first (parse input))))
    58 "2x3x4"
    43 "1x1x10"))


(deftest ribbon-test
  (are [expected input]
    (= expected (ribbon (first (parse input))))
    34 "2x3x4"
    14 "1x1x10"))
