(ns inventory-manager.components.new-product
  (:require [reagent.core :as r]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [inventory-manager.ajax :as ajax]
            [ajax.core :refer [GET POST]])
  (:import goog.History))



(defn render [active-view]
  (fn []
    [:div.New {:class (:new @active-view)}
     [:div.row
      [:div.col-md-12
       [:img {:src "/img/warning_clojure.png"}]]]]))
