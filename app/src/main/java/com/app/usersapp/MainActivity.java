package com.app.usersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.usersapp.Activities.FirebaseActivities.FirebaseMainActivity;
import com.app.usersapp.Activities.SQLiteActivities.SQLiteMainActivity;
import com.app.usersapp.Activities.WeatherActivities.WeatherMainActivity;

public class MainActivity extends AppCompatActivity {

    private Button firebase_users,sqli_users,weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebase_users = findViewById(R.id.firebase_users);
        sqli_users = findViewById(R.id.sqli_users);
        weather = findViewById(R.id.weather);

        firebase_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FirebaseMainActivity.class);
                startActivity(intent);
            }
        });
        sqli_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SQLiteMainActivity.class);
                startActivity(intent);
            }
        });

        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WeatherMainActivity.class);
                startActivity(intent);
            }
        });
    }
}
