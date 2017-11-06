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
import com.nexti.android.dragonglass.db.qry.UserSessionDbUtils;
import com.nexti.android.dragonglass.db.qry.UserSessionDbUtils.TableDesc;
import com.nexti.android.dragonglass.model.entity.UserSessionEntity;
import com.nexti.android.dragonglass.util.DateUtil;
import com.nexti.android.dragonglass.util.DbCursorParserUtil;

import java.util.ArrayList;
import java.util.List;

public class UserSessionDaoImpl implements IDao<UserSessionEntity> {
    private Context context;
    private DataSource ds;
    private static final String TAG = UserSessionDaoImpl.class.getSimpleName();

    private static final String TABLE_NAME = UserSessionDbUtils.TABLE_NAME;

    public UserSessionDaoImpl(Context context, DataSource dataSource){
        this.context = context;
        try {
            ds = dataSource;
            ds.open();
        }catch (Exception e){
            Log.i(TAG, "Error while opening database.", e);
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
            values.put(TableDesc.USER_NAME, dto.getUserName());
            values.put(TableDesc.USER_PASS, dto.getUserPass());
            values.put(TableDesc.REMEMBER_USER, dto.getRememberUser());
            values.put(TableDesc.LAST_LATITUDE, dto.getLastLatitude());
            values.put(TableDesc.LAST_LONGITUDE, dto.getLastLongitude());
            values.put(TableDesc.IMEI, dto.getImei());
            values.put(TableDesc.BATTERY_PCT, dto.getBatteryPct());
            values.put(TableDesc.LAST_SERVER_COMM_DT,DateUtil.dateToSQLDateTime(dto.getLastServerCommDt()));
            values.put(TableDesc.GCM_TOKEN, dto.getGcmToken());
            values.put(TableDesc.CREATE_TIMESTAMP,DateUtil.dateToSQLDateTime(dto.getCreateTimestamp()));
            values.put(TableDesc.UPDATE_TIMESTAMP,DateUtil.dateToSQLDateTime(dto.getUpdateTimestamp()));
            values.put(TableDesc.VERSION, dto.getVersion());

            // Inserting Row
            id = getDb().insert(TABLE_NAME, null, values);
        }catch(Exception e){
            Log.e(TAG,"Unexpected error",e);
        }
        return id;
    }

    // Updating single contact
    public int update(UserSessionEntity dto) {
        int recordsAffected = 0;
        try{
            ContentValues values = new ContentValues();
            //values.put("id_sesion", dto.getIdSesion());
            values.put(TableDesc.USER_NAME, dto.getUserName());
            values.put(TableDesc.USER_PASS, dto.getUserPass());
            values.put(TableDesc.REMEMBER_USER, dto.getRememberUser());
            values.put(TableDesc.LAST_LATITUDE, dto.getLastLatitude());
            values.put(TableDesc.LAST_LONGITUDE, dto.getLastLongitude());
            values.put(TableDesc.IMEI, dto.getImei());
            values.put(TableDesc.BATTERY_PCT, dto.getBatteryPct());
            values.put(TableDesc.LAST_SERVER_COMM_DT, DateUtil.dateToSQLDateTime(dto.getLastServerCommDt()));
            values.put(TableDesc.GCM_TOKEN, dto.getGcmToken());
            values.put(TableDesc.CREATE_TIMESTAMP,DateUtil.dateToSQLDateTime(dto.getCreateTimestamp()));
            values.put(TableDesc.UPDATE_TIMESTAMP,DateUtil.dateToSQLDateTime(dto.getUpdateTimestamp()));
            values.put(TableDesc.VERSION, dto.getVersion());

            // updating row
            recordsAffected = this.getDb().update(TABLE_NAME, values,
                    TableDesc.ID + " = ?",
                    new String[] { String.valueOf(dto.getId()) });
        }catch(Exception e){
            Log.e(TAG,"Unexpected error",e);
        }
        return recordsAffected;
    }

    // Deleting single row
    public void delete(UserSessionEntity dto) {
        this.getDb().delete(TABLE_NAME, TableDesc.ID + " = ?",
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
            String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + TableDesc.ID + " = " + id;
            Log.d(TAG,"DB Query: " + selectQuery);
            cursor = this.getDb().rawQuery(selectQuery, new String[0]);
            Log.d(TAG,"Cursor count: " + cursor.getCount());

            if (cursor.moveToFirst()) {
                dto = new UserSessionEntity();

                populateDto(dto, cursor);
            }

        }catch(Exception e){
            Log.e(TAG,"Unexpected error",e);
        }finally{
            if (cursor!=null) cursor.close();
        }
        return dto;
    }

    // SELECT All rows
    public List<UserSessionEntity> findAll()  {
        List<UserSessionEntity> list = new ArrayList<>();
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
        List<UserSessionEntity> list = new ArrayList<>();
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
    public void populateDto(UserSessionEntity dto, Cursor cursor) {
        DbCursorParserUtil parser = new DbCursorParserUtil(cursor);

        dto.setId( parser.getInt(TableDesc.ID));
        dto.setCreateTimestamp(parser.getSqlDateTime(TableDesc.CREATE_TIMESTAMP));
        dto.setUpdateTimestamp(parser.getSqlDateTime(TableDesc.UPDATE_TIMESTAMP));
        dto.setVersion( parser.getInt(TableDesc.VERSION));

        dto.setUserName( parser.getString(TableDesc.USER_NAME));
        dto.setUserPass( parser.getString(TableDesc.USER_PASS));
        dto.setRememberUser( parser.getInt(TableDesc.REMEMBER_USER));
        dto.setLastLatitude( parser.getString(TableDesc.LAST_LATITUDE));
        dto.setLastLongitude( parser.getString(TableDesc.LAST_LONGITUDE));
        dto.setImei( parser.getString(TableDesc.IMEI));
        dto.setBatteryPct(parser.getFloat(TableDesc.BATTERY_PCT));
        dto.setLastServerCommDt(parser.getSqlDateTime(TableDesc.LAST_SERVER_COMM_DT));
        dto.setGcmToken(parser.getString(TableDesc.GCM_TOKEN));

        parser = null;
    }

}
