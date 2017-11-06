package com.nexti.android.dragonglass.model.entity;

/**
 * Created by ISCesar on 29/10/2017.
 */
public class DgMobileEntity extends AuditableEntity {

    private String tag;
    private String gcmToken;
    private int wifiEnabled;
    private int bluetoothEnabled;
    private int mobileDataEnabled;
    private int phoneCallEnabled;
    private int appsAccessEnabled;
    private String appsList;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getGcmToken() {
        return gcmToken;
    }

    public void setGcmToken(String gcmToken) {
        this.gcmToken = gcmToken;
    }

    public int getWifiEnabled() {
        return wifiEnabled;
    }

    public void setWifiEnabled(int wifiEnabled) {
        this.wifiEnabled = wifiEnabled;
    }

    public int getBluetoothEnabled() {
        return bluetoothEnabled;
    }

    public void setBluetoothEnabled(int bluetoothEnabled) {
        this.bluetoothEnabled = bluetoothEnabled;
    }

    public int getMobileDataEnabled() {
        return mobileDataEnabled;
    }

    public void setMobileDataEnabled(int mobileDataEnabled) {
        this.mobileDataEnabled = mobileDataEnabled;
    }

    public int getPhoneCallEnabled() {
        return phoneCallEnabled;
    }

    public void setPhoneCallEnabled(int phoneCallEnabled) {
        this.phoneCallEnabled = phoneCallEnabled;
    }

    public int getAppsAccessEnabled() {
        return appsAccessEnabled;
    }

    public void setAppsAccessEnabled(int appsAccessEnabled) {
        this.appsAccessEnabled = appsAccessEnabled;
    }

    public String getAppsList() {
        return appsList;
    }

    public void setAppsList(String appsList) {
        this.appsList = appsList;
    }

    @Override
    public String toString() {
        return "DgMobileEntity{" +
                "tag='" + tag + '\'' +
                ", gcmToken='" + gcmToken + '\'' +
                ", wifiEnabled=" + wifiEnabled +
                ", bluetoothEnabled=" + bluetoothEnabled +
                ", mobileDataEnabled=" + mobileDataEnabled +
                ", phoneCallEnabled=" + phoneCallEnabled +
                ", appsAccessEnabled=" + appsAccessEnabled +
                ", appsList='" + appsList + '\'' +
                "} " + super.toString();
    }
}
