(defproject pedestal-lab "0.0.1-SNAPSHOT"
  :description "server part - pedestal learning playground"
  :url "https://github.com/ardumont/pedestal-lab"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.0"]
                 [io.pedestal/pedestal.service "0.1.2"]

                 ;; Remove this line and uncomment the next line to
                 ;; use Tomcat instead of Jetty:
                 [io.pedestal/pedestal.jetty "0.1.2"]
                 ;; [io.pedestal/pedestal.tomcat "0.1.2-SNAPSHOT"]

                 ;; Logging
                 [ch.qos.logback/logback-classic "1.0.7"]
                 [org.slf4j/jul-to-slf4j "1.7.2"]
                 [org.slf4j/jcl-over-slf4j "1.7.2"]
                 [org.slf4j/log4j-over-slf4j "1.7.2"]
                 [com.datomic/datomic-free "0.8.3826"]]
  :profiles {:dev {:source-paths ["dev"]}}
  :min-lein-version "2.0.0"
  :resource-paths ["config"]
  :main ^{:skip-aot true} pedestal-lab.server)
