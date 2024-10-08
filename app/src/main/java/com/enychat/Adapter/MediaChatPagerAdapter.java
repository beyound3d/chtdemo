package com.enychat.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.enychat.Fragment.FragmentCalls;
import com.enychat.Fragment.FragmentChats;
import com.enychat.Fragment.FragmentDocuments;
import com.enychat.Fragment.FragmentLinks;
import com.enychat.Fragment.FragmentMedia;
import com.enychat.Fragment.FragmentStatus;

public class MediaChatPagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    public MediaChatPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new FragmentMedia();
            case 1:
                return new FragmentDocuments();
            case 2:
                return new FragmentLinks();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
