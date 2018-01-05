package com.yvs.ssaat.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.yvs.ssaat.Dal.DalUserMaster;
import com.yvs.ssaat.MainActivity;
import com.yvs.ssaat.R;
import com.yvs.ssaat.common.Constants;
import com.yvs.ssaat.common.SqliteConstants;
import com.yvs.ssaat.db.DBManager;
import com.yvs.ssaat.db.DatabaseHandler;
import com.yvs.ssaat.models.UserMasterDO;
import com.yvs.ssaat.session.SessionManager;

import java.util.Locale;


public class LoginActivity extends AppCompatActivity {
    private Button btn_login;
    private EditText edt_username, edt_pwd;
    private TextView txt_forgot, txt_signup;
    private SharedPreferences sharedpreferences;
    Spinner sp_Langauge;
    public static final String MyPREFERENCES = "MyPrefs";
    SessionManager sessionManager;
    DalUserMaster dalUserMaster;
    SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        createTableUserMaster();
        dalUserMaster = new DalUserMaster();

        try {
//
           DBManager.initializeInstance(this);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        sessionManager = new SessionManager(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        txt_signup = (TextView) findViewById(R.id.txt_signup);
        txt_forgot = (TextView) findViewById(R.id.txt_forgot);
        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_username.setText("nouser");
        edt_pwd = (EditText) findViewById(R.id.edt_pwd);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setText(R.string.Login);
        sp_Langauge = (Spinner) findViewById(R.id.sp_Langauge);
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

        sp_Langauge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Locale locale;
                if(sp_Langauge.getSelectedItem().equals("Telugu"))
                {
                    locale = new Locale("te");
                    Locale.setDefault(locale);
                }
                else
                {
                    locale = new Locale("en");
                    Locale.setDefault(locale);
                }
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                btn_login.setText(R.string.Login);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
            Constants.userMasterDO = new UserMasterDO();
            String pinnumber = dalUserMaster.getPin(mDatabase, edt_pwd.getText().toString());
            if (edt_pwd.getText().toString().trim().length() == 4 && edt_pwd.getText().toString().trim().equalsIgnoreCase(pinnumber)) {

                Intent intent = new Intent(LoginActivity.this, SSAATScheme.class);
                Constants.PIN = edt_pwd.getText().toString();
                sessionManager.createLoginSession("","","","","","");
                startActivity(intent);
                LoginActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }else {
                edt_pwd.setError("Please enter valid PIN");
            }
        }
    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, getString(R.string.exit), Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }

            }, 3 * 1000);
        }
    }


    public void createTableUserMaster()
    {
        try {
            mDatabase = openOrCreateDatabase(Constants.SSAATDATABASE_NAME, MODE_PRIVATE, null);
            mDatabase.execSQL(
                    "CREATE TABLE IF NOT EXISTS " + SqliteConstants.TABLE_USERMASTER + " (\n" +
                            "    userid varchar(200),\n" +
                            "    username varchar(200) NOT NULL,\n" +
                            "    mobile_number varchar(200) NOT NULL,\n" +
                            "    email varchar(200) NOT NULL,\n" +
                            "    pin varchar(200) NOT NULL,\n" +
                            "    designation varchar(200) NOT NULL,\n" +
                            "    created_datetime varchar(200) NOT NULL\n" +
                            ");"
            );
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }

}
