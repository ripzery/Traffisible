package com.example.ripzery.traffisible;

import android.app.ActionBar;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MyActivity extends FragmentActivity {

    /*private static String APP_ID = "61d787a9";
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
    private ArrayList<News> listNews;*/

/*    public static String md5(String s) {
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
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            ReportNewsFragment report = new ReportNewsFragment();
            report.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(R.id.fragment_Traffic_News, report).commit();
        }

//        setContentView(R.layout.activity_my);

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

        /*randomString = getRandomString();
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
        }*/
    }

    /*public String getURL(String apiType, String apiFormat) {
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
    }*/

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
