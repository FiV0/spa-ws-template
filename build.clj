(ns build
  (:require [clojure.tools.build.api :as b]))

(def lib 'clj.template/lib)
(def version (format "0.1.%s" (b/git-count-revs nil)))
(def class-dir "target/classes")
(def basis (b/create-basis {:project "deps.edn"}))
(def jar-file (format "target/%s-%s.jar" (name lib) version))
(def main 'scratch)

(defn clean
  "Cleans the target path."
  [_]
  (b/delete {:path "target"}))

(defn jar [_]
  (b/copy-dir {:src-dirs ["src" "resources"]
               :target-dir class-dir})
  (b/compile-clj {:basis basis
                  :src-dirs ["src"]
                  :class-dir class-dir})
  (b/uber {:class-dir class-dir
           :uber-file jar-file
           :main main
           :basis basis}))

(comment
  (clean nil)
  (jar nil))
