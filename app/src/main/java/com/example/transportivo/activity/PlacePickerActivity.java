package com.example.transportivo.activity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.transportivo.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.nonNull;

public class PlacePickerActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static final int RESULT_OK = 100;

    private GoogleMap googleMap;
    private Geocoder geocoder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_picker);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        geocoder = new Geocoder(getApplicationContext());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        setInitialPosition();

        googleMap.setOnMapClickListener(latLng -> {
            googleMap.clear();
            googleMap.addMarker(new MarkerOptions().position(latLng));

            try {
                final List<Address> fromLocation = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                final Address address = fromLocation.get(0);
                final String addressLine = address.getAddressLine(0);

                Intent data = new Intent();
                data.setData(Uri.parse(addressLine));
                setResult(RESULT_OK, data);
                finish();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setInitialPosition() {
        String addressName = getIntent().getDataString();
        if (nonNull(addressName)) {
            try {
                final List<Address> addresses = geocoder.getFromLocationName(addressName, 1);
                if (!addresses.isEmpty()) {
                    final Address address = addresses.get(0);
                    final LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                    googleMap.clear();
                    googleMap.addMarker(new MarkerOptions().position(latLng));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
