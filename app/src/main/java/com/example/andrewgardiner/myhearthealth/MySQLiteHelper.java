package com.example.andrewgardiner.myhearthealth;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Andrew gardiner on 26/02/2016.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_DATA = "data";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_BPM = "bpm";
    public static final String COLUMN_DIASTOLICBP = "diastolic_bp";
    public static final String COLUMN_SYSTOLICBP = "systolic_bp";
    public static final String COLUMN_BLOOD_GLUCOSE = "blood_glucose";
    public static final String COLUMN_WEIGHT = "weight";

    private static final String DATABASE_NAME = "data.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_DATA + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_BPM
            + " integer not null,"+ COLUMN_SYSTOLICBP +" integer not null,"+COLUMN_DIASTOLICBP+ " integer not null,"
            + COLUMN_BLOOD_GLUCOSE + " integer not null," + COLUMN_WEIGHT + " integer not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        onCreate(db);
    }

}
