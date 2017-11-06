package com.nexti.android.dragonglass.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nexti.android.dragonglass.db.DataSource;

import java.util.List;

/**
 * Created by ISCesar on 01/08/2017.
 */
public interface IDao<T> {

    DataSource getDs();
    SQLiteDatabase getDb();

    long insert(T dto);
    int update(T dto);
    void delete(T dto);

    int getCount();
    T findByPrimaryKey(final int id);
    List<T> findAll();
    List<T> findByDynamicWhere(String where);

    void populateDto(T dto, Cursor cursor);

}
