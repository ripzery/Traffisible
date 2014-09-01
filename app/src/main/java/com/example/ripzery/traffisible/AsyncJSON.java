package com.example.ripzery.traffisible;

import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.stream.MalformedJsonException;

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
    private MyActivity myActivity;
    private String url = "";

    public AsyncJSON(MyActivity myActivity, String url) {
        this.myActivity = myActivity;
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
        } catch (MalformedJsonException e) {
            Toast.makeText(myActivity, "Cannot fetch data, Please try again", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
