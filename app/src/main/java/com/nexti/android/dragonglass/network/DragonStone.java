package com.nexti.android.dragonglass.network;

import com.nexti.android.dragonglass.model.dto.DgMobileDto;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by ISCesar on 01/10/2017.
 */
public interface DragonStone {

    @GET("/mobileAccount")
    DgMobileDto.List mobileAccountList();

    @GET("/mobileAccount/{id}")
    DgMobileDto mobileAccountGet(@Path("id") int id);

}
