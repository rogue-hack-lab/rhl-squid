(ns rhl-squid.meetup
  (:require [clojure.core.strint :refer (<<)]
            [clj-http.client :as client]
            [rhl-squid.config :as config]
            [rhl-squid.env :as env]
            [rhl-squid.squid :refer (deftentacle)]))

(defn validate-config
  [config-map]
  (config/require-keys config-map [:meetup-group-urlname]))

(defn validate-env
  []
  (env/require-vars ["MEETUP_API_KEY"]))

(defn get-events
  [config-map]
  (let [params {"group_urlname" (:meetup-group-urlname config-map)
                "key" (System/getenv "MEETUP_API_KEY")}]
    (client/get (<< "https://api.meetup.com/2/events?~(client/generate-query-string params)")
                {:as :json})))

(deftentacle :meetup
  {:validate-config validate-config
   :validate-env validate-env
   :get-events get-events
   :put-event (constantly nil)})
