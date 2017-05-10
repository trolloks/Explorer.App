package za.co.westcoastexplorers.exploreapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

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

    // attractions
    RecyclerView mRecyclerView;
    AttractionAdapter mAdapter;
    ArrayList<Attraction> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions);
        setTitle(getString(R.string.home_attractions));

        // init menu
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mItems = FireBaseController.getInstance().getAttractions();
        if (mItems != null) {
            findViewById(R.id.loading).setVisibility(View.GONE);
            mAdapter = new AttractionAdapter(this, mItems);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
