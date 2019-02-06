(ns inventory-manager.components.product.sold
  (:require [reagent.core :as r]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [inventory-manager.ajax :as ajax]
            [inventory-manager.state :refer [handle-state-change]]
            [ajax.core :refer [GET POST]])
  (:import goog.History))

(defn product-add-success []
  (handle-state-change "update-product-list")
  (js/alert "Product Marked As Sold!"))

(defn sell-item [sold-price shipping-cost id]
  (POST  (str "/api/product/" id "/sold")
        {:headers {"Accept" "application/transit+json"}
         :params {:contents {:sold_price @sold-price :shipping_cost @shipping-cost}}
         :handler product-add-success}))

(defn render [current-product]
  (let [sold-price (atom "") shipping-cost (atom "")]
    (fn []
    [:div.Sold-section
      [:input {:type "text" :placeholder "Sold Price" :on-change #(reset! sold-price (-> % .-target .-value))}]
      [:input {:type "text" :placeholder "Shipping Cost" :on-change #(reset! shipping-cost (-> % .-target .-value))}]
      [:button {:on-click #(sell-item sold-price shipping-cost (:id @current-product))} "Sold!"]])))
