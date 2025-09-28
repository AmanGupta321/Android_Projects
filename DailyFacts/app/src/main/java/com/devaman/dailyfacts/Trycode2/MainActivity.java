package com.devaman.dailyfacts.Trycode2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.devaman.dailyfacts.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager myviewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myviewpager = (ViewPager) findViewById(R.id.myviewpager);

        ArrayList<MyFragment> fragmentList = new ArrayList<>();
        MyAdaptor myAdaptor = new MyAdaptor(getSupportFragmentManager(), fragmentList);

        myviewpager.setAdapter(myAdaptor);

        myviewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Check if the last fragment is being displayed
                if (position == myAdaptor.getCount() - 1) {
                    // Add a new fragment to the list and notify the adapter
                    MyFragment newFragment = createNewFragment();
                    fragmentList.add(newFragment);
                    myAdaptor.notifyDataSetChanged();
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        // Add the initial fragment
        MyFragment initialFragment = createNewFragment();
        fragmentList.add(initialFragment);
        myAdaptor.notifyDataSetChanged();
    }

    private MyFragment createNewFragment() {
        MyFragment newFragment = new MyFragment();
        // Set fetched data to the new fragment

        return newFragment;
    }
}