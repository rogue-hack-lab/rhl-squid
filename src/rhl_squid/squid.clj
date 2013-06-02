(ns rhl-squid.squid
  (:require [clojure.core.strint :refer (<<)]
            [clojure.pprint]
            [rhl-squid.config :as config]
            [rhl-squid.env :as env]))

(def all-tentacles (atom {}))
(def active-tentacles (atom {}))
(def active-brain (atom {}))

(defmacro deftentacle
  [tname tdef]
  `(do
     (assert (contains? ~tdef :validate-config))
     (assert (contains? ~tdef :validate-env))
     (assert (contains? ~tdef :put-event))
     (swap! all-tentacles assoc ~tname ~tdef)))

(defn validate-config
  [config-map]
  (config/require-keys config-map [:brain :tentacles])
  (let [brain (:brain config-map)
        tentacles (:tentacles config-map)]
    (if-not (set? tentacles)
      (config/barf (<< "tentacles (~{tentacles}) must be a set")))
    (doseq [tentacle (conj tentacles brain)]
      (if-not (contains? @all-tentacles tentacle)
        (config/barf (<< "unknown tentacle ~{tentacle}")))
      ((:validate-config (get @all-tentacles tentacle)) config-map))
    (reset! active-brain (select-keys @all-tentacles [brain]))
    (reset! active-tentacles (select-keys @all-tentacles tentacles))))

(defn validate-env
  []
  (doseq [tentacle (vals (conj @active-tentacles @active-brain))]
    ((:validate-env tentacle))))

(defn omnomnom
  [config]
  (doseq [event (map #((:get-events %) config) (vals @active-brain))]
    (doseq [tentacle (vals @active-tentacles)]
      ((:put-event tentacle) config event))))
