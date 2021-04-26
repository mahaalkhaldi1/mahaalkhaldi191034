package com.app.usersapp.Models.WeatherModels;

import com.app.usersapp.Activities.WeatherActivities.WeatherMainActivity;

public class WeatherResponse {
    private WeatherCloudsObject clouds = new WeatherCloudsObject();
    private WeatherMainObject main = new WeatherMainObject();

    public WeatherCloudsObject getClouds() {
        return clouds;
    }

    public void setClouds(WeatherCloudsObject clouds) {
        this.clouds = clouds;
    }

    public WeatherMainObject getMain() {
        return main;
    }

    public void setMain(WeatherMainObject main) {
        this.main = main;
    }
}
