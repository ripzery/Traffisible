package com.example.ripzery.traffisible;

import android.app.ActionBar;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.ripzery.traffisible.Fragment.MapsFragment;
import com.example.ripzery.traffisible.Fragment.ReportNewsFragment;
import com.example.ripzery.traffisible.JSONObjectClass.News;

import java.util.ArrayList;


public class MyActivity extends FragmentActivity {

    private Fragment reportFrag;
    private MapsFragment mapsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

//        if (savedInstanceState == null) {
        reportFrag = new ReportNewsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.content_layout, reportFrag);
//            transaction.addToBackStack("news");
        transaction.commit();
//        }


        int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
        if (actionBarTitleId > 0) {
            TextView title = (TextView) findViewById(actionBarTitleId);
            if (title != null) {
                title.setTextColor(Color.WHITE);
            }
        }
        ActionBar bar = getActionBar();
        bar.setCustomView(R.layout.actionbar_view);
        bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME);

    }

    public void openMap(ArrayList<News> listNews, View view, int i) {

//        Log.d("Text",""+listNews.get(i).getTitle());
        News news = listNews.get(i);
        Bundle bundle = new Bundle();
        News.Location location = news.getLocation();
        if (location.getType().equals("point")) {
            News.Location.Point point = location.getPoint();
            bundle.putString("type", news.getType());
            bundle.putString("title", news.getTitle());
            bundle.putString("des", news.getDescription());
            bundle.putString("name", point.getName());
            bundle.putString("lat", point.getLatitude());
            bundle.putString("lon", point.getLongitude());

        } else if (location.getType().equals("line")) {
            News.Location.Road road = location.getRoad();
            News.Location.StartPoint startPoint = location.getStartPoint();
            News.Location.EndPoint endPoint = location.getEndPoint();

            bundle.putString("type", news.getType());
            bundle.putString("title", news.getTitle());
            bundle.putString("des", news.getDescription());
            bundle.putString("road", road.getName());
            bundle.putString("startpoint_name", startPoint.getName());
            bundle.putString("startpoint_lat", startPoint.getLatitude());
            bundle.putString("startpoint_lon", startPoint.getLongitude());
            bundle.putString("endpoint_name", startPoint.getName());
            bundle.putString("endpoint_lat", startPoint.getLatitude());
            bundle.putString("endpoint_lon", startPoint.getLongitude());
        }
        mapsFragment = new MapsFragment();
        mapsFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.content_layout, mapsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
