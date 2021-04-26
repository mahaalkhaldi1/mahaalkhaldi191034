package com.app.usersapp.Activities.WeatherActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.usersapp.API.RetrofitAPI;
import com.app.usersapp.Models.WeatherModels.WeatherMainObject;
import com.app.usersapp.Models.WeatherModels.WeatherResponse;
import com.app.usersapp.R;
import com.app.usersapp.Utils.Consts;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

public class WeatherMainActivity extends AppCompatActivity {

    private Button get_athens,get_another;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_main);

        get_athens = findViewById(R.id.athens);
        get_another = findViewById(R.id.get_another);

        sharedPreferences = getSharedPreferences("Main", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        get_athens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("city","Athens");
                editor.commit();
                Toast.makeText(WeatherMainActivity.this, "Athens is selected successfully", Toast.LENGTH_SHORT).show();
                getWeather();
            }
        });

        get_another.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherMainActivity.this,WeatherSelectCityActivity.class);
                startActivity(intent);
            }
        });

        getWeather();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weather_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.clouds:{
                Intent intent = new Intent(WeatherMainActivity.this,WeatherCloudsActivity.class);
                startActivity(intent);
            }break;
            case R.id.temp:{
                Intent intent = new Intent(WeatherMainActivity.this,WeatherTempActivity.class);
                startActivity(intent);
            }break;
            case R.id.humidity:{
                Intent intent = new Intent(WeatherMainActivity.this,WeatherHumidityActivity.class);
                startActivity(intent);
            }break;
        }
        return true;
    }

    private void getWeather(){
        final ProgressDialog dialog = new ProgressDialog(WeatherMainActivity.this);
        dialog.setMessage("Getting weather, please wait..");
        dialog.show();

        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(25, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder().client(client).baseUrl(Consts.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI api = retrofit.create(RetrofitAPI.class);
        Call<WeatherResponse> call = api.getWeather(sharedPreferences.getString("city","Athens"),
                "775d6bc788b7f536360e6f05a4c5f59e",
                "metric");
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                dialog.cancel();
                if (response.isSuccessful()){
                    String json = new Gson().toJson(response.body());
                    WeatherResponse success = new Gson().fromJson(json,WeatherResponse.class);
                    editor.putFloat("clouds",success.getClouds().getAll());
                    editor.putFloat("temp",success.getMain().getTemp());
                    editor.putFloat("humidity",success.getMain().getHumidity());
                    editor.commit();
                    Toast.makeText(WeatherMainActivity.this, "Weather data successfully saved!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(WeatherMainActivity.this, "City name not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                dialog.cancel();
                Toast.makeText(WeatherMainActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
