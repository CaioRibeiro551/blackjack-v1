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
;1º REGRA  - J , Q , K = 10
;2º REGRA  - A = 11 ou 1



;SOMA DOS PONTOS
(defn points-cards [cards]
  ;; Início da definição da função points-cards, que recebe uma lista de cartas como entrada.

  (let [map-val {1 [1 11], 11 10, 12 10, 13 10}]
    ;; Definimos um mapa chamado map-val que associa cada valor de carta ao seu valor numérico no blackjack.
    ;; As chaves 11, 12 e 13 representam as cartas J, Q e K, respectivamente. Todos eles têm valor 10.
    ;; A chave 1 representa o Ás, que pode ter valor 1 ou 11, dependendo da situação.

    (letfn [(get-value [card]
              ;; Definimos uma função interna chamada get-value que recebe um número de carta como entrada e retorna o valor correspondente no blackjack.

              (if (<= card 10)
                ;; Se a carta for um número de 2 a 10, seu valor é igual ao próprio número.
                card
                ;; Caso contrário, se for uma carta especial (J, Q, K, ou A), procuramos em map-val.

                (get map-val card 10)))]
      ;; Fim da definição da função interna get-value.

      (let [sorted-cards (sort cards)
            ;; Ordenamos as cartas recebidas em ordem crescente.

            count-aces (count (filter #(= % 1) sorted-cards))
            ;; Contamos quantos ases (cartas com valor 1) temos.

            non-ace-cards (remove #(= % 1) sorted-cards)
            ;; Retiramos os ases da lista de cartas.

            non-ace-points (reduce + (map get-value non-ace-cards))]
        ;; Calculamos a soma dos valores das cartas que não são ases.

        (if (> count-aces 0)
          ;; Se tivermos pelo menos um Ás na mão:

          (let [min-points (+ non-ace-points count-aces)
                ;; Calculamos os pontos mínimos assumindo que cada Ás vale 1.

                max-points (+ non-ace-points (* count-aces 11))]
            ;; Calculamos os pontos máximos assumindo que todos os ases valem 11.

            (if (> max-points 21)
              ;; Se os pontos máximos forem maiores que 21 (estouramos), usamos os pontos mínimos.
              min-points
              ;; Caso contrário, usamos os pontos máximos.
              max-points))

          ;; Se não tivermos ases na mão, usamos apenas os pontos das outras cartas.
          non-ace-points)))))






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
