(ns api-events.core
  (:require [clojure.java.io :as io]

            [hiccup.core :refer [html]])
  (:gen-class))

(defn separator [margin-top] [:separator {:marginTop margin-top}])
(defn shadow-separator [margin-top] [:separator
                                     {:style "1"
                                      :margintop margin-top
                                      :marginbottom "20"
                                      :align ""
                                      :height "3"
                                      :width ""
                                      :color ""
                                      :shadow "5"}])
(defn column
  [{:keys [type last] :or {last ""}} content]
  [:column {:type type
            :last last
            :nospace ""
            :bgcolor ""
            :bgimage ""
            :bgpattern ""
            :bgtransparency ""
            :fontsize ""
            :border ""
            :borderonly ""
            :borderstyle "solid"
            :bordercolor ""
            :textalign ""
            :textcolor ""
            :textjustify ""
            :radius ""
            :verticalmiddle ""
            :nopadding ""
            :lrpadding ""
            :tbpadding ""} content])

  (defn title [title]
    (html [:div.title.animate.none.animated
           {:data-wow-delay "0.1" :style "visibility:visible;width:100%"}
           [:h2 (str title ":")]
           [:div.title-holder
            [:div.title-style]]]))

(defn review [{:keys [hour title]}]
  (vector
   (html [:div.review
          [:div.review-border
           [:div.review-content
            [:h2
             [:strong (str hour " " title)]]]]])
   (separator 10)))

(defn speaker-review
[{:keys [hour title description]}
 {:keys [name role picture company company-url company-logo]}]
(vector [:review {:style ""
                  :name name
                  :image picture
                  :designation role
                  :company company
                  :company-url company-url
                  :logo company-logo
                  :icon ""
                  :textcolor ""
                  :bordercolor ""
                  :urlcolor ""
                  :urlovercolor ""
                  :quoteicon ""
                  :radius ""
                  :namecolor ""
                  :designationcolor ""}
         [(html [:div
                 [:h2 [:strong (str hour " " title)]]
                 [:h2 [:span {:style "font-size:20px;color:#adadad"} name]]
                 description])]]
        (shadow-separator 5)
        (separator 10)))

(defn team-member [{:keys [name picture role company company-url linkedin]}]
  [:team_member {:bgcolor "#222"
                 :namecolor "#fff"
                 :name name
                 :photo picture
                 :soc_ld linkedin}
   (str role (html [:a {:style "display:block" :href company-url} company]))])

(defn content-box [content]
  [:content_box {:title ""
                 :titlemargin ""
                 :titlecolor "#000"
                 :titlefontsize "Title Font Size"
                 :rollovertitlecolor ""
                 :icon ""
                 :iconcolor ""
                 :iconborder ""
                 :iconbordercolor ""
                 :rollovericonbordercolor ""
                 :style "1"
                 :iconbgcolor ""
                 :rollovericoncolor ""
                 :rollovericonbgcolor ""
                 :iconshadowcolor ""
                 :rollovericonshadowcolor ""
                 :textcolor ""
                 :textcolorover ""
                 :container ""
                 :bgtransparency ""
                 :shadow ""
                 :button ""
                 :buttonalign "left"
                 :buttonbgcolor ""
                 :buttonbgovercolor ""
                 :buttontextcolor ""
                 :justify ""
                 :align ""
                 :link ""
                 :image ""
                 :imageovereffect "0"
                 :animation "none"
                 :animation_delay ""
                 :animation_repeat ""
                 :animation_duration ""}
   content])

(defn content-boxes [speakers]
  (vector
   "<div id=\"team_custom\">"
   [:content_boxes {:class "test"}
    (mapv (fn [[_ s]] (content-box (team-member s))) speakers)]
   "</div>"))

(defn fancybox [content {:keys [title linecolor shadow line] :or {title "" linecolor "" shadow "" line ""}}]
  [:fancybox {:title title
              :linecolor linecolor
              :shadow shadow
              :line line
              :button ""
              :bggradstart "#fafafa"
              :bggradend "#ffffff"}
   content])

(defn inscriptions-form [{:keys [name contact-form-id]}]
  [:fullwidth {:top "40"
               :bottom "40"
               :bgcolor "#000"
               :textcolor "#fff"
               :bgimage "https://api-ne.ch/wp-content/uploads/2018/10/ama-dablam-2064522.jpg"
               :scrolltype ""
               :bgpattern ""
               :bgtransparency "80"
               :textalign ""
               :innershadow ""
               :bordertop ""
               :borderbottom ""
               :tbcolor ""
               :bbcolor ""
               :innerbottomshadow ""
               :innerbottomshadowcolor ""
               :innerbottomshadowsize ""
               :gradientstart ""
               :gradientend ""
               :height ""
               :margintop ""
               :zindex ""
               :containerbgcolor ""
               :containertopradius ""
               :containerbotradius ""
               :containersideshadow ""}
   [:umform {:bgtransparency "10"
             :inputbg "#fff"
             :inputborder "0"
             :bordercolor ""
             :inputborderradius ""
             :inputtextcolor "#efefef"
             :inputpadding "10"
             :placetextcolor "#ccc"
             :btnbgcolor "accent"
             :btnbgovercolor "primary"
             :btntextcolor "#efefef"
             :btntextovercolor "#fff"
             :btntransparency "20"
             :inputbgfocus "accent"
             :btnwidth ""}
    [:contact-form-7 {:id contact-form-id
                      :title (str "Inscription événement - " name)}
     (str "Vous n'êtes pas encore membre? "
          (html [:a {:style "color: white"
                     :href "https://api-ne.ch/devenir-membre/"
                     :target "_blank"
                     :rel "noopener"}
                 "Inscrivez-vous ici!"]))]]])

(def logo-api
  (html
   [:img.wp-image-3044.size-full.alignnone
    {:src "https://api-ne.ch/wp-content/uploads/2019/03/logo-api-standard-PNG-devtalks-2.png"
     :width "706"
     :height "152"}]))

(def logo-hearc
  (html
   [:img.wp-image-3086.size-full.aligncenter
    {:src "https://api-ne.ch/wp-content/uploads/2019/03/GES-MAN3-MOD001-logo-couleur-cmyk.jpg"
     :width "712"
     :height "115"}]))

(def logo-hesso
  (html
   [:img.wp-image-3085.size-full.alignnone.aligncenter
    {:src "https://api-ne.ch/wp-content/uploads/2019/03/HESSOfrencouleur.gif"
     :width "125"
     :height "76"}]))

(defn details [{:keys [event location location-url date hour]}]
  (fancybox
   (html [:ul.cbp-l-project-details-list
          [:li [:strong "Événement"] event]
          [:li [:strong "Où"] [:a {:href location-url} location]]
          [:li [:strong "Date"] date]
          [:li [:strong "Heure"] hour]])
   {:shadow "2" :line "left"}))

(defn image [url]
  [:image {:src url
           :popsrc ""
           :lightbox "1"
           :link ""
           :linktarget ""
           :group "gal"
           :title ""
           :float ""
           :top ""
           :right ""
           :bottom ""
           :left ""
           :radius ""
           :border ""
           :hovereffect "1"
           :bordercolor "accent"
           :bordertrans ""
           :noborder ""
           :zindex ""
           :animation_type "none"
           :animation_delay ""}])

(defn event [{:keys [name description description2 inscriptions1 speakers program pictures] :as event}]
  (vector
   (separator 25)
   (column {:type "two_third"}
           [(separator 28)
            (title (str "<strong>" name "</strong>"))
            description
            (separator 25)
            description2
            (separator 42)
            (title "Programme de la soirée")
            (separator 10)
            (for [p program]
              (if-let [speaker-kw (:speaker p)]
                (speaker-review p (speaker-kw speakers))
                (review p)))
            (title "Intervenants")
            (content-boxes speakers)
            (separator 10)
            (title "Inscriptions")
            (fancybox inscriptions1 {:linecolor "orange"
                                     :shadow "2"
                                     :line "left"})
            (inscriptions-form event)])
   (column {:type "one_third"
            :last true}
           [logo-api
            (separator 25)
            (fancybox
             [logo-hearc
              (separator 10)
              logo-hesso]
             {:title "<em><strong>En partenariat avec :</strong></em>"
              :shadow "5"})
            (separator 10)
            (details event)
            (separator 10)
            (title "Photos des derniers DevTalks")
            (for [p pictures]
              [(image p)
               (separator 10)])])
   (separator 96)))

(defn render-opts [opts]
  (reduce-kv (fn [acc k v]
               (str acc
                    " "
                    (name k)
                    "="
                    (if (or ((complement string?) v) (seq v))
                      (str "\"" v "\"")
                      "\"\"")))
             ""
             opts))

(defn render [data]
  (prn "?")
  (prn data)
  (cond
    (keyword? (first data))
    (let [[tag opts content] data]
      (str "[" (name tag) (render-opts opts) "]"
           (if (vector? content) (render content) content)
           "[/" (name tag) "]"))

    (string? data)
    data

    (seq data)
    (reduce (fn [acc o] (str acc (render o))) "" data)))

(comment
  (spit "/home/arnaudgeiser/test.txt" (render (read-string (slurp (io/resource "events/devtalks_5.0.edn"))))))
