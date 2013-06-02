(defproject rhl-squid "0.1.0"
  :description "move events from meetup.com to other mediums"
  :url "https://github.com/rogue-hack-lab/rhl-squid"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/core.incubator "0.1.2"]
                 [org.clojure/tools.reader "0.7.4"]
                 [slingshot "0.10.3"]
                 [clj-http "0.7.2"]]
  :main rhl-squid.core)
