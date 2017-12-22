package com.yvs.ssaat.activities;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Toast;

import com.yvs.ssaat.R;

public class WorkDetail extends BaseActivity {

    private AppCompatButton save,submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_work_detail, frame_base);

        save = (AppCompatButton)findViewById(R.id.save);
        submit = (AppCompatButton)findViewById(R.id.submit);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(WorkDetail.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(WorkDetail.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
