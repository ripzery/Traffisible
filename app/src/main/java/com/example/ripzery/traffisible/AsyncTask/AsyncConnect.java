package com.example.ripzery.traffisible.AsyncTask;

import android.os.AsyncTask;

import com.example.ripzery.traffisible.MyActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

/**
 * Created by visit on 8/31/14 AD.
 */
public class AsyncConnect extends AsyncTask<String, String, String> {

    private static String APP_ID = "61d787a9";
    private final MyActivity myActivity;
    private String getRandomStringURL = "", line;

    public AsyncConnect(MyActivity myActivity) {
        this.myActivity = myActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet get = new HttpGet("http://api.traffy.in.th/apis/getKey.php?appid=" + APP_ID);
            HttpResponse response = httpClient.execute(get);

            BufferedHttpEntity buf = new BufferedHttpEntity(response.getEntity());
            BufferedReader in = new BufferedReader(new InputStreamReader(buf.getContent()));

            while ((line = in.readLine()) != null) {
                getRandomStringURL = getRandomStringURL + line;
            }
            in.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getRandomStringURL;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }
}
