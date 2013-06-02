(ns rhl-squid.meetup
  (:require [clojure.core.strint :refer (<<)]
            [clj-http.client :as client]
            [rhl-squid.config :refer (require-keys)]
            [rhl-squid.env :refer (require-vars)]
            [rhl-squid.squid :refer (deftentacle)]))

(defn validate-config
  [config]
  (require-keys config [:meetup-group-urlname]))

(defn validate-env
  []
  (require-vars ["MEETUP_API_KEY"]))

(defn get-events
  [config]
  (let [params {"group_urlname" (:meetup-group-urlname config)
                "key" (System/getenv "MEETUP_API_KEY")}]
    (client/get (<< "https://api.meetup.com/2/events?~(client/generate-query-string params)")
                {:as :json})))

(deftentacle :meetup
  {:validate-config validate-config
   :validate-env validate-env
   :get-events get-events
   :put-event (constantly nil)})
