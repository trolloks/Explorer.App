package za.co.westcoastexplorers.exploreapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import za.co.westcoastexplorers.R;
import za.co.westcoastexplorers.exploreapp.utils.ViewGroupPagerAdapter;

/**
 * Created by rikus on 2017/05/17.
 */

public class Tutorial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        final ViewGroupPagerAdapter adapter = new ViewGroupPagerAdapter();
        ViewPager pager = (ViewPager)findViewById(R.id.pages);
        pager.setAdapter(adapter);

        ViewGroup tut1 = (ViewGroup) ((LayoutInflater) Tutorial.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.tutorial_1, null, false);
        tut1.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Tutorial.this, Email.class);
                startActivity(intent);
                finish();
            }
        });
        adapter.addViewGroup(tut1);

        ViewGroup tut2 = (ViewGroup) ((LayoutInflater) Tutorial.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.tutorial_2, null, false);
        tut2.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Tutorial.this, Email.class);
                startActivity(intent);
                finish();
            }
        });
        adapter.addViewGroup(tut2);

        ViewGroup tut3 = (ViewGroup) ((LayoutInflater) Tutorial.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.tutorial_3, null, false);
        tut3.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Tutorial.this, Email.class);
                startActivity(intent);
                finish();
            }
        });
        adapter.addViewGroup(tut3);



    }
}
