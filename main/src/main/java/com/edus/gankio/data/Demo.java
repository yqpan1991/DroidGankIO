package com.edus.gankio.data;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Demo {

    @SerializedName("error")
    @Expose
    public Boolean error;
    @SerializedName("commonResources")
    @Expose
    public List<CommonResource> commonResources = null;

    @Override
    public String toString() {
        return "Demo{" +
                "error=" + error +
                ", commonResources=" + commonResources +
                '}';
    }
}