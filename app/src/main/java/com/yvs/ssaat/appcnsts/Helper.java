package com.yvs.ssaat.appcnsts;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.yvs.ssaat.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Helper {


    //set typeFace for TetxViews
    //.......
    public static void getTypeFace(final Context ctx, final String fontname, final TextView textView) {
        Typeface myTypeface = Typeface.createFromAsset(ctx.getAssets(), fontname);
        textView.setTypeface(myTypeface);
    }

    //set network error  notification for app
    //.......
    public static void getnetworkError(final Context ctx) {

        final Dialog d = new Dialog(ctx, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setCancelable(false);
        d.setCanceledOnTouchOutside(false);
        d.setContentView(R.layout.dialog_network_notification);
        d.show();
        TextView txt_head_network = (TextView) d.findViewById(R.id.txt_head_network);
        TextView txt_subtitle = (TextView) d.findViewById(R.id.txt_subtitle);
        TextView txt_desc = (TextView) d.findViewById(R.id.txt_desc);
        TextView txt_gotit = (TextView) d.findViewById(R.id.txt_gotit);
        TextView txt_retry = (TextView) d.findViewById(R.id.txt_retry);

        getTypeFace(ctx, ctx.getString(R.string.headerfontname), txt_head_network);
        getTypeFace(ctx, ctx.getString(R.string.contentfontname), txt_subtitle);
        getTypeFace(ctx, ctx.getString(R.string.contentfontname), txt_desc);
        getTypeFace(ctx, ctx.getString(R.string.contentfontname), txt_head_network);
        getTypeFace(ctx, ctx.getString(R.string.headerfontname), txt_gotit);

        txt_gotit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                d.dismiss();
                ((Activity) ctx).finish();
            }
        });

        txt_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CheckNetwork.isInternetAvailable(ctx)) {
                    d.dismiss();
                } else {
                    d.dismiss();
                    getnetworkError(ctx);
                }

            }
        });

    }



    public static String checkNetworkType(Context ctx) {
        String ssid = null;
        String wifi = null;
        ConnectivityManager connManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final WifiManager wifiManager = (WifiManager) ctx.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        if (networkInfo.isConnected()) {
            if (connectionInfo != null && !TextUtils.isEmpty(connectionInfo.getSSID())) {
                ssid = connectionInfo.getSSID();
                if (ssid != null) {
                    wifi = "Wifi";
                }

            }

        } else {
            TelephonyManager mTelephonyManager = (TelephonyManager)
                    ctx.getSystemService(Context.TELEPHONY_SERVICE);
            int networkType = mTelephonyManager.getNetworkType();
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return "2g";
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:

                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:

                    return "3g";
                case TelephonyManager.NETWORK_TYPE_LTE:

                    return "4g";

                case ConnectivityManager.TYPE_WIFI:
                    return "wifi";
                default:
                    return "nofound";
            }

        }
        return wifi;
    }

    public static String getUniqId(Context ctx) {
        String uniqid = Settings.Secure.getString(ctx.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        return uniqid;
    }


    public static String getDeviceversionName(Context ctx) {
        String deviceversionname = Build.VERSION_CODES.class.getFields()[Build.VERSION.SDK_INT].getName();
        return deviceversionname;
    }

    public static String getversionname(Context ctx) {
        String versionname;
        try {
            PackageInfo pInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
            versionname = pInfo.versionName;
            return versionname;
        } catch (
                PackageManager.NameNotFoundException e)

        {
            e.printStackTrace();
        }

        return "version";
    }

    public static int getversioncode(Context ctx) {
        int versioncode;
        try {
            PackageInfo pInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
            versioncode = pInfo.versionCode;
            return versioncode;
        } catch (
                PackageManager.NameNotFoundException e)

        {
            e.printStackTrace();
        }

        return 1;
    }









    public static int getDisplaywidth(Context ctx) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics = ctx.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        return width;
    }


    public static int getDisplayheight(Context ctx) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics = ctx.getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        return height;
    }

    public static boolean validateEmailAddress(String emailAddress) {
        String expression = "^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = emailAddress;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }


}
