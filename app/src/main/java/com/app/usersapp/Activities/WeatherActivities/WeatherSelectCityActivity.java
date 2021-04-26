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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.usersapp.API.RetrofitAPI;
import com.app.usersapp.Models.WeatherModels.WeatherResponse;
import com.app.usersapp.R;
import com.app.usersapp.Utils.Consts;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

public class WeatherSelectCityActivity extends AppCompatActivity {

    private EditText name;
    private Button save;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_select_city);

        name = findViewById(R.id.name);
        save = findViewById(R.id.save);

        sharedPreferences = getSharedPreferences("Main", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equals("")){
                    Toast.makeText(WeatherSelectCityActivity.this, "You must write city name", Toast.LENGTH_SHORT).show();
                    return;
                }
                editor.putString("city",name.getText().toString());
                editor.commit();
                getWeather();
            }
        });
    }

    private void getWeather(){
        final ProgressDialog dialog = new ProgressDialog(WeatherSelectCityActivity.this);
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
                    Toast.makeText(WeatherSelectCityActivity.this, "Weather data successfully saved!", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(WeatherSelectCityActivity.this, "City name not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                dialog.cancel();
                Toast.makeText(WeatherSelectCityActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
