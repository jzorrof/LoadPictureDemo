package com.joe.demo.loadpicturedemo;

import android.graphics.Bitmap;
import android.media.Image;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

/**
 * Created by joe_fan on 15/12/2.
 * TODO
 */
public class LoadPicture extends Request<Bitmap>{

    public LoadPicture(int method, String url, Response.ErrorListener listener) {
        super(method, url, listener);
    }

    @Override
    protected Response<Bitmap> parseNetworkResponse(NetworkResponse response) {
        return null;
    }

    @Override
    protected void deliverResponse(Bitmap response) {

    }
}
