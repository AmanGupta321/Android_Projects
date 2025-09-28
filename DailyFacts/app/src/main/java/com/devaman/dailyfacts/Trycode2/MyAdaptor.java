package com.devaman.dailyfacts.Trycode2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.devaman.dailyfacts.Trycode1.Fact1;
import com.devaman.dailyfacts.Trycode1.Fact2;
import com.devaman.dailyfacts.Trycode1.Fact3;

import java.util.List;

class MyAdaptor extends FragmentPagerAdapter
{
    int pageCount;
    List<MyFragment> fragmentList;

    public MyAdaptor(@NonNull FragmentManager fm, List<MyFragment> fragments) {
        super(fm);
        this.fragmentList = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}