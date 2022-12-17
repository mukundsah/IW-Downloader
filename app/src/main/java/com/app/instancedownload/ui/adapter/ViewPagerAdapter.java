package com.app.instancedownload.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.app.instancedownload.ui.fragment.ImageFragment;
import com.app.instancedownload.ui.fragment.VideoFragment;


public class ViewPagerAdapter extends FragmentStateAdapter {

    private final int numOfTabs;

    public ViewPagerAdapter(@NonNull FragmentManager fm, @NonNull Lifecycle lifecycle, int numOfTabs) {
        super(fm, lifecycle);
        this.numOfTabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ImageFragment();

            case 1:
                return new VideoFragment();

            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return numOfTabs;
    }
}