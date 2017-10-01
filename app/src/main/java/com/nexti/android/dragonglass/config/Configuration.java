package com.nexti.android.dragonglass.config;

/**
 * Created by ISCesar on 01/08/2017.
 */
public class Configuration {

    private String bdName = "dragonglassDb.s3db";
    private String repositorioBD = "/data/data/com.nexti.android.dragonglass";

    public static String URL_WS_DEV = "";
    public static String URL_WS_QA = "";
    public static String URL_WS_PROD = "";

    /**
     * @return the repositorioBD
     */
    public String getRepositorioBD() {
        return repositorioBD;
    }

    /**
     * @param repositorioBD the repositorioBD to set
     */
    public void setRepositorioBD(String repositorioBD) {
        this.repositorioBD = repositorioBD;
    }

    /**
     * @return the bdName
     */
    public String getBdName() {
        return bdName;
    }

    /**
     * @param bdName the bdName to set
     */
    public void setBdName(String bdName) {
        this.bdName = bdName;
    }

    public static String getUrlWsProd() {
        return URL_WS_PROD;
    }

    public static void setUrlWsProd(String urlWsProd) {
        URL_WS_PROD = urlWsProd;
    }

    public static String getUrlWsDev() {
        return URL_WS_DEV;
    }

    public static void setUrlWsDev(String urlWsDev) {
        URL_WS_DEV = urlWsDev;
    }

    public static String getUrlWsQa() {
        return URL_WS_QA;
    }

    public static void setUrlWsQa(String urlWsQa) {
        URL_WS_QA = urlWsQa;
    }
}