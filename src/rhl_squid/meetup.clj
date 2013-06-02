(ns rhl-squid.meetup
  (:require [clojure.core.strint :refer (<<)]
            [clj-http.client :as client]
            [slingshot.slingshot :refer (throw+)]))

(defn validate-config
  [config]
  (doseq [required-key [:meetup-group-urlname]]
    (if-not (contains? config required-key)
      (throw+ {:config-error (<< "missing configuration key ~{required-key}")}))))

(defn validate-environment
  []
  (doseq [required-env ["MEETUP_API_KEY"]]
    (if-not (System/getenv required-env)
      (throw+ {:env-error (<< "missing environment variable ~{required-env}")}))))

(defn get-events
  [config]
  (let [params {"group_urlname" (:meetup-group-urlname config)
                "key" (System/getenv "MEETUP_API_KEY")}]
    (client/get (<< "https://api.meetup.com/2/events?~(client/generate-query-string params)")
                {:as :json})))
