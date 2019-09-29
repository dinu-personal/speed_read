package com.ucsc.dinusha.speedread;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SettingsLevelsActivity extends AppCompatActivity{

    private ViewPager mViewPager;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_levels);

        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);
        mContext = this;

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabTextColors(
                ContextCompat.getColor(mContext, R.color.colorAccent),
                ContextCompat.getColor(mContext, R.color.colorAccent)
        );

        try {
            Analytics.getInstance(this).pushToAnalytics("Settings Screen");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupViewPager(ViewPager viewPager){
        List<SettingsLevelsFragment> fragments = new ArrayList<>();
        fragments.add(SettingsLevelsFragment.newInstance("Windows"));
        fragments.add(SettingsLevelsFragment.newInstance("Words"));
        SettingsLevelsPageAdapter adapter = new SettingsLevelsPageAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);


        if(getIntent().getStringExtra("commingFromWindowOrWord") != null){
            if(getIntent().getStringExtra("commingFromWindowOrWord").equals("word")){
                viewPager.setCurrentItem(2);
            }
        }

    }
}
