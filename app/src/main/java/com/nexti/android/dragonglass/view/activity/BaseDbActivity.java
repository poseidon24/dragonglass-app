package com.nexti.android.dragonglass.view.activity;

import android.app.Activity;

import com.nexti.android.dragonglass.db.DataSource;

/**
 * Created by ISCesar on 02/08/2017.
 */
public abstract class BaseDbActivity extends Activity {

    private DataSource dataSource = new DataSource(this);

    @Override
    protected void onStart() {
        super.onStart();
        dataSource.open();
    }

    @Override
    protected void onStop() {
        dataSource.close();
        super.onStop();
    }

    public DataSource getDataSource() {
        return dataSource;
    }

}
