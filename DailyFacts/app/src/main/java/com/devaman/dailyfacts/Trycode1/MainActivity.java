package com.devaman.dailyfacts.Trycode1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.devaman.dailyfacts.R;

public class MainActivity extends AppCompatActivity {

    ViewPager myviewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myviewpager = (ViewPager) findViewById(R.id.myviewpager);

        MyAdaptor myAdaptor = new MyAdaptor(getSupportFragmentManager());

        myviewpager.setAdapter(myAdaptor);

    }
}