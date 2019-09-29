package com.ucsc.dinusha.speedread.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WordsGsonObject extends BaseGsonObject {

    @SerializedName("wordsPerMin")
    @Expose
    public String wordsPerMin;
    @SerializedName("wordsPerWindow")
    @Expose
    public String wordsPerWindow;
}
