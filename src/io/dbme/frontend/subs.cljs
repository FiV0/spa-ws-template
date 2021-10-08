(ns io.dbme.frontend.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 :app/data
 (fn [db ]
   (get-in db [:data])))

(rf/reg-sub
 :app/connected
 (fn [db _]
   (true? (get-in db [:connected]))))
