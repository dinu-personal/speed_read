package com.ucsc.dinusha.speedread.sharedPreferenceDB;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class WebViewTextSharedPreferences {

    public static WebViewTextSharedPreferences sInstance;

    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public WebViewTextSharedPreferences(Context context) {
        this.mContext = context;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mSharedPreferences.edit();
    }

    public static WebViewTextSharedPreferences getInstance(Context context) {
        if(sInstance == null){
            sInstance = new WebViewTextSharedPreferences(context);
        }
        return sInstance;
    }

    public void addWebViewText(String webViewText) {
        mEditor.putString("webViewtext", webViewText);
        mEditor.commit();
    }

    public String getWebViewText() {
        return mSharedPreferences.getString("webViewtext", null);
    }

}
