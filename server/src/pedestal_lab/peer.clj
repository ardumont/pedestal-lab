(ns pedestal-lab.peer
  (:require [datomic.api :as d :refer (q)]))

(def uri "datomic:mem://pedestal-lab")

(def schema-tx (read-string (slurp "resources/pedestal-lab/schema.dtm")))
(def data-tx (read-string (slurp "resources/pedestal-lab/seed-data.dtm")))

(defn init-db []
  (when (d/create-database uri)
    (let [conn (d/connect uri)]
      @(d/transact conn schema-tx)
      @(d/transact conn data-tx))))

(defn results []
  (init-db)
  (let [conn (d/connect uri)]
    (q '[:find ?c :where [?e :hello/color ?c]] (d/db conn))))
