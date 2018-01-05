package com.yvs.ssaat;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.yvs.ssaat.Dal.DalDoorToDoorDetails;
import com.yvs.ssaat.Dal.DalWorksiteDetails;
import com.yvs.ssaat.activities.AuditActivity;
import com.yvs.ssaat.activities.AuditDetailsActivity;
import com.yvs.ssaat.activities.BaseActivity;
import com.yvs.ssaat.activities.CSVParsing;
import com.yvs.ssaat.activities.LoginActivity;
import com.yvs.ssaat.activities.SplashScreen;
import com.yvs.ssaat.common.SqliteConstants;
import com.yvs.ssaat.db.DBManager;
import com.yvs.ssaat.session.SessionManager;

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
    SQLiteDatabase mDatabase;
    private TextView total_wage_seekers,total_areas_worked,total_worksite_sync_records,total_jobseeker_sync_records,total_jobseeker_unsync_records,total_worksite_unsync_records;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int READ_REQUEST_CODE = 42;
    private ArrayList<String> categoryList = new ArrayList<String>();
    public static final String DATABASE_NAME = "myemployeedatabase";
    private ArrayList<String> checkList = new ArrayList<String>();
    private ProgressDialog progressDialog;
    Dialog dialog;
    SessionManager sessionManager;
    String selectedauditType = "";
    private int countOfFormat4A,countFormat4AResults;;
    private int countOfFormat5A;
    private Intent intent;
    private List<String[]> listdata = new ArrayList();
    private int countFormat5AResults;
    DalDoorToDoorDetails dalDoorToDoorDetails;
    DalWorksiteDetails dalWorksiteDetails;
    private Button start_audit,upload_csv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_main, frame_base);

        dalDoorToDoorDetails = new DalDoorToDoorDetails();
        dalWorksiteDetails = new DalWorksiteDetails();
        sessionManager = new SessionManager(this);
        upload_csv = (Button) findViewById(R.id.upload_csv);
        start_audit = (Button) findViewById(R.id.start_audit);
        total_wage_seekers = (TextView) findViewById(R.id.total_wage_seekers);
        total_areas_worked = (TextView) findViewById(R.id.total_areas_worked);
        total_jobseeker_sync_records = (TextView) findViewById(R.id.total_jobseeker_sync_records);
        total_worksite_sync_records = (TextView) findViewById(R.id.total_worksite_sync_records);
        total_jobseeker_unsync_records = (TextView) findViewById(R.id.total_jobseeker_unsync_records);
        total_worksite_unsync_records = (TextView) findViewById(R.id.total_worksite_unsync_records);
        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        try{
//            createEmployeeTable();
//            createWorkSiteTable();
//            createFormat4ATable();
//            createFormat5ATable();
//            createTableUserMaster();
        }catch (SQLException error){
            System.out.println(error.toString());
        }
        progressDialog = new ProgressDialog(MainActivity.this);
        if (!checkPermission()) {
            requestPermission();
        }

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            System.out.println("not read available");
        }
        upload_csv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSelectLocalityDialog();
            }
        });

        start_audit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDashboard();
                //we used rawQuery(sql, selectionargs) for fetching all the employees
                //cursorFormat4A = mDatabase.rawQuery("SELECT * FROM employees", null);
                //we used rawQuery(sql, selectionargs) for fetching all the employees
                //cursorFormat5A = mDatabase.rawQuery("SELECT * FROM worksite", null);
                if (countOfFormat4A > 0) {
                    if (countOfFormat5A > 0) {
                        intent = new Intent(MainActivity.this, AuditDetailsActivity.class);
                        intent.putExtra("size", listdata.size());
                        intent.putExtra("data", listdata.toString());
                        startActivity(intent);
                        MainActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else {
                        Toast.makeText(MainActivity.this, "Please download Work Site Audit Data", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please download Door To Door Audit Data", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void openSelectLocalityDialog() {
        dialog = new Dialog(MainActivity.this);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_select_locality);
        Button uploadCSV = dialog.findViewById(R.id.uploadCSV);
        final Spinner selectAuditType = dialog.findViewById(R.id.selectAuditType);

        uploadCSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String auditType = selectAuditType.getSelectedItem().toString();
                if(auditType.equalsIgnoreCase("Upload file for:")){
                    Toast.makeText(MainActivity.this,"Select upload file for...",Toast.LENGTH_SHORT).show();
                }else {
                    if(auditType.equalsIgnoreCase("Door To Door")){
                        selectedauditType = "Door To Door";
                    }else{
                        selectedauditType = "Work Site";
                    }
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
                dialog.dismiss();
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
                progressDialog.setCancelable(false);
                progressDialog.show();
                final File myExternalFile = new File(Environment.getExternalStorageDirectory() + "/Download/", ss[ss.length - 1]);
                readCSVdata(myExternalFile);


            }
        }
    }

    private void createEmployeeTable() {
        mDatabase.execSQL("CREATE TABLE IF NOT EXISTS employees (\n" +
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
                ");");
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


    private void readCSVdata(File file) {

        //List<String[]> list = new ArrayList<String[]>();
        String[] next = {};
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader csvStreamReader = new InputStreamReader(fis);

            CSVReader reader = new CSVReader(csvStreamReader);
            if(selectedauditType.equalsIgnoreCase("Door To Door")){

                for (; ; ) {
                    next = reader.readNext();
                    if (next != null)
                        insertToDoorToDoor(next);
                    else
                        break;
                }
                progressDialog.hide();
                updateDashboard();
                Toast.makeText(this,"Door to Door Audit File Downloaded Sucesfully",Toast.LENGTH_LONG).show();
            }else if(selectedauditType.equalsIgnoreCase("Work Site")){
                for (; ; ) {
                    next = reader.readNext();
                    if (next != null)
                        insertToWorkSite(next);
                    else
                        break;
                }
                progressDialog.hide();
                updateDashboard();
                Toast.makeText(this,"Work Site Audit File Downloaded Sucesfully",Toast.LENGTH_LONG).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }



        //return list;
    }

    public void insertToDoorToDoor(String[] list){
        //for (int i = 0; i < list.size(); i++) {
            //categoryList.add(list.get(i)[0]);
        String[] csvparts = list[0].split("\\^");
        dalDoorToDoorDetails.insertOrUpdateDoorToDoorData(csvparts);


//            System.out.println("Content==doortodoor====" + list[0]);
//
//            String insertSQL = "INSERT INTO employees \n" +
//                    "(district_code, mandal_code, panchayat_code,village_code,habitation_code,ssaat_code," +
//                    "household_code,worker_code,surname,telugu_surname,name,telugu_name,account_no,work_code,work_name," +
//                    "work_name_telugu,work_location,work_location_telugu,work_progress_code, from_date,to_date, days_worked," +
//                    "amount_paid, payment_date,audit_payslip_date, audit_is_passbook_avail, audit_is_payslip_issuing, audit_is_jobcard_avail," +
//                    "audit_days_worked, audit_amount_rec, audit_remarks, status, sent_file_name, sent_date, resp_filename, resp_date," +
//                    "created_date,department, muster_id)\n" +
//                    "VALUES \n" +
//                    "(?, ?, ?,?,?, ?, ?,?,?, ?, ?,?, ?, ?,?,?, ?, ?,?,?, ?, ?,?, ?, ?,?,?, ?, ?,?,?, ?, ?,?, ?,?,?, ?, ?);";
//
//            String district_code = csvparts[0]; // 004
//            String mandal_code = csvparts[1]; // 034556
//            String panchayat_code = csvparts[2];
//            String village_code = csvparts[3];
//            String habitation_code = csvparts[4];
//            String ssaat_code = csvparts[5];
//            String household_code = csvparts[6];
//            String worker_code = csvparts[7];
//            String surname = csvparts[8];
//            String telugu_surname = csvparts[9];
//            String name = csvparts[10];
//            String telugu_name = csvparts[11];
//            String account_no = csvparts[12];
//            String work_code = csvparts[13];
//            String work_name = csvparts[14];
//            String work_name_telugu = csvparts[15];
//            String work_location = csvparts[16];
//            String work_location_telugu = csvparts[17];
//            String work_progress_code = csvparts[18];
//            String from_date = csvparts[19];
//            String to_date = csvparts[20];
//            String days_worked = csvparts[21];
//            String amount_paid = csvparts[22];
//            String payment_date = csvparts[23];
//            String audit_payslip_date = csvparts[24];
//            String audit_is_passbook_avail = csvparts[25];
//            String audit_is_payslip_issuing = csvparts[26];
//            String audit_is_jobcard_avail = csvparts[27];
//            String audit_days_worked = csvparts[28];
//            String audit_amount_rec = csvparts[29];
//            String audit_remarks = csvparts[30];
//            String status = csvparts[31];
//            String sent_file_name = csvparts[32];
//            String sent_date = csvparts[33];
//            String resp_filename = csvparts[34];
//            String resp_date = csvparts[35];
//            String created_date = csvparts[36];
//            String department = csvparts[37];
//            String muster_id = csvparts[38];
//
//            mDatabase.execSQL(insertSQL, new String[]{district_code, mandal_code, panchayat_code, village_code, habitation_code, ssaat_code,
//                    household_code, worker_code, surname, telugu_surname, name, telugu_name, account_no, work_code, work_name, work_name_telugu,
//                    work_location, work_location_telugu, work_progress_code, from_date, to_date, days_worked, amount_paid, payment_date, audit_payslip_date,
//                    audit_is_passbook_avail, audit_is_payslip_issuing, audit_is_jobcard_avail, audit_days_worked, audit_amount_rec,
//                    audit_remarks, status, sent_file_name, sent_date, resp_filename, resp_date, created_date, department, muster_id});

       // }

    }

    public void insertToWorkSite(String[] list){
        //for (int i = 0; i < list.size(); i++) {
            //categoryList.add(list.get(i)[0]);
            String[] csvparts = list[0].split("\\^",-1);
            System.out.println("Content=worksite=====" + list[0]);
            dalWorksiteDetails.insertOrUpdateWorksiteDetails(mDatabase, csvparts);

//            String insertSQL = "INSERT INTO worksite \n" +
//                    "(district_code, mandal_code, panchayat_code, village_code, habitation_code, ssaat_code, work_code, work_name, work_name_telugu," +
//                    " work_location, " +
//                    "work_location_telugu, task_code, task_name, skill_type, qty_sanc, amount_sanc, qty_done, amount_spent, audit_is_work_done," +
//                    "audit_is_work_done_location, audit_qty_sanc, audit_amount_sanc, audit_qty_done, audit_amount_spent, audit_remarks, status," +
//                    "sent_file_name, sent_date, resp_filename, resp_date, created_date, department, audit_usefull_work)" +
//                    "VALUES \n" +
//                    "(?, ?, ?,?,?, ?, ?,?,?, ?, ?,?, ?, ?,?,?, ?, ?,?,?, ?, ?,?, ?, ?,?,?, ?, ?,?,?, ?, ?);";
//
//            System.out.println("test: "+csvparts.length);
//
//            mDatabase.execSQL(insertSQL, new String[]{csvparts[0], csvparts[1], csvparts[2], csvparts[3], csvparts[4], csvparts[5],
//                    csvparts[6], csvparts[7], csvparts[8], csvparts[9], csvparts[10], csvparts[11], csvparts[12], csvparts[13], csvparts[14], csvparts[15],
//                    csvparts[16], csvparts[17], csvparts[18], csvparts[19], csvparts[20], csvparts[21], csvparts[22], csvparts[23], csvparts[24],
//                    csvparts[25], csvparts[26], csvparts[27], csvparts[28], csvparts[29], csvparts[30], csvparts[31], csvparts[32]});

    }


    @Override
    protected void onResume() {
        sessionManager.checkLogin();
        super.onResume();
    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            super.onBackPressed();
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, getString(R.string.exit), Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }

            }, 3 * 1000);
        }
    }



    private void updateDashboard(){
        countOfFormat4A = stringToIn(DBManager.getInstance().getScalar("SELECT count(1) FROM employees"));
        total_wage_seekers.setText(String.valueOf(countOfFormat4A));

        countOfFormat5A = stringToIn(DBManager.getInstance().getScalar("SELECT count(1) FROM worksite"));
        total_areas_worked.setText(String.valueOf(countOfFormat5A));

        //we used rawQuery(sql, selectionargs) for fetching all the employees
        countFormat4AResults = stringToIn(DBManager.getInstance().getScalar("SELECT count(1) FROM format4A"));
        total_jobseeker_sync_records.setText(String.valueOf(countFormat4AResults));

        //we used rawQuery(sql, selectionargs) for fetching all the employees
        countFormat5AResults = stringToIn(DBManager.getInstance().getScalar("SELECT count(1) FROM format5A"));
        total_worksite_sync_records.setText(String.valueOf(countFormat5AResults));

        total_jobseeker_unsync_records.setText(String.valueOf(countOfFormat4A - countFormat4AResults));
        total_worksite_unsync_records.setText(String.valueOf(countOfFormat5A - countFormat5AResults));

    }

    @Override
    protected void onStart() {
        super.onStart();
        updateDashboard();
    }

    public int stringToIn(String result)
    {
        try {
            if (result == null || result.equals("")) {
                return 0;
            }
            return Integer.parseInt(result);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return 0;
    }

}
