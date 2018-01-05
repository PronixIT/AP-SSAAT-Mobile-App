package com.yvs.ssaat;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;

import com.yvs.ssaat.common.Constants;

import java.io.File;

/**
 * Created by ravi on 12/29/2017.
 */

public class ssaat extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
//        MultiDex.install(this);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
//                AndroidUtils.showMsg(AndroidUtils.getExceptionRootMessage(throwable), MicroXtend.this.getApplicationContext());
                System.exit(0);
            }
        });
        setAppDirectorypath();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public void setAppDirectorypath() {
        try {
            String device = Build.DEVICE.toUpperCase();
            if (device.equals("GENERIC") || device.equals("SDK")) {
                Constants.ROOTDIRECTORYPATH = getFilesDir().getAbsolutePath();
            } else {
                File[] file = this.getExternalFilesDirs(null);
                Constants.ROOTDIRECTORYPATH = file[0].getAbsolutePath();
            }

        } catch (Exception e) {

        }
    }





}
