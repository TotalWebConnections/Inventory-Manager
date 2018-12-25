(ns inventory-manager.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [inventory-manager.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[inventory-manager started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[inventory-manager has shut down successfully]=-"))
   :middleware wrap-dev})
