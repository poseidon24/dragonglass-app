package com.nexti.android.dragonglass.db.dao;

/**
 * Created by ISCesar on 29/10/2017.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nexti.android.dragonglass.db.DataSource;
import com.nexti.android.dragonglass.db.qry.DgMobileDbUtils;
import com.nexti.android.dragonglass.model.entity.DgMobileEntity;
import com.nexti.android.dragonglass.util.DateUtil;
import com.nexti.android.dragonglass.db.qry.DgMobileDbUtils.TableDesc;
import com.nexti.android.dragonglass.util.DbCursorParserUtil;

import java.util.ArrayList;
import java.util.List;

public class DgMobileDaoImpl implements IDao<DgMobileEntity> {
    private Context context;
    private DataSource ds;
    private static final String TAG = DgMobileDaoImpl.class.getSimpleName();

    private static final String TABLE_NAME = DgMobileDbUtils.TABLE_NAME;

    public DgMobileDaoImpl(Context context, DataSource dataSource){
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
    public long insert(DgMobileEntity dto) {
        long id = -1;
        try{
            ContentValues values = new ContentValues();
            //values.put(TableDesc.ID, dto.getId());
            values.put(TableDesc.TAG, dto.getTag());
            values.put(TableDesc.GCM_TOKEN, dto.getGcmToken());
            values.put(TableDesc.WIFI_ENABLED, dto.getWifiEnabled());
            values.put(TableDesc.BT_ENABLED, dto.getBluetoothEnabled());
            values.put(TableDesc.MOB_DATA_ENABLED, dto.getPhoneCallEnabled());
            values.put(TableDesc.PHONE_CALL_ENABLED, dto.getPhoneCallEnabled());
            values.put(TableDesc.APP_ACCESS_ENABLED, dto.getAppsAccessEnabled());
            values.put(TableDesc.APP_LIST, dto.getAppsList());
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
    public int update(DgMobileEntity dto) {
        int recordsAffected = 0;
        try{
            ContentValues values = new ContentValues();
            //values.put(TableDesc.ID, dto.getId());
            values.put(TableDesc.TAG, dto.getTag());
            values.put(TableDesc.GCM_TOKEN, dto.getGcmToken());
            values.put(TableDesc.WIFI_ENABLED, dto.getWifiEnabled());
            values.put(TableDesc.BT_ENABLED, dto.getBluetoothEnabled());
            values.put(TableDesc.MOB_DATA_ENABLED, dto.getPhoneCallEnabled());
            values.put(TableDesc.PHONE_CALL_ENABLED, dto.getPhoneCallEnabled());
            values.put(TableDesc.APP_ACCESS_ENABLED, dto.getAppsAccessEnabled());
            values.put(TableDesc.APP_LIST, dto.getAppsList());
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
    public void delete(DgMobileEntity dto) {
        this.getDb().delete(TABLE_NAME, TableDesc.ID + " = ?",
                new String[] { String.valueOf(dto.getId()) });
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
        }
        return count;
    }

    // SELECT single row
    public DgMobileEntity findByPrimaryKey(final int id)  {
        DgMobileEntity dto = null;
        Cursor cursor = null;
        try{
            String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + TableDesc.ID + " = " + id;
            Log.d(TAG,"DB Query: " + selectQuery);
            cursor = this.getDb().rawQuery(selectQuery, new String[0]);
            Log.d(TAG,"Cursor count: " + cursor.getCount());

            if (cursor.moveToFirst()) {
                dto = new DgMobileEntity();

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
    public List<DgMobileEntity> findAll()  {
        List<DgMobileEntity> list = new ArrayList<>();
        Cursor cursor = null;
        try{
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_NAME;

            cursor = this.getDb().rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    DgMobileEntity dto = new DgMobileEntity();

                    populateDto(dto, cursor);
                    // Adding contact to list
                    list.add(dto);
                } while (cursor.moveToNext());
            }
        }catch(Exception e){
            Log.e(TAG,"Unexpected error",e);
        }finally{
            if (cursor!=null) cursor.close();
        }

        return list;
    }

    // SELECT By dynamic Where
    public List<DgMobileEntity> findByDynamicWhere(String where) {
        List<DgMobileEntity> list = new ArrayList<>();
        Cursor cursor = null;
        try{
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + where;

            cursor = this.getDb().rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    DgMobileEntity dto = new DgMobileEntity();
                    populateDto(dto, cursor);
                    list.add(dto);
                } while (cursor.moveToNext());
            }
        }catch(Exception e){
            Log.e(TAG,"Unexpected error",e);
        }finally{
            if (cursor!=null) cursor.close();
        }
        return list;
    }

    /**
     * Populates a DTO with data from a Cursor
     *
     */
    public void populateDto(DgMobileEntity dto, Cursor cursor) {
        DbCursorParserUtil parser = new DbCursorParserUtil(cursor);

        dto.setId( parser.getInt(TableDesc.ID));
        dto.setCreateTimestamp(parser.getSqlDateTime(TableDesc.CREATE_TIMESTAMP));
        dto.setUpdateTimestamp(parser.getSqlDateTime(TableDesc.UPDATE_TIMESTAMP));
        dto.setVersion( parser.getInt(TableDesc.VERSION));

        dto.setTag( parser.getString(TableDesc.TAG) );
        dto.setGcmToken( parser.getString(TableDesc.GCM_TOKEN)) ;
        dto.setWifiEnabled( parser.getInt(TableDesc.WIFI_ENABLED));
        dto.setBluetoothEnabled( parser.getInt(TableDesc.BT_ENABLED) );
        dto.setMobileDataEnabled( parser.getInt(TableDesc.MOB_DATA_ENABLED));
        dto.setPhoneCallEnabled( parser.getInt(TableDesc.PHONE_CALL_ENABLED));
        dto.setAppsAccessEnabled( parser.getInt(TableDesc.APP_ACCESS_ENABLED));
        dto.setAppsList( parser.getString(TableDesc.APP_LIST));

        parser = null;
    }

}
