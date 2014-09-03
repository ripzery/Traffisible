package com.example.ripzery.traffisible;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * interface.
 */
public class ReportNewsFragment extends Fragment {

    private static String APP_ID = "61d787a9";
    private static String KEY = "jADjas9PXU";
    private AbsListView mListView;
    private ListAdapter mAdapter;
    private AsyncConnect connect;
    private AsyncJSON connectJSON;
    private JsonObject jsonObject;
    private JsonArray jsonArray;
    private JsonElement jsonElement;
    private String jsonString;
    private String randomString = "";
    private String url;
    private String passKey = "";
    private ArrayList<News> listNews;
    private MyActivity myActivity;

    public ReportNewsFragment() {
    }

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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.myActivity = (MyActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        randomString = getRandomString();
        passKey = md5(APP_ID + randomString) + md5(KEY + randomString);
        url = getURL("getIncident", "JSON", APP_ID);
        connectJSON = new AsyncJSON(myActivity, url);
        try {
            jsonString = connectJSON.execute().get();
            Log.d("TADA", url);
            Gson gson = new Gson();
            JsonParser jsonParser = new JsonParser();
            jsonElement = jsonParser.parse(jsonString);
            jsonElement = jsonElement.getAsJsonObject().getAsJsonObject("info").get("news");

            News[] news = gson.fromJson(jsonElement, News[].class);

            Log.d("News Size", "" + news.length);

            listNews = new ArrayList<News>();
            for (News temp : news) {
                listNews.add(temp);
            }


            final DynamicListView listView = (DynamicListView) myActivity.findViewById(R.id.dynamiclistview);
            final CardAdapter adapter = new CardAdapter(myActivity, listNews);

//            TimedUndoAdapter timedUndoAdapter = new TimedUndoAdapter(adapter, myActivity,
//                    new OnDismissCallback() {
//                        @Override
//                        public void onDismiss(@NonNull ViewGroup viewGroup, @NonNull int[] ints) {
//                            for (int position : ints) {
//                                listNews.remove(position);
//                                adapter.notifyDataSetChanged();
//                            }
//                        }
//                    });
//            timedUndoAdapter.setAbsListView(listView);
//            listView.setAdapter(timedUndoAdapter);
//            listView.enableSimpleSwipeUndo();


        } catch (JsonSyntaxException e) {
            onCreate(savedInstanceState);
            e.printStackTrace();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_traffic_news, container, false);
        return view;
    }
}
