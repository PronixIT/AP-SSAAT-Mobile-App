package com.yvs.ssaat.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yvs.ssaat.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

// class that parse the category.csv stored in assets file and display all category in spinner
public class CSVParsing extends BaseActivity {
    /**
     * Called when the activity is first created.
     */
    private TextView tvMainSelectedCate;
    private ArrayList<String> categoryList = new ArrayList<String>();
    private ArrayList<String> checkList = new ArrayList<String>();
    Button readCSV, checkCSV;

    public static final String DATABASE_NAME = "myemployeedatabase";
    SQLiteDatabase mDatabase;
    ProgressDialog progress;
    private static final int READ_REQUEST_CODE = 42;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_csvparsing, frame_base);

        tvMainSelectedCate = (TextView) findViewById(R.id.tvMainSelectedCate);
        progress = new ProgressDialog(this);
        readCSV = (Button) findViewById(R.id.readCSV);
        checkCSV = (Button) findViewById(R.id.checkCSV);
        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);


        readCSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Uri uri = Uri.parse(Environment.getExternalStorageDirectory() + "/Download/");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setDataAndType(uri, "*/*");
                if (intent.resolveActivityInfo(getPackageManager(), 0) != null) {
                    startActivityForResult(intent, READ_REQUEST_CODE);
                } else {
                    Toast.makeText(CSVParsing.this, "File Not Found Exception", Toast.LENGTH_SHORT).show();
                }

            }
        });

        checkCSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkData();
            }
        });

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            System.out.println("not read available");
        }
    }


    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            System.out.println("read only");
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            System.out.println("available");
            return true;
        }
        return false;
    }

    private void checkData() {
        //opening the database
        mDatabase = openOrCreateDatabase(CSVParsing.DATABASE_NAME, MODE_PRIVATE, null);
        //we used rawQuery(sql, selectionargs) for fetching all the employees
        Cursor cursorEmployees = mDatabase.rawQuery("SELECT * FROM employees", null);
        //if the cursor has some data
        if (cursorEmployees.moveToFirst()) {
            //looping through all the records
            do {
                //pushing each record in the employee list
                checkList.add(cursorEmployees.getString(6));
            } while (cursorEmployees.moveToNext());
        }
        //closing the cursor
        cursorEmployees.close();

        System.out.println("-----------database: " + checkList.toString());
        tvMainSelectedCate.setText("First column(district codes): " + checkList.toString());
    }

    private void createEmployeeTable() {
        mDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS employees (\n" +
                        "    id INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
                        "    district_code varchar(200) NOT NULL,\n" +
                        "    mandal_code varchar(200) NOT NULL,\n" +
                        "    panchayat_code varchar(200) NOT NULL,\n" +
                        "    village_code varchar(200) NOT NULL,\n" +
                        "    habitation_code varchar(200) NOT NULL,\n" +
                        "    ssaat_code varchar(200) NOT NULL,\n" +
                        "    household_code varchar(200) NOT NULL,\n" +
                        "    worker_code varchar(200) NOT NULL,\n" +
                        "    surname varchar(200) NOT NULL,\n" +
                        "    telugu_surname varchar(200) NOT NULL,\n" +
                        "    name varchar(200) NOT NULL,\n" +
                        "    telugu_name varchar(200) NOT NULL,\n" +
                        "    account_no varchar(200) NOT NULL,\n" +
                        "    work_code varchar(200) NOT NULL,\n" +
                        "    work_name varchar(200) NOT NULL,\n" +
                        "    work_name_telugu varchar(200) NOT NULL,\n" +
                        "    work_location varchar(200) NOT NULL,\n" +
                        "    work_location_telugu varchar(200) NOT NULL,\n" +
                        "    work_progress_code varchar(200) NOT NULL,\n" +
                        "    from_date varchar(200) NOT NULL,\n" +
                        "    to_date varchar(200) NOT NULL,\n" +
                        "    days_worked varchar(200) NOT NULL,\n" +
                        "    amount_paid varchar(200) NOT NULL,\n" +
                        "    payment_date varchar(200) NOT NULL,\n" +
                        "    audit_payslip_date varchar(200) NOT NULL,\n" +
                        "    audit_is_passbook_avail varchar(200) NOT NULL,\n" +
                        "    audit_is_payslip_issuing varchar(200) NOT NULL,\n" +
                        "    audit_is_jobcard_avail varchar(200) NOT NULL,\n" +
                        "    audit_days_worked varchar(200) NOT NULL,\n" +
                        "    audit_amount_rec varchar(200) NOT NULL,\n" +
                        "    audit_remarks varchar(200) NOT NULL,\n" +
                        "    status varchar(200) NOT NULL,\n" +
                        "    sent_file_name varchar(200) NOT NULL,\n" +
                        "    sent_date varchar(200) NOT NULL,\n" +
                        "    resp_filename varchar(200) NOT NULL,\n" +
                        "    resp_date varchar(200) NOT NULL,\n" +
                        "    created_date varchar(200) NOT NULL,\n" +
                        "    department varchar(200) NOT NULL,\n" +
                        "    muster_id varchar(200) NOT NULL\n" +
                        ");"
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {


        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i("CSVScreen", "Uri: " + uri.getPath());
                String[] ss = uri.getPath().split("\\/");
                //System.out.println("-------------0"+ss[0]+"-1"+ss[1]+"-2"+ss[2]+"-3"+ss[3]+"----length"+ss[ss.length-1]);
                final File myExternalFile = new File(Environment.getExternalStorageDirectory() + "/Download/", ss[ss.length - 1]);
                new Thread(new Runnable() {
                    public void run() {
                        readCSVdata(myExternalFile);
                    }
                }).start();
            }
        }
    }

    private void readCSVdata(File file) {

        List<String[]> list = new ArrayList<String[]>();
        String next[] = {};
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader csvStreamReader = new InputStreamReader(fis);

            CSVReader reader = new CSVReader(csvStreamReader);
            for (; ; ) {
                next = reader.readNext();
                if (next != null) {
                    list.add(next);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < list.size(); i++) {
            categoryList.add(list.get(i)[0]);
            String clip = list.get(i)[0];
            createEmployeeTable();
            String insertSQL = "INSERT INTO employees \n" +
                    "(district_code, mandal_code, panchayat_code,village_code,habitation_code,ssaat_code," +
                    "household_code,worker_code,surname,telugu_surname,name,telugu_name,account_no,work_code,work_name," +
                    "work_name_telugu,work_location,work_location_telugu,work_progress_code, from_date,to_date, days_worked," +
                    "amount_paid, payment_date,audit_payslip_date, audit_is_passbook_avail, audit_is_payslip_issuing, audit_is_jobcard_avail," +
                    "audit_days_worked, audit_amount_rec, audit_remarks, status, sent_file_name, sent_date, resp_filename, resp_date," +
                    "created_date,department, muster_id)\n" +
                    "VALUES \n" +
                    "(?, ?, ?,?,?, ?, ?,?,?, ?, ?,?, ?, ?,?,?, ?, ?,?,?, ?, ?,?, ?, ?,?,?, ?, ?,?,?, ?, ?,?, ?,?,?, ?, ?);";
            String[] csvparts = clip.split("\\^");
            String district_code = csvparts[0]; // 004
            String mandal_code = csvparts[1]; // 034556
            String panchayat_code = csvparts[2];
            String village_code = csvparts[3];
            String habitation_code = csvparts[4];
            String ssaat_code = csvparts[5];
            String household_code = csvparts[6];
            String worker_code = csvparts[7];
            String surname = csvparts[8];
            String telugu_surname = csvparts[9];
            String name = csvparts[10];
            String telugu_name = csvparts[11];
            String account_no = csvparts[12];
            String work_code = csvparts[13];
            String work_name = csvparts[14];
            String work_name_telugu = csvparts[15];
            String work_location = csvparts[16];
            String work_location_telugu = csvparts[17];
            String work_progress_code = csvparts[18];
            String from_date = csvparts[19];
            String to_date = csvparts[20];
            String days_worked = csvparts[21];
            String amount_paid = csvparts[22];
            String payment_date = csvparts[23];
            String audit_payslip_date = csvparts[24];
            String audit_is_passbook_avail = csvparts[25];
            String audit_is_payslip_issuing = csvparts[26];
            String audit_is_jobcard_avail = csvparts[27];
            String audit_days_worked = csvparts[28];
            String audit_amount_rec = csvparts[29];
            String audit_remarks = csvparts[30];
            String status = csvparts[31];
            String sent_file_name = csvparts[32];
            String sent_date = csvparts[33];
            String resp_filename = csvparts[34];
            String resp_date = csvparts[35];
            String created_date = csvparts[36];
            String department = csvparts[37];
            String muster_id = csvparts[38];

            mDatabase.execSQL(insertSQL, new String[]{district_code, mandal_code, panchayat_code, village_code, habitation_code, ssaat_code,
                    household_code, worker_code, surname, telugu_surname, name, telugu_name, account_no, work_code, work_name, work_name_telugu,
                    work_location, work_location_telugu, work_progress_code, from_date, to_date, days_worked, amount_paid, payment_date, audit_payslip_date,
                    audit_is_passbook_avail, audit_is_payslip_issuing, audit_is_jobcard_avail, audit_days_worked, audit_amount_rec,
                    audit_remarks, status, sent_file_name, sent_date, resp_filename, resp_date, created_date, department, muster_id});

        }
    }

}