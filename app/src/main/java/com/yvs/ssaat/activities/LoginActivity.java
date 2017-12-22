package com.yvs.ssaat.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yvs.ssaat.MainActivity;
import com.yvs.ssaat.R;


public class LoginActivity extends AppCompatActivity {
    private Button btn_login;
    private EditText edt_username, edt_pwd;
    private TextView txt_forgot, txt_signup;
    private SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        txt_signup = (TextView) findViewById(R.id.txt_signup);
        txt_forgot = (TextView) findViewById(R.id.txt_forgot);
        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_username.setText("nouser");
        edt_pwd = (EditText) findViewById(R.id.edt_pwd);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLoginValidation("");
            }
        });

        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                LoginActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });


    }

    private void getLoginValidation(String url) {
        if (edt_username.getText().toString().equalsIgnoreCase("") && edt_pwd.getText().toString().equalsIgnoreCase("")) {
            edt_username.setError("user name is required");
            edt_pwd.setError("password is required");
        } else if (edt_username.getText().toString().equalsIgnoreCase("")) {
            edt_username.setError("user name is required");
        } else if (edt_pwd.getText().toString().equalsIgnoreCase("")) {
            edt_pwd.setError("Please enter valid PIN");
        } else {
           /* Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            LoginActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();*/

            int pin = sharedpreferences.getInt("pin", 0);
            if (edt_pwd.getText().toString().trim().equalsIgnoreCase(String.valueOf(pin))) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                LoginActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }else {
                edt_pwd.setError("Please enter valid PIN");
            }


        }
    }


}
