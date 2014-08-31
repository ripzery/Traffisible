package com.example.ripzery.traffisible;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by visit on 8/31/14 AD.
 */
public class AsyncJSON extends AsyncTask<String, String, JSONObject>{

    private String url = "";

    public AsyncJSON(String url){
        this.url = url;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {

        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        String line = null;
        StringBuilder builder = new StringBuilder();
        try {
            HttpResponse response = client.execute(get);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            while((line = reader.readLine()) != null){
                builder.append(line);
                builder.append("\n");
            }
            Log.d("JSON", builder.toString());
            JSONObject json = new JSONObject(builder.toString());
            return json;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
