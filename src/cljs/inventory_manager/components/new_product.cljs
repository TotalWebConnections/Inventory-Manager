(ns inventory-manager.components.new-product
  (:require [reagent.core :as r]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [inventory-manager.ajax :as ajax]
            [inventory-manager.state :refer [handle-state-change]]
            [ajax.core :refer [GET POST]])
  (:import goog.History))

(defn hide-new-product [active-view]
  (swap! active-view conj {:new false}))

(defn product-add-success []
  (handle-state-change "update-product-list"))

(defn add-product [product active-view]
  (POST "/api/product"
        {:headers {"Accept" "application/transit+json"}
         :params {:contents @product}
         :handler product-add-success})
         (hide-new-product active-view))

(defn render [active-view]
  (let [product (atom {:name ""
                       :sku ""
                       :purchase_price ""
                       :quantity ""
                       :est_shipping_cost ""
                       :categories ""
    })]
    (fn []
      [:div.New {:class (:new @active-view)}
        [:div.New-header
          [:h3 "New Product"]
          [:p.closeNew {:on-click #(swap! active-view conj {:new false})} "X"]]
        [:div.New-body]
          [:input {:type "text" :placeholder "name" :on-change #(swap! product conj {:name (-> % .-target .-value)})}]
          [:input {:type "text" :placeholder "sku" :on-change #(swap! product conj {:sku (-> % .-target .-value)})}]
          [:input {:type "text" :placeholder "Purchase Price" :on-change #(swap! product conj {:purchase_price (-> % .-target .-value)})}]
          [:input {:type "text" :placeholder "quantity" :on-change #(swap! product conj {:quantity (-> % .-target .-value)})}]
          [:input {:type "text" :placeholder "est_shipping_cost" :on-change #(swap! product conj {:est_shipping_cost (-> % .-target .-value)})}]
          [:input {:type "text" :placeholder "Categories" :on-change #(swap! product conj {:categories (-> % .-target .-value)})}]
          [:button {:on-click #(add-product product active-view)} "Add"]])))
