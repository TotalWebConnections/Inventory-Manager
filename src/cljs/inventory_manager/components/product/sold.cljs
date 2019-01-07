(ns inventory-manager.components.product.sold
  (:require [reagent.core :as r]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [inventory-manager.ajax :as ajax]
            [ajax.core :refer [GET POST]])
  (:import goog.History))

(defn sell-item [sold-price]
  (js/console.log @sold-price))

(defn render [current-product]
  (let [sold-price (atom "")]
    (fn []
    [:div.Sold-section
      [:input {:type "text" :placeholder "Sold Price" :on-change #(reset! sold-price (-> % .-target .-value))}]
      [:button {:on-click #(sell-item sold-price)} "Sold!"]])))
