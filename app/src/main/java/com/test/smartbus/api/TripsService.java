package com.test.smartbus.api;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Artem on 2/17/17.
 */

public interface TripsService {

    @GET("api/trips")

    Call<Trips> getTripsByDate(@Query("from_date") String fromDate, @Query("to_date") String toDate);

    Retrofit builder = new Retrofit.Builder()
            .baseUrl("http://smartbus.gmoby.org/web/index.php/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
