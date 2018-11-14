(ns boomshine.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [boomshine.bubble :refer :all]))

(defn setup []
  ; Set frame rate to 60 frames per second.
  (q/frame-rate 60)
  ; setup function returns initial state. It contains
  ; circle color and position.
  {:bubbles (repeatedly 100 #(agent (generate-bubble)))})

(defn update-state [state]
  ; Update state
  {:bubbles (map #(send % move) (:bubbles state))})

(defn draw-state [state]
  ; Clear the sketch by filling it with light-grey color.
  (q/background 0 0 0)

  (doseq [item (:bubbles state)]
    ; Set color
    (case (rand-int 3)
      0 (q/fill 255 0 0)
      1 (q/fill 0 255 0)
      2 (q/fill 0 0 255))
    ; Draw ellipse
    (q/ellipse (:x @item) (:y @item) 20 20)))


(q/defsketch boomshine
  :title "Boomshine"
  :size [800 600]
  ; setup function called only once, during sketch initialization.
  :setup setup
  ; update-state is called on each iteration before draw-state.
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  ; This sketch uses functional-mode middleware.
  ; Check quil wiki for more info about middlewares and particularly
  ; fun-mode.
  :middleware [m/fun-mode])

(defn -main [& args]
  boomshine)
