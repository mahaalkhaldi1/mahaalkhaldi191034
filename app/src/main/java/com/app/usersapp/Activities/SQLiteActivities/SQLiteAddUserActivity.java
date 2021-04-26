package com.app.usersapp.Activities.SQLiteActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.usersapp.Activities.FirebaseActivities.FirebaseAddUserActivity;
import com.app.usersapp.Models.UserObject;
import com.app.usersapp.R;
import com.app.usersapp.Utils.Consts;
import com.app.usersapp.Utils.Database;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SQLiteAddUserActivity extends AppCompatActivity {

    private EditText first_name,last_name,phone,email;
    private LinearLayout add;

    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_add_user);

        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        add = findViewById(R.id.add);

        database = new Database(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (first_name.getText().toString().equals("")){
                    Toast.makeText(SQLiteAddUserActivity.this, "You must write first name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (last_name.getText().toString().equals("")){
                    Toast.makeText(SQLiteAddUserActivity.this, "You must write last name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.getText().toString().equals("")){
                    Toast.makeText(SQLiteAddUserActivity.this, "You must write phone", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (email.getText().toString().equals("")){
                    Toast.makeText(SQLiteAddUserActivity.this, "You must write email", Toast.LENGTH_SHORT).show();
                    return;
                }

                addUser();
            }
        });

        Consts.setWeather(SQLiteAddUserActivity.this);
    }

    void addUser(){
        long i = database.addUser(
                getIntent().getIntExtra("id",0),
                first_name.getText().toString(),
                last_name.getText().toString(),
                phone.getText().toString(),
                email.getText().toString()
        );
        if (i > 0){
            Toast.makeText(this, "User created successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
