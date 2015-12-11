package com.joe.demo.loadpicturedemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;

/**
 * Created by joe_fan on 15/12/8.
 */
public class MyTools {
    public final static String TAG = "DEMO_Volley_";
    public final static String RESPONSE202 = "200";
    private static Activity mActivity;


    public static void packageInstall(Context context, String filename){
        String fileName = filename;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * @param activity
     * @param title
     * @see [自定义标题栏]
     */
    public static void getTitleBarTwoBtn(Activity activity, String title, int imageSrc, View.OnClickListener onClickListener) {
        mActivity = activity;
        activity.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        activity.setContentView(R.layout.custom_title);
        activity.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.custom_title);
        TextView textView = (TextView) activity.findViewById(R.id.head_center_text);
        textView.setText(title);
        ImageButton titleBackBtn = (ImageButton) activity.findViewById(R.id.head_TitleBackBtn);
        ImageButton rightBtn = (ImageButton) activity.findViewById(R.id.head_RightBtn);
        rightBtn.setImageResource(imageSrc);
        rightBtn.setOnClickListener(onClickListener);
        rightBtn.setVisibility(View.VISIBLE);
        titleBackBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                KeyEvent newEvent = new KeyEvent(KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_BACK);
                mActivity.onKeyDown(KeyEvent.KEYCODE_BACK, newEvent);
                mActivity.finish();
            }
        });
    }

    public static String getVersionName(Context context) throws Exception
    {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
        String version = packInfo.versionName;
        Log.v(TAG, "verion:" + version);
        return version;
    }
}

