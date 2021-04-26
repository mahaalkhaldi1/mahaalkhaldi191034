package com.app.usersapp.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.app.usersapp.R;

public class Consts {
    public static final String database_url = "https://mahaalkhaldi191034-default-rtdb.firebaseio.com/";
    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    public static void setWeather(Activity context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Main",Context.MODE_PRIVATE);
        TextView clouds = context.findViewById(R.id.clouds);
        TextView temp = context.findViewById(R.id.temp);
        TextView humidity = context.findViewById(R.id.humidity);

        //If clouds is still having the default value then the other measures are definitely having the defaults also.
        if (sharedPreferences.getFloat("clouds",0.0f) == 0.0f){
            clouds.setText("Clouds : You should get weather first");
            temp.setText("Temperature : You should get weather first");
            humidity.setText("Humidity : You should get weather first");
        }else {
            clouds.setText("Clouds : "+sharedPreferences.getFloat("clouds",0.0f));
            temp.setText("Temperature : "+sharedPreferences.getFloat("temp",0.0f));
            humidity.setText("Humidity : "+sharedPreferences.getFloat("humidity",0.0f));
        }
    }
}
