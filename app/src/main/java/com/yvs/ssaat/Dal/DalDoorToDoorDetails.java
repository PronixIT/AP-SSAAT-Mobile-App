package com.yvs.ssaat.Dal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.yvs.ssaat.db.DBManager;
import com.yvs.ssaat.pojo.Worker;

import java.util.ArrayList;

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

    public ArrayList<Worker> getDoorToDoorDetails(String householdCode)
    {
        ArrayList<Worker> arrayList = new ArrayList<>();
        Cursor cursorEmployees = null;
            String query = "SELECT EM.id, EM.district_code, EM.mandal_code, EM.panchayat_code, EM.village_code, EM.habitation_code, \n" +
                "EM.ssaat_code, EM.household_code, EM.worker_code, EM.surname,EM.telugu_surname, EM.name, EM.telugu_name, EM.account_no,\n" +
                "EM.work_code, EM.work_name, EM.work_name_telugu, EM.work_location, EM.work_location_telugu, EM.work_progress_code, \n" +
                "EM.from_date, EM.to_date, EM.days_worked, EM.amount_paid, EM.payment_date, EM.audit_payslip_date, \n" +
                "EM.audit_is_passbook_avail, EM.audit_is_payslip_issuing, EM.audit_is_jobcard_avail, EM.audit_days_worked, \n" +
                "EM.audit_amount_rec, EM.audit_remarks, EM.status, EM.sent_file_name, EM.sent_date, EM.resp_filename, EM.resp_date, EM.created_date, EM.department, EM.muster_id,\n" +
                "IFNULL(f4a.actualWorkedDays,''), IFNULL(f4a.actualAmtPaid,''), IFNULL(f4a.differenceInAmt,''), IFNULL(f4a.isJobCardAvail,''), IFNULL(f4a.isPassbookAvail,''), IFNULL(f4a.isPayslipIssued,''),\n" +
                "IFNULL(f4a.respPersonName,''), IFNULL(f4a.respPersonDesig,''), IFNULL(f4a.categoryone,''), IFNULL(f4a.categorytwo,''), " +
                "IFNULL(f4a.categorythree,''), IFNULL(f4a.comments,'')\n" +
                " FROM employees AS EM LEFT OUTER JOIN format4A AS f4a ON EM.household_code = IFNULL(f4a.wageSeekerId,'') \n" +
                " AND EM.muster_id = IFNULL(f4a.musterId,'')  WHERE EM.household_code IN ('"+ householdCode +"')";
        cursorEmployees = DBManager.getInstance().getRawQuery(query);
        arrayList.clear();
        int count = 1;
        if(cursorEmployees.moveToFirst())
        {
            do {
                arrayList.add(new Worker(
                        String.valueOf(count++),
                        cursorEmployees.getString(1),
                        cursorEmployees.getString(2),
                        cursorEmployees.getString(3),
                        cursorEmployees.getString(4),
                        cursorEmployees.getString(5),
                        cursorEmployees.getString(6),
                        cursorEmployees.getString(7),
                        cursorEmployees.getString(8),
                        cursorEmployees.getString(9),
                        cursorEmployees.getString(11),
                        cursorEmployees.getString(13),
                        cursorEmployees.getString(14),
                        cursorEmployees.getString(15),
                        cursorEmployees.getString(17),
                        cursorEmployees.getString(19),
                        cursorEmployees.getString(20),
                        cursorEmployees.getString(21),
                        cursorEmployees.getString(22),
                        cursorEmployees.getString(23),
                        cursorEmployees.getString(24),
                        cursorEmployees.getString(25),
                        cursorEmployees.getString(39),
                        cursorEmployees.getString(40),
                        cursorEmployees.getString(41),
                        cursorEmployees.getString(42),
                        cursorEmployees.getString(43),
                        cursorEmployees.getString(44),
                        cursorEmployees.getString(45),
                        cursorEmployees.getString(46),
                        cursorEmployees.getString(47),
                        cursorEmployees.getString(48),
                        cursorEmployees.getString(49),
                        cursorEmployees.getString(50),
                        cursorEmployees.getString(51)));
            }while (cursorEmployees.moveToNext());
        }
        return arrayList;
    }
}
