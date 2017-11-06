package com.nexti.android.dragonglass.network;

import android.util.Log;

import com.nexti.android.dragonglass.model.dto.IDto;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

/**
 * Created by ISCesar on 01/10/2017.
 */
public class MobileAccountRequest extends RetrofitSpiceRequest<IDto, DragonStone> {

    public enum Method {
        LIST_ALL,
        GET_BY_ID
    }

    private static final String TAG = "MobileAccountRequest";
    private Method method;

    public MobileAccountRequest(Method method) {
        super(IDto.class, DragonStone.class);
        this.method = method;
    }

    @Override
    public IDto loadDataFromNetwork() {
        Log.d(TAG, "Call web service.");
        switch (this.method){
            case LIST_ALL:
                break;
            case GET_BY_ID:
                return getService().mobileAccountGet(getIdMobile());
            default:
        }
        return null;
    }

    // WS Methods atributes
    private int idMobile;

    public int getIdMobile() {
        return idMobile;
    }

    public void setIdMobile(int idMobile) {
        this.idMobile = idMobile;
    }
}
