(ns rhl-squid.tentacles.log
  (:require [clojure.pprint :refer (pprint)]
            [rhl-squid.squid :refer (deftentacle)]))

(deftentacle :log
  {:validate-config (constantly true)
   :validate-env (constantly true)
   :put-event (fn [config event] (pprint event))})
