package com.nexti.android.dragonglass.util;

import android.database.Cursor;

import java.util.Date;

/**
 * Created by ISCesar on 29/10/2017.
 */
public class DbCursorParserUtil {

    private Cursor c;

    public DbCursorParserUtil(Cursor c) {
        this.c = c;
    }

    public String getString(String columnName){
        return c.getString(c.getColumnIndex(columnName));
    }

    public int getInt(String columnName){
        return Integer.parseInt(getString(columnName));
    }

    public float getFloat(String columnName){
        return Float.parseFloat(getString(columnName));
    }

    public Date getSqlDateTime(String columnName){
        return DateUtil.parseDateTimeSQL(getString(columnName));
    }

}
