package com.example.andrewgardiner.myhearthealth;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew gardiner on 26/02/2016.
 */
public class DatabaseManager {
    private SQLiteDatabase database;
    private MySQLiteHelper helper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID,MySQLiteHelper.COLUMN_BPM,MySQLiteHelper.COLUMN_SYSTOLICBP, MySQLiteHelper.COLUMN_DIASTOLICBP,
            MySQLiteHelper.COLUMN_BLOOD_GLUCOSE, MySQLiteHelper.COLUMN_WEIGHT};

    public DatabaseManager(Context context){
        helper = new MySQLiteHelper(context);

    }
    public void open() throws android.database.SQLException {
        database = helper.getWritableDatabase();

    }
    public void close(){
        helper.close();
    }

    public Profile createProfile(int bpm, int systalic, int distalic, int bloodglucose, int weight){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_BPM, bpm);
        values.put(MySQLiteHelper.COLUMN_SYSTOLICBP, systalic);
        values.put(MySQLiteHelper.COLUMN_DIASTOLICBP, distalic);
        values.put(MySQLiteHelper.COLUMN_BLOOD_GLUCOSE, bloodglucose);
        values.put(MySQLiteHelper.COLUMN_WEIGHT, weight);
        System.out.println(bloodglucose +  " blood gluc");
        long insertId = database.insert(MySQLiteHelper.TABLE_DATA, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_DATA, allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId,null,null,null,null);
        cursor.moveToFirst();
        Profile newProfile = cursorToProfile(cursor);
        cursor.close();

        System.out.println(newProfile.getBlood_glucose() +  " New profile bl");
        return newProfile;



    }
    public void deleteAllrecords(){
        String delete = "DELETE  FROM " + MySQLiteHelper.TABLE_DATA;
        helper.getWritableDatabase().execSQL(delete);
    }
    public List<Profile> allData(){
        List<Profile> profiles = new ArrayList<Profile>();
        String query = "SELECT * FROM " + MySQLiteHelper.TABLE_DATA;
      // Cursor cursor = database.query(MySQLiteHelper.TABLE_DATA, allColumns, null, null, null, null, null);
       // cursor.moveToFirst();
        SQLiteDatabase db  = helper.getReadableDatabase();
        Cursor cursor    = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                Profile profile = cursorToProfile(cursor);
                profiles.add(profile);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return profiles;
    }
    public Profile getPrevious(){
        String select = "SELECT * FROM " + MySQLiteHelper.TABLE_DATA + " WHERE " +  MySQLiteHelper.COLUMN_ID + " = (SELECT MAX(" + MySQLiteHelper.COLUMN_ID + ") FROM " + MySQLiteHelper.TABLE_DATA + ")";
        Cursor cursor = helper.getWritableDatabase().rawQuery(select, null);
        cursor.moveToFirst();
        Profile profile = cursorToProfile(cursor);

        return profile;


    }

    private Profile cursorToProfile(Cursor cursor){
        Profile profile = new Profile();
        profile.setId(cursor.getInt(0));
        profile.setBpm(cursor.getInt(1));
        profile.setSystalicBP(cursor.getInt(2));
        profile.setDistolicBP(cursor.getInt(3));
        profile.setBlood_glucose(cursor.getInt(4));
        profile.setWeight(cursor.getInt(5));
        return profile;

    }
}
