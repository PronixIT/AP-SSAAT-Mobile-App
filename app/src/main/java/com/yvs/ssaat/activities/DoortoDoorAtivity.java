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
import com.yvs.ssaat.db.DBManager;

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


        //Checking if items are there in Format4A table
//        checkDataForm4aTable();
    }

    private void checkData() {
        //opening the database
        mDatabase = openOrCreateDatabase(CSVParsing.DATABASE_NAME, MODE_PRIVATE, null);
         String jobSeekerId  = householdCode.getText().toString();
        //we used rawQuery(sql, selectionargs) for fetching all the employees
        Cursor cursorEmployees = DBManager.getInstance().getRawQuery("SELECT * FROM employees WHERE household_code IN ('"+jobSeekerId+"')");
        //if the cursor has some data
        if (cursorEmployees.moveToFirst()) {
            //looping through all the records
            do {
                checkList.add(cursorEmployees.getString(1));
                break;
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
        Cursor cursorFormat4A = DBManager.getInstance().getRawQuery("SELECT * FROM format4A");
        //if the cursor has some data

        if (cursorFormat4A.moveToFirst()) {
            //looping through all the records
            do {
                checkList2.add(cursorFormat4A.getString(22));
                System.out.println("in db date: "+cursorFormat4A.getString(22));
            } while (cursorFormat4A.moveToNext());
        }
        //closing the cursor
        cursorFormat4A.close();
        System.out.println("Format 4A Size: "+checkList2.size());
    }


}
