package com.yvs.ssaat.Dal;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.yvs.ssaat.db.DBManager;

/**
 * Created by ravi on 1/2/2018.
 */

public class DalDoorToDoorDetails {


    public void insertOrUpdateDoorToDoorData(String[] data) {

        long res = -1;
        String strWhereClauseValues = "";
        try
        {

            ContentValues newTaskValue = new ContentValues();
            newTaskValue.put("district_code", data[0]);
            newTaskValue.put("mandal_code", data[1]);
            newTaskValue.put("panchayat_code", data[2]);
            newTaskValue.put("village_code", data[3]);
            newTaskValue.put("habitation_code", data[4]);
            newTaskValue.put("ssaat_code", data[5]);
            newTaskValue.put("household_code", data[6]);
            newTaskValue.put("worker_code", data[7]);
            newTaskValue.put("surname", data[8]);
            newTaskValue.put("telugu_surname", data[9]);
            newTaskValue.put("name", data[10]);
            newTaskValue.put("telugu_name", data[11]);
            newTaskValue.put("account_no", data[12]);
            newTaskValue.put("work_code", data[13]);
            newTaskValue.put("work_name", data[14]);
            newTaskValue.put("work_name_telugu", data[15]);
            newTaskValue.put("work_location", data[16]);
            newTaskValue.put("work_location_telugu", data[17]);
            newTaskValue.put("work_progress_code", data[18]);
            newTaskValue.put("from_date", data[19]);
            newTaskValue.put("to_date", data[20]);
            newTaskValue.put("days_worked", data[21]);
            newTaskValue.put("amount_paid", data[22]);
            newTaskValue.put("payment_date", data[23]);
            newTaskValue.put("audit_payslip_date", data[24]);
            newTaskValue.put("audit_is_passbook_avail", data[25]);
            newTaskValue.put("audit_is_payslip_issuing", data[26]);
            newTaskValue.put("audit_is_jobcard_avail", data[27]);
            newTaskValue.put("audit_days_worked", data[28]);
            newTaskValue.put("audit_amount_rec", data[29]);
            newTaskValue.put("audit_remarks", data[30]);
            newTaskValue.put("status", data[31]);
            newTaskValue.put("sent_file_name", data[32]);
            newTaskValue.put("sent_date", data[33]);
            newTaskValue.put("resp_filename", data[34]);
            newTaskValue.put("resp_date", data[35]);
            newTaskValue.put("created_date", data[36]);
            newTaskValue.put("department", data[37]);
            newTaskValue.put("muster_id", data[38]);


            strWhereClauseValues = data[6] + "," + data[7] + "," + data[13] + ","
                    + data[19] + "," + data[20] + "," + data[38];

            res = DBManager.getInstance().updateRecord("employees", newTaskValue,
                    "household_code=? " +
                            "AND worker_code=?  AND work_code=? AND from_date=? AND to_date=? AND muster_id=?", strWhereClauseValues);
            if (res == 0)
            {
                res = DBManager.getInstance().insertRecord("employees",  newTaskValue);
            }
        }
        catch (Exception e)
        {

        }
//        database.update("worksite", )
    }

    public int updateRecord(SQLiteDatabase database, String tableName, ContentValues contentValues, String whereClause, String whereArgs)
    {
        int iResult = 0;
        String[] strWhereArgs = whereArgs.split(",");
        try
        {
            try
            {
                iResult = database.update(tableName, contentValues, whereClause, strWhereArgs);
            }
            catch (Exception e)
            {
//                AndroidUtils.logMsg("DBManager.updateRecord(): TableName: " + tableName + " " + e.getMessage());
            }
        }
        catch (SQLiteException e)
        {
//            AndroidUtils.logMsg("DBManager.getScalar(): " + e.getMessage());
        }

        return iResult;
    }
}
