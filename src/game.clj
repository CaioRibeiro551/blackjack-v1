(ns game
  (:require [card-ascii-art.core :as card]))

; Função que vai gerar carta pra mim
; Cartas A,2,3,4,5,6,7,8,9,10,J,Q,K,1....13

(defn new-card []
  ;gerar numeros de cartas de 1 a 13
   (+(rand-int 13)1))

; PROBLEMA É QUE EU NÃO QUERO O 0 E QUERO O 13 ,E ESTA INDO ATE O 12
; então vou somar 1 nos numeros que ele gerar

;como representar um jogador

; vai calcular os pontos de acordo com as cartas
;1º REGRA  - J , Q , K = 10 (NÃO 11, 12 E 13)
;2º REGRA  - [A 10] = 11 OU 21 = 21
;3º REGRA - [A 5 7] = 1+5+7(13) OU 11+5+7 (23)



;REGRA 1
(defn JQK>10 [card]
  (if (> card 10) 10 card))


;SOMA DOS PONTOS
(defn points-cards [cards]
  (let [cards-without-JQK (map JQK>10 cards)]
    (reduce + cards-without-JQK)))



(defn player [player-name]
  (let [card1 (new-card)
        card2 (new-card)
        cards [card1 card2]
        points (points-cards cards)]
    {:player-name player-name
     :cards cards
     :points points}))


(card/print-player (player "Caio"))
(card/print-player (player "Dealer"))



;mudei o def de dentro da função player para let pois o def poderia ser acessado fora da função e mudado o simbolo
