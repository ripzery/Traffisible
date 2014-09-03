package com.example.ripzery.traffisible;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.ripzery.traffisible.dummy.DummyContent;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.TimedUndoAdapter;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 * <p/>
 * See the Android Training lesson <a href=
 * "http://developer.android.com/training/basics/fragments/communicating.html"
 * >Communicating with Other Fragments</a> for more information.
 */
public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    public void onFragmentInteraction(String id);
}

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class ReportNewsFragment extends Fragment implements AbsListView.OnItemClickListener {

    private static String APP_ID = "61d787a9";
    private static String KEY = "jADjas9PXU";
    private OnFragmentInteractionListener mListener;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        randomString = getRandomString();
        passKey = md5(APP_ID + randomString) + md5(KEY + randomString);
        url = getURL("getIncident", "JSON", APP_ID);
        connectJSON = new AsyncJSON(this, url);
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


            final DynamicListView listView = (DynamicListView) findViewById(R.id.dynamiclistview);
            final CardAdapter adapter = new CardAdapter(this, listNews);

            TimedUndoAdapter timedUndoAdapter = new TimedUndoAdapter(adapter, MyActivity.this,
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
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

//                    News point = listNews.get(position).
                }
            });


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

}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyText instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

}
