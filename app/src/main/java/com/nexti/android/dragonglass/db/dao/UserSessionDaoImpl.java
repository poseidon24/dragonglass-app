package com.nexti.android.dragonglass.db.dao;

/**
 * Created by ISCesar on 01/08/2017.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nexti.android.dragonglass.db.DataSource;
import com.nexti.android.dragonglass.db.qry.UserSession;
import com.nexti.android.dragonglass.model.entity.UserSessionEntity;
import com.nexti.android.dragonglass.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class UserSessionDaoImpl implements IDao {
    public static final String TAG =  "UserSessionDaoImpl";
    private Context context;
    // DataSource
    private DataSource ds;

    // Contacts table name
    private static final String TABLE_NAME = UserSession.TABLE_NAME;

    public UserSessionDaoImpl(Context context, DataSource dataSource){
        this.context = context;
        try {
            ds = dataSource;
            ds.open();
        }catch (Exception e){
            Log.i(TAG, "Error while openning database.", e);
        }
    }

    public DataSource getDs() {
        return ds;
    }

    public SQLiteDatabase getDb(){
        return getDs().getDb();
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // INSERT
    public long insert(UserSessionEntity dto) {
        long id = -1;
        try{
            ContentValues values = new ContentValues();
            //values.put("id_sesion", dto.getIdSesion());
            values.put(UserSession.UserSessionT.USER_NAME, dto.getUserName());
            values.put(UserSession.UserSessionT.USER_PASS, dto.getUserPass());
            values.put(UserSession.UserSessionT.REMEMBER_USER, dto.getRememberUser());
            values.put(UserSession.UserSessionT.LAST_LATITUDE, dto.getLastLatitude());
            values.put(UserSession.UserSessionT.LAST_LONGITUDE, dto.getLastLongitude());
            values.put(UserSession.UserSessionT.IMEI, dto.getImei());
            values.put(UserSession.UserSessionT.BATTERY_PCT, dto.getBatteryPct());
            values.put(UserSession.UserSessionT.LAST_SERVER_COMM_DT,DateUtil.dateToSQLDateTime(dto.getLastServerCommDt()));
            values.put(UserSession.UserSessionT.GCM_TOKEN, dto.getGcmToken());
            values.put(UserSession.UserSessionT.CREATE_TIMESTAMP,DateUtil.dateToSQLDateTime(dto.getCreateTimestamp()));
            values.put(UserSession.UserSessionT.UPDATE_TIMESTAMP,DateUtil.dateToSQLDateTime(dto.getUpdateTimestamp()));
            values.put(UserSession.UserSessionT.VERSION, dto.getVersion());

            // Inserting Row
            id = getDb().insert(TABLE_NAME, null, values);
        }catch(Exception e){
            Log.e(TAG,"Unexpected error",e);
        }finally{
            //getDs().close();
        }
        return id;
    }

    // Updating single contact
    public int update(UserSessionEntity dto) {
        int recordsAffected = 0;
        try{
            ContentValues values = new ContentValues();
            //values.put("id_sesion", dto.getIdSesion());
            values.put(UserSession.UserSessionT.USER_NAME, dto.getUserName());
            values.put(UserSession.UserSessionT.USER_PASS, dto.getUserPass());
            values.put(UserSession.UserSessionT.REMEMBER_USER, dto.getRememberUser());
            values.put(UserSession.UserSessionT.LAST_LATITUDE, dto.getLastLatitude());
            values.put(UserSession.UserSessionT.LAST_LONGITUDE, dto.getLastLongitude());
            values.put(UserSession.UserSessionT.IMEI, dto.getImei());
            values.put(UserSession.UserSessionT.BATTERY_PCT, dto.getBatteryPct());
            values.put(UserSession.UserSessionT.LAST_SERVER_COMM_DT, DateUtil.dateToSQLDateTime(dto.getLastServerCommDt()));
            values.put(UserSession.UserSessionT.GCM_TOKEN, dto.getGcmToken());
            values.put(UserSession.UserSessionT.CREATE_TIMESTAMP,DateUtil.dateToSQLDateTime(dto.getCreateTimestamp()));
            values.put(UserSession.UserSessionT.UPDATE_TIMESTAMP,DateUtil.dateToSQLDateTime(dto.getUpdateTimestamp()));
            values.put(UserSession.UserSessionT.VERSION, dto.getVersion());

            // updating row
            recordsAffected = this.getDb().update(TABLE_NAME, values,
                    UserSession.UserSessionT.ID + " = ?",
                    new String[] { String.valueOf(dto.getId()) });
        }catch(Exception e){
            Log.e(TAG,"Unexpected error",e);
        }finally{
            //getDs().close();
        }
        return recordsAffected;
    }

    // Deleting single row
    public void delete(UserSessionEntity dto) {
        this.getDb().delete(TABLE_NAME, UserSession.UserSessionT.ID + " = ?",
                new String[] { String.valueOf(dto.getId()) });
        //getDs().close();
    }

    // Getting Count
    public int getCount() {
        int count = 0;
        Cursor cursor = null;
        try{
            String countQuery = "SELECT  * FROM " + TABLE_NAME;
            cursor = this.getDb().rawQuery(countQuery, null);
            count = cursor.getCount();
        }catch(Exception e){
            Log.e(TAG,"Unexpected error",e);
        }finally{
            if (cursor!=null) cursor.close();
            //getDs().close();
        }
        return count;
    }

    // SELECT single row
    public UserSessionEntity findByPrimaryKey(final int id)  {
        UserSessionEntity dto = null;
        Cursor cursor = null;
        try{
            String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + UserSession.UserSessionT.ID + " = " + id;
            Log.d(TAG,"DB Query: " + selectQuery);
            cursor = this.getDb().rawQuery(selectQuery, new String[0]);
            Log.d(TAG,"Cursor count: " + cursor.getCount());

            if (cursor != null){
                if (cursor.moveToFirst()){
                    dto = new UserSessionEntity();

                    populateDto(dto, cursor);

                }
            }
        }catch(Exception e){
            Log.e(TAG,"Unexpected error",e);
        }finally{
            if (cursor!=null) cursor.close();
            //getDs().close();
        }
        return dto;
    }

    // SELECT All rows
    public List<UserSessionEntity> findAll()  {
        List<UserSessionEntity> list = new ArrayList<UserSessionEntity>();
        Cursor cursor = null;
        try{
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_NAME;

            cursor = this.getDb().rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    UserSessionEntity dto = new UserSessionEntity();

                    populateDto(dto, cursor);
                    // Adding contact to list
                    list.add(dto);
                } while (cursor.moveToNext());
            }
        }catch(Exception e){
            Log.e(TAG,"Unexpected error",e);
        }finally{
            if (cursor!=null) cursor.close();
            //getDs().close();
        }

        return list;
    }

    // SELECT By dynamic Where
    public List<UserSessionEntity> findByDynamicWhere(String where) {
        List<UserSessionEntity> list = new ArrayList<UserSessionEntity>();
        Cursor cursor = null;
        try{
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + where;

            cursor = this.getDb().rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    UserSessionEntity dto = new UserSessionEntity();
                    populateDto(dto, cursor);
                    list.add(dto);
                } while (cursor.moveToNext());
            }
        }catch(Exception e){
            Log.e(TAG,"Unexpected error",e);
        }finally{
            if (cursor!=null) cursor.close();
            //getDs().close();
        }
        return list;
    }

    /**
     * Populates a DTO with data from a Cursor
     *
     */
    protected void populateDto(UserSessionEntity dto, Cursor cursor) {
        dto.setId( Integer.parseInt(cursor.getString(cursor.getColumnIndex(UserSession.UserSessionT.ID))) );

        dto.setUserName( cursor.getString(cursor.getColumnIndex(UserSession.UserSessionT.USER_NAME)) );
        dto.setUserPass( cursor.getString(cursor.getColumnIndex(UserSession.UserSessionT.USER_PASS)) );
        dto.setRememberUser( Integer.parseInt(cursor.getString(cursor.getColumnIndex(UserSession.UserSessionT.REMEMBER_USER))) );
        dto.setLastLatitude( cursor.getString(cursor.getColumnIndex(UserSession.UserSessionT.LAST_LATITUDE)) );
        dto.setLastLongitude( cursor.getString(cursor.getColumnIndex(UserSession.UserSessionT.LAST_LONGITUDE)) );
        dto.setImei( cursor.getString(cursor.getColumnIndex(UserSession.UserSessionT.IMEI)) );
        dto.setBatteryPct( Float.parseFloat(cursor.getString(cursor.getColumnIndex(UserSession.UserSessionT.BATTERY_PCT))) );
        dto.setLastServerCommDt(DateUtil.parseDateTimeSQL(cursor.getString(cursor.getColumnIndex(UserSession.UserSessionT.LAST_SERVER_COMM_DT))));
        dto.setGcmToken(cursor.getString(cursor.getColumnIndex(UserSession.UserSessionT.GCM_TOKEN)) );

        dto.setCreateTimestamp(DateUtil.parseDateTimeSQL(cursor.getString(cursor.getColumnIndex(UserSession.UserSessionT.CREATE_TIMESTAMP))));
        dto.setUpdateTimestamp(DateUtil.parseDateTimeSQL(cursor.getString(cursor.getColumnIndex(UserSession.UserSessionT.UPDATE_TIMESTAMP))));
        dto.setVersion( Integer.parseInt(cursor.getString(cursor.getColumnIndex(UserSession.UserSessionT.VERSION))) );
    }

}
