package com.yvs.ssaat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yvs.ssaat.MainActivity;
import com.yvs.ssaat.R;

public class AuditActivity extends BaseActivity {
    private TextView txt_audit;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_audit, frame_base);

        int size = getIntent().getIntExtra("size", 0);
        String data = getIntent().getStringExtra("data");

        new AlertDialog.Builder(AuditActivity.this)
                .setMessage("File uploaded successfully.")
                .setPositiveButton("OK", null)
                .create()
                .show();

        txt_audit = (TextView) findViewById(R.id.txt_audit);
        textView = (TextView) findViewById(R.id.textView);
        textView.setText("Total Records "+size);
        txt_audit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuditActivity.this, AuditDetailsActivity.class);
                startActivity(intent);
                AuditActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}
