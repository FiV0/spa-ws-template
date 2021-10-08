(ns io.dbme.client
  (:require [cljs.reader]
            [io.dbme.frontend.events]
            [io.dbme.frontend.socket :as socket]
            [io.dbme.frontend.subs]
            [lambdaisland.glogi :as log]
            [lambdaisland.glogi.console :as glogi-console]
            [re-frame.core :as rf]
            [reagent.dom]
            [reagent.core :as r]))

(glogi-console/install!)

(log/set-levels
 {:glogi/root   :info    ;; Set a root logger level, this will be inherited by all loggers
  ;; 'my.app.thing :trace   ;; Some namespaces you might want detailed logging
  })

(defn connect-button []
  [:input {:type     :button
           :value    "Connect"
           :disabled @(rf/subscribe [:app/connected])
           :on-click #(rf/dispatch [:app/connect])}])

(defn input-field []
  (let [state (r/atom {:text ""
                       :error false})]
    (fn []
      (cond-> [:div
               [:textarea {:id "text-field"
                           :rows 5
                           :value (:text @state)
                           :on-change #(swap! state assoc :text (.. % -target -value))}]
               [:input {:type     :button
                        :value    "Send"
                        :disabled (not @(rf/subscribe [:app/connected]))
                        :on-click #(try
                                     (rf/dispatch [:app/send (cljs.reader/read-string (:text @state))])
                                     (swap! state assoc :text "" :error false)
                                     (catch js/Error _
                                       (swap! state assoc :error true)))}]]
        (:error @state) (conj [:div {:style {:color :red}} "Input not valid edn"])))))


(defn app []
  [:div
   [connect-button]
   [:h2
    (str @(rf/subscribe [:app/data]))]
   [input-field]])

;; start is called by init and after code reloading finishes
(defn ^:dev/after-load start! []
  (js/console.log "start")
  (rf/dispatch-sync [:app/init])
  ;; In case you immediatly want to create a connection
  ;; (socket/create-client!)
  ;; (socket/start-router!)
  (reagent.dom/render [app] (js/document.getElementById "app")))

(defn ^:export init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (js/console.log "init")
  (start!))

;; this is called before any code is reloaded
(defn ^:dev/before-load stop []
  (js/console.log "stop")
  (socket/stop-router!))
