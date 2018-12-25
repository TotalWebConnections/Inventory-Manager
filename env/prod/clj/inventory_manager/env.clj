(ns inventory-manager.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[inventory-manager started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[inventory-manager has shut down successfully]=-"))
   :middleware identity})
