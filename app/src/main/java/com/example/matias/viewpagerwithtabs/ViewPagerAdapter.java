package com.example.matias.viewpagerwithtabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        DemoFragment demoFragment = new DemoFragment();
        position = position++;
        Bundle bundle = new Bundle();
        bundle.putString("message", "Fragment: " +position);
        bundle.putInt("position", position);
        demoFragment.setArguments(bundle);
        return demoFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        position = position++;

        if (position == 0){return "Etusivu";}
        else if (position == 1){return "Tietosi";}
        else {return "Kalenteri";}


    }
}