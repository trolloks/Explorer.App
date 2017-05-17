package za.co.westcoastexplorers.exploreapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import za.co.westcoastexplorers.R;
import za.co.westcoastexplorers.exploreapp.adapters.AttractionAdapter;
import za.co.westcoastexplorers.exploreapp.adapters.SingleLineListAdapter;
import za.co.westcoastexplorers.exploreapp.controller.FireBaseController;
import za.co.westcoastexplorers.exploreapp.models.Attraction;
import za.co.westcoastexplorers.exploreapp.models.SingleLineListItem;

/**
 * Created by rikus on 2017/05/09.
 */

public class Attractions extends AppCompatActivity {

    // menu
    RecyclerView mRecyclerView;
    SingleLineListAdapter mAdapter;
    ArrayList<SingleLineListItem> mItems;

    public static final String [] ATTRACTION_GROUPS = new String[]{"Restaurant", "Accommodation", "Retail", "Place of interest", "Bicycle trail", "Walking / Hiking trail", ""}; // empty is for other
    public static int [] mAttractionIcons = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions);
        setTitle(getString(R.string.home_attractions));

        // init menu
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mAttractionIcons = new int[]{R.drawable.ic_restaurant_black_24dp, R.drawable.ic_hotel_black_24dp, R.drawable.ic_local_activity_black_24dp, R.drawable.ic_local_activity_black_24dp, R.drawable.ic_directions_bike_black_24dp, R.drawable.ic_local_activity_black_24dp, R.drawable.ic_local_activity_black_24dp}; // empty is for other

        mItems = new ArrayList<>();
        for (final String group : ATTRACTION_GROUPS){
            SingleLineListItem item = new SingleLineListItem();
            item.text = group.isEmpty() ? "Other" : group;
            item.image = mAttractionIcons[Arrays.asList(ATTRACTION_GROUPS).indexOf(group)];
            item.clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Attractions.this, AttractionGroup.class);
                    intent.putExtra("group", group);
                    Attractions.this.startActivity(intent);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
