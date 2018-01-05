package com.yvs.ssaat.activities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.yvs.ssaat.Dal.DalWorksiteDetails;
import com.yvs.ssaat.Dal.DalWorksiteResults;
import com.yvs.ssaat.R;
import com.yvs.ssaat.common.Constants;
import com.yvs.ssaat.db.DBManager;
import com.yvs.ssaat.models.WorkersAdapter5A;
import com.yvs.ssaat.pojo.WorkSitePOJO;
import com.yvs.ssaat.pojo.Worker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Format5ARow extends AppCompatActivity implements View.OnClickListener {

    SQLiteDatabase mDatabase;
    private ArrayList<WorkSitePOJO> checkList = new ArrayList<WorkSitePOJO>();
    Bundle bundle;
    String work_code = "";
    RecyclerView rcyVw_workers;
    WorkersAdapter5A mAdapter;
    Context mCon = this;
    TextView tv_SNo, tv_WorkId, tv_WorkNameOrLocatio, tv_TaskDetails, tv_TechnologyType, tv_ApprEstValues,
    tv_ApprEstValues_Measurements, Tv_ApprEstValues_Total, tv_AsPerMBReport, tv_AsPerMBReport_Measurements, tv_AsPerMBReport_Total;
    TextView tv_isWorkDone, tv_CheckingValues, tv_CheckingValues_Measurements, tv_CheckingValues_Total, tv_Difference, tv_Difference_Measurements
            , tv_Difference_Total, tv_ResponsiblePersonName, tv_RespPersonJobDesg, tv_ImportanceOfWork, tv_Comments;
    DalWorksiteDetails dalWorksiteDetails;
    DalWorksiteResults dalWorksiteResults;
    Button but_Save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_format5_new);
        dalWorksiteDetails = new DalWorksiteDetails();
        dalWorksiteResults = new DalWorksiteResults();
        initializeControls();
        setTextValues();

        rcyVw_workers = (RecyclerView) findViewById(R.id.rcyVw_workers);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            work_code = bundle.getString("work_code");
            System.out.println("work_code" + work_code);
        }
        mAdapter = new WorkersAdapter5A(mCon, checkList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rcyVw_workers.setLayoutManager(mLayoutManager);
        rcyVw_workers.setNestedScrollingEnabled(false);
        rcyVw_workers.setAdapter(mAdapter);

        checkData();
    }

    private void checkData() {
        //opening the database
//        mDatabase = openOrCreateDatabase(CSVParsing.DATABASE_NAME, MODE_PRIVATE, null);
        checkList = dalWorksiteDetails.getWorkSiteDetails(work_code);
        //we used rawQuery(sql, selectionargs) for fetching all the employees
//        Cursor cursorWorksite = DBManager.getInstance().getRawQuery("SELECT * FROM worksite WHERE work_code IN ('" + work_code + "')");
//        //if the cursor has some data
//        if (cursorWorksite.moveToFirst()) {
//            //looping through all the records
//            int count=1;
//            do {
                //pushing each record in the employee list
//                checkList.add(new WorkSitePOJO(
//                        String.valueOf(count++),
//                        cursorWorksite.getString(1),
//                        cursorWorksite.getString(2),
//                        cursorWorksite.getString(3),
//                        cursorWorksite.getString(4),
//                        cursorWorksite.getString(5),
//                        cursorWorksite.getString(6),
//                        cursorWorksite.getString(7),
//                        cursorWorksite.getString(8),
//                        cursorWorksite.getString(9),
//                        cursorWorksite.getString(10),
//                        cursorWorksite.getString(11),
//                        cursorWorksite.getString(12),
//                        cursorWorksite.getString(13),
//                        cursorWorksite.getString(14),
//                        cursorWorksite.getString(15),
//                        cursorWorksite.getString(16),
//                        cursorWorksite.getString(17),
//                        cursorWorksite.getString(18),
//                        cursorWorksite.getString(19),
//                        cursorWorksite.getString(20),
//                        cursorWorksite.getString(21),
//                        cursorWorksite.getString(22),
//                        cursorWorksite.getString(23),
//                        cursorWorksite.getString(24),
//                        cursorWorksite.getString(25),
//                        cursorWorksite.getString(26),
//                        cursorWorksite.getString(27),
//                        cursorWorksite.getString(28),
//                        cursorWorksite.getString(29),
//                        cursorWorksite.getString(30),
//                        cursorWorksite.getString(31),
//                        cursorWorksite.getString(32),
//                        cursorWorksite.getString(33),
//                        cursorWorksite.getString(34),
//                        cursorWorksite.getString(35),
//                        cursorWorksite.getString(36),
//                        cursorWorksite.getString(37),
//                        cursorWorksite.getString(38),
//                        cursorWorksite.getString(39),
//                        cursorWorksite.getString(40),
//                        cursorWorksite.getString(41),
//                        cursorWorksite.getString(42)));
//            } while (cursorWorksite.moveToNext());
//        }
        //closing the cursor
//        cursorWorksite.close();

        System.out.println("---Selected work site array size: " + checkList.size());
        //tvMainSelectedCate.setText("First column(district codes): "+checkList.toString());
        loadListView(checkList);
    }
    private void loadListView(ArrayList<WorkSitePOJO> checkList) {
        if (checkList.size() > 0) {
            mAdapter = new WorkersAdapter5A(mCon, checkList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rcyVw_workers.setLayoutManager(mLayoutManager);
            rcyVw_workers.setAdapter(mAdapter);
        } else {
            Toast.makeText(Format5ARow.this, "No data found for this work code", Toast.LENGTH_SHORT).show();
        }

    }

    public void initializeControls()
    {
        tv_SNo = (TextView) findViewById(R.id.tv_SNo);
        tv_WorkId = (TextView) findViewById(R.id.tv_WorkId);
        tv_WorkNameOrLocatio = (TextView) findViewById(R.id.tv_WorkNameOrLoc);
        tv_TaskDetails = (TextView) findViewById(R.id.tv_TaskDetails);
        tv_TechnologyType = (TextView) findViewById(R.id.tv_TechnologyType);
        tv_ApprEstValues = (TextView) findViewById(R.id.tv_apprEstValues);
        tv_ApprEstValues_Measurements = (TextView) findViewById(R.id.tv_apprEstValues_Measurements);
        Tv_ApprEstValues_Total = (TextView) findViewById(R.id.tv_apprEstValues_Total);
        tv_AsPerMBReport = (TextView) findViewById(R.id.tv_AsPerMBReport);
        tv_AsPerMBReport_Measurements = (TextView) findViewById(R.id.tv_AsPerMBReport_Total);
        tv_AsPerMBReport_Total = (TextView) findViewById(R.id.tv_AsPerMBReport_Total);
        tv_isWorkDone = (TextView) findViewById(R.id.tv_IsWorkDone);
        tv_CheckingValues = (TextView) findViewById(R.id.tv_CheckingValues);
        tv_CheckingValues_Measurements = (TextView) findViewById(R.id.tv_CheckingValues_Measurements);
        tv_CheckingValues_Total = (TextView) findViewById(R.id.tv_CheckingValues_Total);
        tv_Difference = (TextView) findViewById(R.id.tv_Difference);
        tv_Difference_Measurements = (TextView) findViewById(R.id.tv_Difference_Measurements);
        tv_Difference_Total = (TextView) findViewById(R.id.tv_Difference_Total);
        tv_ResponsiblePersonName = (TextView) findViewById(R.id.tv_RespPersonName);
        tv_RespPersonJobDesg = (TextView) findViewById(R.id.tv_RespPersonDesg);
        tv_ImportanceOfWork = (TextView) findViewById(R.id.tv_ImportanceOfWork);
        tv_Comments = (TextView) findViewById(R.id.tv_Comments);
        but_Save = (Button) findViewById(R.id.but_Formate5ASave);
        but_Save.setOnClickListener(this);
    }

    public void setTextValues()
    {

        tv_SNo.setText(R.string.sNo);
        tv_WorkId.setText(R.string.workId);
        tv_WorkNameOrLocatio.setText(R.string.workNameorLocation);
        tv_TaskDetails.setText(R.string.taskDetails);
        tv_TechnologyType.setText(R.string.technologyType);
        tv_ApprEstValues.setText(R.string.approvedEstvalues);
        tv_ApprEstValues_Measurements.setText(R.string.measurments);
        Tv_ApprEstValues_Total.setText(R.string.total);
        tv_AsPerMBReport.setText(R.string.asPerMBReporrt);
        tv_AsPerMBReport_Measurements.setText(R.string.measurments);
        tv_AsPerMBReport_Total.setText(R.string.total);
        tv_isWorkDone.setText(R.string.isWorkDone);
        tv_CheckingValues.setText(R.string.checkingValues);
        tv_CheckingValues_Measurements.setText(R.string.measurments);
        tv_CheckingValues_Total.setText(R.string.total);
        tv_Difference.setText(R.string.difference);
        tv_Difference_Measurements.setText(R.string.measurments);
        tv_Difference_Total.setText(R.string.total);
        tv_ResponsiblePersonName.setText(R.string.resPersonName);
        tv_RespPersonJobDesg.setText(R.string.resPersonJobDesg);
        tv_ImportanceOfWork.setText(R.string.impOfWork);
        tv_Comments.setText(R.string.comments);

    }

    public void saveListItems()
    {
        TextView serialNo, work_code, allWorkDetails,taskDetails,techType,ap_measure,ap_total,amb_measure,amb_total;
        EditText cv_measure,cv_total,diff_measure,diff_total,
                respPersonName,respPersonDesig,imp_of_work,comments;
        Spinner isworkDoneSpinner;
        long result = -1;
        for (int i=0; i<checkList.size(); i++) {
            View view = rcyVw_workers.getChildAt(i);
            serialNo = view.findViewById(R.id.serialNo);
            work_code = view.findViewById(R.id.work_code);
            allWorkDetails = view.findViewById(R.id.allWorkDetails);
            taskDetails = view.findViewById(R.id.taskDetails);
            techType = view.findViewById(R.id.techType);
            ap_measure = view.findViewById(R.id.ap_measure);
            ap_total = view.findViewById(R.id.ap_total);
            amb_measure = view.findViewById(R.id.amb_measure);
            amb_total = view.findViewById(R.id.amb_total);
            isworkDoneSpinner = view.findViewById(R.id.isworkDoneSpinner);
            cv_measure = view.findViewById(R.id.cv_measure);
            cv_total = view.findViewById(R.id.cv_total);
            diff_measure = view.findViewById(R.id.diff_measure);
            diff_total = view.findViewById(R.id.diff_total);
            respPersonName = view.findViewById(R.id.respPersonName);
            respPersonDesig = view.findViewById(R.id.respPersonDesig);
            imp_of_work = view.findViewById(R.id.imp_of_work);
            comments = view.findViewById(R.id.comments);
            String created_time = "";
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
            Calendar cal = Calendar.getInstance();
            created_time = dateFormat.format(cal.getTime());

            result = dalWorksiteResults.insertOrUpdateWorksiteResultData(mDatabase,
                    serialNo.getText().toString(),
                    work_code.getText().toString(),
                    allWorkDetails.getText().toString(),
                    taskDetails.getText().toString(),
                    techType.getText().toString(),
                    ap_measure.getText().toString(),
                    ap_total.getText().toString(),
                    amb_measure.getText().toString(),
                    amb_total.getText().toString(),
                    isworkDoneSpinner.getSelectedItem().toString(),
                    cv_measure.getText().toString(),
                    cv_total.getText().toString(),
                    diff_measure.getText().toString(),
                    diff_total.getText().toString(),
                    respPersonName.getText().toString(),
                    respPersonDesig.getText().toString(),
                    imp_of_work.getText().toString(),
                    comments.getText().toString(),
                    created_time,
                    Constants.userMasterDO.userName,(new SimpleDateFormat("yyyy-MM-dd HH:mm:SS")).format(new Date()),
                    Constants.userMasterDO.userName,"", checkList.get(i).getTask_code());
            if(result == 0 || result == -1)
            {
                break;
            }


//              Toast.makeText(this, tv_workId.getText().toString(), Toast.LENGTH_SHORT).show();
        }
        if(result != 0 && result != -1)
            Toast.makeText(mCon,"Details saved successfully",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(mCon,"Failed to save details",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.but_Formate5ASave:
                saveListItems();
                break;
        }

    }
}
