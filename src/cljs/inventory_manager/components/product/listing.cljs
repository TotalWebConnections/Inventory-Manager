(ns inventory-manager.components.product.listing
  (:require [reagent.core :as r]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [inventory-manager.ajax :as ajax]
            [ajax.core :refer [GET POST]])
  (:import goog.History))

(defn add-listing [listing id]
  (POST  (str "/api/product/" id)
        {:headers {"Accept" "application/transit+json"}
         :params {:contents @listing}})
         :success (js/alert "Product Updated!"))

(defn render [current-product]
  (let [listing (atom {
                  :list_price (:list_price @current-product)
                  :est_shipping_cost (:est_shipping_cost @current-product)
                  :listing_fees (:listing_fees @current-product)})]
    (fn []
      [:div.Listing-section
        [:p "Create Or Edit Your Listing"]
        [:input {:type "text" :placeholder "List Price" :default-value (:list_price @current-product) :on-change #(swap! listing conj {:list_price (-> % .-target .-value)})}]
        [:input {:type "text" :placeholder "Estimated Shipping Cost" :default-value (:est_shipping_cost @current-product)  :on-change #(swap! listing conj {:est_shipping_cost (-> % .-target .-value)})}]
        [:input {:type "text" :placeholder "Listing Fees" :default-value (:listing_fees @current-product)  :on-change #(swap! listing conj {:listing_fees (-> % .-target .-value)})}]
        [:button {:on-click #(add-listing listing (:id @current-product))} "Add"]])))
