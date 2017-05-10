package za.co.westcoastexplorers.exploreapp.controller;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import za.co.westcoastexplorers.exploreapp.models.Attraction;

/**
 * Created by rikus on 2017/05/09.
 */

public class FireBaseController {

    public static FireBaseController instance;

    private ArrayList<Attraction> mAttractions;
    private FireBaseListener mListener;
    private boolean firstTime = true;

    private FireBaseController(){

        // init database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("attractions");

        mAttractions = new ArrayList<>();
        firstTime = true;

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Object value = dataSnapshot.getValue(Object.class);
                Log.d("DB", "Value is: " + value);

                if (value instanceof ArrayList){
                    ArrayList<HashMap> arrayList = (ArrayList<HashMap>)value;
                    if (mAttractions != null) {
                        mAttractions.clear();
                        for (HashMap hashMap : arrayList){
                            Attraction item1 = new Attraction();
                            if (hashMap.containsKey("name") && hashMap.get("name") instanceof String)
                                item1.name = (String)hashMap.get("name");
                            if (hashMap.containsKey("lat") && hashMap.get("lat") instanceof Double)
                                item1.lat = (Double)hashMap.get("lat");
                            if (hashMap.containsKey("lng") && hashMap.get("lng") instanceof Double)
                                item1.lng = (Double)hashMap.get("lng");
                            mAttractions.add(item1);
                        }
                    }
                }

                if (mListener != null && firstTime) {
                    mListener.onSuccess();
                    firstTime = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DB", "Failed to read value.", error.toException());

                if (mListener != null)
                    mListener.onError();
            }
        });

    }

    public void destroy(){
        instance = null;
    }

    public static FireBaseController getInstance(){
        return instance == null ? instance = new FireBaseController() : instance;
    }

    public void setFireBaseListener(FireBaseListener listener){
        mListener = listener;
    }

    public ArrayList<Attraction> getAttractions(){
        return (mAttractions == null ? new ArrayList<Attraction>() : mAttractions);
    }

    public interface FireBaseListener{
        public void onSuccess();
        public void onError();
    }
}
