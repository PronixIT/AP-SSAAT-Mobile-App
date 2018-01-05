package com.yvs.ssaat.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yvs.ssaat.R;
import com.yvs.ssaat.db.DBManager;

import java.util.ArrayList;

public class DoortoDoorF5Ativity extends BaseActivity {
    Button btn_search;
    EditText householdCode;
    SQLiteDatabase mDatabase;
    private ArrayList<String> checkList = new ArrayList<String>();
    private ArrayList<String> checkList2 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_doortodoor5a, frame_base);
        householdCode = (EditText) findViewById(R.id.householdCode);
        btn_search= (Button) findViewById(R.id.btn_search);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!householdCode.getText().toString().equalsIgnoreCase("")){
                    checkData();
                }else{
                    Toast.makeText(DoortoDoorF5Ativity.this,"Please enter work code",Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Checking if items are there in Format4A table
        checkDataForm5aTable();
    }

    private void checkData() {
        try {
            //opening the database
            mDatabase = openOrCreateDatabase(CSVParsing.DATABASE_NAME, MODE_PRIVATE, null);
            String workCardIdstr = householdCode.getText().toString();
            //we used rawQuery(sql, selectionargs) for fetching all the employees
            Cursor cursorEmployees = DBManager.getInstance().getRawQuery("SELECT * FROM worksite WHERE work_code IN ('" + workCardIdstr + "')");
            //if the cursor has some data
            if (cursorEmployees.moveToFirst()) {
                //looping through all the records
                do {
                    checkList.add(cursorEmployees.getString(1));
                } while (cursorEmployees.moveToNext());
            }
            //closing the cursor
            cursorEmployees.close();
            System.out.println("-----------checking employees with work code array size: " + checkList.size());
            if (checkList.size() == 0) {
                Toast.makeText(DoortoDoorF5Ativity.this, "Please enter valid Work code", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(DoortoDoorF5Ativity.this, Format5ARow.class);
                intent.putExtra("work_code", householdCode.getText().toString());
                startActivity(intent);
                DoortoDoorF5Ativity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        }catch (SQLException error){
            System.out.println("error: "+error.toString());
        }
    }

    public void checkDataForm5aTable(){
        //opening the database
        mDatabase = openOrCreateDatabase(CSVParsing.DATABASE_NAME, MODE_PRIVATE, null);
        //we used rawQuery(sql, selectionargs) for fetching all the employees
        Cursor cursorFormat5A = DBManager.getInstance().getRawQuery("SELECT * FROM format5A");
        //if the cursor has some data

        if (cursorFormat5A.moveToFirst()) {
            //looping through all the records
            do {
                checkList2.add(cursorFormat5A.getString(19));
                System.out.println("date: "+cursorFormat5A.getString(19));
            } while (cursorFormat5A.moveToNext());
        }
        //closing the cursor
        cursorFormat5A.close();
        System.out.println("Format 5A Size: "+checkList2.size());
    }


}
