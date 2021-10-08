(ns user
  (:require [clojure.java.browse :as browse]
            [shadow.cljs.devtools.api :as shadow]
            [shadow.cljs.devtools.server :as server]))

(defn cljs-repl
  "Connects to a given build-id. Defaults to `:app`."
  ([]
   (cljs-repl :app))
  ([build-id]
   (server/start!)
   (shadow/watch build-id)
   (shadow/nrepl-select build-id)))

(defn browse [port]
  (browse/browse-url (str "http://localhost:" port)))
