package com.app.usersapp.Activities.FirebaseActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.usersapp.Models.UserObject;
import com.app.usersapp.R;
import com.app.usersapp.Utils.Consts;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseAddUserActivity extends AppCompatActivity {


    private EditText first_name,last_name,phone,email;
    private LinearLayout add;

    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_firebase_user);

        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        add = findViewById(R.id.add);

        database = FirebaseDatabase.getInstance(Consts.database_url).getReference("Users");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (first_name.getText().toString().equals("")){
                    Toast.makeText(FirebaseAddUserActivity.this, "You must write first name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (last_name.getText().toString().equals("")){
                    Toast.makeText(FirebaseAddUserActivity.this, "You must write last name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.getText().toString().equals("")){
                    Toast.makeText(FirebaseAddUserActivity.this, "You must write phone", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (email.getText().toString().equals("")){
                    Toast.makeText(FirebaseAddUserActivity.this, "You must write email", Toast.LENGTH_SHORT).show();
                    return;
                }

                addUser();
            }
        });

        Consts.setWeather(FirebaseAddUserActivity.this);
    }

    void addUser(){
        UserObject user = new UserObject();
        user.setUserId(getIntent().getIntExtra("id",0));
        user.setFirstName(first_name.getText().toString());
        user.setLastName(last_name.getText().toString());
        user.setEmailAddress(email.getText().toString());
        user.setPhoneNumber(phone.getText().toString());
        database.child(String.valueOf(user.getUserId())).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(FirebaseAddUserActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(FirebaseAddUserActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
