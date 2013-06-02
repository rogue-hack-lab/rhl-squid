(ns rhl-squid.core
  (:require [clojure.tools.reader.edn :as edn]
            [rhl-squid.config :as config]
            [rhl-squid.env :as env]
            [rhl-squid.squid :as squid]
            [rhl-squid.tentacles.meetup]
            [rhl-squid.tentacles.log]))

(defn -main
  [& args]
  (if-let [config-path (first args)]
    (do
      (let [config (edn/read-string (slurp config-path))]
        (config/with-polite-exit
          (squid/validate-config config))
        (env/with-polite-exit
          (squid/validate-env))
        (squid/omnomnom config)))
    (do
      (println "Usage: lein run <squid.edn>")
      (System/exit 1))))
