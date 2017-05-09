package za.co.westcoastexplorers.exploreapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import za.co.westcoastexplorers.R;
import za.co.westcoastexplorers.exploreapp.adapters.SingleLineListAdapter;
import za.co.westcoastexplorers.exploreapp.models.SingleLineListItem;

/**
 * Created by Trolloks on 5/1/2017.
 */

public class Map extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    // menu
    RecyclerView mRecyclerView;
    SingleLineListAdapter mAdapter;
    ArrayList<SingleLineListItem> mItems;

    // permissions
    int PERMISSIONCODE = 909;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        // init database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("attractions");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Object value = dataSnapshot.getValue(Object.class);
                Log.d("DB", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DB", "Failed to read value.", error.toException());
            }
        });

        // init menu
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mItems = new ArrayList<>();

        SingleLineListItem item1 = new SingleLineListItem();
        item1.text = getString(R.string.home_attractions);
        item1.image = R.drawable.ic_local_activity_black_24dp;
        item1.clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Map.this, Attractions.class);
                Map.this.startActivity(intent);
            }
        };
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


        // toggle menu
        findViewById(R.id.menutoggle).setOnClickListener(new View.OnClickListener() {
            boolean open = true;

            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    AutoTransition auto = new AutoTransition();
                    auto.setDuration(100);
                    TransitionManager.beginDelayedTransition((ViewGroup) findViewById(R.id.cardView), auto);
                }

                if (open)
                    findViewById(R.id.menucontainer).setVisibility(View.GONE);
                else
                    findViewById(R.id.menucontainer).setVisibility(View.VISIBLE);

                open = !open;

                ((AppCompatImageView) findViewById(R.id.dropdown)).setImageResource(!open ? R.drawable.ic_arrow_drop_down_black_24dp : R.drawable.ic_arrow_drop_up_black_24dp);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Yzerfontein, and move the camera.
        LatLng yzerfontein = new LatLng(-33.342506, 18.173862);
        mMap.addMarker(new MarkerOptions().position(yzerfontein).title("Welcome to Yzerfontein"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yzerfontein, 16.0f));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 909);
            return;
        }

        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONCODE){



        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
