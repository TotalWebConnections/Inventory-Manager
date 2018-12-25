(ns inventory-manager.app
  (:require [inventory-manager.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
