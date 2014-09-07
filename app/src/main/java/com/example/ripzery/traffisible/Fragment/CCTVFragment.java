package com.example.ripzery.traffisible.Fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.ripzery.traffisible.CCTVCardAdapter;
import com.example.ripzery.traffisible.JSONObjectClass.CCTV;
import com.example.ripzery.traffisible.MyActivity;
import com.example.ripzery.traffisible.R;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
import java.util.Map;
import java.util.Set;


public class CCTVFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private String passKey = "", appId = "";

    // TODO: Rename and change types of parameters
    private View mRootView;
    private MyActivity myActivity;
    private OnFragmentInteractionListener mListener;
    private ArrayList<CCTV> listCCTV = new ArrayList<CCTV>();
    private DynamicListView listView;

    public CCTVFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            passKey = getArguments().getString("passkey");
            appId = getArguments().getString("appid");
            Log.d("passkey", passKey);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cctvimage, container, false);
        mRootView = view;
        loadContent();
        return view;
    }

    public void loadContent() {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = getURL();
        Log.d("URL", url);
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                try {
                    JsonElement jsonElement = parser.parse(json);
                    JsonObject jsonObject = jsonElement.getAsJsonArray().get(0).getAsJsonObject();
                    Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();

                    for (Map.Entry<String, JsonElement> entry : entrySet) {
                        jsonElement = jsonObject.get(entry.getKey());
                        listCCTV.add(gson.fromJson(jsonElement, CCTV.class));
                    }

                    listView = (DynamicListView) mRootView.findViewById(R.id.dynamiclistview2);
                    final CCTVCardAdapter adapter = new CCTVCardAdapter(myActivity, listCCTV);
                    AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(adapter);
                    animationAdapter.setAbsListView(listView);
                    listView.setAdapter(adapter);

                    TimedUndoAdapter timedUndoAdapter = new TimedUndoAdapter(adapter, myActivity,
                            new OnDismissCallback() {
                                @Override
                                public void onDismiss(@NonNull ViewGroup viewGroup, @NonNull int[] ints) {
                                    for (int position : ints) {
                                        listCCTV.remove(position);
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
                            myActivity.openCCTVFragment(listCCTV, view, i);
                        }
                    });


                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    Toast.makeText(myActivity, "Connection failed !", Toast.LENGTH_SHORT).show();
                    myActivity.setPassKey();
                    new CountDownTimer(5500, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            if (listView == null)
                                Toast.makeText(myActivity, "Reconnect in " + millisUntilFinished / 1000, Toast.LENGTH_SHORT).show();
                            else {
                                Toast.makeText(myActivity, "Connection was successful", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFinish() {
                            passKey = myActivity.getPassKey();
                            loadContent();
                        }
                    }.start();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public String getURL() {
        return "http://api.traffy.in.th/apis/apitraffy.php?format=JSON&api=getCCTV&available=t&key=" + passKey + "&appid=" + appId;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myActivity = (MyActivity) activity;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
