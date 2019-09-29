package com.ucsc.dinusha.speedread.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WindowsGsonObject extends BaseGsonObject {

    @SerializedName("windowPerLine")
    @Expose
    public String windowPerLine;
    @SerializedName("overlapingCharacters")
    @Expose
    public String overlapingCharacters;
    @SerializedName("speedType")
    @Expose
    public String speedType;
    @SerializedName("speed")
    @Expose
    public String speed;
}
