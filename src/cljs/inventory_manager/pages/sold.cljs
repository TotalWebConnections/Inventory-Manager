(ns inventory-manager.pages.sold
  (:require [baking-soda.core :as b]
            [reagent.core :as r]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [markdown.core :refer [md->html]]
            [inventory-manager.ajax :as ajax]
            [ajax.core :refer [GET POST]]
            [secretary.core :as secretary :include-macros true])
  (:import goog.History))

(defn open-product-page [current-product product]
  (reset! current-product product)
  (secretary/dispatch! "/product"))

(defn render [items current-product]
  [:div.Sold
    [:div.row>div.col-sm-12
      [:h2 "Unlisted Items"]
      [:table
        [:tr
          [:th "Name"]
          [:th "Purchase Date"]
          [:th "Quantity"]
          [:th "Status"]]
        (doall (for [product @items]
          (if (= "Sold" (:status product))
            (do
              [:tr {:on-click #(open-product-page current-product product)}
                [:td (:name product)]
                [:td (:purchase_date product)]
                [:td (:quantity product)]
                [:td (:status product)]]))))]]])
