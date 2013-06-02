(ns rhl-squid.env
  (:require [clojure.core.strint :refer (<<)]
            [slingshot.slingshot :refer (try+ throw+)]))

(defn barf [error-message]
  (throw+ {:config-error error-message}))

(defn require-vars [vs]
  (doseq [v vs]
    (if-not (System/getenv v)
      (barf (<< "missing environment variable ~{v}")))))

(defmacro with-polite-exit
  [& body]
  `(try+
     ~@body
     (catch :config-error {error# :config-error}
       (println (format "Error: %s" error#))
       (System/exit 1))))
