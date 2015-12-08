package com.joe.demo.loadpicturedemo;

import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by joe_fan on 15/12/8.
 */
public class DownloadTask extends AsyncTask<String, Void, String>{

    private File downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        OutputStream outputStream = null;


        URL url = new URL(myurl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        int response = conn.getResponseCode();
        Log.d(MyTools.TAG, "The response is: " + response);
        is = conn.getInputStream();

        return null;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            downloadUrl(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}


