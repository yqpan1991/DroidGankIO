package com.edus.gankio.data;


import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * Created by panda on 2017/5/9.
 */

public class XianduData implements Serializable {


    private static final long serialVersionUID = -1230672554987683838L;

    @SerializedName("_id")
    @Expose
    public String id;

    @SerializedName("content")
    @Expose
    public String content;

    @SerializedName("cover")
    @Expose
    public String cover;

    @SerializedName("crawled")
    @Expose
    public String crawled;


    @SerializedName("created_at")
    @Expose
    public String createdAt;

    @SerializedName("deleted")
    @Expose
    public boolean deleted;

    @SerializedName("published_at")
    @Expose
    public String publicAt;

    @SerializedName("raw")
    @Expose
    public String raw;

    @SerializedName("site")
    @Expose
    public Site site;

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("uid")
    @Expose
    public String uid;

    @SerializedName("url")
    @Expose
    public String url;

    public String getDisplayPublishAt(){
        if(TextUtils.isEmpty(publicAt)){
            return null;
        }
        return publicAt.replace('T', ' ').replace('Z', ' ');
    }

    public static class Site implements Serializable{

        private static final long serialVersionUID = -5444303599333339772L;

        @SerializedName("cat_cn")
        @Expose
        public String categoryCn;

        @SerializedName("cat_en")
        @Expose
        public String categoryEn;

        @SerializedName("desc")
        @Expose
        public String desc;

        @SerializedName("feed_id")
        @Expose
        public String feedId;

        @SerializedName("icon")
        @Expose
        public String icon;

        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("name")
        @Expose
        public String name;

        @SerializedName("subscribers")
        @Expose
        public int subscribers;

        @SerializedName("type")
        @Expose
        public String type;

        @SerializedName("url")
        @Expose
        public String url;
    }


}
