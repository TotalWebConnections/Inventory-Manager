(ns inventory-manager.pages.product
  (:require [baking-soda.core :as b]
            [reagent.core :as r]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [markdown.core :refer [md->html]]
            [inventory-manager.ajax :as ajax]
            [ajax.core :refer [GET POST]]
            [inventory-manager.components.product.listing :as listing]
            [secretary.core :as secretary :include-macros true])
  (:import goog.History))



(defn render [current-product]
  (fn []
    [:div.container.Product
     [:div.row
      [:div.col-md-12.Product-header.header
       [:h2 (:name @current-product)]]
       [:div.col-md-12
        [:h4 "Details"]]
      [:div.col-md-12
        [:h4 "Listing"]
        [listing/render]]]]))
