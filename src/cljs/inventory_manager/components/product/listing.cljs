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
         :params {:contents @listing}}))

(defn render [id]
  (let [listing (atom {:list_price "" :est_shipping_cost ""})]
    (fn []
    [:div.Listing-section
      [:p "Create Your Listing"]
      [:input {:type "text" :placeholder "List Price" :on-change #(swap! listing conj {:list_price (-> % .-target .-value)})}]
      [:input {:type "text" :placeholder "Estimated Shipping Cost" :on-change #(swap! listing conj {:est_shipping_cost (-> % .-target .-value)})}]
      [:button {:on-click #(add-listing listing id)} "Add"]])))
