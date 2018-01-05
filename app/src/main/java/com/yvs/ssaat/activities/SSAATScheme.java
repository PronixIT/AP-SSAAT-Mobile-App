package com.yvs.ssaat.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yvs.ssaat.Dal.DalUserMaster;
import com.yvs.ssaat.MainActivity;
import com.yvs.ssaat.R;
import com.yvs.ssaat.common.Constants;

/**
 * Created by ravi on 1/2/2018.
 */

public class SSAATScheme  extends BaseActivity implements View.OnClickListener{
    RelativeLayout rl_MGNREGA;
    TextView tv_ProfileName;
    private SharedPreferences sharedpreferences;
    public String MyPREFERENCES = "MyPrefs";

    TextView tv_MGNREGA;
    SQLiteDatabase mDatabase;
    DalUserMaster dalUserMaster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            getLayoutInflater().inflate(R.layout.activity_ssaatscheme, frame_base);
            dalUserMaster = new DalUserMaster();
            mDatabase = openOrCreateDatabase(Constants.SSAATDATABASE_NAME, MODE_PRIVATE, null);
        dalUserMaster.getUserDetails(mDatabase,Constants.PIN);

        initializeControls();
        setLaunguageTexts();
    }

    public void initializeControls()
    {
        rl_MGNREGA = (RelativeLayout) findViewById(R.id.rl_MGNREGA);
        rl_MGNREGA.setOnClickListener(this);
        tv_MGNREGA = (TextView) findViewById(R.id.tv_MGNREGA);
        tv_ProfileName = (TextView) findViewById(R.id.tv_ProfileName);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String name = sharedpreferences.getString("name", null);
        String designation = sharedpreferences.getString("designation", null);
        tv_ProfileName.setText(Constants.userMasterDO.designation + " : " + Constants.userMasterDO.userName);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.rl_MGNREGA:
                Intent in = new Intent();
                in.setClass(this, MainActivity.class);
                startActivity(in);
                break;
        }
    }

    public void setLaunguageTexts()
     {
//         tv_MGNREGA.setText(R.string.MGNREGA);

    }
}
