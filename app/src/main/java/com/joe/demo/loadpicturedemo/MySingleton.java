package com.joe.demo.loadpicturedemo;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by joe_fan on 15/12/4.
 */
public class MySingleton {
    private RequestQueue mRequestQueue;
    private static MySingleton ourInstance;
    private static Context mCtx;

    public static MySingleton getInstance(Context context) {
        if(ourInstance == null){
            ourInstance = new MySingleton(context);
        }
        return ourInstance;
    }


    private MySingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue(){
        if (mRequestQueue == null){

            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());

        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
