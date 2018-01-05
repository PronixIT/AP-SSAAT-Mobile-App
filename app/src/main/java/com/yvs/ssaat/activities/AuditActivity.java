package com.yvs.ssaat.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.yvs.ssaat.MainActivity;
import com.yvs.ssaat.R;

public class AuditActivity extends BaseActivity {
    private TextView txt_audit;
    private TextView textView,total_wage_seekers,total_areas_worked,total_unsync_records,total_sync_records;
    private SQLiteDatabase mDatabase;
    private Cursor cursorFormat4A;
    private Cursor cursorFormat5AResults;
    private Intent intent;
    private Cursor cursorFormat5A;

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
        total_wage_seekers = (TextView) findViewById(R.id.total_wage_seekers);
        total_areas_worked = (TextView) findViewById(R.id.total_areas_worked);
        total_unsync_records = (TextView) findViewById(R.id.total_unsync_records);
        total_sync_records = (TextView) findViewById(R.id.total_sync_records);
        textView = (TextView) findViewById(R.id.textView);
        textView.setText("Total Records "+size);

        txt_audit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(AuditActivity.this, AuditDetailsActivity.class);
                startActivity(intent);
                AuditActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDatabase = openOrCreateDatabase(CSVParsing.DATABASE_NAME, MODE_PRIVATE, null);
        //we used rawQuery(sql, selectionargs) for fetching all the employees
        cursorFormat4A = mDatabase.rawQuery("SELECT * FROM employees", null);
        total_wage_seekers.setText(String.valueOf(cursorFormat4A.getCount()));

        //we used rawQuery(sql, selectionargs) for fetching all the employees
        cursorFormat5AResults = mDatabase.rawQuery("SELECT * FROM format5A", null);
        total_areas_worked.setText(String.valueOf(cursorFormat5AResults.getCount()));

        //we used rawQuery(sql, selectionargs) for fetching all the employees
        Cursor cursorFormat4AResults = mDatabase.rawQuery("SELECT * FROM format4A", null);
        total_sync_records.setText(String.valueOf(cursorFormat4AResults.getCount()));

        //we used rawQuery(sql, selectionargs) for fetching all the employees
       // cursorFormat5A = mDatabase.rawQuery("SELECT * FROM worksite", null);

        total_unsync_records.setText(String.valueOf(cursorFormat4A.getCount() - cursorFormat4AResults.getCount()));

    }
}
