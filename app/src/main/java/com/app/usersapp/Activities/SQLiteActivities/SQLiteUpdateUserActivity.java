package com.app.usersapp.Activities.SQLiteActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.usersapp.Activities.FirebaseActivities.FirebaseUpdateUserActivity;
import com.app.usersapp.Models.UserObject;
import com.app.usersapp.R;
import com.app.usersapp.Utils.Consts;
import com.app.usersapp.Utils.Database;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SQLiteUpdateUserActivity extends AppCompatActivity {

    private EditText first_name, last_name, phone, email;
    private LinearLayout update;

    private Database database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_update_user);

        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        update = findViewById(R.id.update);

        database = new Database(this);

        getData();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (first_name.getText().toString().equals("")) {
                    Toast.makeText(SQLiteUpdateUserActivity.this, "You must write first name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (last_name.getText().toString().equals("")) {
                    Toast.makeText(SQLiteUpdateUserActivity.this, "You must write last name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.getText().toString().equals("")) {
                    Toast.makeText(SQLiteUpdateUserActivity.this, "You must write phone", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (email.getText().toString().equals("")) {
                    Toast.makeText(SQLiteUpdateUserActivity.this, "You must write email", Toast.LENGTH_SHORT).show();
                    return;
                }

                updateData(
                        getIntent().getIntExtra("id", 0),
                        first_name.getText().toString(),
                        last_name.getText().toString(),
                        phone.getText().toString(),
                        email.getText().toString()
                );
            }
        });
        Consts.setWeather(SQLiteUpdateUserActivity.this);
    }

    private void getData() {
        UserObject userObject = database.getUserById(getIntent().getIntExtra("id", 0));
        first_name.setText(userObject.getFirstName());
        last_name.setText(userObject.getLastName());
        phone.setText(userObject.getPhoneNumber());
        email.setText(userObject.getEmailAddress());
    }

    private void updateData(
            int userId,
            String first_name,
            String last_name,
            String phone,
            String email
    ) {
        int i = database.updateUser(
                userId,
                first_name,
                last_name,
                phone,
                email
        );
        if (i > 0){
            Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
