package com.yvs.ssaat.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yvs.ssaat.R;

import java.util.ArrayList;

public class DoortoDoorAtivity extends BaseActivity {
    Button btn_search;
    EditText householdCode;
    SQLiteDatabase mDatabase;
    private ArrayList<String> checkList = new ArrayList<String>();
    private ArrayList<String> checkList2 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_doortodoor, frame_base);
        householdCode = (EditText) findViewById(R.id.householdCode);
        btn_search= (Button) findViewById(R.id.btn_search);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!householdCode.getText().toString().equalsIgnoreCase("")){
                    checkData();
                }else{
                    Toast.makeText(DoortoDoorAtivity.this,"Please enter work card ID",Toast.LENGTH_SHORT).show();
                }
            }
        });

        createFormat4ATable();
    }

    private void checkData() {
        //opening the database
        mDatabase = openOrCreateDatabase(CSVParsing.DATABASE_NAME, MODE_PRIVATE, null);
         String jobSeekerId  = householdCode.getText().toString();
        //we used rawQuery(sql, selectionargs) for fetching all the employees
        Cursor cursorEmployees = mDatabase.rawQuery("SELECT * FROM employees WHERE household_code IN ('"+jobSeekerId+"')", null);
        //if the cursor has some data
        if (cursorEmployees.moveToFirst()) {
            //looping through all the records
            do {
                checkList.add(cursorEmployees.getString(1));
            } while (cursorEmployees.moveToNext());
        }
        //closing the cursor
        cursorEmployees.close();
        System.out.println("-----------checking employees with housecode array size: " + checkList.size());
        if(checkList.size()==0){
            Toast.makeText(DoortoDoorAtivity.this,"Please enter valid Household code",Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(DoortoDoorAtivity.this, Format4ARow.class);
            intent.putExtra("householdCode",householdCode.getText().toString());
            startActivity(intent);
            DoortoDoorAtivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
    }

    public void checkDataForm4aTable(){
        //opening the database
        mDatabase = openOrCreateDatabase(CSVParsing.DATABASE_NAME, MODE_PRIVATE, null);
        //we used rawQuery(sql, selectionargs) for fetching all the employees
        Cursor cursorFormat4A = mDatabase.rawQuery("SELECT * FROM format4A", null);
        //if the cursor has some data

        if (cursorFormat4A.moveToFirst()) {
            //looping through all the records
            do {
                checkList2.add(cursorFormat4A.getString(1));
            } while (cursorFormat4A.moveToNext());
        }
        //closing the cursor
        cursorFormat4A.close();
        System.out.println("Format 4A Size: "+checkList2.size());
    }

    private void createFormat4ATable() {
        //Creating format 4A table
        mDatabase = openOrCreateDatabase(CSVParsing.DATABASE_NAME, MODE_PRIVATE, null);
        mDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS format4A (\n" +
                        "    id INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
                        "    sno varchar(200) NOT NULL,\n" +
                        "    wageSeekerId varchar(200) NOT NULL,\n" +
                        "    fullname varchar(200) NOT NULL,\n" +
                        "    postBank varchar(200) NOT NULL,\n" +
                        "    work_details varchar(200) NOT NULL,\n" +
                        "    work_duration varchar(200) NOT NULL,\n" +
                        "    payOrderRelDate varchar(200) NOT NULL,\n" +
                        "    musterId varchar(200) NOT NULL,\n" +
                        "    workedDays varchar(200) NOT NULL,\n" +
                        "    amtToBePaid varchar(200) NOT NULL,\n" +
                        "    actualWorkedDays varchar(200) NOT NULL,\n" +
                        "    actualAmtPaid varchar(200) NOT NULL,\n" +
                        "    differenceInAmt varchar(200) NOT NULL,\n" +
                        "    isJobCardAvail varchar(200) NOT NULL,\n" +
                        "    isPassbookAvail varchar(200) NOT NULL,\n" +
                        "    isPayslipIssued varchar(200) NOT NULL,\n" +
                        "    respPersonName varchar(200) NOT NULL,\n" +
                        "    respPersonDesig varchar(200) NOT NULL,\n" +
                        "    categoryone varchar(200) NOT NULL,\n" +
                        "    categorytwo varchar(200) NOT NULL,\n" +
                        "    categorythree varchar(200) NOT NULL\n" +
                        ");"
        );

        //Checking if items are there in Format4A table
        checkDataForm4aTable();
    }
}
