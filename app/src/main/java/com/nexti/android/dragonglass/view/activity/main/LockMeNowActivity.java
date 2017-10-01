/***
  Copyright (c) 2012 CommonsWare, LLC
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
  by applicable law or agreed to in writing, software distributed under the
  License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
  OF ANY KIND, either express or implied. See the License for the specific
  language governing permissions and limitations under the License.
  
  Covered in detail in the book _The Busy Coder's Guide to Android Development_
    https://commonsware.com/Android
 */

package com.nexti.android.dragonglass.view.activity.main;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.WallpaperManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.nexti.android.dragonglass.listener.AdminReceiver;
import com.nexti.android.dragonglass.R;
import com.nexti.android.dragonglass.model.entity.UserSessionEntity;
import com.nexti.android.dragonglass.service.LockMeNowService;
import com.nexti.android.dragonglass.view.activity.BaseSessionActivity;

import java.io.IOException;

public class LockMeNowActivity extends BaseSessionActivity {
  private DevicePolicyManager mgr=null;
  private ComponentName cn=null;
  private Button buttonSetWallpaper;
  private ImageView imagePreview;
  private LockMeNowService lockMeNowService;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.main);
    cn=new ComponentName(this, AdminReceiver.class);
    mgr=(DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
    initControls();
  }

  @Override
  protected void onStart() {
    super.onStart();
    testDbConnection();
  }

  private void initControls(){
    buttonSetWallpaper = (Button)findViewById(R.id.btnWallpaper);
    imagePreview = (ImageView)findViewById(R.id.ivPreview);
  }

  private void testDbConnection(){
    if (getUserSessionBO()!=null) {
      UserSessionEntity uSession = getUserSessionBO().getUserSessionEntity();
      Toast.makeText(this, uSession.toString(), Toast.LENGTH_LONG).show();
    }
  }

  public void lockMeNow(View v) {
    if (mgr.isAdminActive(cn)) {
      mgr.lockNow();
    }
    else {
      Intent intent=
          new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
      intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, cn);
      intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                      getString(R.string.device_admin_explanation));
      startActivity(intent);
    }
  }

  public void changeWallpaper(View v){
    imagePreview.setImageResource(R.drawable.leonidas_cabo);

    buttonSetWallpaper.setOnClickListener(new Button.OnClickListener(){
      @Override
      public void onClick(View arg0) {
        WallpaperManager myWallpaperManager
                = WallpaperManager.getInstance(getApplicationContext());
        try {
          myWallpaperManager.setResource(+R.drawable.leonidas_cabo);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }});
  }


  public void startServiceLock(View view) {
    requestUsageStatsPermission();
    startService(new Intent(this, LockMeNowService.class));
  }


  public void stopServiceLock(View view) {
    stopService(new Intent(this, LockMeNowService.class));
  }

  private void requestUsageStatsPermission() {
    if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
            && !hasUsageStatsPermission(this)) {
      startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
    }
  }

  @TargetApi(Build.VERSION_CODES.KITKAT)
  private boolean hasUsageStatsPermission(Context context) {
    AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
    int mode = appOps.checkOpNoThrow("android:get_usage_stats",
            android.os.Process.myUid(), context.getPackageName());
    boolean granted = mode == AppOpsManager.MODE_ALLOWED;
    return granted;
  }


  public void gotoConnectionLockActivity(View view){
    Intent intent = new Intent(this, ConnectionLockActivity.class);
    startActivity(intent);
  }
}