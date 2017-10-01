package com.nexti.android.dragonglass.view.activity.unlock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nexti.android.dragonglass.R;
import com.nexti.android.dragonglass.service.LockMeNowService;

public class UnlockActivity extends Activity {

    private EditText txtUnlockPass;
    private Button btnUnlock;

    private static final String password = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock);

        initGuiControls();
    }

    @Override
    public void onBackPressed() {
        // Do nothing on Back button pressed
        showHomeScreen();
    }

    private void initGuiControls() {
        txtUnlockPass = (EditText) findViewById(R.id.txtUnlockPass);
        btnUnlock = (Button) findViewById(R.id.btnUnlock);
    }

    public void removeLock(View view) {
        String pwdUser = txtUnlockPass.getText().toString();
        if (password.equals(pwdUser)){
            //Unlock
            //this.finish();
            this.moveTaskToBack(true);
        }else{
            // Show home screen
            showHomeScreen();
        }
    }

    public boolean showHomeScreen(){
        LockMeNowService.LAST_LOCKED_PACKAGE_NAME=LockMeNowService.CURRENT_PACKAGE_NAME;
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        return true;
    }
}
