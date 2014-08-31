package com.example.ripzery.traffisible;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by visit on 8/31/14 AD.
 */
public class AsyncJSON extends AsyncTask<String, String, String> {

    private String url = "";

    public AsyncJSON(String url){
        this.url = url;
    }

    @Override
    protected String doInBackground(String... strings) {

        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        String line = null;
        StringBuilder builder = new StringBuilder();
        try {
            HttpResponse response = client.execute(get);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            while((line = reader.readLine()) != null){
                builder.append(line);
//                Log.d("MSG",line);
            }

            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
