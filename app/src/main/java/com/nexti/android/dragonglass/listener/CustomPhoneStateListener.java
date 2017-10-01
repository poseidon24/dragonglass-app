package com.nexti.android.dragonglass.listener;

import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by ISCesar on 23/07/2017.
 */
public class CustomPhoneStateListener extends PhoneStateListener {
    private final String TAG = this.getClass().getSimpleName();
    private Context context;

    public CustomPhoneStateListener(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void onCallStateChanged(int state, String callingNumber)
    {
        super.onCallStateChanged(state, callingNumber);
        Log.d(TAG,"Call State Changed. State: " + state + ". Calling number: " + callingNumber);
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                //endCall(callingNumber);
                declinePhone(context);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                //handle out going call
                //endCall(callingNumber);
                declinePhone(context);
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                //handle in coming call
                //endCall(callingNumber);
                declinePhone(context);
                break;
            default:
                break;
        }
    }

    private void endCall(String outGoingNumber) {
        try {
            Log.d(TAG,"Trying to end Call");
            // Java reflection to gain access to TelephonyManager's
            // ITelephony getter
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Class c = Class.forName(tm.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            Object telephonyService = m.invoke(tm); // Get the internal ITelephony object
            c = Class.forName(telephonyService.getClass().getName()); // Get its class
            m = c.getDeclaredMethod("endCall"); // Get the "endCall()" method
            m.setAccessible(true); // Make it accessible
            Log.d(TAG,"Invoking endCall method");
            m.invoke(telephonyService); // invoke endCall()

        } catch (Exception e) {
            Log.e(TAG,"Error while ending call", e);
        }
    }

    private void declinePhone(Context context) {

        try {

            String serviceManagerName = "android.os.ServiceManager";
            String serviceManagerNativeName = "android.os.ServiceManagerNative";
            String telephonyName = "com.android.internal.telephony.ITelephony";
            Class<?> telephonyClass;
            Class<?> telephonyStubClass;
            Class<?> serviceManagerClass;
            Class<?> serviceManagerNativeClass;
            Method telephonyEndCall;
            Object telephonyObject;
            Object serviceManagerObject;
            telephonyClass = Class.forName(telephonyName);
            telephonyStubClass = telephonyClass.getClasses()[0];
            serviceManagerClass = Class.forName(serviceManagerName);
            serviceManagerNativeClass = Class.forName(serviceManagerNativeName);
            Method getService = // getDefaults[29];
                    serviceManagerClass.getMethod("getService", String.class);
            Method tempInterfaceMethod = serviceManagerNativeClass.getMethod("asInterface", IBinder.class);
            Binder tmpBinder = new Binder();
            tmpBinder.attachInterface(null, "fake");
            serviceManagerObject = tempInterfaceMethod.invoke(null, tmpBinder);
            IBinder retbinder = (IBinder) getService.invoke(serviceManagerObject, "phone");
            Method serviceMethod = telephonyStubClass.getMethod("asInterface", IBinder.class);
            telephonyObject = serviceMethod.invoke(null, retbinder);
            telephonyEndCall = telephonyClass.getMethod("endCall");
            Log.d(TAG,"Invoking endCall method");
            telephonyEndCall.invoke(telephonyObject);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("unable", "msg cant dissconect call....");
        }
    }
}
