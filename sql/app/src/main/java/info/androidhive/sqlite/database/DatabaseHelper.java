package info.androidhive.sqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.sqlite.database.model.Guarantee;

/**
 * Created by ravi on 15/03/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "guarantees_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Guarantee.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Guarantee.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertNote(String note, String to_dt, String from_dt) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Guarantee.COLUMN_NOTE, note);
        values.put(Guarantee.COLUMN_FROM_DT, to_dt);
        values.put(Guarantee.COLUMN_TO_DT, from_dt);
        long id = db.insert(Guarantee.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public Guarantee getNote(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Guarantee.TABLE_NAME,
                new String[]{Guarantee.COLUMN_ID, Guarantee.COLUMN_NOTE, Guarantee.COLUMN_TIMESTAMP, Guarantee.COLUMN_FROM_DT, Guarantee.COLUMN_TO_DT},
                Guarantee.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare guarantee object
        Guarantee guarantee = new Guarantee(
                cursor.getInt(cursor.getColumnIndex(Guarantee.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Guarantee.COLUMN_NOTE)),
                cursor.getString(cursor.getColumnIndex(Guarantee.COLUMN_TIMESTAMP)),
                cursor.getString(cursor.getColumnIndex(Guarantee.COLUMN_FROM_DT)),
                cursor.getString(cursor.getColumnIndex(Guarantee.COLUMN_TO_DT)));

        // close the db connection
        cursor.close();

        return guarantee;
    }

    public List<Guarantee> getAllNotes() {
        List<Guarantee> guarantees = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Guarantee.TABLE_NAME + " ORDER BY " +
                Guarantee.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Guarantee guarantee = new Guarantee();
                guarantee.setId(cursor.getInt(cursor.getColumnIndex(Guarantee.COLUMN_ID)));
                guarantee.setNote(cursor.getString(cursor.getColumnIndex(Guarantee.COLUMN_NOTE)));
                guarantee.setTimestamp(cursor.getString(cursor.getColumnIndex(Guarantee.COLUMN_TIMESTAMP)));
                guarantee.setTimestamp(cursor.getString(cursor.getColumnIndex(Guarantee.COLUMN_FROM_DT)));
                guarantee.setTimestamp(cursor.getString(cursor.getColumnIndex(Guarantee.COLUMN_TO_DT)));

                guarantees.add(guarantee);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return guarantees list
        return guarantees;
    }

    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + Guarantee.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateNote(Guarantee guarantee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Guarantee.COLUMN_NOTE, guarantee.getNote());

        // updating row
        return db.update(Guarantee.TABLE_NAME, values, Guarantee.COLUMN_ID + " = ?",
                new String[]{String.valueOf(guarantee.getId())});
    }

    public void deleteNote(Guarantee guarantee) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Guarantee.TABLE_NAME, Guarantee.COLUMN_ID + " = ?",
                new String[]{String.valueOf(guarantee.getId())});
        db.close();
    }
}
