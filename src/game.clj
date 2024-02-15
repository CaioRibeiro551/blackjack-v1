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

(defn player [player-name]
  (let [card1 (new-card)
        card2 (new-card)]
    {:player-name player-name
     :cards [card1 card2]}))


(card/print-player (player "Caio"))
(card/print-player (player "Dealer"))



;mudei o def de dentro da função player para let pois o def poderia ser acessado fora da função e mudado o simbolo
