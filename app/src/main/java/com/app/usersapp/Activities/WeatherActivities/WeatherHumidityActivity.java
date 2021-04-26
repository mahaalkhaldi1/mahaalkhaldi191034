package com.app.usersapp.Activities.WeatherActivities;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.app.usersapp.API.RetrofitAPI;
import com.app.usersapp.Models.WeatherModels.WeatherResponse;
import com.app.usersapp.R;
import com.app.usersapp.Utils.Consts;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

public class WeatherHumidityActivity extends AppCompatActivity {

    private TextView value;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_humidity);

        value = findViewById(R.id.value);

        sharedPreferences = getSharedPreferences("Main", Context.MODE_PRIVATE);

        getHumidity();
    }

    private void getHumidity() {
        value.setText(String.valueOf(Math.round(sharedPreferences.getFloat("humidity",0.0f))));
    }
}
