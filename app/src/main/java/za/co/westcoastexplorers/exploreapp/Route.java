package za.co.westcoastexplorers.exploreapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import za.co.westcoastexplorers.R;
import za.co.westcoastexplorers.exploreapp.adapters.AttractionAdapter;
import za.co.westcoastexplorers.exploreapp.controller.FireBaseController;
import za.co.westcoastexplorers.exploreapp.models.Attraction;

/**
 * Created by rikus on 2017/05/09.
 */

public class Route extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    // attractions
    RecyclerView mRecyclerView;
    AttractionAdapter mAdapter;
    ArrayList<Attraction> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        setTitle(getString(R.string.home_route));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

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
            mMap.addMarker(new MarkerOptions().position(latLng).title(attraction.name));
        }

        if (latLngs.size() > 1) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBoundsBuilder.build(),
                    getResources().getDisplayMetrics().widthPixels,
                    getResources().getDisplayMetrics().heightPixels - (((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics()))), // 300 less for title bar and menu
                    (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics())));
        } else if (!latLngs.isEmpty()) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngs.get(0), 14));
        }
    }
}
