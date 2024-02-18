(ns game
  (:require [card-ascii-art.core :as card]))

(defn receive-names []
  (println "Quantos jogadores irão participar? (máximo de 12)")
  (let [entrada (read-line)
        num-jogadores (if (re-matches #"\d+" entrada)
                        (Integer/parseInt entrada)
                        nil)]
    (if (and num-jogadores (<= num-jogadores 12))
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
        (println "Número máximo de jogadores é 12. Por favor, digite um número válido.")
        (receive-names)))))

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
        card3 (new-card)
        cards [card1 card2 card3]
        points (points-cards cards)]
    {:player-name player-name
     :cards cards
     :points points
     :wins 0})) ; Adicionando a contagem de vitórias seguidas ao jogador

(defn found-winner [players]
  (let [vencedores (filter #(<= (:points %) 21) players)]
    (cond
      (empty? vencedores) (println "Nenhum jogador fez 21 pontos ou menos.")
      :else
      (let [max-points (apply max (map :points vencedores))
            vencedores (filter #(= (:points %) max-points) vencedores)]
        (cond
          (empty? vencedores) (println "Nenhum jogador fez 21 pontos exatos.")
          (> (count vencedores) 1) (println "Houve um empate.")
          :else (let [vencedor (:player-name (first vencedores))]
                  (println (str "O jogador que venceu a rodada foi: " vencedor))
                  vencedor))))))

(defn play-round []
  (let [nomes-jogadores (receive-names)
        players (map player nomes-jogadores)]
    (loop [players players]
      (doseq [player players]
        (card/print-player player))
      (let [winner (found-winner players)]
        (when winner
          (println (str winner " venceu!"))
          (let [players (map #(if (= (:player-name %) winner)
                                (update % :wins inc)
                                %)
                             players)]
            players))))))

(defn play-game [main-menu]
  (loop []
    (let [players (play-round)]
      (println "Deseja jogar novamente? (sim/não)")
      (let [resposta (read-line)]
        (if (= resposta "não")
          (do
            (println "Obrigado por jogar!")
            ;; Retornar um valor especial de saída para indicar que o jogo deve encerrar
            :exit)
          (do
            (println "Voltando ao menu principal...")
            (main-menu)))))))

(defn main-menu []
  (println "Bem-vindo ao jogo!")
  (println "Escolha uma opção:")
  (println "1. Iniciar jogo")
  (println "2. Sair")
  (let [option (read-line)]
    (case option
      "1" (play-game main-menu)
      "2" (println "Até mais!")
      (do
        (println "Opção inválida. Por favor, escolha novamente.")
        (main-menu)))))

(main-menu)
