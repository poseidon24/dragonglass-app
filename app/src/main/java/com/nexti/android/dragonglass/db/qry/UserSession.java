package com.nexti.android.dragonglass.db.qry;

/**
 * Created by ISCesar on 01/08/2017.
 */
public class UserSession {

    public static final String TABLE_NAME = "user_session";

    public class UserSessionT{
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
            + UserSessionT.ID + " INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + UserSessionT.USER_NAME + " VARCHAR(100) DEFAULT '', "
            + UserSessionT.USER_PASS + " VARCHAR(100) DEFAULT '', "
            + UserSessionT.REMEMBER_USER + " INTEGER DEFAULT '0', "
            + UserSessionT.LAST_LATITUDE + " VARCHAR(30) DEFAULT '0', "
            + UserSessionT.LAST_LONGITUDE + " VARCHAR(30) DEFAULT '0', "
            + UserSessionT.IMEI + " VARCHAR(30) DEFAULT '', "
            + UserSessionT.BATTERY_PCT + " FLOAT DEFAULT '0' NOT NULL, "
            + UserSessionT.LAST_SERVER_COMM_DT + " VARCHAR(19)  DEFAULT '', "
            + UserSessionT.GCM_TOKEN + " VARCHAR(512) DEFAULT '', "
            + UserSessionT.CREATE_TIMESTAMP + " VARCHAR(19)  DEFAULT '', "
            + UserSessionT.UPDATE_TIMESTAMP + " VARCHAR(19)  DEFAULT '', "
            + UserSessionT.VERSION + " INTEGER NOT NULL "
            + ")";

    public final static String INSERT_INIT_DATA = "INSERT INTO " + TABLE_NAME
            + "("
            + UserSessionT.ID + ", "
            + UserSessionT.USER_NAME + ", "
            + UserSessionT.USER_PASS + ", "
            + UserSessionT.REMEMBER_USER + ", "
            + UserSessionT.LAST_LATITUDE + ", "
            + UserSessionT.LAST_LONGITUDE + ", "
            + UserSessionT.IMEI + ", "
            + UserSessionT.BATTERY_PCT + ", "
            + UserSessionT.LAST_SERVER_COMM_DT + ", "
            + UserSessionT.GCM_TOKEN + ","
            + UserSessionT.CREATE_TIMESTAMP + ","
            + UserSessionT.UPDATE_TIMESTAMP + ","
            + UserSessionT.VERSION + ""
            + ")"
            + " VALUES "
            + "(1,'','',0,'0','0','',0,'','','2017-01-01 00:00:01','',1)";

    //public final static String AL_TABLE_USESSION_V2 = "ALTER TABLE " + TABLE_USESSION + " ADD COLUMN " + this.PERMISO_MODIFICAR_CLIENTE + " INTEGER DEFAULT '3'";


}
