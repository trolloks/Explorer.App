package za.co.westcoastexplorers.exploreapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import za.co.westcoastexplorers.R;
import za.co.westcoastexplorers.exploreapp.adapters.SingleLineListAdapter;
import za.co.westcoastexplorers.exploreapp.models.SingleLineListItem;

/**
 * Created by rikus on 2017/05/09.
 */

public class Attractions extends AppCompatActivity {

    // attractions
    RecyclerView mRecyclerView;
    SingleLineListAdapter mAdapter;
    ArrayList<SingleLineListItem> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions);

        setTitle("Attractions");


        // init menu
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mItems = new ArrayList<>();

        SingleLineListItem item1 = new SingleLineListItem();
        item1.text = getString(R.string.home_attractions);
        item1.image = R.drawable.ic_local_activity_black_24dp;
        mItems.add(item1);

        SingleLineListItem item2 = new SingleLineListItem();
        item2.text = getString(R.string.home_route);
        item2.image = R.drawable.ic_directions_car_black_24dp;
        mItems.add(item2);

        SingleLineListItem item3 = new SingleLineListItem();
        item3.text = getString(R.string.home_member);
        item3.image = R.drawable.ic_lock_black_24dp;
        mItems.add(item3);

        mAdapter = new SingleLineListAdapter(this, mItems);
        mRecyclerView.setAdapter(mAdapter);
    }
}
