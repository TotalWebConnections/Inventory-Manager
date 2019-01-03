(ns inventory-manager.pages.home
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
  (js/console.log @current-product)
  (secretary/dispatch! "/product"))

(defn render [items current-product]
  [:div.container
    [:div.row>div.col-sm-12
      [:h2 "Dashboard"]
      [:table
        [:tr
          [:th "Name"]
          [:th "Purchase Date"]
          [:th "Quantity"]
          [:th "Status"]]
        (doall (for [product @items]
          [:tr {:on-click #(open-product-page current-product product)}
            [:td (:name product)]
            [:td (:purchase_date product)]
            [:td (:quantity product)]
            [:td (:status product)]]))]]])
