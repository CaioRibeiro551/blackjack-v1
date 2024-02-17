(ns game
  (:require [card-ascii-art.core :as card]))

(defn receber-nomes-jogadores []
  (println "Quantos jogadores irão participar? (máximo de 12)")
  (let [num-jogadores (Integer/parseInt (read-line))]
    (if (<= num-jogadores 12)
      (loop [jogadores [] contador 1]
        (if (<= contador num-jogadores)
          (let [nome (println (str "Digite o nome do jogador " contador ":"))]
            (recur (conj jogadores (read-line)) (inc contador)))
          (do
            (println "Jogadores registrados:")
            (doseq [jogador jogadores]
              (println jogador))
            jogadores)))
      (do
        (println "Número máximo de jogadores é 12. Por favor, digite novamente.")
        (receber-nomes-jogadores)))))

(defn new-card []
  (+ (rand-int 13) 1))

(defn points-cards [cards]
  (let [map-val {1 [1 11], 11 10, 12 10, 13 10}]
    (letfn [(get-value [card]
              (if (<= card 10)
                card
                (get map-val card 10)))]
      (let [sorted-cards (sort cards)
            count-aces (count (filter #(= % 1) sorted-cards))
            non-ace-cards (remove #(= % 1) sorted-cards)
            non-ace-points (reduce + (map get-value non-ace-cards))]
        (if (> count-aces 0)
          (let [min-points (+ non-ace-points count-aces)
                max-points (+ non-ace-points (* count-aces 11))]
            (if (> max-points 21)
              min-points
              max-points))
          non-ace-points)))))

(defn player [player-name]
  (let [card1 (new-card)
        card2 (new-card)
        cards [card1 card2]
        points (points-cards cards)]
    {:player-name player-name
     :cards cards
     :points points}))

(let [nomes-jogadores (receber-nomes-jogadores)]
  (doseq [nome nomes-jogadores]
    (card/print-player (player nome))))
