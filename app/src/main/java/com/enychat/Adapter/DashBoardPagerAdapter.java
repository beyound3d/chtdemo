package com.enychat.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.enychat.Fragment.FragmentCalls;
import com.enychat.Fragment.FragmentChats;
import com.enychat.Fragment.FragmentStatus;

public class DashBoardPagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    public DashBoardPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new FragmentChats();
            case 1:
                return new FragmentStatus();
            case 2:
                return new FragmentCalls();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
