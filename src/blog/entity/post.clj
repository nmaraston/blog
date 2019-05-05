(ns blog.entity.post
  (:require [clojure.string :as string]))

(defn- validate [err-msg & conditions]
  (let [errs (map last (filter #(not (first %)) (partition 2 conditions)))
        msg (string/join " " (cons err-msg errs))]
    (if (empty? errs)
     nil
     (throw (IllegalArgumentException. msg)))))

(defn- validate-post [post]
  (validate
    "Failed to build post."
    (not (.isEmpty (:title post))) "title must not be empty."
    (not (.isEmpty (:content post))) "content must not be empty."))

(defn post 
  ([new-post]
   (post (:title new-post) (:content new-post))) 
  ([title content]
   (let [new-post {:title title :content content}]
    (or 
      (validate-post new-post)
      new-post))))





