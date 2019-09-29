package com.ucsc.dinusha.speedread;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);

        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash_screen);

        try {
            Analytics.getInstance(this).pushToAnalytics("Splash Screen");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, TextSourcesActivity.class);
        startActivity(intent);
        finish();
    }
}
