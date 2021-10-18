(ns api-events.main
  (:require [api-events.core :as core]
            [clojure.java.io :as io])
  (:gen-class))

(defn -main [& args]
  (let [[command param] args]
    (cond
      (= "generate" command)
      (->> (io/resource (str "events/" param))
           (slurp)
           (read-string)
           (core/event)
           (core/render)
           (print))

      :else
      (println (str "Invalid command:" command)))))
