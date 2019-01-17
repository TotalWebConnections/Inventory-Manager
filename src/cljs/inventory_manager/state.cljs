(ns inventory-manager.state
  (:require [baking-soda.core :as b]
            [reagent.core :as r]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [markdown.core :refer [md->html]]
            [inventory-manager.ajax :as ajax]
            [ajax.core :refer [GET POST]]
            ; [inventory-manager.core :as core]
            [secretary.core :as secretary :include-macros true]))

(defonce items (r/atom "")) ; Holds a reference to all the current Items in the Database

(defn set-products [products]
  (reset! items (js->clj (.parse js/JSON products) :keywordize-keys true )))

(defn update-product-list []
  (GET "/api/products" {:handler set-products}))
