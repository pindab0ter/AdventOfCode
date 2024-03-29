(ns nl.pindab0ter.aoc2015.day03.perfect-spherical-houses-in-a-vacuum
  (:require
    [nl.pindab0ter.common.advent-of-code :refer [get-input]]))


(defn move [position direction]
  (let [[x1 y1] position
        [x2 y2] (case direction
                  \^ [0 1]
                  \> [1 0]
                  \v [0 -1]
                  \< [-1 0])]
    [(+ x1 x2) (+ y1 y2)]))


(defn follow [directions]
  (let [positions     (transient #{[0 0]})
        last-position (atom [0 0])]
    (doseq [direction directions]
      (let [new-position (move @last-position direction)]
        (conj! positions new-position)
        (reset! last-position new-position)))
    (persistent! positions)))


(defn -main []
  (let [directions (get-input 2015 3)
        positions  (follow directions)
        unique     (count (set positions))]
    (println "Amount of houses receiving at least one present:" unique)))
