package com.yvs.ssaat.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.yvs.ssaat.activities.LoginActivity;

/**
 * Created by NAVEEN KS on 12/26/2017.
 */

public class SessionManager {
    private static final String IS_LOGIN ="isLogin" ;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE=0;

    private static final String PREF_NAME = "welcome";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String KEY_USER_ID = "userid";
    private static final String KEY_USER_NAME = "username";
    private static final String KEY_USER_ROLE = "userrole";
    private static final String KEY_USER_MOBILE = "usermobile";
    private static final String KEY_USER_TYPE = "usertype";
    private static final String KEY_USER_MACID = "usermac";

    public SessionManager(Context context){
        this.context=context;
        preferences=context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor=preferences.edit();
    }

    public void setFirstTimeLaunched(boolean isFirstTime){
        editor.putBoolean(IS_FIRST_TIME_LAUNCH,isFirstTime);
        editor.commit();
    }
    public boolean isFirstTime(){
        return preferences.getBoolean(IS_FIRST_TIME_LAUNCH,true);
    }

    public void checkLogin()
    {
        if(!this.isLoggedIn()) {
            Intent i = new Intent(context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    public void logoutUser()
    {
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(IS_LOGIN, false);
    }

    public void createLoginSession(String userId, String actualUserName,String roleName,
                                   String mobileNumber,String userType,String userMacId)
    {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USER_ID, userId);
        editor.putString(KEY_USER_NAME , actualUserName);
        editor.putString(KEY_USER_ROLE , roleName);
        editor.putString(KEY_USER_MOBILE , mobileNumber);
        editor.putString(KEY_USER_TYPE , userType);
        editor.putString(KEY_USER_MACID , userMacId);
        System.out.println("usertype:ff "+userType);
        editor.commit();
    }

    public String getUserID(){
        return preferences.getString(KEY_USER_ID,"");
    }

}
