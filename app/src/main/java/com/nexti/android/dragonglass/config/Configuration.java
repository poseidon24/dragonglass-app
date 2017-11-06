package com.nexti.android.dragonglass.config;

/**
 * Created by ISCesar on 01/08/2017.
 *
 * Singleton pattern
 */
public class Configuration {

    //Singleton
    private static Configuration ourInstance = new Configuration();
    public static Configuration getInstance() {
        return ourInstance;
    }
    private Configuration() {
    }

    public static String DB_NAME = "dragonglassDb.s3db";
    public static String DB_REPOSITORY = "/data/data/com.nexti.android.dragonglass";
    public static AppEnvironment APP_ENVIRONMENT = AppEnvironment.DEV;

    private static String URL_WS_DEV = "http://192.168.0.15:8084/dragonstone-console"; //"http://192.168.0.15:8080";//"http://192.168.1.73:8080";
    private static String URL_WS_QA = "";
    private static String URL_WS_PROD = "";

    public String getUrlWS(){
        switch (APP_ENVIRONMENT){
            case DEV:
                return URL_WS_DEV;
            case QA:
                return URL_WS_QA;
            case PROD:
            default:
                return URL_WS_PROD;
        }
    }

    public enum AppEnvironment{
        DEV, QA, PROD;
    }
}