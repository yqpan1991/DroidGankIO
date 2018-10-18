package com.edus.gankio.data;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Demo {

    @SerializedName("error")
    @Expose
    public Boolean error;
    @SerializedName("results")
    @Expose
    public List<Result> results = null;

    @Override
    public String toString() {
        return "Demo{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}