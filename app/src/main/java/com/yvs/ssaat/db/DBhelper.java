package com.yvs.ssaat.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper
{

  public DBhelper(Context context, String name, CursorFactory factory, int version)
  {
    super(context, name, factory, version);
  }

  @Override
  public void onCreate(SQLiteDatabase arg0)
  {
    // onCreate() is only run when the database file did not exist and was just
    // created.

  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
  {
    // onUpgrade() is only called when the database file exists but the stored
    // version number is lower than requested in constructor.
    // should implement data migration

  }

}