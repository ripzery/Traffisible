package com.example.ripzery.traffisible;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ripzery.traffisible.Fragment.MapsFragment;
import com.example.ripzery.traffisible.Fragment.ReportNewsFragment;
import com.example.ripzery.traffisible.JSONObjectClass.News;

import java.util.ArrayList;


public class MyActivity extends FragmentActivity {

    private Fragment reportFrag;
    private MapsFragment mapsFragment;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] mDrawerString;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBar mActionBar;
    private TextView mActionTitle;
    private int actionBarTitleId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        mActionBar = getActionBar();

        SpannableString mStringTitle = new SpannableString(" TRAFFISIBLE");
        SpannableString mStringSubTitle = new SpannableString("    รายงานข่าวจราจร - ล่าสุด");
        mStringTitle.setSpan(new TypefaceSpan(this, "Roboto-Medium.ttf"), 0, mStringTitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mStringSubTitle.setSpan(new TypefaceSpan(this, "Roboto-Light.ttf"), 0, mStringTitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mActionBar.setTitle(mStringTitle);
        mActionBar.setSubtitle(mStringSubTitle);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerString = getResources().getStringArray(R.array.drawer_array);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mDrawerString));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

//        if (savedInstanceState == null) {
        reportFrag = new ReportNewsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.content_layout, reportFrag);
//            transaction.addToBackStack("news");
        transaction.commit();
//        }


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
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
//        Fragment fragment = new PlanetFragment();
//        Bundle args = new Bundle();
//        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
//        fragment.setArguments(args);
//
//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
//
//        // update selected item and title, then close the drawer
//        mDrawerList.setItemChecked(position, true);
//        setTitle(mPlanetTitles[position]);
//        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mActionBar.setTitle(title);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
}
