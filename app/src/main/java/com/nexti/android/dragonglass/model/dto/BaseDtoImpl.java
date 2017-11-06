package com.nexti.android.dragonglass.model.dto;

import com.google.gson.Gson;

/**
 * Created by ISCesar on 10/10/2017.
 */
public abstract class BaseDtoImpl implements IDto{

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
