package com.joe.demo.loadpicturedemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by joe_fan on 15/12/8.
 * modify from android.developer.com Demo.
 */
public class DownloadTask extends AsyncTask<String, Integer, String>{
    String path = Environment.getExternalStorageDirectory().getPath() + "/update.apk";
    private Context cxt=null;
    ProgressDialog pd;

    DownloadTask(Context context){
        cxt = context;
        pd = new ProgressDialog(context);
    }

    private File downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        publishProgress(0);

        try {
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
//            is = conn.getInputStream();
            is = new BufferedInputStream(url.openStream(),10*1024); //add buffer
            os = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/update.apk");
            int lenghtOfFile = conn.getContentLength();
            int count = 0;
            byte data[] = new byte[1024];
            long total = 0;
            while ((count = is.read(data)) != -1) {
                total += count;
                // Write data to file
                publishProgress((int) total * 100/lenghtOfFile);
                os.write(data, 0, count);
            }
            // Flush output
            os.flush();
            // Close streams
            os.close();
            is.close();

        } catch (Exception e){
            //if(!isCancelled())
                //downloadUrl(myurl);
            Log.e(MyTools.TAG, e.toString());
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setTitle("ProgressDialog");
        pd.setMessage("Download file ...");
        pd.setButton(DialogInterface.BUTTON_NEGATIVE,"取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancel(true);
            }
        });
        pd.setCancelable(false);
        pd.show();

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
    protected void onProgressUpdate(Integer... values) {
        pd.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pd.dismiss();
        if(!isCancelled())
            MyTools.packageInstall(cxt, path);//package install

    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}


