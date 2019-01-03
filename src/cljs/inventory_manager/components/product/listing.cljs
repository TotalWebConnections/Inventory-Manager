(ns inventory-manager.components.product.listing
  (:require [reagent.core :as r]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [inventory-manager.ajax :as ajax]
            [ajax.core :refer [GET POST]])
  (:import goog.History))



(defn render []
    (fn []
    [:div.Listing-section]
      [:p "Listing Details"]))
