package com.yvs.ssaat.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yvs.ssaat.R;
import com.yvs.ssaat.db.DBManager;
import com.yvs.ssaat.models.ReportsAdapter4A;
import com.yvs.ssaat.models.ReportsAdapter5A;
import com.yvs.ssaat.pojo.Format4Apojo;
import com.yvs.ssaat.pojo.Format5Apojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Reports5A extends AppCompatActivity implements View.OnClickListener {
    SQLiteDatabase mDatabase;
    private ArrayList<Format5Apojo> reports4A = new ArrayList<Format5Apojo>();
    Bundle bundle;
    RecyclerView rcyVw_reports5a;
    ReportsAdapter5A mAdapter;
    Context mCon = this;

    static String dateType = "";
    RelativeLayout fromDateLayout, toDateLayout;
    static TextView fromDateTxt, toDateTxt;
    int year, month, day;
    Button getReportsBtn;
    String sFromDate, sToDate;
    LinearLayout format5Aview;
    TextView format4Aheader, nodata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports5);
        rcyVw_reports5a = (RecyclerView) findViewById(R.id.rcyVw_reports5a);
        bundle = getIntent().getExtras();
        mAdapter = new ReportsAdapter5A(mCon, reports4A);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rcyVw_reports5a.setLayoutManager(mLayoutManager);
        rcyVw_reports5a.setNestedScrollingEnabled(false);
        rcyVw_reports5a.setAdapter(mAdapter);
        getReportsBtn = (Button) findViewById(R.id.getReportsBtn);
        format4Aheader = (TextView) findViewById(R.id.format4Aheader);
        format5Aview = (LinearLayout) findViewById(R.id.format5Aview);
        nodata = (TextView) findViewById(R.id.nodata);

        fromDateLayout = (RelativeLayout) findViewById(R.id.fromDateLayout);
        toDateLayout = (RelativeLayout) findViewById(R.id.toDateLayout);

        fromDateTxt = (TextView) findViewById(R.id.fromDateTxt);
        toDateTxt = (TextView) findViewById(R.id.toDateTxt);

        fromDateLayout.setOnClickListener(this);
        toDateLayout.setOnClickListener(this);
        getReportsBtn.setOnClickListener(this);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

    }

    public void loadFormat4Areports(String fromDate, String toDate) {
        reports4A.clear();
        try {
            //opening the database
            mDatabase = openOrCreateDatabase(CSVParsing.DATABASE_NAME, MODE_PRIVATE, null);
            //we used rawQuery(sql, selectionargs) for fetching all the employees
            Cursor cursorFormat4A =
                    DBManager.getInstance().getRawQuery("SELECT * FROM format5A Where date(created_date) BETWEEN date('" + fromDate + "') AND date('" + toDate + "')");
            //if the cursor has some data

            if (cursorFormat4A.moveToFirst()) {
                //looping through all the records
                int count = 1;
                do {
                    reports4A.add(new Format5Apojo(
                            String.valueOf(count++),
                            cursorFormat4A.getString(2),
                            cursorFormat4A.getString(3),
                            cursorFormat4A.getString(4),
                            cursorFormat4A.getString(5),
                            cursorFormat4A.getString(6),
                            cursorFormat4A.getString(7),
                            cursorFormat4A.getString(8),
                            cursorFormat4A.getString(9),
                            cursorFormat4A.getString(10),
                            cursorFormat4A.getString(11),
                            cursorFormat4A.getString(12),
                            cursorFormat4A.getString(13),
                            cursorFormat4A.getString(14),
                            cursorFormat4A.getString(15),
                            cursorFormat4A.getString(16),
                            cursorFormat4A.getString(17),
                            cursorFormat4A.getString(18),
                            cursorFormat4A.getString(19),
                            cursorFormat4A.getString(20),
                            cursorFormat4A.getString(21),
                            cursorFormat4A.getString(22),
                            cursorFormat4A.getString(23)));

                    System.out.println("in db date: " + cursorFormat4A.getString(19));
                } while (cursorFormat4A.moveToNext());
            }
            //closing the cursor
            cursorFormat4A.close();
            System.out.println("Format 5A Size: " + reports4A.size());
            loadListView(reports4A);
        } catch (SQLException error) {
            System.out.println(error.toString());
        }
    }


    private void loadListView(ArrayList<Format5Apojo> reports4A) {
        if (reports4A.size() > 0) {
            mAdapter = new ReportsAdapter5A(mCon, reports4A);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rcyVw_reports5a.setLayoutManager(mLayoutManager);
            rcyVw_reports5a.setAdapter(mAdapter);
            format4Aheader.setVisibility(View.VISIBLE);
            format5Aview.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.GONE);
        } else {
            format4Aheader.setVisibility(View.GONE);
            format5Aview.setVisibility(View.GONE);
            nodata.setVisibility(View.VISIBLE);
            Toast.makeText(Reports5A.this, "No data found for the selected dates.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fromDateLayout:
                showDatePickerDialog();
                dateType = "FromDate";
                break;
            case R.id.toDateLayout:
                showDatePickerDialog();
                dateType = "ToDate";
                break;
            case R.id.getReportsBtn:
                sFromDate = fromDateTxt.getText().toString();
                sToDate = toDateTxt.getText().toString();
                if (this.sFromDate.length() == 0 || this.sFromDate.isEmpty() || sFromDate.equalsIgnoreCase("from date") ||
                        this.sToDate.length() == 0 || this.sToDate.isEmpty() || sToDate.equalsIgnoreCase("to date")) {
                    Toast.makeText(Reports5A.this, "Both the dates are required", Toast.LENGTH_SHORT).show();
                } else {
                    loadFormat4Areports(sFromDate, sToDate);
                }
                break;
        }
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new Reports5A.DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            int month1 = month + 1;
            String inputTimeStamp = year + "-" + month1 + "-" + day;
            System.out.println("dateformat: " + inputTimeStamp);
            final String inputFormat = "yyyy-MM-dd";
            final String outputFormat = "yyyy-MM-dd";
            try {
                if (dateType.equalsIgnoreCase("FromDate")) {
                    fromDateTxt.setText(TimeStampConverter(inputFormat, inputTimeStamp, outputFormat));
                } else if (dateType.equalsIgnoreCase("ToDate")) {
                    toDateTxt.setText(TimeStampConverter(inputFormat, inputTimeStamp, outputFormat));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ;
        }
    }

    private static String TimeStampConverter(final String inputFormat,
                                             String inputTimeStamp, final String outputFormat)
            throws ParseException {
        return new SimpleDateFormat(outputFormat).format(new SimpleDateFormat(
                inputFormat).parse(inputTimeStamp));
    }
}
