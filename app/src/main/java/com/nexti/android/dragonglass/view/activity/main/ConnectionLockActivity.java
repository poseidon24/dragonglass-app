package com.nexti.android.dragonglass.view.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.nexti.android.dragonglass.R;
import com.nexti.android.dragonglass.listener.IncomingCallReceiver;
import com.nexti.android.dragonglass.listener.OutgoingCallReceiver;
import com.nexti.android.dragonglass.util.NetworkUtil;

public class ConnectionLockActivity extends Activity implements CompoundButton.OnCheckedChangeListener {

    // GUI controls
    private boolean wifiOn = true;
    private boolean bluetoothOn = true;
    private boolean mobileNetworkOn = true;
    private boolean blockCalls = false;
    private Switch swtWifi;
    private Switch swtBluetooth;
    private Switch swtMobileNet;
    private Switch swtCalls;
    private EditText txtRedirPhone;

    // Business attributes and variables
    private NetworkUtil networkUtil;
        // auxiliar variable for avoid auto-fired onCheckedChanged when the Activity is being created or resumed
    private boolean isReadingState = false;
    //public static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 101;
    private OutgoingCallReceiver outgoingCallReceiver = null;
    private IncomingCallReceiver incomingCallReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_lock);

        initValues();
        initGuiControls();
        initGuiValues();

        //requestPermissionsCalls();
    }

    @Override
    protected void onResume() {
        super.onResume();

        initGuiValues();
    }

    private void initValues() {
        networkUtil = new NetworkUtil(this);
        outgoingCallReceiver = new OutgoingCallReceiver();
        incomingCallReceiver = new IncomingCallReceiver();
    }

    private void initGuiControls() {
        swtWifi = (Switch) findViewById(R.id.swtWifi);
        swtBluetooth = (Switch) findViewById(R.id.swtBluetooth);
        swtMobileNet = (Switch) findViewById(R.id.swtMobileNet);
        swtCalls = (Switch) findViewById(R.id.swtCalls);
        txtRedirPhone = (EditText) findViewById(R.id.txtRedirPhone);

        // init Listeners
        swtWifi.setOnCheckedChangeListener(this);
        swtBluetooth.setOnCheckedChangeListener(this);
        swtMobileNet.setOnCheckedChangeListener(this);
        swtCalls.setOnCheckedChangeListener(this);
    }

    private void initGuiValues() {
        wifiOn = networkUtil.checkNetworkState(ConnectivityManager.TYPE_WIFI);
        bluetoothOn = networkUtil.checkNetworkState(ConnectivityManager.TYPE_BLUETOOTH);
        mobileNetworkOn = networkUtil.checkNetworkState(ConnectivityManager.TYPE_MOBILE);
        blockCalls = outgoingCallReceiver.isRegistered() && incomingCallReceiver.isRegistered();

        isReadingState = true;
        swtWifi.setChecked(wifiOn);
        swtBluetooth.setChecked(bluetoothOn);
        swtMobileNet.setChecked(mobileNetworkOn);
        swtCalls.setChecked(blockCalls);
        isReadingState = false;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isReadingState) {
            switch (buttonView.getId()) {
                case R.id.swtWifi:
                    networkUtil.switchNetwork(ConnectivityManager.TYPE_WIFI, isChecked);
                    break;
                case R.id.swtBluetooth:
                    networkUtil.switchNetwork(ConnectivityManager.TYPE_BLUETOOTH, isChecked);
                    break;
                case R.id.swtMobileNet:
                    networkUtil.switchNetwork(ConnectivityManager.TYPE_MOBILE, isChecked);
                    break;
                case R.id.swtCalls:
                    if (isChecked) {
                        //enableBroadcastReceiver();
                        enableRedirectOutGoingCalls();
                        enableBlockIncomingCalls();
                    } else {
                        //disableBroadcastReceiver();
                        disableRedirectOutGoingCalls();
                        disableBlockIncomingCalls();
                    }
                    break;
                default:
                    ;
            }
        }

    }

    private void enableRedirectOutGoingCalls(){
        String redirectNumber = txtRedirPhone.getText().toString();
        registerOutGoingCallReceiver(redirectNumber);
        Toast.makeText(this, "Redirecting calls to '" + redirectNumber + "' number", Toast.LENGTH_LONG).show();
    }

    private void disableRedirectOutGoingCalls(){
        unregisterOutGoingCallReceiver();
        Toast.makeText(this, "Redirecting disable", Toast.LENGTH_LONG).show();
    }

    private void registerOutGoingCallReceiver(String number) {
        outgoingCallReceiver.setNumber(number);
        IntentFilter filter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
        registerReceiver(outgoingCallReceiver, filter);
        outgoingCallReceiver.setRegistered(true);
    }

    private void unregisterOutGoingCallReceiver(){
        if (outgoingCallReceiver.isRegistered()) {
            unregisterReceiver(outgoingCallReceiver);
            outgoingCallReceiver.setRegistered(false);
        }
    }

    private void enableBlockIncomingCalls(){
        registerIncomingCallReceiver();
        Toast.makeText(this, "Blocking incoming calls enabled", Toast.LENGTH_LONG).show();
    }

    private void disableBlockIncomingCalls(){
        unregisterIncomingCallReceiver();
        Toast.makeText(this, "Blocking incoming calls Disabled", Toast.LENGTH_LONG).show();
    }

    private void registerIncomingCallReceiver() {
        IntentFilter filter = new IntentFilter("android.intent.action.PHONE_STATE");
        registerReceiver(incomingCallReceiver, filter);
        incomingCallReceiver.setRegistered(true);
    }

    private void unregisterIncomingCallReceiver(){
        if (incomingCallReceiver.isRegistered()) {
            unregisterReceiver(incomingCallReceiver);
            incomingCallReceiver.setRegistered(false);
        }
    }

}
