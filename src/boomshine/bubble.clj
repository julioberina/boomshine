(ns boomshine.bubble)

(defn sin [deg]
  (Math/sin (/ (* deg Math/PI) 180.0)))

(defn cos [deg]
  (Math/cos (/ (* deg Math/PI) 180.0)))

(defprotocol BubbleProtocol
  (top-quad? [this angle])
  (hits-top-or-bottom? [this y])
  (hits-left-or-right? [this x])
  (move [this]))

(defrecord Bubble [x y angle clr]

  BubbleProtocol
  (top-quad? [this angle] (and (>= angle 0.0) (<= angle 180.0)))
  (hits-top-or-bottom? [this y] (or (<= y 10.0) (>= y 590.0)))
  (hits-left-or-right? [this x] (or (<= x 10.0) (>= x 790.0)))

  ;; Use top methods to facilitate bubble movement changes
  (move [this]
    (let [a (atom angle)]
      (cond
        (hits-top-or-bottom? this y) (reset! a (- 360 angle))
        (hits-left-or-right? this x)
        (if (top-quad? this angle)
          (reset! a (- 180 angle))
          (reset! a (- 360 (- angle 180)))))
      (assoc this :x (+ x (cos @a)) :y (+ y (* (sin @a) -1)) :angle @a))))

(defn generate-bubble []
  (let [clr (atom [])]
    (case (rand-int 4)
      0 (reset! clr [255 0 0]) ; red
      1 (reset! clr [0 255 0]) ; green
      2 (reset! clr [0 0 255]) ; blue
      3 (reset! clr [255 255 0])) ; yellow
    (->Bubble (+ (rand-int 778) 11) (+ (rand-int 578) 11) (rand-int 360) @clr)))
