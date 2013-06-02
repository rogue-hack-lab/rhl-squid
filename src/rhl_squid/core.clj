(ns rhl-squid.core
  (:require [clojure.core.strint :refer (<<)]
            [clojure.pprint :refer (pprint)]
            [clojure.tools.reader.edn :as edn]
            [slingshot.slingshot :refer (try+)]
            [rhl-squid.meetup :as meetup]))

(defn -main
  [& args]
  (if-let [config-path (first args)]
    (do
      (let [config (edn/read-string (slurp config-path))]
        (try+
          (meetup/validate-config config)
          (catch :config-error {error :config-error}
            (println (<< "Error: ~{error}"))
            (System/exit 1)))
        (try+
          (meetup/validate-environment)
          (catch :env-error {error :env-error}
            (println (<< "Error: ~{error}"))
            (System/exit 1)))
        (pprint (meetup/get-events config))))
    (do
      (println "Usage: lein run <squid.edn>")
      (System/exit 1))))
