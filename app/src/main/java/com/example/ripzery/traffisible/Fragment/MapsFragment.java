package com.example.ripzery.traffisible.Fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ripzery.traffisible.JSONObjectClass.News;
import com.example.ripzery.traffisible.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private final LatLngBounds Thailand = new LatLngBounds(new LatLng(5.371270, 97.859916), new LatLng(19.680066, 104.957083));
    private String mName, mDescription, mTitle, mType;
    private String mLatitude, mLongitude;
    private View mRootView;
    private GoogleMap mMap;
    private News news;

    public MapsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mName = bundle.getString("name");
            mTitle = bundle.getString("title");
            mType = bundle.getString("type");
            mDescription = bundle.getString("des");
            mLatitude = bundle.getString("lat");
            mLongitude = bundle.getString("lon");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        mRootView = view;
        TextView tvRoad = (TextView) mRootView.findViewById(R.id.tvRoad);
        tvRoad.setText("ตำแหน่ง : " + mName + "\n" + "เหตุการณ์ : " + mDescription);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf");
        tvRoad.setTypeface(tf);
        setUpMapIfNeeded();
        final LatLng latlng = new LatLng(Double.parseDouble(mLatitude), Double.parseDouble(mLongitude));
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
//                setUpMap();
                setCameraPosition(latlng);
                MarkerOptions marker;

                String extend = "";
                if (mTitle.equals("การก่อสร้าง")) {
                    extend = "มี";
                }
                marker = new MarkerOptions().position(latlng).title("ตำแหน่ง : " + mName + " " + extend + mTitle).icon(getIcon());


                mMap.addMarker(marker);


            }
        });
        return view;
    }

    public BitmapDescriptor getIcon() {
        BitmapDescriptor bitmap;
        if (mType.equals("สภาพอากาศ")) {
            bitmap = mTitle.equals("ฝนตก") ? BitmapDescriptorFactory.fromAsset("marker-icons/rainy.png") : BitmapDescriptorFactory.fromAsset("marker-icons/flood.png");
        } else if (mType.equals("เหตุฉุกเฉิน")) {
            bitmap = BitmapDescriptorFactory.fromAsset("marker-icons/caution.png");
        } else if (mType.equals("การก่อสร้าง")) {
            bitmap = BitmapDescriptorFactory.fromAsset("marker-icons/bulldozer.png");
        } else if (mType.equals("การจราจร")) {
            bitmap = BitmapDescriptorFactory.fromAsset("marker-icons/flagman.png");
        } else {
            bitmap = BitmapDescriptorFactory.fromAsset("marker-icons/repair.png");
        }

        return bitmap;
    }

    private void setCameraPosition(LatLng Location) {
        CameraPosition camPos = new CameraPosition.Builder().target(Location).zoom(14).tilt(45).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camPos));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Fragment f = getFragmentManager().findFragmentById(R.id.map);
        if (f != null)
            getFragmentManager().beginTransaction().remove(f).commit();
        mMap.clear();
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map))
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
