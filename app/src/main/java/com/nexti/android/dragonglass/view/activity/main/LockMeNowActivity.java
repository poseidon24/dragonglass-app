package com.nexti.android.dragonglass.view.activity.main;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.WallpaperManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.nexti.android.dragonglass.bo.DgMobileBO;
import com.nexti.android.dragonglass.listener.AdminReceiver;
import com.nexti.android.dragonglass.R;
import com.nexti.android.dragonglass.model.dto.DgMobileDto;
import com.nexti.android.dragonglass.model.dto.IDto;
import com.nexti.android.dragonglass.model.entity.DgMobileEntity;
import com.nexti.android.dragonglass.model.entity.UserSessionEntity;
import com.nexti.android.dragonglass.network.MobileAccountRequest;
import com.nexti.android.dragonglass.service.LockMeNowService;
import com.nexti.android.dragonglass.view.activity.base.BaseSessionActivity;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.springframework.beans.BeanUtils;

import java.io.IOException;

public class LockMeNowActivity extends BaseSessionActivity {
  private final static String TAG = "LockMeNowActivity";
  private DevicePolicyManager mgr=null;
  private ComponentName cn=null;
  private Button buttonSetWallpaper;
  private ImageView imagePreview;
  private MobileAccountRequest mobileAccountRequest;

  private static final String CACHE_KEY_MOBILE_ACCOUNT = "mobile_account";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.main);
    cn=new ComponentName(this, AdminReceiver.class);
    mgr=(DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
    initControls();
    initWsContext();
  }

  @Override
  protected void onStart() {
    super.onStart();
    //testDbConnection();
    requestMobileAccountData();
  }

  private void initControls(){
    buttonSetWallpaper = (Button)findViewById(R.id.btnWallpaper);
    imagePreview = (ImageView)findViewById(R.id.ivPreview);
  }

  private void initWsContext(){
    mobileAccountRequest = new MobileAccountRequest(MobileAccountRequest.Method.GET_BY_ID);
    mobileAccountRequest.setIdMobile(1);
  }

  private void requestMobileAccountData(){
    getSpiceManager().execute(mobileAccountRequest, CACHE_KEY_MOBILE_ACCOUNT, DurationInMillis.ONE_MINUTE, new MobileAccountRequestListener());
  }

  private void testDbConnection(){
    if (getUserSessionBO()!=null) {
      UserSessionEntity uSession = getUserSessionBO().getUserSessionEntity();
      Log.d(TAG, uSession.toString());
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
    if (LockMeNowService.stopped) {
      requestUsageStatsPermission();
      startService(new Intent(this, LockMeNowService.class));
    }
  }


  public void stopServiceLock(View view) {
    if (!LockMeNowService.stopped)
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
    return mode == AppOpsManager.MODE_ALLOWED;
  }


  public void gotoConnectionLockActivity(View view){
    Intent intent = new Intent(this, ConnectionLockActivity.class);
    startActivity(intent);
  }


  private void processWsMobileAccountResponse(final IDto iDto){
      DgMobileDto dgMobileDto = (DgMobileDto) iDto;
      if (dgMobileDto!=null){
        DgMobileBO dgMobileBO = new DgMobileBO(getApplicationContext(),getDataSource());
        DgMobileEntity dgMobileEntity = dgMobileBO.convertDtoToEntity(dgMobileDto);
        dgMobileEntity.setId(1); // ever update a unique record
        dgMobileBO.save(dgMobileEntity);

        if (dgMobileDto.getAppsAccessEnabled()==0
                || dgMobileDto.getAppsAccessEnabled()==2
                || dgMobileDto.getAppsAccessEnabled()==3){
          //lock apps access
          startServiceLock(null);
        }else{
          //unlock apps access
          stopServiceLock(null);
        }
      }
  }

  // ============================================================================================
  // INNER CLASSES
  // ============================================================================================

  public final class MobileAccountRequestListener implements RequestListener<IDto> {

    @Override
    public void onRequestFailure(SpiceException spiceException) {
      Log.e(TAG, "WS response failure.", spiceException);
    }

    @Override
    public void onRequestSuccess(final IDto result) {
      Log.d(TAG, "WS response success: " + result.toString());
      processWsMobileAccountResponse(result);
    }
  }
}