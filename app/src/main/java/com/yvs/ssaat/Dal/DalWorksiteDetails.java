package com.yvs.ssaat.Dal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.yvs.ssaat.db.DBManager;
import com.yvs.ssaat.pojo.WorkSitePOJO;

import java.util.ArrayList;

/**
 * Created by ravi on 1/2/2018.
 */

public class DalWorksiteDetails {

    public void insertOrUpdateWorksiteDetails(SQLiteDatabase database, String[] data)
    {
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
            newTaskValue.put("work_code", data[6]);
            newTaskValue.put("work_name", data[7]);
            newTaskValue.put("work_name_telugu", data[8]);
            newTaskValue.put("work_location", data[9]);
            newTaskValue.put("work_location_telugu", data[10]);
            newTaskValue.put("task_code", data[11]);
            newTaskValue.put("task_name", data[12]);
            newTaskValue.put("skill_type", data[13]);
            newTaskValue.put("qty_sanc", data[14]);
            newTaskValue.put("amount_sanc", data[15]);
            newTaskValue.put("qty_done", data[16]);
            newTaskValue.put("amount_spent", data[17]);
            newTaskValue.put("audit_is_work_done", data[18]);
            newTaskValue.put("audit_is_work_done_location", data[19]);
            newTaskValue.put("audit_qty_sanc", data[20]);
            newTaskValue.put("audit_amount_sanc", data[21]);
            newTaskValue.put("audit_qty_done", data[22]);
            newTaskValue.put("audit_amount_spent", data[23]);
            newTaskValue.put("audit_remarks", data[24]);
            newTaskValue.put("status", data[25]);
            newTaskValue.put("sent_file_name", data[26]);
            newTaskValue.put("sent_date", data[27]);
            newTaskValue.put("resp_filename", data[28]);
            newTaskValue.put("resp_date", data[29]);
            newTaskValue.put("created_date", data[30]);
            newTaskValue.put("department", data[31]);
            newTaskValue.put("audit_usefull_work", data[32]);


            strWhereClauseValues = data[6]+ "," + data[11];

            res = DBManager.getInstance().updateRecord("worksite", newTaskValue,
                    "work_code=? AND task_code=?", strWhereClauseValues);
            if (res == 0)
            {
                res = DBManager.getInstance().insertRecord("worksite", newTaskValue);
            }
        }
        catch (Exception e)
        {

        }
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


    public ArrayList<WorkSitePOJO> getWorkSiteDetails(String workCode)
    {
        ArrayList<WorkSitePOJO> arrayList = new ArrayList<>();
        Cursor cursorWorksite = null;
        String query = "select ws.Id, ws.district_code,ws.mandal_code, ws.panchayat_code, ws.village_code, ws.habitation_code, \n" +
                "ws.ssaat_code, ws.work_code,ws.work_name, ws.work_name_telugu, ws.work_location, ws.work_location_telugu,\n" +
                "ws.task_code, ws.task_name, ws.skill_type, ws.qty_sanc ,ws.amount_sanc, ws.qty_done, ws.amount_spent, \n" +
                "ws.audit_is_work_done,ws.audit_is_work_done_location, ws.audit_qty_sanc, ws.audit_amount_sanc, \n" +
                "ws.audit_qty_done , ws.audit_amount_spent, ws.audit_remarks, ws.status, ws.sent_file_name, ws.sent_date, ws.resp_filename, \n" +
                "ws.resp_date, ws.created_date, ws.department, ws.audit_usefull_work, \n" +
                "IFNULL(f5a.is_work_done,''), IFNULL(f5a.chechvalues_measurements,''), IFNULL(f5a.chechvalues_total,''), IFNULL(f5a.difference_measurements,'')\n" +
                ", IFNULL(f5a.difference_total,''),IFNULL(f5a.respPersonName,''), IFNULL(f5a.respPersonDesig,''), \n" +
                "IFNULL(f5a.imp_of_work,''), IFNULL(f5a.comments,'') from worksite as ws " +
                "LEFT OUTER JOIN format5A as f5a ON ws.work_code = f5a.work_code AND ws.task_code = f5a.task_code \n" +
                " WHERE ws.work_code = '" + workCode + "'";
        cursorWorksite = DBManager.getInstance().getRawQuery(query);
        arrayList.clear();
        WorkSitePOJO workSitePOJO;
        int count = 1;

        if(cursorWorksite.moveToFirst())
        {
            do {
                arrayList.add(new WorkSitePOJO(String.valueOf(count++),
                        cursorWorksite.getString(1),
                        cursorWorksite.getString(2),
                        cursorWorksite.getString(3),
                        cursorWorksite.getString(4),
                        cursorWorksite.getString(5),
                        cursorWorksite.getString(6),
                        cursorWorksite.getString(7),
                        cursorWorksite.getString(8),
                        cursorWorksite.getString(9),
                        cursorWorksite.getString(10),
                        cursorWorksite.getString(11),
                        cursorWorksite.getString(12),
                        cursorWorksite.getString(13),
                        cursorWorksite.getString(14),
                        cursorWorksite.getString(15),
                        cursorWorksite.getString(16),
                        cursorWorksite.getString(17),
                        cursorWorksite.getString(18),
                        cursorWorksite.getString(19),
                        cursorWorksite.getString(20),
                        cursorWorksite.getString(21),
                        cursorWorksite.getString(22),
                        cursorWorksite.getString(23),
                        cursorWorksite.getString(24),
                        cursorWorksite.getString(25),
                        cursorWorksite.getString(26),
                        cursorWorksite.getString(27),
                        cursorWorksite.getString(28),
                        cursorWorksite.getString(29),
                        cursorWorksite.getString(30),
                        cursorWorksite.getString(31),
                        cursorWorksite.getString(32),
                        cursorWorksite.getString(33),
                        cursorWorksite.getString(34),
                        cursorWorksite.getString(35),
                        cursorWorksite.getString(36),
                        cursorWorksite.getString(37),
                        cursorWorksite.getString(38),
                        cursorWorksite.getString(39),
                        cursorWorksite.getString(40),
                        cursorWorksite.getString(41),
                        cursorWorksite.getString(42)));
            }while (cursorWorksite.moveToNext());
        }

        return arrayList;

    }
}
