(ns inventory-manager.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [inventory-manager.core-test]))

(doo-tests 'inventory-manager.core-test)

