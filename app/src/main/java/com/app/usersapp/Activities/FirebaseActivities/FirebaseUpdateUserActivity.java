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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseUpdateUserActivity extends AppCompatActivity {

    private EditText first_name,last_name,phone,email;
    private LinearLayout update;

    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        update = findViewById(R.id.update);

        database = FirebaseDatabase.getInstance(Consts.database_url).getReference("Users");

        getData();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (first_name.getText().toString().equals("")){
                    Toast.makeText(FirebaseUpdateUserActivity.this, "You must write first name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (last_name.getText().toString().equals("")){
                    Toast.makeText(FirebaseUpdateUserActivity.this, "You must write last name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.getText().toString().equals("")){
                    Toast.makeText(FirebaseUpdateUserActivity.this, "You must write phone", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (email.getText().toString().equals("")){
                    Toast.makeText(FirebaseUpdateUserActivity.this, "You must write email", Toast.LENGTH_SHORT).show();
                    return;
                }

                updateData(
                        getIntent().getIntExtra("id",0),
                        first_name.getText().toString(),
                        last_name.getText().toString(),
                        phone.getText().toString(),
                        email.getText().toString()
                );
            }
        });

        Consts.setWeather(FirebaseUpdateUserActivity.this);
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

    private void updateData(
            int userId,
            String first_name,
            String last_name,
            String phone,
            String email
    ){
        UserObject user = new UserObject();
        user.setUserId(userId);
        user.setFirstName(first_name);
        user.setLastName(last_name);
        user.setPhoneNumber(phone);
        user.setEmailAddress(email);
        database.child(String.valueOf(user.getUserId())).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(FirebaseUpdateUserActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(FirebaseUpdateUserActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
