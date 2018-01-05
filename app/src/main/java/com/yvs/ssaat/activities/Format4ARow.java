package com.yvs.ssaat.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.yvs.ssaat.Dal.DalDoorToDoorDetails;
import com.yvs.ssaat.Dal.DalDoorToDoorResults;
import com.yvs.ssaat.R;
import com.yvs.ssaat.common.Constants;
import com.yvs.ssaat.db.DBManager;
import com.yvs.ssaat.models.WorkersAdapter4A;
import com.yvs.ssaat.pojo.Worker;
import com.yvs.ssaat.session.SessionManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Format4ARow extends AppCompatActivity {
    SQLiteDatabase mDatabase;
    private ArrayList<Worker> checkList = new ArrayList<Worker>();
    Bundle bundle;
    String householdCode = "";
    RecyclerView rcyVw_workers;
    WorkersAdapter4A mAdapter;
    Context mCon = this;
    List<List<String>> format4aFamilyList;
    List<String> format4aIndividualList;
    private Button btn_save_format4a;
    SessionManager sessionManager;

    TextView serialNo, wageSeekerId,fullname,postOfficeBankAccDetails,allWorkDetails,workDuration,
            payOrderReleaseDate,musterId,noOfWorkingDays,amtToBePaid;
    EditText actualWorkedDays,actualAmtPaid,diffInAmtPaid,respPersonName,respPersonDesig,comments;
    Spinner isJobCardAvailSpinner,isPassbookAvailSpinner,isPaylipIssuedSpinner,mainCategorySpinner,subCategorySpinner1,subCategorySpinner2;
    private DalDoorToDoorResults dalDoorToDoorResults;
    DalDoorToDoorDetails dalDoorToDoorDetails;

    TextView tv_SNo, tv_JobSeekerId, tv_FullName, tv_POOrBA, tv_WorkDetails, tv_WorkDuration, tv_PayOrderRelease, tv_MusterId, tv_NoOfWorkingDays,
            tv_AmountToBePaid, tv_ActualNofOfDaysWorked, tv_ActualPaidAmt, tv_DiffInAmtPaid, tv_IsJobCardAvail, tv_IsPassBookAvail, tv_PayslipsIssuing,
            tv_RespPersonName, tv_RespPersonDesg, tv_Category1, tv_Category2, tv_Category3, tv_Comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_format4_arow);
        dalDoorToDoorResults = new DalDoorToDoorResults();
        dalDoorToDoorDetails = new DalDoorToDoorDetails();
        initializeControls();
        setTextTitls();
        btn_save_format4a = (Button) findViewById(R.id.btn_save_format4a);
        rcyVw_workers = (RecyclerView) findViewById(R.id.rcyVw_workers);
        bundle = getIntent().getExtras();
        format4aFamilyList = new ArrayList<>();
        sessionManager = new SessionManager(mCon);
        if (bundle != null) {
            householdCode = bundle.getString("householdCode");
            System.out.println("householdCode" + householdCode);
        }


        mAdapter = new WorkersAdapter4A(mCon, checkList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rcyVw_workers.setLayoutManager(mLayoutManager);
        rcyVw_workers.setNestedScrollingEnabled(false);
        rcyVw_workers.setAdapter(mAdapter);

        //To check and load if any data is present for selected household code
        checkData();


        btn_save_format4a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkValidations()){
                    storeData();
                }
            }
        });


    }

    private void checkData() {
        //opening the database
        mDatabase = openOrCreateDatabase(CSVParsing.DATABASE_NAME, MODE_PRIVATE, null);
        //we used rawQuery(sql, selectionargs) for fetching all the employees
        checkList = dalDoorToDoorDetails.getDoorToDoorDetails(householdCode);
//        Cursor cursorEmployees = DBManager.getInstance().getRawQuery("SELECT * FROM employees WHERE household_code IN ('" + householdCode + "')");
//        //if the cursor has some data
//        if (cursorEmployees.moveToFirst()) {
//            int count = 1;
//            //looping through all the records
//            do {
                //pushing each record in the employee list
//                checkList.add(new Worker(
//                        String.valueOf(count++),
//                        cursorEmployees.getString(1),
//                        cursorEmployees.getString(2),
//                        cursorEmployees.getString(3),
//                        cursorEmployees.getString(4),
//                        cursorEmployees.getString(5),
//                        cursorEmployees.getString(6),
//                        cursorEmployees.getString(7),
//                        cursorEmployees.getString(8),
//                        cursorEmployees.getString(9),
//                        cursorEmployees.getString(11),
//                        cursorEmployees.getString(13),
//                        cursorEmployees.getString(14),
//                        cursorEmployees.getString(15),
//                        cursorEmployees.getString(17),
//                        cursorEmployees.getString(19),
//                        cursorEmployees.getString(20),
//                        cursorEmployees.getString(21),
//                        cursorEmployees.getString(22),
//                        cursorEmployees.getString(23),
//                        cursorEmployees.getString(24),
//                        cursorEmployees.getString(25),
//                        cursorEmployees.getString(39)));
//            } while (cursorEmployees.moveToNext());
//        }
//        //closing the cursor
//        cursorEmployees.close();

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

    private boolean checkValidations(){
        boolean flag = false;
        for (int i = 0; i < checkList.size(); i++) {
            View holder = rcyVw_workers.getChildAt(i);
            flag = false;
            actualWorkedDays = holder.findViewById(R.id.actualWorkedDays);
            actualAmtPaid = holder.findViewById(R.id.actualAmtPaid);
            diffInAmtPaid = holder.findViewById(R.id.diffInAmtPaid);
            isJobCardAvailSpinner = holder.findViewById(R.id.isJobCardAvailSpinner);
            isPassbookAvailSpinner = holder.findViewById(R.id.isPassbookAvailSpinner);
            isPaylipIssuedSpinner = holder.findViewById(R.id.isPaylipIssuedSpinner);
            respPersonName = holder.findViewById(R.id.respPersonName);
            respPersonDesig = holder.findViewById(R.id.respPersonDesig);
            mainCategorySpinner = holder.findViewById(R.id.mainCategorySpinner);
            subCategorySpinner1 = holder.findViewById(R.id.subCategorySpinner1);
            subCategorySpinner2 = holder.findViewById(R.id.subCategorySpinner2);
            comments = holder.findViewById(R.id.comments);
            if (actualWorkedDays.getText().toString().trim().equals("")) {
                //Toast.makeText(mCon, "Actual Work Days should not be empty", Toast.LENGTH_SHORT).show();
//                actualWorkedDays.setError("Actual Work Days should not be empty");
                showalert(this, "Alert", "Actual Work Days should not be empty for " + checkList.get(i).getName());
                break;
            } else {
                if (actualAmtPaid.getText().toString().trim().equals("")) {
                    //Toast.makeText(mCon, "Actual Amount Paid should not be empty", Toast.LENGTH_SHORT).show();
//                    actualAmtPaid.setError("Actual Work Days should not be empty");
                    showalert(this, "Alert", "Actual amount paid should not be empty for " + checkList.get(i).getName());
                    break;
                } else {
                    if (diffInAmtPaid.getText().toString().trim().equals("")) {
                        //Toast.makeText(mCon, "Diiference in amount paid should not be empty if not keep 0", Toast.LENGTH_SHORT).show();
//                        diffInAmtPaid.setError("Actual Work Days should not be empty");
                        showalert(this, "Alert", "Difference amount paid should not be empty for " + checkList.get(i).getName());
                        break;
                    } else {
                        flag = true;
                    }
                }
            }
        }
        return flag;

    }

    private void storeData(){
        try {
            long result = -1;
            for (int i = 0; i < checkList.size(); i++) {
                View holder = rcyVw_workers.getChildAt(i);
                serialNo = holder.findViewById(R.id.serialNo);
                wageSeekerId = holder.findViewById(R.id.wageSeekerId);
                fullname = holder.findViewById(R.id.fullname);
                postOfficeBankAccDetails = holder.findViewById(R.id.postOfficeBankAccDetails);
                allWorkDetails = holder.findViewById(R.id.allWorkDetails);
                workDuration = holder.findViewById(R.id.workDuration);
                payOrderReleaseDate = holder.findViewById(R.id.payOrderReleaseDate);
                musterId = holder.findViewById(R.id.musterId);
                noOfWorkingDays = holder.findViewById(R.id.noOfWorkingDays);
                amtToBePaid = holder.findViewById(R.id.amtToBePaid);
                actualWorkedDays = holder.findViewById(R.id.actualWorkedDays);
                actualAmtPaid = holder.findViewById(R.id.actualAmtPaid);
                diffInAmtPaid = holder.findViewById(R.id.diffInAmtPaid);
                isJobCardAvailSpinner = holder.findViewById(R.id.isJobCardAvailSpinner);
                isPassbookAvailSpinner = holder.findViewById(R.id.isPassbookAvailSpinner);
                isPaylipIssuedSpinner = holder.findViewById(R.id.isPaylipIssuedSpinner);
                respPersonName = holder.findViewById(R.id.respPersonName);
                respPersonDesig = holder.findViewById(R.id.respPersonDesig);
                mainCategorySpinner = holder.findViewById(R.id.mainCategorySpinner);
                subCategorySpinner1 = holder.findViewById(R.id.subCategorySpinner1);
                subCategorySpinner2 = holder.findViewById(R.id.subCategorySpinner2);
                //updateWorkerDetails = holder.findViewById(R.id.updateWorkerDetails);
                comments = holder.findViewById(R.id.comments);

                format4aIndividualList = new ArrayList<>();

                String created_time = "";
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
                Calendar cal = Calendar.getInstance();
                System.out.println("created timestamp" + dateFormat.format(cal.getTime()));
                created_time = dateFormat.format(cal.getTime());
                mDatabase = mCon.openOrCreateDatabase(CSVParsing.DATABASE_NAME, MODE_PRIVATE, null);
                System.out.println("Selected Values: " + mainCategorySpinner.getSelectedItem().toString().trim() + "---" + subCategorySpinner1.getSelectedItem().toString().trim() +
                        "-------" + subCategorySpinner2.getSelectedItem().toString().trim());
//                          String insertSQL = "INSERT INTO format4A \n" +
//                            "(sno, wageSeekerId ,fullname, postBank, work_details,work_duration,payOrderRelDate, musterId, workedDays, amtToBePaid," +
//                            " actualWorkedDays," +
//                            "actualAmtPaid,differenceInAmt,isJobCardAvail,isPassbookAvail,isPayslipIssued,respPersonName,respPersonDesig," +
//                            "categoryone,categorytwo,categorythree,created_date,comments, created_by, modified_date,modified_by,isActive)\n" +
//                            "VALUES \n" +
//                            "(?, ?, ?,?,?, ?, ?,?,?, ?, ?,?, ?, ?,?,?, ?,?,?,?,?, ?,?,?,?,?,?);";
//                              mDatabase.execSQL(insertSQL,
//                            new String[]{

                String[] jobseeker = wageSeekerId.getText().toString().trim().split("\\/");


                result = dalDoorToDoorResults.insertOrUpdateDoorToDoorResultData(mDatabase,
                        serialNo.getText().toString().trim(),
                        jobseeker[0],
                        jobseeker[1],
                        fullname.getText().toString().trim(),
                        postOfficeBankAccDetails.getText().toString().trim(),
                        allWorkDetails.getText().toString().trim(),
                        workDuration.getText().toString().trim(),
                        payOrderReleaseDate.getText().toString().trim(),
                        musterId.getText().toString().trim(),
                        noOfWorkingDays.getText().toString().trim(),
                        amtToBePaid.getText().toString().trim(),
                        actualWorkedDays.getText().toString().trim(),
                        actualAmtPaid.getText().toString().trim(),
                        diffInAmtPaid.getText().toString().trim(),
                        isJobCardAvailSpinner.getSelectedItem().toString().trim(),
                        isPassbookAvailSpinner.getSelectedItem().toString().trim(),
                        isPaylipIssuedSpinner.getSelectedItem().toString().trim(),
                        respPersonName.getText().toString().trim(),
                        respPersonDesig.getText().toString().trim(),
                        mainCategorySpinner.getSelectedItem().toString().trim(),
                        subCategorySpinner1.getSelectedItem().toString().trim(),
                        subCategorySpinner2.getSelectedItem().toString().trim(),
                        created_time,
                        comments.getText().toString().trim(),
                        Constants.userMasterDO.userName,(new SimpleDateFormat("yyyy-MM-dd HH:mm:SS")).format(new Date()),
                        Constants.userMasterDO.userName, "");

                if(result == 0 || result == -1)
                {
                    break;
                }
            }
            if(result != 0 || result != -1)
            {
                Toast.makeText(this, "Details saved successfully", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this, "Failed to save details", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }

    public void showalert(Context context, String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        builder.show();
    }

    public void initializeControls()
    {

        tv_SNo = (TextView) findViewById(R.id.tv_SNo);
        tv_JobSeekerId = (TextView) findViewById(R.id.tv_JobSeekerId);
        tv_FullName = (TextView) findViewById(R.id.tv_FullName);
        tv_POOrBA = (TextView) findViewById(R.id.tv_POOrBA);
        tv_WorkDetails = (TextView) findViewById(R.id.tv_WorkDetails);
        tv_WorkDuration = (TextView) findViewById(R.id.tv_WorkDuration);
        tv_PayOrderRelease = (TextView) findViewById(R.id.tv_PayOrderRelease);
        tv_MusterId = (TextView) findViewById(R.id.tv_MusterId);
        tv_NoOfWorkingDays = (TextView) findViewById(R.id.tv_NoOfWorkingDays);
        tv_AmountToBePaid = (TextView) findViewById(R.id.tv_AmountToBePaid);
        tv_ActualNofOfDaysWorked = (TextView) findViewById(R.id.tv_ActualNofOfDaysWorked);
        tv_ActualPaidAmt = (TextView) findViewById(R.id.tv_ActualPaidAmt);
        tv_DiffInAmtPaid = (TextView) findViewById(R.id.tv_DiffInAmtPaid);
        tv_IsJobCardAvail = (TextView) findViewById(R.id.tv_IsJobCardAvail);
        tv_IsPassBookAvail = (TextView) findViewById(R.id.tv_IsPassBookAvail);
        tv_PayslipsIssuing = (TextView) findViewById(R.id.tv_PayslipsIssuing);
        tv_RespPersonName = (TextView) findViewById(R.id.tv_RespPersonName);
        tv_RespPersonDesg = (TextView) findViewById(R.id.tv_RespPersonDesg);
        tv_Category1 = (TextView) findViewById(R.id.tv_Category1);
        tv_Category2 = (TextView) findViewById(R.id.tv_Category2);
        tv_Category3 = (TextView) findViewById(R.id.tv_Category3);
        tv_Comments = (TextView) findViewById(R.id.tv_Comments);
    }

    public void setTextTitls()
    {
        tv_SNo.setText(R.string.sNo);
        tv_JobSeekerId.setText(R.string.jobSeekerId);
        tv_FullName.setText(R.string.fullName);
        tv_POOrBA.setText(R.string.POorBAD);
        tv_WorkDetails.setText(R.string.WorkDetails);
        tv_WorkDuration.setText(R.string.workDuration);
        tv_PayOrderRelease.setText(R.string.payOrderReleaseDate);
        tv_MusterId.setText(R.string.musterId);
        tv_NoOfWorkingDays.setText(R.string.noOfWorkingDays);
        tv_AmountToBePaid.setText(R.string.AmtToBePaid);
        tv_ActualNofOfDaysWorked.setText(R.string.actualNoOfdaysWorked);
        tv_ActualPaidAmt.setText(R.string.actualPaidAmt);
        tv_DiffInAmtPaid.setText(R.string.diffInAmtPaid);
        tv_IsJobCardAvail.setText(R.string.isJobCardIdAvai);
        tv_IsPassBookAvail.setText(R.string.isPassbookisAvailable);
        tv_PayslipsIssuing.setText(R.string.paySlipsIssuing);
        tv_RespPersonName.setText(R.string.resPersonName);
        tv_RespPersonDesg.setText(R.string.resPersonJobDesg);
        tv_Category1.setText(R.string.category1);
        tv_Category2.setText(R.string.category2);
        tv_Category3.setText(R.string.category3);
        tv_Comments.setText(R.string.comments);
    }
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