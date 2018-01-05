package com.yvs.ssaat.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.yvs.ssaat.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class FragWorkSite extends Fragment{

    public FragWorkSite() {
        // Required empty public constructor
    }
    static TextView date1,date2;

    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    static String dateType="from";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.view_worksites, container, false);
        date1 = view.findViewById(R.id.date1);
        date2 = view.findViewById(R.id.date2);
        // Get current date by calender

        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);

        // Show current date

        date1.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append("From Date: ")
                .append(year).append("-")
                .append(month + 1).append("-").append(day).append(" "));

        date2.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append("To Date: ")
                .append(year).append("-")
                .append(month + 1).append("-").append(day).append(" "));

        // Button listener to show date picker dialog

        date1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // On button click show datepicker dialog
                dateType="from";
                showDatePickerDialog();
            }

        });


        date2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // On button click show datepicker dialog
                dateType="to";
                showDatePickerDialog();


            }

        });

        return  view;
    }



    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
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
            int month1 = month+1;
            String inputTimeStamp = year+"-"+month1+"-"+day;
            System.out.println("dateformat: "+ inputTimeStamp);
            final String inputFormat = "yyyy-MM-dd";
            final String outputFormat = "yyyy-MM-dd";
            try {
                if (dateType.equalsIgnoreCase("from")){
                    date1.setText("From Date: "+ TimeStampConverter(inputFormat, inputTimeStamp, outputFormat));
                }else{
                    date2.setText("To Date: "+TimeStampConverter(inputFormat, inputTimeStamp, outputFormat));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            };
        }
    }

    private static String TimeStampConverter(final String inputFormat,
                                             String inputTimeStamp, final String outputFormat)
            throws ParseException {
        return new SimpleDateFormat(outputFormat).format(new SimpleDateFormat(
                inputFormat).parse(inputTimeStamp));
    }

}
