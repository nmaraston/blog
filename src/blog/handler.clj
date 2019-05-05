(ns blog.handler
  (:require [clojure.java.io :as io]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.util.response :refer [response not-found created]]
            [blog.storage :as storage]
            [blog.entity.post :as post-entity]
            [stencil.core :refer [render-string]]))

(defn read-template [filename]
  (slurp (io/resource filename)))

(defroutes app-routes
  (POST "/posts" request
     (let [post-request (post-entity/post (:body request))
           post (storage/add-post post-request)]
       (created (str "/posts/" (:id post)) post)))
  (GET "/posts/:id" [id]
    (let [post (storage/get-post id)]
      (if post (response post) (not-found {}))))
  (GET "/posts" request
    (response (storage/get-all-posts)))
  (route/not-found "Not Found"))

(def app (->
           app-routes
           (wrap-json-body {:keywords? true})
           (wrap-json-response)))
