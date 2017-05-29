package za.co.westcoastexplorers.exploreapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import za.co.westcoastexplorers.R;
import za.co.westcoastexplorers.exploreapp.custom.SwipeProgress;
import za.co.westcoastexplorers.exploreapp.utils.ViewGroupPagerAdapter;

/**
 * Created by rikus on 2017/05/17.
 */

public class Tutorial extends AppCompatActivity {
    public static final String PREFS_NAME = "TutorialSettings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        final ViewGroupPagerAdapter adapter = new ViewGroupPagerAdapter();
        ViewPager pager = (ViewPager)findViewById(R.id.pages);
        pager.setAdapter(adapter);

        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean skipTut = settings.getBoolean("skipTut", false);

        if (skipTut){
            goNext();
        }

        ViewGroup tut1 = (ViewGroup) ((LayoutInflater) Tutorial.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.tutorial_1, null, false);
        tut1.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goNext();
            }
        });
        adapter.addViewGroup(tut1);

        ViewGroup tut2 = (ViewGroup) ((LayoutInflater) Tutorial.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.tutorial_2, null, false);
        tut2.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goNext();
            }
        });
        adapter.addViewGroup(tut2);

        ViewGroup tut3 = (ViewGroup) ((LayoutInflater) Tutorial.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.tutorial_3, null, false);
        tut3.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goNext();
            }
        });

        final SwipeProgress swipeProgress = (SwipeProgress)findViewById(R.id.swipe);
        swipeProgress.setSwipeCount(3);
        swipeProgress.setColor(ContextCompat.getColor(Tutorial.this, R.color.colorAccent));
        swipeProgress.setIndex(0);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                swipeProgress.setIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        adapter.addViewGroup(tut3);
    }

    private void goNext(){
        // set to cache
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("skipTut", true);
        editor.apply();

        Intent intent = new Intent(Tutorial.this, Email.class);
        startActivity(intent);
        finish();
    }
}
