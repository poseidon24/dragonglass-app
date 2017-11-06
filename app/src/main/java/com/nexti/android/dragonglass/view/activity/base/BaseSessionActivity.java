package com.nexti.android.dragonglass.view.activity.base;

import com.nexti.android.dragonglass.bo.UserSessionBO;
import com.nexti.android.dragonglass.network.BaseRetrofitSpiceService;
import com.octo.android.robospice.SpiceManager;

/**
 * Created by ISCesar on 02/08/2017.
 */
public abstract class BaseSessionActivity extends  BaseDbActivity {

    private SpiceManager spiceManager = new SpiceManager(BaseRetrofitSpiceService.class);
    private UserSessionBO userSessionBO;

    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
        userSessionBO = new UserSessionBO(this,getDataSource(), 1);
    }

    @Override
    protected void onStop() {
        userSessionBO = null;
        spiceManager.shouldStop();
        super.onStop();
    }

    protected UserSessionBO getUserSessionBO() {
        return userSessionBO;
    }

    protected SpiceManager getSpiceManager() {
        return spiceManager;
    }
}
