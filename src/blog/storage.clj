(ns blog.storage)

(def id-counter (atom 0))
(def posts (atom []))

(defn next-id []
  (str (swap! id-counter inc)))

(defn add-post [post]
  (let [id (next-id)
        id-post (assoc post :id id)]
    (and
      (swap! posts #(conj % id-post))
      id-post)))

(defn get-post [id]
  (some #(when (= id (% :id)) %) @posts))

(defn get-all-posts [] @posts)

