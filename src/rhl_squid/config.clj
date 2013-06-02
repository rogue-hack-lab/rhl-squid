(ns rhl-squid.config
  (:require [clojure.core.strint :refer (<<)]
            [slingshot.slingshot :refer (try+ throw+)]))

(defn barf [error-message]
  (throw+ {:config-error error-message}))

(defn require-keys [config ks]
  (doseq [k ks]
    (if-not (contains? config k)
      (barf (<< "missing configuration key ~{k}")))))

(defmacro with-polite-exit
  [& body]
  `(try+
     ~@body
     (catch :config-error {error# :config-error}
       (println (format "Error: %s" error#))
       (System/exit 1))))
