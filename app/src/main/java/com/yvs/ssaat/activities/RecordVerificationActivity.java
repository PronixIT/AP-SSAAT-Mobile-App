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

public class RecordVerificationActivity extends BaseActivity {
    @BindView(R.id.txt_jobno_one)
    TextView txt_jobno_one;

    @OnClick(R.id.txt_jobno_one)
    public void next1() {
        startActivity(new Intent(RecordVerificationActivity.this, RecordVerificationDetail.class));
        finish();
    }

    @BindView(R.id.txt_jobno_two)
    TextView txt_jobno_two;

    @OnClick(R.id.txt_jobno_two)
    public void next2() {
        startActivity(new Intent(RecordVerificationActivity.this, RecordVerificationDetail.class));
        finish();
    }

    @BindView(R.id.txt_jobno_three)
    TextView txt_jobno_three;

    @OnClick(R.id.txt_jobno_three)
    public void next3() {
        startActivity(new Intent(RecordVerificationActivity.this, RecordVerificationDetail.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_recordverification, frame_base);
        ButterKnife.bind(this);
    }
}
