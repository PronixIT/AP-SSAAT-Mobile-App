package com.yvs.ssaat.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yvs.ssaat.R;
import com.yvs.ssaat.common.Constants;

public class ProfileActivity extends BaseActivity {
    private TextView user_profile_name, txt_designation, txt_email, txt_mobilenumber, txt_pin;
    private SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_profiles, frame_base);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        user_profile_name = (TextView) findViewById(R.id.user_profile_name);
        txt_designation = (TextView) findViewById(R.id.txt_designation);
        txt_email = (TextView) findViewById(R.id.txt_email);
        txt_mobilenumber = (TextView) findViewById(R.id.txt_mobilenumber);
        txt_pin = (TextView) findViewById(R.id.txt_pin);

        String name = sharedpreferences.getString("name", null);
        String email = sharedpreferences.getString("email", null);
        String password = sharedpreferences.getString("password", null);
        String number = sharedpreferences.getString("number", null);
        String designation = sharedpreferences.getString("designation", null);
        int pin = sharedpreferences.getInt("pin", 0);
        user_profile_name.setText(Constants.userMasterDO.userName);
        txt_designation.setText(Constants.userMasterDO.designation);
        txt_email.setText(Constants.userMasterDO.email);
        txt_mobilenumber.setText(Constants.userMasterDO.mobileNumber);
        txt_pin.setText(Constants.PIN);

    }
}
