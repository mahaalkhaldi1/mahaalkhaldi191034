package com.app.usersapp.Activities.FirebaseActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.app.usersapp.Models.UserObject;
import com.app.usersapp.R;
import com.app.usersapp.Utils.Consts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseViewUserActivity extends AppCompatActivity {

    private TextView first_name,last_name,phone,email;

    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_view_user);

        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);

        database = FirebaseDatabase.getInstance(Consts.database_url).getReference("Users");

        getData();
    }

    private void getData(){
        database.child(String.valueOf(getIntent().getIntExtra("id",0))).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserObject userObject = snapshot.getValue(UserObject.class);
                first_name.setText(userObject.getFirstName());
                last_name.setText(userObject.getLastName());
                phone.setText(userObject.getPhoneNumber());
                email.setText(userObject.getEmailAddress());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
