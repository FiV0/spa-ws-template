(ns io.dbme.frontend.events
  (:require [re-frame.core :as rf]
            [io.dbme.frontend.socket :as socket]))

(rf/reg-event-db
 :app/init
 (fn [_ _]
   {:connected false
    :data {:initial :data}}))

(rf/reg-event-db
 :app/connected
 (fn [db [_ value]]
   (assoc-in db [:connected] value)))

(rf/reg-event-db
 :app/set-data
 (fn [db [_ data]]
   (assoc-in db [:data] data)))

(rf/reg-event-fx
 :app/connect
 (fn [_ _]
   (socket/start!)
   {:dispatch [:app/connected true]}))
