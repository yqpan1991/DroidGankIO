package com.edus.gankio.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class XianduCategoryItem implements Serializable {

    private static final long serialVersionUID = -6061203454991626811L;

    @SerializedName("_id")
    @Expose
    public String id;

    @SerializedName("en_name")
    @Expose
    public String enName;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("rank")
    @Expose
    public int rank;
}
