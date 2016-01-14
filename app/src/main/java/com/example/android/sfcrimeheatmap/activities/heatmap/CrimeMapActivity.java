package com.example.android.sfcrimeheatmap.activities.heatmap;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.android.sfcrimeheatmap.R;
import com.example.android.sfcrimeheatmap.activities.BaseActivity;
import com.example.android.sfcrimeheatmap.activities.MaterialDialogModule;
import com.example.android.sfcrimeheatmap.application.CMAppComponent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CrimeMapActivity extends BaseActivity implements OnMapReadyCallback, CrimeMapView {

    private static final LatLng SOUTH_WEST_SF_BOUNDARY = new LatLng(37.707990, -122.502147);
    private static final LatLng NORTH_EAST_SF_BOUNDARY = new LatLng(37.820354, -122.356578);

    @Inject
    CrimeMapActivityPresenter presenter;

    private GoogleMap mMap;

    @Bind(R.id.mapTitle)
    TextView mapTitleTv;

    @Override
    protected void setupDataInjection(CMAppComponent appComponent) {
        DaggerCrimeMapActivityComponent
                .builder()
                .cMAppComponent(appComponent)
                .crimeMapActivityModule(new CrimeMapActivityModule(this))
                .materialDialogModule(new MaterialDialogModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_map);
        ButterKnife.bind(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        initMap();
    }

    private void initMap() {
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.12);

        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(getZoomBounds(), width, height, padding));

        presenter.loadMapMarkers();
    }

    private LatLngBounds getZoomBounds() {
        return new LatLngBounds(SOUTH_WEST_SF_BOUNDARY, NORTH_EAST_SF_BOUNDARY);
    }

    @Override
    public void showMarkers(List<MarkerOptions> markerOptionsList) {
        mMap.setInfoWindowAdapter(new CustomMarkerAdapter(this));
        for(MarkerOptions markerOption: markerOptionsList){
            mMap.addMarker(markerOption);
        }
    }

    @Override
    public void showDate(String dateToDisplay) {
        mapTitleTv.setText(getString(R.string.crime_data_since, dateToDisplay));
        mapTitleTv.setVisibility(View.VISIBLE);
    }
}
