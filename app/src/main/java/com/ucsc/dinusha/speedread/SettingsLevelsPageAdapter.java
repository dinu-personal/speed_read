package com.ucsc.dinusha.speedread;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class SettingsLevelsPageAdapter extends FragmentPagerAdapter {

    private List<SettingsLevelsFragment> mFragmentList;

    public SettingsLevelsPageAdapter(FragmentManager fm, List<SettingsLevelsFragment> fragmentList) {
        super(fm);
        this.mFragmentList = fragmentList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentList.get(position).getSettingType();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
