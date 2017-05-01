package za.co.westcoastexplorers.exploreapp;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
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

import za.co.westcoastexplorers.R;

/**
 * Created by Trolloks on 5/1/2017.
 */

public class Map extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

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


        // toggle menu
        findViewById(R.id.menutoggle).setOnClickListener(new View.OnClickListener() {
            boolean open = true;

            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    AutoTransition auto = new AutoTransition();
                    auto.setDuration(200);
                    TransitionManager.beginDelayedTransition((ViewGroup)findViewById(R.id.cardView), auto);
                }

                if (open)
                    findViewById(R.id.menucontainer).setVisibility(View.GONE);
                else
                    findViewById(R.id.menucontainer).setVisibility(View.VISIBLE);

                open = !open;

                ( (AppCompatImageView)findViewById(R.id.dropdown)).setImageResource(!open ? R.drawable.ic_arrow_drop_down_black_24dp : R.drawable.ic_arrow_drop_up_black_24dp);
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
    }
}
