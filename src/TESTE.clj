(ns TESTE)



(defn -main []
  (let [number (rand-int 100)]
    (loop []
      (println "Enter a guess:")
      (let [guess (Integer/parseInt (read-line))]
        (cond (> number guess)
              (do (println "Too Low!")
                  (recur))
              (< number guess)
              (do (println "Too Big!")
                  (recur))
              :else
              (println "Yeah!"))))))
(-main)