package com.nexti.android.dragonglass.db.dao;

import android.database.sqlite.SQLiteDatabase;

import com.nexti.android.dragonglass.db.DataSource;

/**
 * Created by ISCesar on 01/08/2017.
 */
public interface IDao {

    DataSource getDs();
    SQLiteDatabase getDb();


}
