package com.joe.demo.loadpicturedemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

public class MainActivity extends Activity {

    private ImageView mImageView;
    private NetworkImageView mNetworkImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mNetworkImageView = (NetworkImageView) findViewById(R.id.networkImageView);
        showImageByNetworkImageView();
    }

    private void showImageByNetworkImageView() {
        String imageUrl = "http://121.40.223.11//mob/downPic?picid=b29fe3a9ab30447a96011105217a82f5";
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



}
