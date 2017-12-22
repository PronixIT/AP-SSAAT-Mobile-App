package com.yvs.ssaat;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yvs.ssaat.activities.AuditActivity;
import com.yvs.ssaat.activities.AuditDetailsActivity;
import com.yvs.ssaat.activities.BaseActivity;
import com.yvs.ssaat.activities.CSVParsing;
import com.yvs.ssaat.activities.LoginActivity;
import com.yvs.ssaat.activities.SplashScreen;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends BaseActivity {
    private ImageView img_upload;
    SQLiteDatabase mDatabase;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int READ_REQUEST_CODE = 42;
    private ArrayList<String> categoryList = new ArrayList<String>();
    public static final String DATABASE_NAME = "myemployeedatabase";
    private ArrayList<String> checkList = new ArrayList<String>();
    private ProgressDialog progressDialog;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_main, frame_base);
        img_upload = (ImageView) findViewById(R.id.img_upload);
        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        createEmployeeTable();
        progressDialog = new ProgressDialog(MainActivity.this);
        if (!checkPermission()) {
            requestPermission();
        }

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            System.out.println("not read available");
        }
        img_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSelectLocalityDialog();
            }
        });
    }

    private void openSelectLocalityDialog() {
        dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_select_locality);
        Button uploadCSV = dialog.findViewById(R.id.uploadCSV);
        uploadCSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Uri uri = Uri.parse(Environment.getExternalStorageDirectory() + "/Download/");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setDataAndType(uri, "*/*");
                if (intent.resolveActivityInfo(getPackageManager(), 0) != null) {
                    startActivityForResult(intent, READ_REQUEST_CODE);
                } else {
                    Toast.makeText(MainActivity.this, "File Not Found Exception", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageView dialogButton = (ImageView) dialog.findViewById(R.id.closeImg);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
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
        //tvMainSelectedCate.setText("First column(district codes): "+checkList.toString());
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);

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
                //System.out.println("-------------0" + ss[0] + "-1" + ss[1] + "-2" + ss[2] + "-3" + ss[3] + "----length" + ss[ss.length - 1]);
                progressDialog.setMessage("Uploading your CSV file, please wait...");
                progressDialog.show();
                final File myExternalFile = new File(Environment.getExternalStorageDirectory() + "/Download/", ss[ss.length - 1]);
                new Thread(new Runnable() {
                    public void run() {

                        List<String[]> listdata = readCSVdata(myExternalFile);
                        Intent intent = new Intent(MainActivity.this, AuditActivity.class);
                        intent.putExtra("size",listdata.size());
                        intent.putExtra("data",listdata.toString());
                        startActivity(intent);
                        MainActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();

                    }
                }).start();
            }
        }
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


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted) {
                        //Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
                    } else {


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    private List<String[]> readCSVdata(File file) {

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
            final String clip = list.get(i)[0];
             System.out.println("Content======" + clip);


      /*      final Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {

                  *//*  final Dialog d = new Dialog(MainActivity.this, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
                    d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    d.setCancelable(false);
                    d.setCanceledOnTouchOutside(false);
                    d.setContentView(R.layout.dialog_showdata);
                    d.show();
                    TextView text = (TextView) d.findViewById(R.id.txt_content);
                    text.setText(""+clip);*//*

                }
            };
            handler.postDelayed(runnable, 3500);*/

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
        return list;
    }


}
