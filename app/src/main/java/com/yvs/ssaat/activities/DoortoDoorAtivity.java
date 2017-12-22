package com.yvs.ssaat.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yvs.ssaat.MainActivity;
import com.yvs.ssaat.R;
import com.yvs.ssaat.pojo.Worker;

import java.util.ArrayList;

public class DoortoDoorAtivity extends BaseActivity {
    Button btn_search;
    EditText workCardId;
    SQLiteDatabase mDatabase;
    private ArrayList<String> checkList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_doortodoor, frame_base);
        workCardId = (EditText) findViewById(R.id.workIdNumber);
        btn_search= (Button) findViewById(R.id.btn_search);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!workCardId.getText().toString().equalsIgnoreCase("")){
                    checkData();
                }else{
                    Toast.makeText(DoortoDoorAtivity.this,"Please enter work card ID",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkData() {
        //opening the database
        mDatabase = openOrCreateDatabase(CSVParsing.DATABASE_NAME, MODE_PRIVATE, null);
         String workCardIdstr  = workCardId.getText().toString();
        //we used rawQuery(sql, selectionargs) for fetching all the employees
        Cursor cursorEmployees = mDatabase.rawQuery("SELECT * FROM employees WHERE household_code IN ('"+workCardIdstr+"')", null);
        //if the cursor has some data
        if (cursorEmployees.moveToFirst()) {
            //looping through all the records
            do {
                //pushing each record in the employee list
                checkList.add(cursorEmployees.getString(1));
            } while (cursorEmployees.moveToNext());
        }
        //closing the cursor
        cursorEmployees.close();
        System.out.println("-----------format 4a: " + checkList.size());
        if(checkList.size()==0){
            Toast.makeText(DoortoDoorAtivity.this,"Please enter valid Household code",Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(DoortoDoorAtivity.this, Format4ARow.class);
            intent.putExtra("workCardId",workCardId.getText().toString());
            startActivity(intent);
            DoortoDoorAtivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
        //tvMainSelectedCate.setText("First column(district codes): "+checkList.toString());
      //  loadListView(checkList);
    }
}
