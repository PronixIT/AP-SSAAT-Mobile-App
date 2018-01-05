package com.yvs.ssaat.Dal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yvs.ssaat.common.Constants;
import com.yvs.ssaat.common.SqliteConstants;
import com.yvs.ssaat.db.DBManager;

/**
 * Created by ravi on 1/3/2018.
 */

public class DalUserMaster {

    public long saveUserDetails(SQLiteDatabase database, String userId, String userName, String mobileNumber, String email, String pin, String designation, String timeStamp)
    {
        long res = -1;
        try {
            ContentValues newTaskValue = new ContentValues();
            newTaskValue.put("userid", userId);
            newTaskValue.put("username", userName);
            newTaskValue.put("mobile_number",mobileNumber);
            newTaskValue.put("email",email);
            newTaskValue.put("pin",pin);
            newTaskValue.put("designation",designation);
            newTaskValue.put("created_datetime",timeStamp);

            res = DBManager.getInstance().insertRecord(SqliteConstants.TABLE_USERMASTER, newTaskValue);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return res;
    }

    public String getPin(SQLiteDatabase database, String enteredPin)
    {
        String query = "SELECT pin FROM " + SqliteConstants.TABLE_USERMASTER + " WHERE pin = '" + enteredPin+"'";
        return DBManager.getInstance().getScalar(query);

    }

    public String getMobile(SQLiteDatabase database, String enteredPin)
    {
        String query = "SELECT pin FROM " + SqliteConstants.TABLE_USERMASTER + " WHERE pin = '" + enteredPin+"'";
        return DBManager.getInstance().getScalar(query);

    }

    public void getUserDetails(SQLiteDatabase database, String pin)
    {
        Cursor c = null;
        try {

            String query = "SELECT userid, username, mobile_number, email, pin, designation FROM " + SqliteConstants.TABLE_USERMASTER
                    + " WHERE pin = '" + pin + "'";
            c = DBManager.getInstance().getRawQuery(query);
            if (c.moveToFirst()) {
                Constants.userMasterDO.userId = c.getString(0);
                Constants.userMasterDO.userName = c.getString(1);
                Constants.userMasterDO.mobileNumber = c.getString(2);
                Constants.userMasterDO.email = c.getString(3);
                Constants.userMasterDO.designation = c.getString(5);
            }

        }
        catch (Exception e)
        {
            e.getMessage();
        }
        finally {
            c.close();
        }

    }

    public String getScalar(String s, SQLiteDatabase database) {
        String result = "";
        try {
            Cursor c = database.rawQuery(s, null);
            c.moveToFirst();
            // result=(isIntValue)?c.getInt(0)+"":c.getString(0);
            result = c.getString(0);
            c.close();
        } catch (Exception e) {
            // HANDLE THIS;
        }
        return result;
    }
}
