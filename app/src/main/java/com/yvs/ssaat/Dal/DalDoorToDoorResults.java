package com.yvs.ssaat.Dal;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.yvs.ssaat.db.DBManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ravi on 1/2/2018.
 */

public class DalDoorToDoorResults {

    public long insertOrUpdateDoorToDoorResultData(SQLiteDatabase database, String sNo, String wageSeekerId, String workerCode,String fullName,
                                                   String postBank, String workDetails, String workDuration, String payOrderRelDate,
                                                   String musterId, String workedDays, String amtToBePaid, String actualWorkedDays,
                                                   String actualAmtPaid, String differenceInAmt, String isJobCardAvail, String isPassbookAvail,
                                                   String isPayslipIssued, String resPersonName, String resPersonDesig, String categoryOne, String categoryTwo,
                                                   String categoryThree, String createDate, String comments, String createdBy, String modifiedDate,
                                                   String modifiedBy, String isActive) {
        long res = -1;
        String strWhereClauseValues = "";
        try {
            ContentValues newTaskValue = new ContentValues();
            newTaskValue.put("sno", sNo);
            newTaskValue.put("wageSeekerId", wageSeekerId.trim());
            newTaskValue.put("worker_code", workerCode.trim());
            newTaskValue.put("fullname", fullName);
            newTaskValue.put("postBank", postBank);
            newTaskValue.put("work_details", workDetails);
            newTaskValue.put("work_duration", workDuration);
            newTaskValue.put("payOrderRelDate", payOrderRelDate);
            newTaskValue.put("musterId", musterId);
            newTaskValue.put("workedDays", workedDays);
            newTaskValue.put("amtToBePaid", amtToBePaid);
            newTaskValue.put("actualWorkedDays", actualWorkedDays);
            newTaskValue.put("actualAmtPaid", actualAmtPaid);
            newTaskValue.put("differenceInAmt", differenceInAmt);
            newTaskValue.put("isJobCardAvail", isJobCardAvail);
            newTaskValue.put("isPassbookAvail", isPassbookAvail);
            newTaskValue.put("isPayslipIssued", isPayslipIssued);
            newTaskValue.put("respPersonName", resPersonName);
            newTaskValue.put("respPersonDesig", resPersonDesig);
            newTaskValue.put("categoryone", categoryOne);
            newTaskValue.put("categorytwo", categoryTwo);
            newTaskValue.put("categorythree", categoryThree);
            newTaskValue.put("created_date", createDate);
            newTaskValue.put("comments", comments);
            newTaskValue.put("modified_date", modifiedDate);
            newTaskValue.put("modified_by", modifiedBy);
            newTaskValue.put("isActive", isActive);


            strWhereClauseValues = wageSeekerId.trim() + "," + musterId;

            res = DBManager.getInstance().updateRecord( "format4A", newTaskValue,
                    "wageSeekerId=? " +
                            "AND musterId=?", strWhereClauseValues);
            if (res == 0) {
                newTaskValue.put("created_date", createDate);
                newTaskValue.put("created_by", createdBy);
                res = DBManager.getInstance().insertRecord("format4A", newTaskValue);
            }
        } catch (Exception e) {

        }
        return res;
    }

}
