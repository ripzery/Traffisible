package com.example.ripzery.traffisible.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.ripzery.traffisible.JSONObjectClass.CCTV;
import com.example.ripzery.traffisible.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


public class MapsCCTVFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private final LatLngBounds Thailand = new LatLngBounds(new LatLng(5.371270, 97.859916), new LatLng(19.680066, 104.957083));
    private String mName, mURL;
    private String mLatitude, mLongitude;
    private View mRootView;
    private GoogleMap mMap;
    private ImageLoaderConfiguration config;
    private CCTV cctv;

    public MapsCCTVFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mName = bundle.getString("name");
            mURL = bundle.getString("img");
            mLatitude = bundle.getString("lat");
            mLongitude = bundle.getString("lng");
        }
        config = new ImageLoaderConfiguration.Builder(getActivity())
                .diskCacheExtraOptions(480, 800, null)
                .threadPriority(Thread.NORM_PRIORITY - 1) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDecoder(new BaseImageDecoder(true)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs()
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cctv_maps, container, false);
        mRootView = view;
        final ImageView imgView = (ImageView) mRootView.findViewById(R.id.ivCCTVImg);
        ImageLoader.getInstance().init(config);
        ImageLoader.getInstance().displayImage(mURL, imgView, new ImageLoadingListener() {
            final ProgressBar mProgressBar = (ProgressBar) mRootView.findViewById(R.id.google_progress2);

            @Override
            public void onLoadingStarted(String imageUri, View view) {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                mProgressBar.setVisibility(View.GONE);
                imgView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
        setUpMapIfNeeded();
        if (mName.equals("Rain Detection Radar")) {
            mLatitude = "13.725518";
            mLongitude = "100.522904";
        }
        final LatLng latlng = new LatLng(Double.parseDouble(mLatitude), Double.parseDouble(mLongitude));
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
//                setUpMap();
                if (mName.equals("Rain Detection Radar")) {
                    setCameraPosition(latlng, 8);
                } else
                    setCameraPosition(latlng, 14);

                MarkerOptions marker;
                marker = new MarkerOptions().position(latlng).title(mName);
                mMap.addMarker(marker);
            }
        });
        return view;
    }

    private void setCameraPosition(LatLng Location, int zoomLevel) {
        CameraPosition camPos = new CameraPosition.Builder().target(Location).zoom(zoomLevel).tilt(45).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camPos));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Fragment f = getFragmentManager().findFragmentById(R.id.map2);
        if (f != null)
            getFragmentManager().beginTransaction().remove(f).commit();
        mMap.clear();
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map2))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
//                setUpMap();
            }
        }
    }

    public void setUpMap() {
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(Thailand, 3));
    }

}
