(ns inventory-manager.components.product.sold
  (:require [reagent.core :as r]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [inventory-manager.ajax :as ajax]
            [ajax.core :refer [GET POST]])
  (:import goog.History))

(defn sell-item [sold-price id]
  (POST  (str "/api/product/" id "/sold")
        {:headers {"Accept" "application/transit+json"}
         :params {:contents {:sold_price @sold-price}}})
         :success (js/alert "Product Marked As Sold!"))

(defn render [current-product]
  (let [sold-price (atom "")]
    (fn []
    [:div.Sold-section
      [:input {:type "text" :placeholder "Sold Price" :on-change #(reset! sold-price (-> % .-target .-value))}]
      [:button {:on-click #(sell-item sold-price (:id @current-product))} "Sold!"]])))
