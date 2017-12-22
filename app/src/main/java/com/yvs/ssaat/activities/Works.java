package com.yvs.ssaat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.yvs.ssaat.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Works extends BaseActivity {
    @BindView(R.id.workIdTxt1)
    TextView workIdTxt1;

    @OnClick(R.id.workIdTxt1)
    public void next1() {
        startActivity(new Intent(Works.this, WorkDetail.class));
        finish();
    }

    @BindView(R.id.workIdTxt2)
    TextView workIdTxt2;

    @OnClick(R.id.workIdTxt2)
    public void next2() {
        startActivity(new Intent(Works.this, WorkDetail.class));
        finish();
    }

    @BindView(R.id.workIdTxt3)
    TextView workIdTxt3;

    @OnClick(R.id.workIdTxt3)
    public void next3() {
        startActivity(new Intent(Works.this, WorkDetail.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_works, frame_base);
        ButterKnife.bind(this);
    }
}
