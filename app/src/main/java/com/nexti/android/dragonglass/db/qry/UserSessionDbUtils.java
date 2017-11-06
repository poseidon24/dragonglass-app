package com.nexti.android.dragonglass.db.qry;

/**
 * Created by ISCesar on 01/08/2017.
 */
public class UserSessionDbUtils {

    public static final String TABLE_NAME = "user_session";

    public class TableDesc {
        public final static String ID = "id_session";
        public final static String USER_NAME = "user_name";
        public final static String USER_PASS = "user_pass";
        public final static String REMEMBER_USER = "remember_user";
        public final static String LAST_LATITUDE = "last_latitude";
        public final static String LAST_LONGITUDE = "last_longitude";
        public final static String IMEI = "imei";
        public final static String BATTERY_PCT = "battery_pct";
        public final static String LAST_SERVER_COMM_DT = "last_server_comm_dt";
        public final static String GCM_TOKEN = "gcm_token";
        public final static String CREATE_TIMESTAMP = "create_timestamp";
        public final static String UPDATE_TIMESTAMP = "update_timestamp";
        public final static String VERSION = "version";
    }

    public final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
            + "("
            + TableDesc.ID + " INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + TableDesc.USER_NAME + " VARCHAR(100) DEFAULT '', "
            + TableDesc.USER_PASS + " VARCHAR(100) DEFAULT '', "
            + TableDesc.REMEMBER_USER + " INTEGER DEFAULT '0', "
            + TableDesc.LAST_LATITUDE + " VARCHAR(30) DEFAULT '0', "
            + TableDesc.LAST_LONGITUDE + " VARCHAR(30) DEFAULT '0', "
            + TableDesc.IMEI + " VARCHAR(30) DEFAULT '', "
            + TableDesc.BATTERY_PCT + " FLOAT DEFAULT '0' NOT NULL, "
            + TableDesc.LAST_SERVER_COMM_DT + " VARCHAR(19)  DEFAULT '', "
            + TableDesc.GCM_TOKEN + " VARCHAR(512) DEFAULT '', "
            + TableDesc.CREATE_TIMESTAMP + " VARCHAR(19)  DEFAULT '', "
            + TableDesc.UPDATE_TIMESTAMP + " VARCHAR(19)  DEFAULT '', "
            + TableDesc.VERSION + " INTEGER NOT NULL "
            + ")";

    public final static String INSERT_INIT_DATA = "INSERT INTO " + TABLE_NAME
            + "("
            + TableDesc.ID + ", "
            + TableDesc.USER_NAME + ", "
            + TableDesc.USER_PASS + ", "
            + TableDesc.REMEMBER_USER + ", "
            + TableDesc.LAST_LATITUDE + ", "
            + TableDesc.LAST_LONGITUDE + ", "
            + TableDesc.IMEI + ", "
            + TableDesc.BATTERY_PCT + ", "
            + TableDesc.LAST_SERVER_COMM_DT + ", "
            + TableDesc.GCM_TOKEN + ","
            + TableDesc.CREATE_TIMESTAMP + ","
            + TableDesc.UPDATE_TIMESTAMP + ","
            + TableDesc.VERSION + ""
            + ")"
            + " VALUES "
            + "(1,'','',0,'0','0','',0,'','','2017-01-01 00:00:01','',1)";

    //public final static String AL_TABLE_USESSION_V2 = "ALTER TABLE " + TABLE_USESSION + " ADD COLUMN " + this.PERMISO_MODIFICAR_CLIENTE + " INTEGER DEFAULT '3'";


}
