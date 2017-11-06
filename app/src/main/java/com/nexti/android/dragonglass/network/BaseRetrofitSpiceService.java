package com.nexti.android.dragonglass.network;

import com.nexti.android.dragonglass.config.Configuration;
import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

/**
 * Created by ISCesar on 01/10/2017.
 */
public class BaseRetrofitSpiceService extends RetrofitGsonSpiceService {

    //private final static String BASE_URL = "http://192.168.0.15:8080";//"http://192.168.1.73:8080";

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(DragonStone.class);
    }

    @Override
    protected String getServerUrl() {
        return Configuration.getInstance().getUrlWS();
    }
}
