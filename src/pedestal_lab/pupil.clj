(ns pedestal-lab.pupil
  (:require [datomic.api :as d :refer (q)]
            [clojure.string :as s]))

(def uri "datomic:mem://pupils")

(def schema-tx (read-string (slurp "resources/pedestal-lab/pupils-schema.dtm")))
(def data-tx (read-string (slurp "resources/pedestal-lab/pupils-data.dtm")))

(defn init-db
  "Initialize data in datomic db."
  []
  (when (d/create-database uri)
    (let [conn (d/connect uri)]
      @(d/transact conn schema-tx)
      @(d/transact conn data-tx))))

(init-db)

(defn get-pupils
  "Retrieve the pupils from the db"
  []
  (init-db)
  (let [conn (d/connect uri)]
    (-> '[:find ?pn ?pfn ?pb ?pa
          :where
          [?c :pupil/name ?pn]
          [?c :pupil/firstname ?pfn]
          [?c :pupil/birthdate ?pb]
          [?c :pupil/active? ?pa]]
        (q (d/db conn)))))
