(ns inventory-manager.pages.product
  (:require [baking-soda.core :as b]
            [reagent.core :as r]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [markdown.core :refer [md->html]]
            [inventory-manager.ajax :as ajax]
            [ajax.core :refer [GET POST]]
            [inventory-manager.components.product.listing :as listing]
            [inventory-manager.components.product.sold :as sold]
            [secretary.core :as secretary :include-macros true])
  (:import goog.History))



(defn render [current-product]
  (fn []
    [:div.container.Product.card
     [:div
      [:div.col-md-12.Product-header.header
       [:h2 (:name @current-product)]
       [:p {:on-click #(secretary/dispatch! "/")} "Back"]]
       [:div.col-md-12.Product-content]
      (if (not= "Sold" (:status @current-product))
        (do
          [:div.col-md-12
            [:h4 "Listing Details"]
            [listing/render current-product]]))
      (if (and (not= "Sold" (:status @current-product)) (not= "N/A" (:status @current-product)))
        (do
          [:div.col-md-12
            [:h4 "Mark As Sold"]
            [sold/render current-product]]))
        (if (= "Sold" (:status @current-product))
          (do
            [:div.col-md-12
              [:h4 (str "Sold On: " (:sold_date @current-product))]
              [:p (str "Purchase Price: " (:purchase_price  @current-product))]
              [:p (str "Sold For: " (:sold_price @current-product))]
              [:p (str "Shipping Cost: " (:shipping_cost @current-product))]
              [:p (str "Fees: " (:listing_fees  @current-product))]
              [:p (str "Sold Amount: " (:sold_amount @current-product))]
              [:p (str "Profit: " (* (-
                    (:sold_price @current-product)
                    (:purchase_price @current-product)
                    (:listing_fees @current-product)
                    (:actual_shipping_cost @current-product)) (:sold_amount @current-product)))]]))
        ]]))
