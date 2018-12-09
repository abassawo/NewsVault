package com.abasscodes.newsy.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
  private static final int VERSION = 1;
  private static final String FAVORITE_ARTICLES_TABLE = DBSchema.NAME;
  private static final String DATABASE_NAME = "NEWSVAULT_DATABASE";

  public DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(initTable(FAVORITE_ARTICLES_TABLE));
  }

  @SuppressWarnings("UnnecessaryLocalVariable")
  public String initTable(String tableName) {
    return "create table " + tableName + "(" +
      "_id integer primary key autoincrement, " +
      DBSchema.Cols._ID + ", " +
      DBSchema.Cols.TITLE + ", " +
      DBSchema.Cols.URL + ", " +
      DBSchema.Cols.IMAGE_URL + ")";
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + FAVORITE_ARTICLES_TABLE);
    db.execSQL(initTable(FAVORITE_ARTICLES_TABLE));
  }
}