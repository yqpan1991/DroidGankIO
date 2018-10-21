package com.edus.gankio.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CommonResult<Data> {

    @SerializedName("error")
    @Expose
    public boolean error;

    @SerializedName("results")
    @Expose
    public Data results = null;
}
