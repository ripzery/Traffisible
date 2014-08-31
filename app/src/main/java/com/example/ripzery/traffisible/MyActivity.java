package com.example.ripzery.traffisible;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class MyActivity extends Activity {

    private static String APP_ID = "61d787a9";
    private static String KEY = "jADjas9PXU";
    private AsyncConnect connect;
    private AsyncJSON connectJSON;
    private JsonObject jsonObject;
    private JsonArray jsonArray;
    private JsonElement jsonElement;
    private String jsonString;
    private String randomString = "";
    private String url;
    private String passKey = "";
    private ArrayList<String> newsDescription;

    public static String md5(String s) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(), 0, s.length());
            String hash = new BigInteger(1, digest.digest()).toString(16);
            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        randomString = getRandomString();
        passKey = md5(APP_ID + randomString) + md5(KEY + randomString);
        url = getURL("getIncident", "JSON", APP_ID);
        connectJSON = new AsyncJSON(url);
        try {
            jsonString = connectJSON.execute().get();
            Log.d("TADA", url);
//            TextView tvFront1 = (TextView)findViewById(R.id.tvFront1);
            Gson gson = new Gson();
            JsonParser jsonParser = new JsonParser();
            jsonElement = jsonParser.parse(jsonString);
            jsonElement = jsonElement.getAsJsonObject().getAsJsonObject("info").get("news");
            News[] listNews = gson.fromJson(jsonElement, News[].class);

            Log.d("News Size", "" + listNews.length);
//            tvFront1.setText("There are "+listNews.length+" news available now.");

            newsDescription = new ArrayList<String>();
            for (News temp : listNews) {
                Log.d("All ID", "" + temp.getId());
                newsDescription.add(temp.getDescription());
            }

            ListView listView = (ListView) findViewById(R.id.list);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, newsDescription);

            listView.setAdapter(adapter);


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public String getURL(String apiType, String apiFormat) {
        return "http://api.traffy.in.th/apis/apitraffy.php?api=" + apiType + "&key=" + passKey + "&format=" + apiFormat;
    }

    public String getURL(String apiType, String apiFormat, String APP_ID) {
        return "http://api.traffy.in.th/apis/apitraffy.php?api=" + apiType + "&key=" + passKey + "&format=" + apiFormat + "&appid=" + APP_ID;
    }

    public String getRandomString() {
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
