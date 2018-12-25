(ns user
  (:require [inventory-manager.config :refer [env]]
            [clojure.spec.alpha :as s]
            [expound.alpha :as expound]
            [mount.core :as mount]
            [inventory-manager.figwheel :refer [start-fw stop-fw cljs]]
            [inventory-manager.core :refer [start-app]]
            [inventory-manager.db.core]
            [conman.core :as conman]
            [luminus-migrations.core :as migrations]))

(alter-var-root #'s/*explain-out* (constantly expound/printer))

(defn start []
  (mount/start-without #'inventory-manager.core/repl-server))

(defn stop []
  (mount/stop-except #'inventory-manager.core/repl-server))

(defn restart []
  (stop)
  (start))

(defn restart-db []
  (mount/stop #'inventory-manager.db.core/*db*)
  (mount/start #'inventory-manager.db.core/*db*)
  (binding [*ns* 'inventory-manager.db.core]
    (conman/bind-connection inventory-manager.db.core/*db* "sql/queries.sql")))

(defn reset-db []
  (migrations/migrate ["reset"] (select-keys env [:database-url])))

(defn migrate []
  (migrations/migrate ["migrate"] (select-keys env [:database-url])))

(defn rollback []
  (migrations/migrate ["rollback"] (select-keys env [:database-url])))

(defn create-migration [name]
  (migrations/create name (select-keys env [:database-url])))


