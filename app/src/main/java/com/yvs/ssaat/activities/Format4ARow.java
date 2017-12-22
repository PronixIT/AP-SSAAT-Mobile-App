package com.yvs.ssaat.activities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.yvs.ssaat.R;
import com.yvs.ssaat.models.WorkersAdapter;
import com.yvs.ssaat.pojo.Worker;

import java.util.ArrayList;

public class Format4ARow extends AppCompatActivity {
    SQLiteDatabase mDatabase;
    private ArrayList<Worker> checkList = new ArrayList<Worker>();
    Bundle bundle;
    String workCardId="";
    RecyclerView rcyVw_workers;
    WorkersAdapter mAdapter;
    Context mCon = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_format4_arow);
        rcyVw_workers = (RecyclerView) findViewById(R.id.rcyVw_workers);
        bundle = getIntent().getExtras();
        if(bundle!=null){
            workCardId = bundle.getString("workCardId");
            System.out.println("workCardId"+workCardId);
        }
        mAdapter = new WorkersAdapter(mCon,checkList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rcyVw_workers.setLayoutManager(mLayoutManager);
        rcyVw_workers.setNestedScrollingEnabled(false);
        rcyVw_workers.setAdapter(mAdapter);

        checkData();
    }

    private void checkData() {
        //opening the database
        mDatabase = openOrCreateDatabase(CSVParsing.DATABASE_NAME, MODE_PRIVATE, null);
        //we used rawQuery(sql, selectionargs) for fetching all the employees
        Cursor cursorEmployees = mDatabase.rawQuery("SELECT * FROM employees WHERE household_code IN ('"+workCardId+"')", null);
        //if the cursor has some data
        if (cursorEmployees.moveToFirst()) {
            //looping through all the records
            do {
                //pushing each record in the employee list
                System.out.println("id ois there ah: "+cursorEmployees.getString(0));
                checkList.add(new Worker(cursorEmployees.getString(1),
                        cursorEmployees.getString(2),
                        cursorEmployees.getString(3),
                        cursorEmployees.getString(4),
                        cursorEmployees.getString(5),
                        cursorEmployees.getString(6),
                        cursorEmployees.getString(7),
                        cursorEmployees.getString(8),
                        cursorEmployees.getString(9),
                        cursorEmployees.getString(11),
                        cursorEmployees.getString(13),
                        cursorEmployees.getString(14),
                        cursorEmployees.getString(15),
                        cursorEmployees.getString(17),
                        cursorEmployees.getString(19),
                        cursorEmployees.getString(20),
                        cursorEmployees.getString(21),
                        cursorEmployees.getString(22),
                        cursorEmployees.getString(23),
                        cursorEmployees.getString(24),
                        cursorEmployees.getString(25),
                        cursorEmployees.getString(39)
                        ));
            } while (cursorEmployees.moveToNext());
        }
        //closing the cursor
        cursorEmployees.close();

        System.out.println("-----------format 4a: " + checkList.size());
        //tvMainSelectedCate.setText("First column(district codes): "+checkList.toString());
        loadListView(checkList);
    }

    private void loadListView(ArrayList<Worker> checkList) {
        if(checkList.size()>0){
            mAdapter = new WorkersAdapter(mCon,checkList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rcyVw_workers.setLayoutManager(mLayoutManager);
            rcyVw_workers.setAdapter(mAdapter);
        }else{
            Toast.makeText(Format4ARow.this,"No data found for this work card ID",Toast.LENGTH_SHORT).show();
        }

    }
}