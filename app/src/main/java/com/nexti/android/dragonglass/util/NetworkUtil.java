package com.nexti.android.dragonglass.util;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by ISCesar on 22/07/2017.
 */
public class NetworkUtil {

    private Context context;
    private final String TAG = this.getClass().getSimpleName();

    public NetworkUtil(Context context){
        this.context = context;
    }

    /**
     * It checks if a network connection is enabled or not.
     * @param networkType uses one of ConnectivityManager static values: TYPE_WIFI, TYPE_MOBILE, TYPE_BLUETOOTH
     * @return true if the connection is enabled, false otherwise
     */
    public boolean checkNetworkState(int networkType){
        boolean isEnabled = false;

        switch (networkType){
            case ConnectivityManager.TYPE_WIFI:
                WifiManager wifiManager = (WifiManager) this.context.getSystemService(Context.WIFI_SERVICE);
                isEnabled = wifiManager.isWifiEnabled();
                break;
            case ConnectivityManager.TYPE_MOBILE:
                isEnabled = isMobileDataEnabledByReflection();
                break;
            case ConnectivityManager.TYPE_BLUETOOTH:
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                isEnabled = mBluetoothAdapter.isEnabled();
                break;
            default: ;
        }

        return isEnabled;
    }

    /**
     * Principal endpoint of this Utility class for switching an specified network connection
     * It works for Wifi, Bluetooth and Mobile Data.
     * @param networkType uses one of ConnectivityManager static values: TYPE_WIFI, TYPE_MOBILE, TYPE_BLUETOOTH
     * @param enable It indicates if the connection is going to be enabled or disabled (true or false)
     */
    public void switchNetwork(int networkType, boolean enable){

        switch (networkType){
            case ConnectivityManager.TYPE_WIFI:
                WifiManager wifiManager = (WifiManager) this.context.getSystemService(Context.WIFI_SERVICE);
                wifiManager.setWifiEnabled(enable);
                break;
            case ConnectivityManager.TYPE_MOBILE:
                try{
                    // 2.1 <= OS <= 4.4
                    if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1
                            && android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                        setMobileDataStateBeforeLollipop(enable);
                    }else{ // OS > 4.4 and Rooted phone
                        setMobileDataState(enable);
                    }
                }catch(Exception ex){
                    Log.e(TAG,"Error while deactivating Mobile Network",ex);
                    Toast.makeText(this.context,"Error while deactivating Mobile Network" + ex.getMessage(),Toast.LENGTH_LONG).show();
                }
                break;
            case ConnectivityManager.TYPE_BLUETOOTH:
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (enable && !mBluetoothAdapter.isEnabled()){
                        mBluetoothAdapter.enable();
                }else if (!enable && mBluetoothAdapter.isEnabled()){
                        mBluetoothAdapter.disable();
                }
                break;
            default: ;
        }

    }

    /**
     * For enabling/disabling the Mobile Data connection (3g/4g)
     * @deprecated it only works with Android 2.1 (API 7) to 4.4 (API 19).
     *             Deprecated on Android 5 (API 21) Lollipop and above.
     * @param enabled
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    private void setMobileDataStateBeforeLollipop(boolean enabled) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        final ConnectivityManager conman = (ConnectivityManager)  this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final Class conmanClass = Class.forName(conman.getClass().getName());
        final Field connectivityManagerField = conmanClass.getDeclaredField("mService");
        connectivityManagerField.setAccessible(true);
        final Object connectivityManager = connectivityManagerField.get(conman);
        final Class connectivityManagerClass =  Class.forName(connectivityManager.getClass().getName());
        final Method setMobileDataEnabledMethod = connectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
        setMobileDataEnabledMethod.setAccessible(true);

        setMobileDataEnabledMethod.invoke(connectivityManager, enabled);
    }

    /**
     * For enabling/disabling the Mobile Data connection (3g/4g)
     * Note: it only works with Android 5 (API 21) Lollipop and above.
     *       it requires a Rooted phone.
     */
    public void setMobileDataState(boolean enabled) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        TelephonyManager telephonyService = (TelephonyManager) this.context.getSystemService(Context.TELEPHONY_SERVICE);
        Method setMobileDataEnabledMethod = telephonyService.getClass().getDeclaredMethod("setDataEnabled", boolean.class);
        if (null != setMobileDataEnabledMethod) {
            setMobileDataEnabledMethod.invoke(telephonyService, enabled);
        }
    }


    /**
     * It checks if the Mobile Data connection is Enabled in the device.
     * It uses different approaches depending on the Android OS version.
     * @return true if the Mobile Data is enabled, false otherwise
     */
    private boolean isMobileDataEnabled(){
        boolean isEnabled = false;
        TelephonyManager tm = (TelephonyManager) this.context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm.getSimState() == TelephonyManager.SIM_STATE_READY) {
            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
                isEnabled = Settings.Global.getInt(context.getContentResolver(), "mobile_data", 1) == 1;
            }
            else{
                isEnabled = Settings.Secure.getInt(context.getContentResolver(), "mobile_data", 1) == 1;
            }
        }
        return isEnabled;
    }

    /**
     * It checks if the Mobile Data connection is Enabled in the device using
     * a Java Reflection approach.
     * Note: it does not work with Android Lollipop version and above
     * @return true if the Mobile Data is enabled, false otherwise
     */
    private boolean isMobileDataEnabledByReflection(){
        boolean isEnabled = false;
        ConnectivityManager cm = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            Class cmClass = Class.forName(cm.getClass().getName());
            Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
            method.setAccessible(true); // Make the method callable
            // get the setting for "mobile data"
            isEnabled = (Boolean)method.invoke(cm);
        }catch(Exception ex){
            Log.d(TAG,"Error while querying Mobile Network",ex);
        }
        return isEnabled;
    }



    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
