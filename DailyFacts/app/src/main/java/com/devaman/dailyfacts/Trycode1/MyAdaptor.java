package com.devaman.dailyfacts.Trycode1;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class MyAdaptor extends FragmentPagerAdapter
{

    public MyAdaptor(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                return new Fact1();
            case 1 :
                return new Fact2();
            case 2 :
                return new Fact3();
            default :
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}