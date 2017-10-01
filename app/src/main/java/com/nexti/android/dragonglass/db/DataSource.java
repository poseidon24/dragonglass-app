package com.nexti.android.dragonglass.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.nexti.android.dragonglass.config.Configuration;

import java.io.File;

/**
 * Created by ISCesar on 01/08/2017.
 */
public class DataSource {
    private SQLiteDatabase db;
    private MySQLiteOpenHelper dbHelper;

    private static Configuration appConfig = new Configuration();
    // Database Name
    private static String DATABASE_NAME = "";
    // Database path (mnt/sdcard/fens/micro/)
    private static String DATABASE_PATH = "";

    public DataSource(Context context) {
        if (appConfig.getBdName() != null)
            DATABASE_NAME = appConfig.getBdName();

        if (appConfig.getRepositorioBD() != null)
            DATABASE_PATH = appConfig.getRepositorioBD() + File.separator
                    + DATABASE_NAME;

        dbHelper = new MySQLiteOpenHelper(context, DATABASE_PATH);
    }

    public void open() {
        open(false);
    }

    public void open(boolean readOnly) {
        db = readOnly ? dbHelper.getReadableDatabase() : dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
        dbHelper.close();
    }

    public SQLiteDatabase getDb() {
        return db;
    }

}