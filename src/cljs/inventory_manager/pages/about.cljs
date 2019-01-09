(ns inventory-manager.pages.about
  (:require [baking-soda.core :as b]
            [reagent.core :as r]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [markdown.core :refer [md->html]]
            [inventory-manager.ajax :as ajax]
            [ajax.core :refer [GET POST]]
            [secretary.core :as secretary :include-macros true])
  (:import goog.History))


(defn render []
  [:div.container
   [:div.row
    [:div.col-md-12
     [:p [:a {:href "#/"} "Back"]]
     [:p "Built and maintained by Total Web Connections LLC"]]]])
