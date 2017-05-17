package com.akujasa.jasacenter;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PesananTokoMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Double lintang;
    Double bujur;
    //UiSettings uiSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pesanan_toko_map_activity);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        String stlintang = getIntent().getExtras().getString("lintang");
        String stbujur = getIntent().getExtras().getString("bujur");
        lintang = Double.parseDouble(stlintang);
        bujur = Double.parseDouble(stbujur);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //uiSettings.setZoomControlsEnabled(true);
        // Add a marker in Sydney and move the camera
        LatLng konsumen = new LatLng(lintang, bujur);
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(konsumen , 14.0f));
        mMap.addMarker(new MarkerOptions().position(konsumen).title("Lokasi Konsumen"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(konsumen));
    }
}
