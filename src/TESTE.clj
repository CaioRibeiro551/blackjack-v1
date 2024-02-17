(ns TESTE)


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







(receber-nomes-jogadores)

