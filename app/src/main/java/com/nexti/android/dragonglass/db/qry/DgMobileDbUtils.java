package com.nexti.android.dragonglass.db.qry;

/**
 * Created by ISCesar on 29/10/2017.
 */
public class DgMobileDbUtils {

    public static final String TABLE_NAME = "dg_mobile";

    public class TableDesc {
        public final static String ID = "id";
        public final static String TAG = "tag";
        public final static String GCM_TOKEN = "gcm_token";
        public final static String WIFI_ENABLED = "wifi_enabled";
        public final static String BT_ENABLED = "bt_enabled";
        public final static String MOB_DATA_ENABLED = "mob_data_enabled";
        public final static String PHONE_CALL_ENABLED = "phone_call_enabled";
        public final static String APP_ACCESS_ENABLED = "app_access_enabled";
        public final static String APP_LIST = "app_list";
        public final static String CREATE_TIMESTAMP = "create_timestamp";
        public final static String UPDATE_TIMESTAMP = "update_timestamp";
        public final static String VERSION = "version";
    }

    public final static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
            + "("
            + TableDesc.ID + " INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + TableDesc.TAG + " VARCHAR(50) DEFAULT '', "
            + TableDesc.GCM_TOKEN + " VARCHAR(512) DEFAULT '', "
            + TableDesc.WIFI_ENABLED + " INTEGER DEFAULT '1', "
            + TableDesc.BT_ENABLED + " INTEGER DEFAULT '1', "
            + TableDesc.MOB_DATA_ENABLED + " INTEGER DEFAULT '1', "
            + TableDesc.PHONE_CALL_ENABLED + " INTEGER DEFAULT '1', "
            + TableDesc.APP_ACCESS_ENABLED + " INTEGER DEFAULT '1', "
            + TableDesc.APP_LIST + " VARCHAR(512) DEFAULT '', "
            + TableDesc.CREATE_TIMESTAMP + " VARCHAR(19)  DEFAULT '', "
            + TableDesc.UPDATE_TIMESTAMP + " VARCHAR(19)  DEFAULT '', "
            + TableDesc.VERSION + " INTEGER NOT NULL "
            + ")";

    public final static String INSERT_INIT_DATA = "INSERT INTO " + TABLE_NAME
            + "("
            + TableDesc.ID + ", "
            + TableDesc.TAG + ", "
            + TableDesc.GCM_TOKEN + ", "
            + TableDesc.WIFI_ENABLED + ", "
            + TableDesc.BT_ENABLED + ", "
            + TableDesc.MOB_DATA_ENABLED + ", "
            + TableDesc.PHONE_CALL_ENABLED + ", "
            + TableDesc.APP_ACCESS_ENABLED + ", "
            + TableDesc.APP_LIST + ", "
            + TableDesc.CREATE_TIMESTAMP + ","
            + TableDesc.UPDATE_TIMESTAMP + ","
            + TableDesc.VERSION + ""
            + ")"
            + " VALUES "
            + "(1,'','',1,1,1,1,1,'','2017-01-01 00:00:01','',1)";

}
