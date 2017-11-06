package com.nexti.android.dragonglass.db;

import android.content.ContentProvider;
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

    public DataSource(Context context) {
        String dbFullPath = Configuration.getInstance().DB_REPOSITORY
                    + File.separator
                    + Configuration.getInstance().DB_NAME;

        dbHelper = new MySQLiteOpenHelper(context, dbFullPath);
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