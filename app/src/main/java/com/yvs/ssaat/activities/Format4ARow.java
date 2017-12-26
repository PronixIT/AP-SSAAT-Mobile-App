package com.yvs.ssaat.activities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.yvs.ssaat.R;
import com.yvs.ssaat.models.WorkersAdapter4A;
import com.yvs.ssaat.pojo.Worker;

import java.util.ArrayList;

/**
 * Created by NAVEEN KS on 12/20/2017.
 */


public class Format4ARow extends AppCompatActivity {
    SQLiteDatabase mDatabase;
    private ArrayList<Worker> checkList = new ArrayList<Worker>();
    Bundle bundle;
    String householdCode = "";
    RecyclerView rcyVw_workers;
    WorkersAdapter4A mAdapter;
    Context mCon = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_format4_arow);
        rcyVw_workers = (RecyclerView) findViewById(R.id.rcyVw_workers);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            householdCode = bundle.getString("householdCode");
            System.out.println("householdCode" + householdCode);
        }
       /* checkList.add(new Worker("S.No",
                "Job Seeker Id (or) Wage Seeker Id",
                "Full name",
                "Post Office / Bank Account Details",
                "Work Details Work-ID/Work_name/Work_location",
                "Work Duration from - to",
                "Pay-order Release Date",
                "Muster Id Number",
                "No of working Days",
                "Amount to be Paid",
                "Actual No of days worked",
                "Actual Paid Amount",
                "Difference in amount paid",
                "Is Job-card-id is available with Job-Seekers(Yes/No)",
                "Is Passbook is available with Job-Seekers(Yes/No)",
                "PaySlips are Issuing(Yes/No)",
                "Responsible Person Name",
                "Responsible Person Job-Designation",
                "Category-I",
                "Category-II",
                "Category-III",
                "No Data",
                "No data"));
*/

       /* checkList.add(new Worker("S.No",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "No of working Days",
                "Post Office / Bank Account Details                                   Amount to be Paid",
                "Full name                                                                     Actual No of days worked",
                "Actual Paid Amount",
                "Job Seeker Id (or) Wage Seeker Id                                          Difference in amount paid",
                "Work Details Work-ID/Work_name/Work_location                     Is Job-card-id is available with Job-Seekers(Yes/No)",
                "Is Passbook is available with Job-Seekers(Yes/No)",
                "PaySlips are Issuing(Yes/No)",
                "Work Duration from - to            Responsible Person Name",
                "Responsible Person Job-Designation",
                "Muster Id Number                                 Category-I",
                "Category-II",
                "Pay-order Release Date                                Category-III",
                "No Data",
                "No data"));*/

       /* checkList.add(new Worker("2.1.1",
                "2.1.2",
                "2.1.3",
                "2.1.4",
                "2.1.5",
                "2.1.6",
                "2.1.7",
                "2.1.8",
                "2.1.9",
                "2.1.10",
                "2.1.11",
                "2.1.12",
                "2.1.13",
                "2.1.14",
                "2.1.15",
                "2.1.16",
                "2.1.17",
                "2.1.18",
                "2.1.19",
                "2.1.20",
                "2.1.21",
                "",
                ""));*/


        mAdapter = new WorkersAdapter4A(mCon, checkList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rcyVw_workers.setLayoutManager(mLayoutManager);
        rcyVw_workers.setNestedScrollingEnabled(false);
        rcyVw_workers.setAdapter(mAdapter);

        //To check and load if any data is present for selected household code
        checkData();
    }

    private void checkData() {
        //opening the database
        mDatabase = openOrCreateDatabase(CSVParsing.DATABASE_NAME, MODE_PRIVATE, null);
        //we used rawQuery(sql, selectionargs) for fetching all the employees
        Cursor cursorEmployees = mDatabase.rawQuery("SELECT * FROM employees WHERE household_code IN ('" + householdCode + "')", null);
        //if the cursor has some data
        if (cursorEmployees.moveToFirst()) {
            int count = 1;
            //looping through all the records
            do {
                //pushing each record in the employee list
                checkList.add(new Worker(
                        String.valueOf(count++),
                        cursorEmployees.getString(1),
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
                        cursorEmployees.getString(39)));
            } while (cursorEmployees.moveToNext());
        }
        //closing the cursor
        cursorEmployees.close();

        System.out.println("---Selected household array size: " + checkList.size());
        //tvMainSelectedCate.setText("First column(district codes): "+checkList.toString());
        loadListView(checkList);
    }

    private void loadListView(ArrayList<Worker> checkList) {
        if (checkList.size() > 0) {
            mAdapter = new WorkersAdapter4A(mCon, checkList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rcyVw_workers.setLayoutManager(mLayoutManager);
            rcyVw_workers.setAdapter(mAdapter);
        } else {
            Toast.makeText(Format4ARow.this, "No data found for this work card ID", Toast.LENGTH_SHORT).show();
        }

    }
}
