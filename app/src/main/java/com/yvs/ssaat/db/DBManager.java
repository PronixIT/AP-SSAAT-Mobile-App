package com.yvs.ssaat.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.yvs.ssaat.common.Constants;
import com.yvs.ssaat.common.SqliteConstants;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

public class DBManager
{
  private AtomicInteger openCounter = new AtomicInteger();

  private static DBManager dbManager;
  private DBhelper dbHelper;
  private SQLiteDatabase database;
  boolean isDbCreated = false;

  public static synchronized void initializeInstance(Context context)
  {
    if (dbManager == null)
    {
      dbManager = new DBManager();
//      dbManager.openCounter.set(0);
//      SQLiteDatabase.loadLibs(context);
      File fileData = new File(Constants.ROOTDIRECTORYPATH + File.separator + Constants.DATABASE_DIRECTORY
              + File.separator + Constants.DATABASE_NAME);
      dbManager.dbHelper = new DBhelper(context, fileData.getAbsolutePath(), null, Constants.DATABASE_VERSION);
      DBManager.getInstance().openDatabase("");
      if (!fileData.exists())
      {
        dbManager.createDataBaseFile();
        dbManager.isDbCreated = true;
      }

    }

    dbManager.createTables();
  }

  private void createDataBaseFile()
  {
    try
    {
      File directory = new File(Constants.ROOTDIRECTORYPATH, Constants.DATABASE_DIRECTORY);
      if (!directory.exists())
      {
        boolean result = directory.mkdir();
      }
      File dbfile = new File(directory.getAbsolutePath(), Constants.DATABASE_NAME);
      boolean result = dbfile.createNewFile();
    }
    catch (Exception ex)
    {
      ex.getMessage();
    }
  }

//  public void existingDatabase()
//  {
//    File directory = new File(Constants.DATABASE_ROOT_PATH, Constants.DATABASE_FOLDER);
//    File dbfile = new File(directory.getAbsolutePath(), Constants.DATABASE_NAME);
//    database = SQLiteDatabase.openDatabase(dbfile.getAbsolutePath(),Constants.DBPASSWORD, null,0);
//  }

  public static synchronized DBManager getInstance()
  {
    if (dbManager == null)
    {
      throw new IllegalStateException(
              DBManager.class.getSimpleName() + " is not initialized, call initializeInstance() method first.");
    }
    return dbManager;
  }

  public synchronized SQLiteDatabase openDatabase(String callerMessage)
  {
    try
    {
//      if (openCounter.incrementAndGet() == 1)
//      {
      database = dbHelper.getReadableDatabase();
        database = dbHelper.getWritableDatabase();


//      }
    }
    catch (SQLiteException e)
    {

      database = dbHelper.getReadableDatabase();

    }
    return database;
  }

  public synchronized void closeDatabase(String callerMessage)
  {
    if (openCounter.decrementAndGet() == 0)
    {
      database.close();

    }

    //SesstionUtils.isSessionCompleted();
    if (openCounter.get() < 0)
      openCounter.set(0);
  }

  public synchronized void forceCloseDatabase()
  {
    // call this method in Gloabal, Uncaught Exception
    openCounter.set(0);
    database.close();
  }

  public synchronized void createTables() // @@BS
  {

    // open database
    SQLiteDatabase database = DBManager.getInstance().openDatabase("createTables");
    try
    {
      // all database operations using the same connection
      if (isDbCreated)
        executeCreateTableScript(database);
      else
      {
        executeCreateTableScript(database);
        executeAlterTableScript(database);
      }
    }
    catch (Exception e)
    {
    }
    finally
    {
      // close database
      DBManager.getInstance().closeDatabase("createTables");
    }
  }

  private void executeCreateTableScript(SQLiteDatabase database)
  {
String doorToDoorTable = "CREATE TABLE IF NOT EXISTS employees (\n" +
        "    id INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
        "    district_code varchar(200) NOT NULL,\n" +
        "    mandal_code varchar(200) NOT NULL,\n" +
        "    panchayat_code varchar(200) NOT NULL,\n" +
        "    village_code varchar(200) NOT NULL,\n" +
        "    habitation_code varchar(200) NOT NULL,\n" +
        "    ssaat_code varchar(200) NOT NULL,\n" +
        "    household_code varchar(200) NOT NULL,\n" +
        "    worker_code varchar(200) NOT NULL,\n" +
        "    surname varchar(200) NOT NULL,\n" +
        "    telugu_surname varchar(200) NOT NULL,\n" +
        "    name varchar(200) NOT NULL,\n" +
        "    telugu_name varchar(200) NOT NULL,\n" +
        "    account_no varchar(200) NOT NULL,\n" +
        "    work_code varchar(200) NOT NULL,\n" +
        "    work_name varchar(200) NOT NULL,\n" +
        "    work_name_telugu varchar(200) NOT NULL,\n" +
        "    work_location varchar(200) NOT NULL,\n" +
        "    work_location_telugu varchar(200) NOT NULL,\n" +
        "    work_progress_code varchar(200) NOT NULL,\n" +
        "    from_date varchar(200) NOT NULL,\n" +
        "    to_date varchar(200) NOT NULL,\n" +
        "    days_worked varchar(200) NOT NULL,\n" +
        "    amount_paid varchar(200) NOT NULL,\n" +
        "    payment_date varchar(200) NOT NULL,\n" +
        "    audit_payslip_date varchar(200) NOT NULL,\n" +
        "    audit_is_passbook_avail varchar(200) NOT NULL,\n" +
        "    audit_is_payslip_issuing varchar(200) NOT NULL,\n" +
        "    audit_is_jobcard_avail varchar(200) NOT NULL,\n" +
        "    audit_days_worked varchar(200) NOT NULL,\n" +
        "    audit_amount_rec varchar(200) NOT NULL,\n" +
        "    audit_remarks varchar(200) NOT NULL,\n" +
        "    status varchar(200) NOT NULL,\n" +
        "    sent_file_name varchar(200) NOT NULL,\n" +
        "    sent_date varchar(200) NOT NULL,\n" +
        "    resp_filename varchar(200) NOT NULL,\n" +
        "    resp_date varchar(200) NOT NULL,\n" +
        "    created_date varchar(200) NOT NULL,\n" +
        "    department varchar(200) NOT NULL,\n" +
        "    muster_id varchar(200) NOT NULL\n" +
        ");";

String worksiteTable = "CREATE TABLE IF NOT EXISTS worksite (\n" +
        "    id INTEGER NOT NULL CONSTRAINT worksite_pk PRIMARY KEY AUTOINCREMENT,\n" +
        "    district_code varchar(200) NOT NULL,\n" +
        "    mandal_code varchar(200) NOT NULL,\n" +
        "    panchayat_code varchar(200) NOT NULL,\n" +
        "    village_code varchar(200) NOT NULL,\n" +
        "    habitation_code varchar(200) NOT NULL,\n" +
        "    ssaat_code varchar(200) NOT NULL,\n" +
        "    work_code varchar(200) NOT NULL,\n" +
        "    work_name varchar(1000) NOT NULL,\n" +
        "    work_name_telugu varchar(1000) NOT NULL,\n" +
        "    work_location varchar(1000) NOT NULL,\n" +
        "    work_location_telugu varchar(1000) NOT NULL,\n" +
        "    task_code varchar(1000) NOT NULL,\n" +
        "    task_name varchar(2000) NOT NULL,\n" +
        "    skill_type varchar(500) NOT NULL,\n" +
        "    qty_sanc varchar(200) NOT NULL,\n" +
        "    amount_sanc varchar(200) NOT NULL,\n" +
        "    qty_done varchar(200) NOT NULL,\n" +
        "    amount_spent varchar(200) NOT NULL,\n" +
        "    audit_is_work_done varchar(200) NOT NULL,\n" +
        "    audit_is_work_done_location varchar(200) NOT NULL,\n" +
        "    audit_qty_sanc varchar(200) NOT NULL,\n" +
        "    audit_amount_sanc varchar(200) NOT NULL,\n" +
        "    audit_qty_done varchar(200) NOT NULL,\n" +
        "    audit_amount_spent varchar(200) NOT NULL,\n" +
        "    audit_remarks varchar(200) NOT NULL,\n" +
        "    status varchar(200) NOT NULL,\n" +
        "    sent_file_name varchar(200) NOT NULL,\n" +
        "    sent_date varchar(200) NOT NULL,\n" +
        "    resp_filename varchar(200) NOT NULL,\n" +
        "    resp_date varchar(200) NOT NULL,\n" +
        "    created_date varchar(200) NOT NULL,\n" +
        "    department varchar(200) NOT NULL,\n" +
        "    audit_usefull_work varchar(2000) NOT NULL\n" +
        ");";

String format4A = "CREATE TABLE IF NOT EXISTS format4A (\n" +
        "    id INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
        "    sno varchar(200) NOT NULL,\n" +
        "    wageSeekerId varchar(200) NOT NULL,\n" +
        "    fullname varchar(200) NOT NULL,\n" +
        "    postBank varchar(200) NOT NULL,\n" +
        "    work_details varchar(200) NOT NULL,\n" +
        "    work_duration varchar(200) NOT NULL,\n" +
        "    payOrderRelDate varchar(200) NOT NULL,\n" +
        "    musterId varchar(200) NOT NULL,\n" +
        "    workedDays varchar(200) NOT NULL,\n" +
        "    amtToBePaid varchar(200) NOT NULL,\n" +
        "    actualWorkedDays varchar(200) NOT NULL,\n" +
        "    actualAmtPaid varchar(200) NOT NULL,\n" +
        "    differenceInAmt varchar(200) NOT NULL,\n" +
        "    isJobCardAvail varchar(200) NOT NULL,\n" +
        "    isPassbookAvail varchar(200) NOT NULL,\n" +
        "    isPayslipIssued varchar(200) NOT NULL,\n" +
        "    respPersonName varchar(200) NOT NULL,\n" +
        "    respPersonDesig varchar(200) NOT NULL,\n" +
        "    categoryone varchar(200) NOT NULL,\n" +
        "    categorytwo varchar(200) NOT NULL,\n" +
        "    categorythree varchar(200) NOT NULL,\n" +
        "    created_date varchar(200) NOT NULL,\n" +
        "    comments varchar(200) NOT NULL,\n" +
        "    created_by varchar(200) NOT NULL,\n" +
        "    modified_date varchar(200) NOT NULL,\n" +
        "    modified_by varchar(200) NOT NULL,\n" +
        "    isActive varchar(200) NOT NULL,\n" +
        "    serverflag varchar(200),\n" +
        "    worker_code varchar(200)\n" +
        ");";
String format5A = "CREATE TABLE IF NOT EXISTS format5A (\n" +
        "    id INTEGER NOT NULL CONSTRAINT format5A_pk PRIMARY KEY AUTOINCREMENT,\n" +
        "    sno varchar(200) NOT NULL,\n" +
        "    work_code varchar(200) NOT NULL,\n" +
        "    work_details varchar(200) NOT NULL,\n" +
        "    task_details varchar(200) NOT NULL,\n" +
        "    technology_type varchar(200) NOT NULL,\n" +
        "    approved_ev_measurements varchar(200) NOT NULL,\n" +
        "    approved_ev_total varchar(200) NOT NULL,\n" +
        "    aspermb_report_measurements varchar(200) NOT NULL,\n" +
        "    aspermb_report_total varchar(200) NOT NULL,\n" +
        "    is_work_done varchar(200) NOT NULL,\n" +
        "    chechvalues_measurements varchar(200) NOT NULL,\n" +
        "    chechvalues_total varchar(200) NOT NULL,\n" +
        "    difference_measurements varchar(200) NOT NULL,\n" +
        "    difference_total varchar(200) NOT NULL,\n" +
        "    respPersonName varchar(200) NOT NULL,\n" +
        "    respPersonDesig varchar(200) NOT NULL,\n" +
        "    imp_of_work varchar(200) NOT NULL,\n" +
        "    comments varchar(200) NOT NULL,\n" +
        "    created_date varchar(200) NOT NULL,\n" +
        "    created_by varchar(200) NOT NULL,\n" +
        "    modified_date varchar(200) NOT NULL,\n" +
        "    modified_by varchar(200) NOT NULL,\n" +
        "    isActive varchar(200) NOT NULL,\n" +
        "    serverflag varchar(200),\n" +
        "    task_code varchar(200) NOT NULL\n" +
        ");";

String userMaster = "CREATE TABLE IF NOT EXISTS " + SqliteConstants.TABLE_USERMASTER + " (\n" +
        "    userid varchar(200),\n" +
        "    username varchar(200) NOT NULL,\n" +
        "    mobile_number varchar(200) NOT NULL,\n" +
        "    email varchar(200) NOT NULL,\n" +
        "    pin varchar(200) NOT NULL,\n" +
        "    designation varchar(200) NOT NULL,\n" +
        "    created_datetime varchar(200) NOT NULL\n" +
        ");";


    try
    {
      database.execSQL(doorToDoorTable);
      database.execSQL(worksiteTable);
      database.execSQL(format4A);
      database.execSQL(format5A);
      database.execSQL(userMaster);


    }

    catch (Exception e)
    {
    }
  }

  private void executeAlterTableScript(SQLiteDatabase database)
  {

//    alterTableAddColumn(database, Constants.TABLE_COLLECTIONINFO, "ExportConfirm");


  }

  public void alterTableAddColumn(SQLiteDatabase database, String tableName, String column)
  {
    if (!isColumnExisis(database, tableName, column))
    {
      String alterSql = "ALTER TABLE " + tableName + " ADD COLUMN " + column + " TEXT";
      try
      {
        database.execSQL(alterSql);
      }
      catch (Exception e)
      {
      }
    }
  }

  public boolean isColumnExisis(SQLiteDatabase database, String tableName, String columnName)
  {
    boolean result = false;
    Cursor cursor = null;
    try
    {
      // PRAGMA table_info('tableName') the second one is the column name
      String sql = "SELECT " + columnName + " FROM " + tableName + " LIMIT 1";
      cursor = database.rawQuery(sql, null);
      if (cursor != null)
      {
        if (cursor.getCount() > -1)
          result = true;
      }
    }
    catch (Exception e)
    {
    }
    finally
    {
      if (cursor != null)
        cursor.close();
    }
    return result;
  }

  public long insertRecord(SQLiteDatabase database, String tableName, ContentValues newTaskValue)
  {
    long result = -1;
    try
    {
      result = database.insertOrThrow(tableName, null, newTaskValue);
    }
    catch (Exception e)
    {
    }
    return result;
  }

  public long insertRecord(String tableName, ContentValues newTaskValue)
  {
    long result = -1;
    // open database
//    SQLiteDatabase database = DBManager.getInstance().openDatabase("insertRecord");
    try
    {
      result = database.insertOrThrow(tableName, null, newTaskValue);
    }
    catch (SQLiteException e)
    {
      e.getMessage();
    }
    catch (Exception e)
    {
    }
    finally
    {
      // close database
//      DBManager.getInstance().closeDatabase("insertRecord");
    }
    return result;
  }

  // all insert, update & delete - where ever execSQL called
  // this method expects a open database connection
  public boolean executeQuery(SQLiteDatabase database, String query)
  {
    boolean result = false;
    try
    {
      database.execSQL(query);
      result = true;
    }
    catch (SQLiteException e)
    {
    }
    return result;
  }

  // all insert, update & delete - where ever execSQL called
  public boolean executeQuery(String query)
  {
    boolean result = false;
    // open database
    SQLiteDatabase database = DBManager.getInstance().openDatabase("executeQuery");
    try
    {
      database.execSQL(query);
      result = true;
    }
    catch (SQLiteException e)
    {
    }
    finally
    {
      // close database
      DBManager.getInstance().closeDatabase("executeQuery");
    }
    return result;
  }

  public String getScalar(SQLiteDatabase database, String query)
  {
    String result = "";
    Cursor cursor = null;
    try
    {
      cursor = database.rawQuery(query, null);
      if (cursor.moveToFirst())
        result = cursor.getString(0);
      // result=(isIntValue)?c.getInt(0)+"":c.getString(0);
    }
    catch (Exception e)
    {
    }
    finally
    {
      if (cursor != null)
        cursor.close();
    }
    return result;
  }

  public String getScalar(String query)
  {
    String result = "";
    Cursor cursor = null;
    // open database
    SQLiteDatabase database = DBManager.getInstance().openDatabase("getScalar");
    try
    {
      try
      {
        cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst())
          // result=(isIntValue)?c.getInt(0)+"":c.getString(0);
          result = cursor.getString(0);
      }
      catch (Exception e)
      {
      }
      finally
      {
        if (cursor != null)
          cursor.close();
      }
    }
    catch (SQLiteException e)
    {
    }
    finally
    {
      // close database
      DBManager.getInstance().closeDatabase("getScalar");
    }
    return result;
  }

  public int updateRecord(SQLiteDatabase database, String tableName, ContentValues contentValues, String whereClause,
                          String whereArgs)
  {
    int iResult = 0;
    String[] strWhereArgs = whereArgs.split(",");
    try
    {
      iResult = database.update(tableName, contentValues, whereClause, strWhereArgs);
    }
    catch (Exception e)
    {
    }
    return iResult;
  }

  public int updateRecord(String tableName, ContentValues contentValues, String whereClause, String whereArgs)
  {
    int iResult = 0;
    String[] strWhereArgs = whereArgs.split(",");
    SQLiteDatabase database = DBManager.getInstance().openDatabase("updateRecord");
    try
    {
      try
      {
        iResult = database.update(tableName, contentValues, whereClause, strWhereArgs);
      }
      catch (Exception e)
      {
      }
    }
    catch (SQLiteException e)
    {
    }
    finally
    {
      // close database
      DBManager.getInstance().closeDatabase("updateRecord");
    }

    return iResult;
  }

  public Cursor getRawQuery(SQLiteDatabase database, String query)
  {
    return database.rawQuery(query, null);
  }

  public Cursor getRawQuery(String query)
  {
    return database.rawQuery(query, null);
  }

}
