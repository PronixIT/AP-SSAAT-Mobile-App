package com.yvs.ssaat.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yvs.ssaat.R;

public class ReportsMain extends AppCompatActivity {
    TextView txt_rightarrow1,txt_rightarrow2;
    LinearLayout ll1,ll2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_main);
        txt_rightarrow1 = (TextView) findViewById(R.id.txt_rightarrow1);
        txt_rightarrow2 = (TextView) findViewById(R.id.txt_rightarrow2);
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        txt_rightarrow1.setText(R.string.rightarrow);
        txt_rightarrow2.setText(R.string.rightarrow);
        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportsMain.this,ReportsActivity.class));
            }
        });
        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportsMain.this,Reports5A.class));
            }
        });
    }
}
