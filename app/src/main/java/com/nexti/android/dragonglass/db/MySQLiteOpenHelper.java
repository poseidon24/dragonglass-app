package com.nexti.android.dragonglass.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nexti.android.dragonglass.db.qry.UserSession;

/**
 * Created by ISCesar on 01/08/2017.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper{

    private final static int DB_VERSION = 1;

    public MySQLiteOpenHelper(Context context, String dbName) {
        super(context, dbName, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(UserSession.CREATE_TABLE);
            try{
                db.execSQL(UserSession.INSERT_INIT_DATA);
            }catch(Exception e){
                Log.e(this.getClass().getSimpleName(), e.toString());
            }
        }catch(Exception ex){
            Log.e(this.getClass().getSimpleName(), "" + ex.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);

        Log.d(this.getClass().getSimpleName(), "Updating Database from version: " + oldVersion + " to: " + newVersion);
        if (newVersion>=2){
            try{
                //db.execSQL(Concepto.AL_TABLE_CONCEPTO_V2_1);
                //db.execSQL(Concepto.AL_TABLE_CONCEPTO_V2_2);
            } catch (Exception e) {
                Log.e(this.getClass().getSimpleName(), e.toString());
            }
        }

    }

}
