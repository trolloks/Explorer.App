package za.co.westcoastexplorers.exploreapp.controller;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import za.co.westcoastexplorers.exploreapp.AttractionDetail;
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
                            final Attraction item1 = new Attraction();
                            if (hashMap.containsKey("name") && hashMap.get("name") instanceof String)
                                item1.name = (String)hashMap.get("name");
                            if (hashMap.containsKey("description") && hashMap.get("description") instanceof String)
                                item1.description = (String)hashMap.get("description");
                            if (hashMap.containsKey("lat") && hashMap.get("lat") instanceof Double)
                                item1.lat = (Double)hashMap.get("lat");
                            if (hashMap.containsKey("lng") && hashMap.get("lng") instanceof Double)
                                item1.lng = (Double)hashMap.get("lng");
                            if (hashMap.containsKey("id") && hashMap.get("id") instanceof String)
                                item1.id = (String)hashMap.get("id");
                            if (hashMap.containsKey("group") && hashMap.get("group") instanceof String)
                                item1.group = (String)hashMap.get("group");
                            if (hashMap.containsKey("placeid") && hashMap.get("placeid") instanceof String)
                                item1.placeId = (String)hashMap.get("placeid");
                            if (hashMap.containsKey("thumbnailurl") && hashMap.get("thumbnailurl") instanceof String)
                                item1.thumbnailURL = (String)hashMap.get("thumbnailurl");
                            item1.clickListener = new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(view.getContext(), AttractionDetail.class);
                                    intent.putExtra("id", item1.id);
                                    view.getContext().startActivity(intent);
                                }
                            };
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

    public ArrayList<Attraction> getAttractions(String filter, Object by){
        ArrayList<Attraction> tempAttractions = new ArrayList<>();
        for (Attraction attraction : getAttractions()){
            if (filter.equals("group") && attraction.group.equals(by)){
                tempAttractions.add(attraction);
            }

            if (filter.equals("name") && attraction.name.equals(by)){
                tempAttractions.add(attraction);
            }
        }

        return tempAttractions;
    }

    public interface FireBaseListener{
        public void onSuccess();
        public void onError();
    }
}
