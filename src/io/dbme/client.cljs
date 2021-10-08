(ns io.dbme.client
  (:require [reagent.dom]))

(defn app []
  [:h2 "Hello from clojurescript!"])

;; start is called by init and after code reloading finishes
(defn ^:dev/after-load start []
  (js/console.log "start")
  (reagent.dom/render [app] (js/document.getElementById "app")))

(defn ^:export init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (js/console.log "init")
  (start))

;; this is called before any code is reloaded
(defn ^:dev/before-load stop []
  (js/console.log "stop"))
