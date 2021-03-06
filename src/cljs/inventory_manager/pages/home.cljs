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
  (secretary/dispatch! "/product"))

(defn render [items current-product]
  [:div.container.card
    [:div.row>div.col-sm-12
      [:h2 "Active Items"]
      [:table
        [:tr
          [:th "Name"]
          [:th "Listed Date"]
          [:th "Quantity"]
          [:th "Status"]]
        (doall (for [product @items]
          (if (or (= "Listed" (:status product)) (= "Partial" (:status product)))
            (let [itemDate (js/Date. (:list_date product))]
              [:tr {:on-click #(open-product-page current-product product)}
                [:td (:name product)]
                [:td (str (+ (.getMonth itemDate) 1) "/" (.getDate itemDate) "/" (.getFullYear itemDate))]
                [:td (:quantity product)]
                [:td (:status product)]]))))]]])
