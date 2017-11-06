package com.nexti.android.dragonglass.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nexti.android.dragonglass.db.qry.DgMobileDbUtils;
import com.nexti.android.dragonglass.db.qry.UserSessionDbUtils;

/**
 * Created by ISCesar on 01/08/2017.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper{

    private static final String TAG = MySQLiteOpenHelper.class.getSimpleName();

    private final static int DB_VERSION = 2;

    public MySQLiteOpenHelper(Context context, String dbName) {
        super(context, dbName, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(UserSessionDbUtils.CREATE_TABLE);
            db.execSQL(DgMobileDbUtils.CREATE_TABLE);
            try{
                db.execSQL(UserSessionDbUtils.INSERT_INIT_DATA);
                db.execSQL(DgMobileDbUtils.INSERT_INIT_DATA);
            }catch(Exception e){
                Log.e(TAG, e.toString());
            }
        }catch(Exception ex){
            Log.e(TAG, ex.toString());
        }
    }

    /**
     * Notice the missing break statement in case 1 and 2. This is what I mean by incremental upgrade.
     * Say if the old version is 2 and new version is 4, then the logic will upgrade the database from 2 to 3 and then to 4
     * If old version is 3 and new version is 4, it will just run the upgrade logic for 3 to 4
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //onCreate(db);

        Log.i(TAG, "Updating Database from version: " + oldVersion + " to: " + newVersion);
        switch(oldVersion) {
            case 1:     //upgrade logic from version 1 to 2
                try{
                    db.execSQL(DgMobileDbUtils.CREATE_TABLE);
                    db.execSQL(DgMobileDbUtils.INSERT_INIT_DATA);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            //case 2:   //upgrade logic from version 2 to 3
            //case 3:   //upgrade logic from version 3 to 4
                break; //keep the break at the end of last case expected, just before Default
            default:
                throw new IllegalStateException(
                        "onUpgrade() with unknown oldVersion " + oldVersion);
        }

    }

}
