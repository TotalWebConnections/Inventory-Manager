(ns inventory-manager.components.sidebar
  (:require [reagent.core :as r]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [inventory-manager.ajax :as ajax]
            [ajax.core :refer [GET POST]])
  (:import goog.History))



(defn render [active-view]
  [:div.Sidebar
    [:ul.Sidebar-links
      [:li [:a {:href "#/"} "Active Listings"]]
      [:li [:a {:href "#/unlisted"} "Unlisted Items"]]
      [:li [:a {:href "#/sold"} "Sold Listings"]]
      [:li [:a {:href "#/about"} "Reports"]]]])
