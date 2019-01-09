(ns inventory-manager.core
  (:require [baking-soda.core :as b]
            [reagent.core :as r]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [markdown.core :refer [md->html]]
            [inventory-manager.ajax :as ajax]
            [ajax.core :refer [GET POST]]
            [secretary.core :as secretary :include-macros true]
            [inventory-manager.pages.about :as about-page]
            [inventory-manager.pages.home :as home-page]
            [inventory-manager.pages.unlisted :as unlisted-page]
            [inventory-manager.pages.sold :as sold-page]
            [inventory-manager.pages.product :as product-page]
            [inventory-manager.components.new-product :as new-product]
            [inventory-manager.components.sidebar :as sidebar])
  (:import goog.History))

(defonce session (r/atom {:page :home}))
(defonce items (r/atom "")) ; Holds a reference to all the current Items in the Database
(defonce active-view (r/atom {:new false}))
(defonce current-product (r/atom ""))

; Handles loading the initial products
(defn set-products [products]
  (reset! items (js->clj (.parse js/JSON products) :keywordize-keys true )))
(GET "/api/products" {:handler set-products})

; the navbar components are implemented via baking-soda [1]
; library that provides a ClojureScript interface for Reactstrap [2]
; Bootstrap 4 components.
; [1] https://github.com/gadfly361/baking-soda
; [2] http://reactstrap.github.io/

(defn nav-link [uri title page]
  [b/NavItem
   [b/NavLink
    {:href   uri
     :active (when (= page (:page @session)) "active")}
    title]])

(defn navbar []
  (r/with-let [expanded? (r/atom true)]
    [b/Navbar {:light true
               :class-name "navbar-dark bg-primary"
               :expand "md"}
     [b/NavbarBrand {:href "/"} "inventory-manager"]
     [b/NavbarToggler {:on-click #(swap! expanded? not)}]
     [b/Collapse {:is-open @expanded? :navbar true}
      [:p {:on-click #(swap! active-view conj {:new "active"})} "Add Product"]
      [b/Nav {:class-name "mr-auto" :navbar true}
       [nav-link "#/about" "About" :about]]]]))

; TODO there's probably a better way to expose the atom to teh views
(defn home-page []
  (home-page/render items current-product))

(defn unlisted-page []
  (unlisted-page/render items current-product))

(defn sold-page []
  (sold-page/render items current-product))

(defn about-page []
  (about-page/render))

(defn product-page []
  (product-page/render current-product))

(defn new-product-component []
  (new-product/render active-view))

(def pages
  {:home #'home-page
   :unlisted #'unlisted-page
   :sold #'sold-page
   :about #'about-page
   :product #'product-page})

(defn page []
  [(pages (:page @session))])

;; -------------------------
;; Routes

(secretary/set-config! :prefix "#")

(secretary/defroute "/" []
  (swap! session assoc :page :home))

(secretary/defroute "/unlisted" []
  (swap! session assoc :page :unlisted))

(secretary/defroute "/sold" []
  (swap! session assoc :page :sold))

(secretary/defroute "/about" []
  (swap! session assoc :page :about))

(secretary/defroute "/product" []
  (swap! session assoc :page :product))

;; -------------------------
;; History
;; must be called after routes have been defined
(defn hook-browser-navigation! []
  (doto (History.)
        (events/listen
          HistoryEventType/NAVIGATE
          (fn [event]
            (secretary/dispatch! (.-token event))))
        (.setEnabled true)))

;; -------------------------
;; Initialize app
(defn fetch-docs! []
  (GET "/docs" {:handler #(swap! session assoc :docs %)}))


  (defn render-new [active-view]
    (fn []
      [:div.New {:class (:new @active-view)}
       [:div.row
        [:div.col-md-12
         [:img {:src "/img/warning_clojure.png"}]]]]))

(defn mount-components []
  (r/render [#'new-product-component] (.getElementById js/document "offscreen-content"))
  (r/render [#'navbar] (.getElementById js/document "navbar"))
  (r/render [#'page] (.getElementById js/document "app"))
  (r/render [#'sidebar/render] (.getElementById js/document "sidebar")))

(defn init! []
  (ajax/load-interceptors!)
  (fetch-docs!)
  (hook-browser-navigation!)
  (mount-components))
