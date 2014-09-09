package com.example.ripzery.traffisible.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ripzery.traffisible.JSONObjectClass.News;
import com.example.ripzery.traffisible.MyActivity;
import com.example.ripzery.traffisible.R;
import com.example.ripzery.traffisible.TrafficNewsCardAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.TimedUndoAdapter;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.Collections;

public class ReportNewsFragment extends Fragment {

    private static final String APP_ID = "61d787a9";
    private JsonElement jsonElement;
    private String jsonString;
    private String url;
    private String passKey = "";
    private ArrayList<News> listNews;
    private MyActivity myActivity;
    private View mRootView;
    private DynamicListView listView;
    private ProgressDialog progress;

    public ReportNewsFragment() {

    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);
        myActivity = (MyActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getArguments() != null) {
            passKey = getArguments().getString("passkey");
        }
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_traffic_news, container, false);
        if (savedInstanceState == null) {
            this.mRootView = view;
            this.myActivity = (MyActivity) getActivity();
            loadContent();
            Log.d("Saved", "null");
        }

        return view;
    }

    public void loadContent() {
        final MediaPlayer mediaPlayer = MediaPlayer.create(myActivity, R.raw.mimimi);
        mediaPlayer.seekTo(5500);
        mediaPlayer.setVolume((float) 0.3, (float) 0.3);
        mediaPlayer.start();
        final DynamicListView dynamicListView = (DynamicListView) mRootView.findViewById(R.id.dynamiclistview);
        final ProgressBar mProgressBar = (ProgressBar) myActivity.findViewById(R.id.google_progress);
        mProgressBar.setVisibility(View.VISIBLE);
        AsyncHttpClient client = new AsyncHttpClient();
        url = getURL(APP_ID);
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                mediaPlayer.stop();
                mProgressBar.setVisibility(View.GONE);
                jsonString = new String(responseBody);
                Log.d("URL :", url);
                Gson gson = new Gson();
                JsonParser jsonParser = new JsonParser();
                try {
                    jsonElement = jsonParser.parse(jsonString);
                    jsonElement = jsonElement.getAsJsonObject().getAsJsonObject("info").get("news");

                    News[] news = gson.fromJson(jsonElement, News[].class);
                    Log.d("News Size", "" + news.length);

                    listNews = new ArrayList<News>();
                    Collections.addAll(listNews, news);

                    listView = (DynamicListView) mRootView.findViewById(R.id.dynamiclistview);
                    final TrafficNewsCardAdapter adapter = new TrafficNewsCardAdapter(myActivity, listNews);
                    AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(adapter);
                    animationAdapter.setAbsListView(listView);
                    listView.setAdapter(adapter);

                    TimedUndoAdapter timedUndoAdapter = new TimedUndoAdapter(adapter, myActivity,
                            new OnDismissCallback() {
                                @Override
                                public void onDismiss(@NonNull ViewGroup viewGroup, @NonNull int[] ints) {
                                    for (int position : ints) {
                                        listNews.remove(position);
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            });
                    timedUndoAdapter.setAbsListView(listView);
                    listView.setAdapter(timedUndoAdapter);
                    listView.enableSimpleSwipeUndo();

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            myActivity.openMapFragment(listNews, view, i);
                        }
                    });
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    Toast.makeText(myActivity, "Connection failed !", Toast.LENGTH_SHORT).show();
                    myActivity.setPassKey();
                    new CountDownTimer(3500, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            if (listView == null)
                                Toast.makeText(myActivity, "Reconnect in " + millisUntilFinished / 1000, Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(myActivity, "Connection was successful", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFinish() {
                            passKey = myActivity.getPassKey();
                            loadContent();
                        }
                    }.start();
                }
                dynamicListView.setVisibility(View.VISIBLE);

            }


            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(myActivity, "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getURL(String APP_ID) {
        return "http://api.traffy.in.th/apis/apitraffy.php?api=" + "getIncident" + "&key=" + passKey + "&format=" + "JSON" + "&appid=" + APP_ID;
    }

}
