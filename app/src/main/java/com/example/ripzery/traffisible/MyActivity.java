package com.example.ripzery.traffisible;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;


public class MyActivity extends Activity {

    private static String APP_ID = "61d787a9";
    private static String KEY = "jADjas9PXU";
    private AsyncConnect connect;
    private AsyncJSON connectJSON;
    private JSONObject jsonObject;
    private String randomString = "";
    private String passKey = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        randomString = getRandomString();
        passKey = md5(APP_ID+randomString)+md5(KEY+randomString);
//        Log.d("TADA",passKey);
        connectJSON = new AsyncJSON(getURL("getIncident","JSON",APP_ID));
        try {
            jsonObject = connectJSON.execute().get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public String getURL(String apiType,String apiFormat){
        return "http://api.traffy.in.th/apis/apitraffy.php?api="+apiType+"&key="+passKey+"&format="+apiFormat;
    }

    public String getURL(String apiType,String apiFormat,String APP_ID){
        return "http://api.traffy.in.th/apis/apitraffy.php?api="+apiType+"&key="+passKey+"&format="+apiFormat+"&appid="+APP_ID;
    }

    public String getRandomString(){
        connect = new AsyncConnect();
        try {
            return connect.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String md5(String s)
    {
        MessageDigest digest;
        try
        {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(),0,s.length());
            String hash = new BigInteger(1, digest.digest()).toString(16);
            return hash;
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
