package com.stegano.myjavaapplication.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.stegano.myjavaapplication.fragments.ChickenStoreListFragment;
import com.stegano.myjavaapplication.fragments.PizzaStoreListFragment;

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    public MainViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "피자 가게";
        }
        return "치킨 가게";
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new PizzaStoreListFragment();
        }
        return new ChickenStoreListFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
