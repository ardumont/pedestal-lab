; Copyright 2013 Relevance, Inc.

; The use and distribution terms for this software are covered by the
; Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0)
; which can be found in the file epl-v10.html at the root of this distribution.
;
; By using this software in any fashion, you are agreeing to be bound by
; the terms of this license.
;
; You must not remove this notice, or any other, from this software.

(ns pedestal-app.app
  (:require [io.pedestal.app :as app]
            [io.pedestal.app.protocols :as p]
            [io.pedestal.app.render :as render]
            [io.pedestal.app.render.push :as push]
            [io.pedestal.app.messages :as msg]
            [domina :as dom]))

;; update the state via the count-model function
(defn count-model [old-state message]
  (condp = (msg/type message)
    msg/init (:value message)
    :inc (inc old-state)))

;; the inputs are named here to help in understanding the sample but really, only the new-value is used!
(defn render-value [renderer [_ _ old-value new-value] input-queue]
  (dom/destroy-children! (dom/by-id "content"))
  (dom/append! (dom/by-id "content")
               (str "<h1>" new-value " Hello Worlds</h1>")))

;; Application data flow to build the application.
;; (Description of functions and inputs are separated from the execution strategy)
(def count-app-flow {:transform {:count {:init 0 :fn count-model}}})

(defn receive-input [input-queue]
  (p/put-message input-queue {msg/topic :count msg/type :inc})
  (.setTimeout js/window #(receive-input input-queue) 3000))

(defn ^:export main []
  (let [;; build the application with the data flow
        app (app/build count-app-flow)
        ;; Compute the rendering function (which will be able to make senses of deltas)
        ;; All changes of value concerning the "content" (dom) will be sent to the render-value function
        render-fn (push/renderer "content" [[:value [:*] render-value]])]
    ;; Renderer receives only delta of what has changed.
    ;; This way, they can concentrate on small part and pieces, which makes them small and simple
    (render/consume-app-model app render-fn)
    (receive-input (:input app))
    (app/begin app)))
