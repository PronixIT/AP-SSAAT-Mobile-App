package com.yvs.ssaat.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yvs.ssaat.R;

import java.util.Arrays;

public class Format5A extends BaseActivity {
    EditText sno,workIdNumber, workNameLoc ,work_details,naipunyaRakam,permission_wise,parimaanam,total,
            reportWise,reportsParimanam,reportsTotal,isJobDoneAtLoc,higherLevelVerificationWise,
            higherLevelVerificationWiseParimanam,higherLevelVerificationWiseTotal,isAmtPaidOrNot,
            differenceInAmt,hodha,hodhaParimanam,hodhaTotal,responsiblePersonName;
    SQLiteDatabase mDatabase;
    Button btn_save_format5a,btn_check_format5a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_format5, frame_base);
        sno = (EditText)findViewById(R.id.sno);
        workIdNumber =(EditText) findViewById(R.id.workIdNumber);
        workNameLoc = (EditText)findViewById(R.id.workNameLoc);
        work_details =(EditText) findViewById(R.id.work_details);
        naipunyaRakam = (EditText)findViewById(R.id.naipunyaRakam);
        permission_wise = (EditText)findViewById(R.id.permission);
        parimaanam = (EditText)findViewById(R.id.parimaanam);
        total = (EditText)findViewById(R.id.total);
        reportWise = (EditText)findViewById(R.id.reportWise);
        reportsParimanam = (EditText)findViewById(R.id.reportsParimanam);
        reportsTotal = (EditText)findViewById(R.id.reportsTotal);
        isJobDoneAtLoc = (EditText)findViewById(R.id.isJobDoneAtLoc);
        higherLevelVerificationWise = (EditText)findViewById(R.id.higherLevelVerificationWise);
        higherLevelVerificationWiseParimanam = (EditText)findViewById(R.id.higherLevelVerificationWiseParimanam);
        higherLevelVerificationWiseTotal = (EditText)findViewById(R.id.higherLevelVerificationWiseTotal);
        isAmtPaidOrNot = (EditText)findViewById(R.id.isAmtPaidOrNot);
        differenceInAmt = (EditText)findViewById(R.id.differenceInAmt);
        hodha = (EditText)findViewById(R.id.hodha);
        hodhaParimanam = (EditText)findViewById(R.id.hodhaParimanam);
        hodhaTotal = (EditText)findViewById(R.id.hodhaTotal);
        responsiblePersonName = (EditText)findViewById(R.id.responsiblePersonName);

        btn_save_format5a = (Button)findViewById(R.id.btn_save_format5a);
        btn_check_format5a = (Button)findViewById(R.id.btn_check_format5a);

        mDatabase = openOrCreateDatabase(CSVParsing.DATABASE_NAME, MODE_PRIVATE, null);
        createFormat5ATable();

        btn_save_format5a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFormat5AtoDB();
            }
        });
        btn_check_format5a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //opening the database
                mDatabase = openOrCreateDatabase(CSVParsing.DATABASE_NAME, MODE_PRIVATE, null);
                //we used rawQuery(sql, selectionargs) for fetching all the employees
                Cursor cursorFormat5A = mDatabase.rawQuery("SELECT * FROM format5A", null);
                //if the cursor has some data

                if (cursorFormat5A.moveToFirst()) {
                    //looping through all the records
                    do {
                        //pushing each record in the employee list
                        System.out.println("-----------database: "+ Arrays.toString(cursorFormat5A.getColumnNames()));
                        Toast.makeText(Format5A.this,Arrays.toString(cursorFormat5A.getColumnNames()),Toast.LENGTH_SHORT).show();
                    } while (cursorFormat5A.moveToNext());
                }
                //closing the cursor
                cursorFormat5A.close();
            }
        });
    }

    private void saveFormat5AtoDB() {
        String insertSQL = "INSERT INTO format5A \n" +
                "(sno,workIdNumber, workNameLoc ,work_details,naipunyaRakam,permission_wise,parimaanam,total,\n" +
                "            reportWise,reportsParimanam,reportsTotal,isJobDoneAtLoc,higherLevelVerificationWise,\n" +
                "            higherLevelVerificationWiseParimanam,higherLevelVerificationWiseTotal,isAmtPaidOrNot,\n" +
                "            differenceInAmt,hodha,hodhaParimanam,hodhaTotal,responsiblePersonName)\n" +
                "VALUES \n" +
                "(?, ?, ?,?,?, ?, ?,?,?, ?, ?,?, ?, ?,?,?, ?,?,?,?,?);";
        mDatabase.execSQL(insertSQL, new String[]{sno.getText().toString(), workIdNumber.getText().toString(), workNameLoc.getText().toString(),
                work_details.getText().toString(),naipunyaRakam.getText().toString(),permission_wise.getText().toString(),parimaanam.getText().toString(),
                total.getText().toString(),reportWise.getText().toString(),reportsParimanam.getText().toString(), reportsTotal.getText().toString(),
                isJobDoneAtLoc.getText().toString(), higherLevelVerificationWise.getText().toString(), higherLevelVerificationWiseParimanam.getText().toString(),
                higherLevelVerificationWiseTotal.getText().toString(), isAmtPaidOrNot.getText().toString(), differenceInAmt.getText().toString(),
                hodha.getText().toString(), hodhaParimanam.getText().toString(), hodhaTotal.getText().toString(), responsiblePersonName.getText().toString()});
    }

    private void createFormat5ATable() {
        mDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS format5A (\n" +
                        "    id INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
                        "    sno varchar(200) NOT NULL,\n" +
                        "    workIdNumber varchar(200) NOT NULL,\n" +
                        "    workNameLoc varchar(200) NOT NULL,\n" +
                        "    work_details varchar(200) NOT NULL,\n" +
                        "    naipunyaRakam varchar(200) NOT NULL,\n" +
                        "    permission_wise varchar(200) NOT NULL,\n" +
                        "    parimaanam varchar(200) NOT NULL,\n" +
                        "    total varchar(200) NOT NULL,\n" +
                        "    reportWise varchar(200) NOT NULL,\n" +
                        "    reportsParimanam varchar(200) NOT NULL,\n" +
                        "    reportsTotal varchar(200) NOT NULL,\n" +
                        "    isJobDoneAtLoc varchar(200) NOT NULL,\n" +
                        "    higherLevelVerificationWise varchar(200) NOT NULL,\n" +
                        "    higherLevelVerificationWiseParimanam varchar(200) NOT NULL,\n" +
                        "    higherLevelVerificationWiseTotal varchar(200) NOT NULL,\n" +
                        "    isAmtPaidOrNot varchar(200) NOT NULL,\n" +
                        "    differenceInAmt varchar(200) NOT NULL,\n" +
                        "    hodha varchar(200) NOT NULL,\n" +
                        "    hodhaParimanam varchar(200) NOT NULL,\n" +
                        "    hodhaTotal varchar(200) NOT NULL,\n" +
                        "    responsiblePersonName varchar(200) NOT NULL\n" +
                        ");"
        );

    }
}
