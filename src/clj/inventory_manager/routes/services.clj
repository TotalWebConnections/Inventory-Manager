(ns inventory-manager.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [cheshire.core :refer :all]
            [inventory-manager.db.core :as db]
            [compojure.api.meta :refer [restructure-param]]
            [buddy.auth.accessrules :refer [restrict]]
            [buddy.auth :refer [authenticated?]]))

; Cheshire properly handle datetimes
(extend-protocol cheshire.generate/JSONable
  java.time.LocalDateTime
    (to-json [dt gen]
    (cheshire.generate/write-string gen (str dt))))

(defn access-error [_ _]
  (unauthorized {:error "unauthorized"}))

(defn wrap-restricted [handler rule]
  (restrict handler {:handler  rule
                     :on-error access-error}))

(defmethod restructure-param :auth-rules
  [_ rule acc]
  (update-in acc [:middleware] conj [wrap-restricted rule]))

(defmethod restructure-param :current-user
  [_ binding acc]
  (update-in acc [:letks] into [binding `(:identity ~'+compojure-api-request+)]))

(defapi service-routes
  {:swagger {:ui "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version "1.0.0"
                           :title "Sample API"
                           :description "Sample Services"}}}}

  (GET "/authenticated" []
       :auth-rules authenticated?
       :current-user user
       (ok {:user user}))
  (context "/api" []
    :tags ["thingie"]

    (POST "/product" []
      :return      String
      :body-params [contents :- s/Any]
      :summary     "Adds a new Product"
      (db/create-product! contents)
      (ok (:name contents)))

    (POST "/product/:id" []
      :return      String
      :path-params [id :- String]
      :body-params [contents :- s/Any]
      :summary     "Updates a product listing"
      (db/update-product! (conj {:id id} contents))
      (ok "Listing Updated"))

    (POST "/product/:id/sold" []
      :return      String
      :path-params [id :- String]
      :body-params [contents :- s/Any]
      :summary     "Marks a product as sold"
      (let [product (first (db/get-single-product {:id id}))]
        (if (or (= (:quantity product) 1)  (= (+ (:sold_amount product) 1) (:quantity product)) ) ;equal to one or just one off the total
          (db/update-product-sold {:id id :sold_amount (+ (:sold_amount product) 1)
            :sold_price (+ (bigdec (:sold_price contents)) (:sold_price product))})
          (db/update-product-sold-partial {:id id :sold_amount (+ (:sold_amount product) 1)
            :sold_price (+ (bigdec (:sold_price contents)) (:sold_price product))})) ; end if
        (ok "Product Marked as Sold")))


    (GET "/products" []
      :return      s/Any
      :summary     "Return all current Products"
      (ok (generate-string (db/get-products)))))) ; We manually encode it here as it breaks on api requests,
                                                ; theres probably a better way to do this in some sort of middleware
