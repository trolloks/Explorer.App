package za.co.westcoastexplorers.exploreapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;

import za.co.westcoastexplorers.R;
import za.co.westcoastexplorers.exploreapp.adapters.AttractionAdapter;
import za.co.westcoastexplorers.exploreapp.adapters.SingleLineListAdapter;
import za.co.westcoastexplorers.exploreapp.controller.FireBaseController;
import za.co.westcoastexplorers.exploreapp.models.Attraction;
import za.co.westcoastexplorers.exploreapp.models.SingleLineListItem;
import za.co.westcoastexplorers.exploreapp.models.Special;

/**
 * Created by rikus on 2017/05/09.
 */

public class Vouchers extends AppCompatActivity {

    // attractions
    RecyclerView mRecyclerView;
    SingleLineListAdapter mAdapter;
    ArrayList<SingleLineListItem> mItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher);
        setTitle(getString(R.string.home_vouchers));

        // init menu
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mItems = new ArrayList<>();
        for (final Special special : FireBaseController.getInstance().getSpecials()){
            SingleLineListItem item = new SingleLineListItem();
            item.text = special.description;
            item.clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            };
            mItems.add(item);
        }


        if (mItems != null) {
            findViewById(R.id.loading).setVisibility(View.GONE);
            mAdapter = new SingleLineListAdapter(this, mItems);
            mRecyclerView.setAdapter(mAdapter);
        }

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
