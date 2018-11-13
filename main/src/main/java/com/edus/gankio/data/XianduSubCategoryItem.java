package com.edus.gankio.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class XianduSubCategoryItem implements Serializable {

    private static final long serialVersionUID = -8913127661619931372L;

    @SerializedName("_id")
    @Expose
    public String id;

    @SerializedName("created_at")
    @Expose
    public String createdAt;

    @SerializedName("icon")
    @Expose
    public String icon;

    @SerializedName("id")
    @Expose
    public String subCategoryId;

    @SerializedName("title")
    @Expose
    public String title;

}
