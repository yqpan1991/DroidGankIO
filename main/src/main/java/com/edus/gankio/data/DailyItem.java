package com.edus.gankio.data;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by panda on 2017/5/9.
 */

public class DailyItem {

    @SerializedName("_id")
    @Expose
    public String id;

    @SerializedName("content")
    @Expose
    public String content;

    @SerializedName("created_at")
    @Expose
    public String createdAt;

    @SerializedName("publishedAt")
    @Expose
    public String publishedAt;

    @SerializedName("rand_id")
    @Expose
    public String randId;

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("updated_at")
    @Expose
    public String updatedAt;

    private transient boolean isImageUrlInited;
    private transient String imageUrl;



    public String getDisplayPublishAt(){
        if(TextUtils.isEmpty(publishedAt)){
            return "UNKNOWN";
        }
        return publishedAt.replace('T', ' ').replace('Z', ' ');
    }


    public String getImgUrl() {
        if(isImageUrlInited){
            return imageUrl;
        }
        isImageUrlInited = true;
        if(TextUtils.isEmpty(content)){
            imageUrl = "";
            return imageUrl;
        }
        int start = content.indexOf("src=\"") + 5;

        int jpgEnd = content.indexOf(".jpg");
        int end = jpgEnd + 4;
        if (jpgEnd == -1) {
            jpgEnd = content.indexOf(".jpeg");
            end = jpgEnd + 5;
        }
        if (jpgEnd == -1) {
            jpgEnd = content.indexOf(".png");
            end = jpgEnd + 4;
        }
        if (end > start){
            imageUrl = content.substring(start, end);
        }else {
            imageUrl = "";
        }
        return imageUrl;
    }
}
