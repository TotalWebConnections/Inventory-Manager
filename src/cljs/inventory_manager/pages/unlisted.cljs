(ns inventory-manager.pages.unlisted
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
  [:div.Unlisted.card
    [:div.row>div.col-sm-12
      [:h2 "Unlisted Items"]
      [:table
        [:tr
          [:th "Name"]
          [:th "Purchase Date"]
          [:th "Quantity"]
          [:th "Status"]]
        (doall (for [product @items]
          (if (= "N/A" (:status product))
            (let [itemDate (js/Date. (:purchase_date product))]
              [:tr {:on-click #(open-product-page current-product product)}
                [:td (:name product)]
                [:td (str (+ (.getMonth itemDate) 1) "/" (.getDate itemDate) "/" (.getFullYear itemDate))]
                [:td (:quantity product)]
                [:td (:status product)]]))))]]])
