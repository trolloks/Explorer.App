package za.co.westcoastexplorers.exploreapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import za.co.westcoastexplorers.R;
import za.co.westcoastexplorers.exploreapp.adapters.SingleLineListAdapter;
import za.co.westcoastexplorers.exploreapp.controller.FireBaseController;
import za.co.westcoastexplorers.exploreapp.models.Attraction;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

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
        item2.image = R.drawable.ic_directions_bike_black_24dp;
        item2.clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Map.this, Stations.class);
                Map.this.startActivity(intent);
            }
        };
        mItems.add(item2);

        SingleLineListItem item3 = new SingleLineListItem();
        item3.text = getString(R.string.home_vouchers);
        item3.image = R.drawable.ic_lock_black_24dp;
        item3.clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Map.this, VoucherLogin.class);
                Map.this.startActivity(intent);
            }
        };
        mItems.add(item3);

        SingleLineListItem item4 = new SingleLineListItem();
        item4.text = getString(R.string.home_member);
        item4.image = R.drawable.ic_lock_black_24dp;
        item4.clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // do login
            }
        };
        mItems.add(item4);

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
        mMap.getUiSettings().setZoomControlsEnabled(true);

        final LatLngBounds.Builder latLngBoundsBuilder = new LatLngBounds.Builder();
        final ArrayList<LatLng> latLngs = new ArrayList<>();

        for (Attraction attraction : FireBaseController.getInstance().getAttractions()){
            LatLng latLng = new LatLng(attraction.lat, attraction.lng);
            latLngs.add(latLng);
            latLngBoundsBuilder.include(latLng);
            MarkerOptions marker = new MarkerOptions().position(latLng).title(attraction.name).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
            mMap.addMarker(marker);
        }

        if (latLngs.size() > 1) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBoundsBuilder.build(),
                    getResources().getDisplayMetrics().widthPixels,
                    getResources().getDisplayMetrics().heightPixels - (((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics()))), // 300 less for title bar and menu
                    (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics())));
        } else if (!latLngs.isEmpty()) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngs.get(0), 14));
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                ArrayList <Attraction> attractions = FireBaseController.getInstance().getAttractions("name", marker.getTitle());
                for (Attraction attraction : attractions){
                    if (attraction.clickListener != null && mRecyclerView != null)
                        attraction.clickListener.onClick(mRecyclerView);
                }
                return false;
            }
        });
    }
}
