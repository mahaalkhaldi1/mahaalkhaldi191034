package com.app.usersapp.API;

import com.app.usersapp.Models.WeatherModels.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitAPI {

    @GET("weather")
    Call<WeatherResponse> getWeather(
            @Query("q") String city_name,
            @Query("appid") String appId,
            @Query("units") String units
    );
}
