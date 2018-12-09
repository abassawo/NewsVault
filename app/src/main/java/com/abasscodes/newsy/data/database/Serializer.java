package com.abasscodes.newsy.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Serializer {
    private static Serializer instance;
    private SQLiteDatabase mDatabase;

    public static Serializer getInstance(SQLiteDatabase db) {
        if(instance == null) {
            instance = new Serializer(db);
        }
        return instance;
    }


    private Serializer(SQLiteDatabase db) {
        this.mDatabase = db;
    }

    private static ContentValues getContentValuesfromFaves(SerializableArticle item) {
        ContentValues cv = new ContentValues();
        cv.put(DBSchema.Cols._ID, item.getId());
        cv.put(DBSchema.Cols.TITLE, item.getTitle());
        cv.put(DBSchema.Cols.URL, item.getUrl());
        cv.put(DBSchema.Cols.IMAGE_URL, item.getImageUrl());
        return cv;
    }


    public HashSet<SerializableArticle> getFaves() {
        HashSet<SerializableArticle> items = new HashSet<>();
        ArticleCursorWrapper cursor = queryImages(DBSchema.NAME, null, null);
        if (cursor.getCount() <= 0) return items;
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                items.add(cursor.getFaveItem());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return items;
    }


    public void addFavorite(SerializableArticle item) {
        ContentValues cv = getContentValuesfromFaves(item);
        mDatabase.insert(DBSchema.NAME, null, cv);
    }


    public boolean hasFaves() {
        return getFaves().size() > 0;
    }


    public ArticleCursorWrapper queryImages(String tableName, String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                tableName,
                null,   //select all columns
                whereClause,
                whereArgs,
                null, //group by
                null,
                null);
        return new ArticleCursorWrapper(cursor);
    }

    public static class ArticleCursorWrapper extends CursorWrapper {
        public ArticleCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public SerializableArticle getFaveItem() {
            String id = getString(getColumnIndex(DBSchema.Cols._ID));
            String title = getString(getColumnIndex(DBSchema.Cols.TITLE));
            String url = getString(getColumnIndex(DBSchema.Cols.URL));
            String imageUrl = getString(getColumnIndex(DBSchema.Cols.IMAGE_URL));
            return new SerializableArticle(id, title, url, imageUrl);
        }
    }
}