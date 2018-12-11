package com.example.matias.viewpagerwithtabs.classes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;

import com.example.matias.viewpagerwithtabs.fragments.FrontFragment;
import com.example.matias.viewpagerwithtabs.fragments.InputFragment;
import com.example.matias.viewpagerwithtabs.fragments.OutputFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 2) {
            OutputFragment outFrag = new OutputFragment();
            Log.d("frag", "Position: " + position);
            return outFrag;
        } else if (position == 0) {
            InputFragment infrag = new InputFragment();
            Log.d("frag", "Position: " + position);
            return infrag;
        } else {
            FrontFragment fronFrag = new FrontFragment();
            Log.d("frag", "Position: " + position);
            return fronFrag;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        position = position++;

        if (position == 0) {
            return "Lisää aktiviteetti";
        } else if (position == 2) {
            return "Ajankäyttö";
        } else {
            return "Viimeisimmät";
        }
    }

    public void refresh() {

    }
}