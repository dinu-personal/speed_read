package com.ucsc.dinusha.speedread.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseGsonObject {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("fontSize")
    @Expose
    public String fontSize;
    @SerializedName("fontStyle")
    @Expose
    public String fontStyle;

}
