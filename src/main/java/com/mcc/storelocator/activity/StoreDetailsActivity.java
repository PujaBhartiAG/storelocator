package com.mcc.storelocator.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mcc.storelocator.R;
import com.mcc.storelocator.data.AppConstant;
import com.mcc.storelocator.model.Store;
import com.mcc.storelocator.utility.ActivityUtils;
import com.mcc.storelocator.utility.AdUtils;
import com.mcc.storelocator.utility.AppUtils;
import com.mcc.storelocator.utility.maputility.MyMapUtils;

public class StoreDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Store storeDetail;
    private TextView txtStrName, txtStrAddress;
    private ImageView imgStore, imgCall;
    private Activity mActivity;
    private Context mContext;
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
        initView();
        initMapView();
        getStoreDetailData();
        initListener();
    }

    private void initVariable() {
        mActivity = StoreDetailsActivity.this;
        mContext = getApplicationContext();
    }

    private void initMapView() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initView() {
        setContentView(R.layout.activity_store_details);
        txtStrName = findViewById(R.id.text_news_store_name);
        txtStrAddress = findViewById(R.id.text_news_store_address);
        imgStore = findViewById(R.id.image_news_banner);
        imgCall = findViewById(R.id.icon_news_call);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        initDestinationOnMap();
        addMarkersToMap();

    }

    private void initListener() {
        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.getInstance().invokePhoneCall(mActivity, storeDetail.getContactNumber());
            }
        });
    }


    private void getStoreDetailData() {
        Bundle extraDetail = getIntent().getExtras();
        storeDetail = (Store) extraDetail.getSerializable(AppConstant.KEY_DETAIL_DATA);
        setStoreDetail(storeDetail);
    }

    public void setStoreDetail(Store storeDetail) {
        txtStrName.setText(storeDetail.getBranchName());
        txtStrAddress.setText(storeDetail.getAddress());
        //Loading image from Glide library.
        Glide.with(mActivity)
                .load(storeDetail.getImageUrl())
                .placeholder(R.color.background)
                .into(imgStore);
    }

    private void initDestinationOnMap() {
        MyMapUtils myMapUtils = new MyMapUtils(mActivity, mMap);
        myMapUtils.getDirectionOnMap(storeDetail.getLatitude(), storeDetail.getLongitude());
    }

    private void addMarkersToMap() {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(AppConstant.MY_LOCATION_LATITUTE, AppConstant.MY_LOCATION_LONGITUTE), 12);
        mMap.animateCamera(cameraUpdate);
        MarkerOptions myMarkerOption = new MarkerOptions()
                .position(new LatLng(AppConstant.MY_LOCATION_LATITUTE, AppConstant.MY_LOCATION_LONGITUTE)).icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
        mMap.addMarker(myMarkerOption);

        MarkerOptions destinationMarkerOption = new MarkerOptions()
                .position(new LatLng(storeDetail.getLatitude(), storeDetail.getLongitude())).icon(AppUtils.getMapMarker(StoreDetailsActivity.this));
        mMap.addMarker(destinationMarkerOption);

    }

    @Override
    protected void onResume() {
        super.onResume();
        AdUtils.getInstance(mContext).showBannerAd((AdView) findViewById(R.id.adView));

    }

}
