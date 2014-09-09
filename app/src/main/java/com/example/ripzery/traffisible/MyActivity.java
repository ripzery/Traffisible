package com.example.ripzery.traffisible;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ripzery.traffisible.Fragment.CCTVFragment;
import com.example.ripzery.traffisible.Fragment.MapsCCTVFragment;
import com.example.ripzery.traffisible.Fragment.MapsFragment;
import com.example.ripzery.traffisible.Fragment.ReportNewsFragment;
import com.example.ripzery.traffisible.JSONObjectClass.CCTV;
import com.example.ripzery.traffisible.JSONObjectClass.News;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionViewTarget;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


public class MyActivity extends FragmentActivity {

    private static final String APP_ID = "61d787a9";
    private static final String KEY = "jADjas9PXU";
    private Fragment reportFrag, cctvFrag;
    private MapsFragment mapsFragment;
    private MapsCCTVFragment mapsCCTVFragment;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] mDrawerString;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBar mActionBar;
    private Fragment newFragment, oldFragment;
    private String passKey = "";
    private ShowcaseView builder, builder2;

    private static String md5(String s) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(), 0, s.length());
            return new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        mActionBar = getActionBar();
        setTitle(" TRAFFISIBLE");
        setSubTitle("     คลิกแถบด้านซ้ายเพื่อเลือกเมนูข่าว");

        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerString = getResources().getStringArray(R.array.drawer_array);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mDrawerString));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
//        mDrawerList.setItemChecked(0,true);
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        builder =
        new ShowcaseView.Builder(this)
                .setTarget(new ActionViewTarget(this, ActionViewTarget.Type.HOME))
                .setContentTitle("Navigation Drawer")
                .setStyle(R.style.CustomShowcaseTheme2)
                .setContentText("You can click here to show menus of content.")
                .hideOnTouchOutside()
                .build();

        if (!isPassKeySet())
            setPassKey();

    }

    public void openMapFragment(ArrayList<News> listNews, View view, int i) {

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
            bundle.putString("endpoint_name", endPoint.getName());
            bundle.putString("endpoint_lat", endPoint.getLatitude());
            bundle.putString("endpoint_lon", endPoint.getLongitude());
        }
        mapsFragment = new MapsFragment();
        mapsFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.content_layout, mapsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void openCCTVFragment(ArrayList<CCTV> listCCTV, View view, int i) {
        CCTV cctv = listCCTV.get(i);
        CCTV.Point point = cctv.getPoint();
        Bundle bundle = new Bundle();
        Log.d("Position", "Lat : " + point.getLat() + " Long : " + point.getLng());
        bundle.putString("name", cctv.getName());
        bundle.putString("lat", point.getLat());
        bundle.putString("lng", point.getLng());
        bundle.putString("img", cctv.getUrl());
        mapsCCTVFragment = new MapsCCTVFragment();
        mapsCCTVFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.content_layout, mapsCCTVFragment);
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

    private void selectItem(final int position) {

        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out);
        oldFragment = newFragment;
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);

        if (position < 2 && !isPassKeySet()) {
            Toast.makeText(getApplicationContext(), "Missing passkey!", Toast.LENGTH_SHORT).show();
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString("passkey", passKey);
        bundle.putString("appid", APP_ID);

        switch (position) {
            case 0:
                if (reportFrag == null) {
                    reportFrag = new ReportNewsFragment();
                    reportFrag.setArguments(bundle);
                    newFragment = reportFrag;
                } else {
                    newFragment = reportFrag;
                }
                setSubTitle("    รายงานข่าวจราจร - ล่าสุด");
                break;
            case 1:

                if (cctvFrag == null) {
                    cctvFrag = new CCTVFragment();
                    cctvFrag.setArguments(bundle);
                    newFragment = cctvFrag;
                } else {
                    newFragment = cctvFrag;
                }
                setSubTitle("     ภาพถ่าย CCTV - ล่าสุด");
                break;
            case 3:
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
                break;
        }
        if (position < 2) {
            if (newFragment.isAdded()) {
                transaction.hide(oldFragment);
                transaction.show(newFragment);
            } else {
                if (oldFragment != null)
                    transaction.hide(oldFragment);
                transaction.add(R.id.content_layout, newFragment);
            }
//            transaction.replace(R.id.content_layout, newFragment);
            transaction.commit();
        }
    }

    public void setPassKey() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(5000);
        final ProgressBar mProgressBar = (ProgressBar) findViewById(R.id.google_progress);
        mProgressBar.setVisibility(View.VISIBLE);
        client.get("http://api.traffy.in.th/apis/getKey.php?appid=" + APP_ID, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String randomString = new String(responseBody);
                passKey = md5(APP_ID + randomString) + md5(KEY + randomString);
                Toast.makeText(getApplicationContext(), "Passkey to access API has been set successfully.", Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.GONE);
                new CountDownTimer(6000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        Toast.makeText(getApplicationContext(), "Reconnect in " + millisUntilFinished / 1000, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish() {
                        setPassKey();
                    }
                }.start();
            }
        });
    }

    public String getPassKey() {
        return passKey;
    }

    public boolean isPassKeySet() {
        return !passKey.equals("");
    }

    public void setSubTitle(String subTitle) {

        SpannableString mStringSubTitle = new SpannableString(subTitle);
        mStringSubTitle.setSpan(new TypefaceSpan(this, "Roboto-Light.ttf"), 0, mStringSubTitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mActionBar.setSubtitle(mStringSubTitle);
    }

    @Override
    public void setTitle(CharSequence title) {
        SpannableString mStringTitle = new SpannableString(title);
        mStringTitle.setSpan(new TypefaceSpan(this, "Roboto-Medium.ttf"), 0, mStringTitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mActionBar.setTitle(mStringTitle);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
}
