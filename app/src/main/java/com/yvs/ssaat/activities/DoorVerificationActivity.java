package com.yvs.ssaat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.yvs.ssaat.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by surya on 11/3/2017.
 */

public class DoorVerificationActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_recordverification, frame_base);
    }
}
