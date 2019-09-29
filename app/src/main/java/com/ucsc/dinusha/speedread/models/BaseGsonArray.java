package com.ucsc.dinusha.speedread.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseGsonArray {

    @SerializedName("windowJsonArray")
    @Expose
    public List<WindowsGsonObject> windowJsonArray = null;
    @SerializedName("wordsJsonArray")
    @Expose
    public List<WordsGsonObject> wordsJsonArray = null;
}
