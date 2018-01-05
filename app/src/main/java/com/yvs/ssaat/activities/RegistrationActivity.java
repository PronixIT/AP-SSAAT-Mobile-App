package com.yvs.ssaat.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.yvs.ssaat.Dal.DalUserMaster;
import com.yvs.ssaat.MainActivity;
import com.yvs.ssaat.R;
import com.yvs.ssaat.common.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class RegistrationActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private EditText _nameText, _emailText, _passwordText, input_number;
    private Button _signupButton;
    private TextView _loginLink;
    private SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    Spinner spnrDesignation;
    DalUserMaster dalUserMaster;
    SQLiteDatabase mDatabase;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mDatabase = openOrCreateDatabase(Constants.SSAATDATABASE_NAME, MODE_PRIVATE, null);
        dalUserMaster = new DalUserMaster();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        _nameText = (EditText) findViewById(R.id.input_name);
        _emailText = (EditText) findViewById(R.id.input_email);
        input_number = (EditText) findViewById(R.id.input_number);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _signupButton = (Button) findViewById(R.id.btn_signup);
        _loginLink = (TextView) findViewById(R.id.link_login);
        spnrDesignation = (Spinner) findViewById(R.id.spnrDesignation);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {


        if (!validate()) {
            onSignupFailed();
            return;
        }
        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RegistrationActivity.this,
                R.style.MyMaterialTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        final String name = _nameText.getText().toString();
        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();
        final String number = input_number.getText().toString();
        final String designation = spnrDesignation.getSelectedItem().toString();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        Random random = new Random();
                        int value = random.nextInt(10000);
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("name", name);
                        editor.putString("email", email);
                        editor.putString("password", password);
                        editor.putString("number", number);
                        editor.putString("designation", designation);
                        editor.putInt("pin", Integer.parseInt(password));
                        editor.commit();
                        long res = dalUserMaster.saveUserDetails(mDatabase, "", name, number, email, password, designation, dateFormat.format(new Date()));
                        onSignupSuccess(res);
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 2000);
    }


    public void onSignupSuccess(long result) {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        if(result != -1) {
            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
            startActivity(intent);
            RegistrationActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
        else
            Toast.makeText(this, "Failed to create user", Toast.LENGTH_SHORT).show();

    }

    public void onSignupFailed() {
        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String number = input_number.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("Please enter User name");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (number.length() != 10) {
            input_number.setError("Please  enter 10 Digit mobile number");
            valid = false;
        } else {
            String mobileNum = dalUserMaster.getMobile(mDatabase, number);
            if(number.equals(mobileNum)) {
                input_number.setError("Mobile number already exists");
                valid = false;
            }
            else
            input_number.setError(null);
        }

        if(spnrDesignation.getSelectedItem().toString().equalsIgnoreCase("Select Designation")){
            Toast.makeText(RegistrationActivity.this,"Please select Designation",Toast.LENGTH_SHORT).show();
            valid = false;
        }

//        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            _emailText.setError("Please enter Valid Email");
//            valid = false;
//        } else {
//            _emailText.setError(null);
//        }

        if (password.isEmpty() || password.length() < 4  ) {
            _passwordText.setError("Please enter 4 digit PIN");
            valid = false;
        } else {
            String pin = dalUserMaster.getPin(mDatabase, password);
            if(pin.equals(password)) {
                _passwordText.setError("Please provide another pin");
                valid = false;
            }
            else
            _passwordText.setError(null);
        }


        return valid;
    }
}