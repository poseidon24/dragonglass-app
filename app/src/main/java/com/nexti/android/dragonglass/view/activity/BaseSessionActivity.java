package com.nexti.android.dragonglass.view.activity;

import com.nexti.android.dragonglass.bo.UserSessionBO;

/**
 * Created by ISCesar on 02/08/2017.
 */
public abstract class BaseSessionActivity extends  BaseDbActivity {

    private UserSessionBO userSessionBO;

    @Override
    protected void onStart() {
        super.onStart();
        userSessionBO = new UserSessionBO(this,getDataSource(), 1);
    }

    @Override
    protected void onStop() {
        userSessionBO = null;
        super.onStop();
    }

    protected UserSessionBO getUserSessionBO() {
        return userSessionBO;
    }
}
