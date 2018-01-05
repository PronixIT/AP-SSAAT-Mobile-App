package com.yvs.ssaat.Dal;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.yvs.ssaat.db.DBManager;

/**
 * Created by ravi on 1/2/2018.
 */

public class DalWorksiteResults {
    public long insertOrUpdateWorksiteResultData(SQLiteDatabase database, String sNo, String workCode, String workDetails, String taskDetails,
                                                 String technologyType, String approvedEvMeasurement, String approvedevTotal, String aspermbReportMeadurement,
                                                 String asperReporttotal, String isWorkDone, String chekValuesMeasurement, String checkValuesTotal, String differenceMeasurements,
                                                 String differenceTotal, String respPersonName, String respPersonDesign, String impOfWork, String comments, String createdDate,
                                                 String createdBy, String modifiedDate, String modifiedBy, String isActive, String taskCode)
    {
        long res = -1;
        String strWhereClauseValues = "";
        try
        {
            ContentValues newTaskValue = new ContentValues();
            newTaskValue.put("sno", sNo);
            newTaskValue.put("work_code", workCode);
            newTaskValue.put("work_details", workDetails);
            newTaskValue.put("task_details", taskDetails);
            newTaskValue.put("technology_type", technologyType);
            newTaskValue.put("approved_ev_measurements", approvedEvMeasurement);
            newTaskValue.put("approved_ev_total", approvedevTotal);
            newTaskValue.put("aspermb_report_measurements", aspermbReportMeadurement);
            newTaskValue.put("aspermb_report_total", asperReporttotal);
            newTaskValue.put("is_work_done", isWorkDone);
            newTaskValue.put("chechvalues_measurements", chekValuesMeasurement);
            newTaskValue.put("chechvalues_total", checkValuesTotal);
            newTaskValue.put("difference_measurements", differenceMeasurements);
            newTaskValue.put("difference_total", differenceTotal);
            newTaskValue.put("respPersonName", respPersonName);
            newTaskValue.put("respPersonDesig", respPersonDesign);
            newTaskValue.put("imp_of_work", impOfWork);
            newTaskValue.put("comments", comments);

            newTaskValue.put("modified_date", modifiedDate);
            newTaskValue.put("modified_by", modifiedBy);
            newTaskValue.put("isActive", isActive);
            newTaskValue.put("task_code", taskCode);

            strWhereClauseValues = workCode + "," + taskCode;

            res = DBManager.getInstance().updateRecord("format5A", newTaskValue,
                    "work_code=? AND task_code=? " , strWhereClauseValues);
            if (res == 0)
            {
                newTaskValue.put("created_date", createdDate);
                newTaskValue.put("created_by", createdBy);
                res = DBManager.getInstance().insertRecord("format5A",  newTaskValue);
            }
        }
        catch (Exception e)
        {

        }
        return res;
    }



}
