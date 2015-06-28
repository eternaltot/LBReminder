package com.example.chart001.lbreminder;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;


/**
 * A placeholder fragment containing a simple view.
 */
public class mapMainFragment extends FragmentActivity implements OnMapReadyCallback {

    public mapMainFragment() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_main);
        MapFragment mapFragment =  (MapFragment)getFragmentManager().findFragmentById(R.id.mapF);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
