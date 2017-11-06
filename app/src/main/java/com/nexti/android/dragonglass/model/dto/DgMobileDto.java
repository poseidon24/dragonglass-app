package com.nexti.android.dragonglass.model.dto;

import com.google.gson.Gson;

/**
 * Created by ISCesar on 01/10/2017.
 */
public class DgMobileDto extends BaseDtoImpl{

    private String tag;
    private String gcmToken;
    private boolean wifiEnabled;
    private boolean bluetoothEnabled;
    private boolean mobileDataEnabled;
    private boolean phoneCallEnabled;
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

    public boolean isWifiEnabled() {
        return wifiEnabled;
    }

    public void setWifiEnabled(boolean wifiEnabled) {
        this.wifiEnabled = wifiEnabled;
    }

    public boolean isBluetoothEnabled() {
        return bluetoothEnabled;
    }

    public void setBluetoothEnabled(boolean bluetoothEnabled) {
        this.bluetoothEnabled = bluetoothEnabled;
    }

    public boolean isMobileDataEnabled() {
        return mobileDataEnabled;
    }

    public void setMobileDataEnabled(boolean mobileDataEnabled) {
        this.mobileDataEnabled = mobileDataEnabled;
    }

    public boolean isPhoneCallEnabled() {
        return phoneCallEnabled;
    }

    public void setPhoneCallEnabled(boolean phoneCallEnabled) {
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

}