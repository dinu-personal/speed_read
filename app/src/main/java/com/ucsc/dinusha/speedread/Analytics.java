package com.ucsc.dinusha.speedread;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class Analytics {

    private static Analytics instance;
    private FirebaseAnalytics mFirebaseAnalytics;

    private Analytics(Context context){
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    public static Analytics getInstance(Context context) {
        if (instance == null) {
            instance = new Analytics(context);
        }
        return instance;
    }

    public void pushToAnalytics(String screenName) {
        Bundle params = new Bundle();
        params.putString("screen_name", screenName);
        mFirebaseAnalytics.logEvent("screenView", params);
    }
}
