package za.co.westcoastexplorers.exploreapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import za.co.westcoastexplorers.R;
import za.co.westcoastexplorers.exploreapp.adapters.AttractionAdapter;
import za.co.westcoastexplorers.exploreapp.controller.FireBaseController;
import za.co.westcoastexplorers.exploreapp.models.Attraction;

/**
 * Created by rikus on 2017/05/09.
 */

public class Vouchers extends AppCompatActivity {

    // attractions
    RecyclerView mRecyclerView;
    AttractionAdapter mAdapter;
    ArrayList<Attraction> mItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher);
        setTitle(getString(R.string.home_vouchers));

        // init menu
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mItems = FireBaseController.getInstance().getAttractions();
        if (mItems != null) {
            findViewById(R.id.loading).setVisibility(View.GONE);
            mAdapter = new AttractionAdapter(this, mItems);
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
