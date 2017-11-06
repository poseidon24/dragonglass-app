package com.nexti.android.dragonglass.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.nexti.android.dragonglass.bo.DgMobileBO;
import com.nexti.android.dragonglass.bo.UserSessionBO;
import com.nexti.android.dragonglass.db.DataSource;
import com.nexti.android.dragonglass.model.dto.DgMobileDto;
import com.nexti.android.dragonglass.model.dto.IDto;
import com.nexti.android.dragonglass.model.entity.DgMobileEntity;
import com.nexti.android.dragonglass.network.BaseRetrofitSpiceService;
import com.nexti.android.dragonglass.network.MobileAccountRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public class UpdateServerDataService extends Service {

    private final static String TAG = LockMeNowService.class.getSimpleName();
    private static final String CACHE_KEY_MOBILE_ACCOUNT = "mobile_account";

    public static UpdateServerDataService instance;
    public static boolean stopped = true;
    private DataSource dataSource;

    private MobileAccountRequest mobileAccountRequest;
    private SpiceManager spiceManager = new SpiceManager(BaseRetrofitSpiceService.class);

    public UpdateServerDataService() {
        super();
    }

    @Override
    public void onCreate() {
        spiceManager.start(getApplicationContext());
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        spiceManager.shouldStop();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        instance = this;
        stopped = false;

        updateDgMobile();

        return START_NOT_STICKY;
    }

    public static void stop() {
        Log.d(TAG,"Stopping Service");
        if (instance != null) {
            stopped = true;
            instance.stopSelf();
            Log.d(TAG,"Service stopped");
        }
    }


    private void updateDgMobile(){

        UserSessionBO userSessionBO = new UserSessionBO(getApplicationContext(), getDataSource());

        Log.d(TAG, "Updating data from WS. Data sent: " + userSessionBO.getUserSessionEntity());
        mobileAccountRequest = new MobileAccountRequest(MobileAccountRequest.Method.GET_BY_ID);
        mobileAccountRequest.setIdMobile(userSessionBO.getUserSessionEntity().getId());
        spiceManager.execute(mobileAccountRequest, CACHE_KEY_MOBILE_ACCOUNT, DurationInMillis.ONE_MINUTE, new MobileAccountRequestListener());

    }

    private void processWsMobileAccountResponse(final IDto iDto){
        DgMobileDto dgMobileDto = (DgMobileDto) iDto;
        if (dgMobileDto!=null){
            DgMobileBO dgMobileBO = new DgMobileBO(getApplicationContext(),getDataSource());
            DgMobileEntity dgMobileEntity = dgMobileBO.convertDtoToEntity(dgMobileDto);
            dgMobileEntity.setId(1); // ever update a unique record
            dgMobileBO.save(dgMobileEntity);
        }
    }

    public DataSource getDataSource() {
        if (dataSource==null){
            dataSource = new DataSource(getApplicationContext());
        }
        if (dataSource.getDb()!=null && !dataSource.getDb().isOpen()){
            dataSource.open(false);
        }
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

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
