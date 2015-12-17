package com.joe.demo.loadpicturedemo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.LruCache;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    private ImageView mImageView;
    private NetworkImageView mNetworkImageView;
    Spinner sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyTools.getTitleBarTwoBtn(this, "test", R.drawable.ic_launcher, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Log.v(MyTools.TAG, "ID: " + item.getItemId());
                        switch (item.getItemId()) {
                            case R.id.check_update:
                                return true;
                            case R.id.version:
                                try {
                                    Dialog dg = new Dialog(MainActivity.this);
                                    dg.setTitle("Version:" + MyTools.getVersionName(getApplicationContext()));
                                    dg.show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return true;
                            case R.id.update:
                                doTask();
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.inflate(R.menu.menu);
                popupMenu.show();
            }
        });
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mNetworkImageView = (NetworkImageView) findViewById(R.id.networkImageView);
//        showImageByNetworkImageView();//load picture
//        showJsonRequestResult();//JsonDemo
//        showJsonArrayRequestResult(); //JsonArrayDemo
//        //doTask();//confirm network connection
//        String test = "1.2123123";
//        String test2 = "0.12123";
//        String path = Environment.getExternalStorageDirectory().getPath() + "/update.apk";
//        float test3 = MyTools.StringtoFloat(test) + MyTools.StringtoFloat(test2);
//        Log.e(MyTools.TAG, "test3" +  test3);

        testWeatherDemo();

    }

//Image
    private void showImageByNetworkImageView() {
        String imageUrl = "";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(
                20);
        ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String key, Bitmap value) {
                lruCache.put(key, value);
            }

            @Override
            public Bitmap getBitmap(String key) {
                return lruCache.get(key);
            }
        };
        ImageLoader imageLoader = new ImageLoader(requestQueue, imageCache);
        mNetworkImageView.setTag("url");
        mNetworkImageView.setImageUrl(imageUrl, imageLoader);
    }

//Json
    private void showJsonRequestResult(){
        String woeid = "202487889";

        String url = "https://query.yahooapis.com/v1/public/yql?q=select%20item.condition%20from%20weather.forecast%20where%20" +
                "woeid" +
                "%20%3D%" +
                woeid +
                "&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Dangou", "Json respose = " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(MyTools.TAG, "Json" + error);
            }
        });
        //Singleton
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    //JsonArray
    private void showJsonArrayRequestResult(){

//        Map<String, String> params = new HashMap<String, String>();
//        params.put("q","select wind from weather.forecast where woeid=2460286");
//        params.put("format","json");
//        JSONObject jsonObject = new JSONObject(params);
        String woeid = "202487889";

        String url = "https://query.yahooapis.com/v1/public/yql?q=select%20item.condition%20from%20weather.forecast%20where%20" +
                "woeid" +
                "%20%3D%" +
                woeid +
                "&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("DANGOU", "GET: " + response.toString());
//                try {
//                    String []realname = new String[2];
//                    String [] mobile = new String[2];
//
//                    for(int i=0; i< response.length(); i++){
//                        JSONObject jsonObject = response.getJSONObject(i);
//                        realname[i] = jsonObject.getString("realname");
//                        mobile[i] = jsonObject.getString("mobile");
//                        Log.e(MyTools.TAG, "JsonObject from loop respose = " + response.toString());
//                        Log.v(MyTools.TAG, "realname: " + realname[i] + " mobile: " + mobile[i]);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                Log.e(MyTools.TAG, "JsonArray respose = " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(MyTools.TAG, "JsonArray error = " + error);
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

    //HTTP
    private final String urls = "http://www.nduoa.com/apk/download/912990?from=ndoo";
    private void doTask(){
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadTask(this).execute(urls); //Download Task
            Log.e(MyTools.TAG, "connected");
        } else {
            Log.e(MyTools.TAG, "disconnect");
        }
    }

    //Yahoo wetherapi
    //curl https://query.yahooapis.com/v1/public/yql \
//    -d q="select wind from weather.forecast where woeid=2460286" \
//            -d format=json
    private void testWeatherDemo(){
        showJsonRequestResult();

    }

}
