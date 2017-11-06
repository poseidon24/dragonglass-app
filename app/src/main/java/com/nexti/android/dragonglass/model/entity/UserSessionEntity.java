package com.nexti.android.dragonglass.model.entity;

import java.util.Date;

/**
 * Created by ISCesar on 01/08/2017.
 */
public class UserSessionEntity extends AuditableEntity implements IEntity {

    private int idSession;
    private String userName;
    private String userPass;
    private int rememberUser;
    private String lastLatitude;
    private String lastLongitude;
    private String imei;
    private float batteryPct = 0;
    private Date lastServerCommDt;
    private String gcmToken;

    @Override
    public int getId() {
        return idSession;
    }

    @Override
    public void setId(int id) {
        this.idSession = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public int getRememberUser() {
        return rememberUser;
    }

    public void setRememberUser(int rememberUser) {
        this.rememberUser = rememberUser;
    }

    public String getLastLatitude() {
        return lastLatitude;
    }

    public void setLastLatitude(String lastLatitude) {
        this.lastLatitude = lastLatitude;
    }

    public String getLastLongitude() {
        return lastLongitude;
    }

    public void setLastLongitude(String lastLongitude) {
        this.lastLongitude = lastLongitude;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public float getBatteryPct() {
        return batteryPct;
    }

    public void setBatteryPct(float batteryPct) {
        this.batteryPct = batteryPct;
    }

    public Date getLastServerCommDt() {
        return lastServerCommDt;
    }

    public void setLastServerCommDt(Date lastServerCommDt) {
        this.lastServerCommDt = lastServerCommDt;
    }

    public String getGcmToken() {
        return gcmToken;
    }

    public void setGcmToken(String gcmToken) {
        this.gcmToken = gcmToken;
    }

    @Override
    public String toString() {
        return "UserSessionEntity{" +
                "idSession=" + idSession +
                ", userName='" + userName + '\'' +
                ", userPass='" + userPass + '\'' +
                ", rememberUser=" + rememberUser +
                ", lastLatitude='" + lastLatitude + '\'' +
                ", lastLongitude='" + lastLongitude + '\'' +
                ", imei='" + imei + '\'' +
                ", batteryPct=" + batteryPct +
                ", lastServerCommDt=" + lastServerCommDt +
                ", gcmToken='" + gcmToken + '\'' +
                "} " + super.toString();
    }
}
