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

public class Format4A extends BaseActivity {
    EditText sno, upadiCardNumber, fullname, po_ba, work_details, tillLastWeek, payOrderNumber, musterId,
            workNumber, amtPaid, actualWorkedDays, actualAmtPaid, differenceInAmt, cooliePassbookYesOrNo,
            AmtPayingOrNot, responsiblePersonName, hodha;
    SQLiteDatabase mDatabase;
    Button btn_save_format4a, btn_check_format4a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_format4, frame_base);
        sno = (EditText) findViewById(R.id.sno);
        upadiCardNumber = (EditText) findViewById(R.id.upadiCardNumber);
        fullname = (EditText) findViewById(R.id.fullname);
        po_ba = (EditText) findViewById(R.id.po_ba);
        work_details = (EditText) findViewById(R.id.work_details);
        tillLastWeek = (EditText) findViewById(R.id.tillLastWeek);
        payOrderNumber = (EditText) findViewById(R.id.payOrderNumber);
        musterId = (EditText) findViewById(R.id.musterId);
        workNumber = (EditText) findViewById(R.id.workNumber);
        amtPaid = (EditText) findViewById(R.id.amtPaid);
        actualWorkedDays = (EditText) findViewById(R.id.actualWorkedDays);
        actualAmtPaid = (EditText) findViewById(R.id.actualAmtPaid);
        differenceInAmt = (EditText) findViewById(R.id.differenceInAmt);
        cooliePassbookYesOrNo = (EditText) findViewById(R.id.cooliePassbookYesOrNo);
        AmtPayingOrNot = (EditText) findViewById(R.id.AmtPayingOrNot);
        responsiblePersonName = (EditText) findViewById(R.id.responsiblePersonName);
        hodha = (EditText) findViewById(R.id.hodha);
        btn_save_format4a = (Button) findViewById(R.id.btn_save_format4a);
        btn_check_format4a = (Button) findViewById(R.id.btn_check_format4a);

        mDatabase = openOrCreateDatabase(CSVParsing.DATABASE_NAME, MODE_PRIVATE, null);
        createFormat4ATable();

        btn_save_format4a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFormat4AtoDB();
            }
        });
        btn_check_format4a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //opening the database
                mDatabase = openOrCreateDatabase(CSVParsing.DATABASE_NAME, MODE_PRIVATE, null);
                //we used rawQuery(sql, selectionargs) for fetching all the employees
                Cursor cursorFormat4A = mDatabase.rawQuery("SELECT * FROM format4A", null);
                //if the cursor has some data

                if (cursorFormat4A.moveToFirst()) {
                    //looping through all the records
                    do {
                        //pushing each record in the employee list
                        System.out.println("-----------database: " + Arrays.toString(cursorFormat4A.getColumnNames()));
                        Toast.makeText(Format4A.this, Arrays.toString(cursorFormat4A.getColumnNames()), Toast.LENGTH_SHORT).show();
                    } while (cursorFormat4A.moveToNext());
                }
                //closing the cursor
                cursorFormat4A.close();
            }
        });
    }

    private void saveFormat4AtoDB() {
        String insertSQL = "INSERT INTO format4A \n" +
                "(sno, wageSeekerId ,fullname, postBank, work_details,work_duration,payOrderRelDate, musterId, workedDays, amtToBePaid, actualWorkedDays," +
                "actualAmtPaid,differenceInAmt,isJobCardAvail,isPassbookAvail,isPayslipIssued,respPersonName,respPersonDesig," +
                "categoryone,categorytwo,categorythree)\n" +
                "VALUES \n" +
                "(?, ?, ?,?,?, ?, ?,?,?, ?, ?,?, ?, ?,?,?, ?,?,?,?,?);";
        mDatabase.execSQL(insertSQL, new String[]{musterId.getText().toString(), sno.getText().toString(), upadiCardNumber.getText().toString(),
                fullname.getText().toString(), po_ba.getText().toString(), work_details.getText().toString(), tillLastWeek.getText().toString(),
                payOrderNumber.getText().toString(), workNumber.getText().toString(), amtPaid.getText().toString(), actualWorkedDays.getText().toString(),
                actualAmtPaid.getText().toString(), differenceInAmt.getText().toString(), cooliePassbookYesOrNo.getText().toString(),
                AmtPayingOrNot.getText().toString(), responsiblePersonName.getText().toString(), hodha.getText().toString()});
    }

    private void createFormat4ATable() {

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

    }
}
