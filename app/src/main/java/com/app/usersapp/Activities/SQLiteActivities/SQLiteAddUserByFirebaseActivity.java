package com.app.usersapp.Activities.SQLiteActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.usersapp.Activities.FirebaseActivities.FirebaseAddUserActivity;
import com.app.usersapp.Activities.FirebaseActivities.FirebaseMainActivity;
import com.app.usersapp.Adapters.AddUserAdapter;
import com.app.usersapp.Adapters.UsersAdapter;
import com.app.usersapp.Interfaces.OnUserSelect;
import com.app.usersapp.Models.UserObject;
import com.app.usersapp.R;
import com.app.usersapp.Utils.Consts;
import com.app.usersapp.Utils.Database;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SQLiteAddUserByFirebaseActivity extends AppCompatActivity implements OnUserSelect {

    private RecyclerView recycler;
    private List<UserObject> list;
    private AddUserAdapter adapter;
    private LinearLayoutManager layoutManager;

    private DatabaseReference fireDatabase;
    private Database sqliDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_add_user_by_firebase);

        recycler = findViewById(R.id.recycler);
        list = new ArrayList<>();
        adapter = new AddUserAdapter(this,list,this);
        layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);

        fireDatabase = FirebaseDatabase.getInstance(Consts.database_url).getReference("Users");
        sqliDatabase = new Database(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getUsers();
    }

    void getUsers(){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait..");
        dialog.show();

        fireDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                adapter = new AddUserAdapter(SQLiteAddUserByFirebaseActivity.this,list,SQLiteAddUserByFirebaseActivity.this);
                recycler.setAdapter(adapter);
                for (DataSnapshot userSnap : snapshot.getChildren()){
                    UserObject user = userSnap.getValue(UserObject.class);
                    list.add(user);
                    adapter.notifyDataSetChanged();
                }

                if (list.size() == 0){
                    Toast.makeText(SQLiteAddUserByFirebaseActivity.this, "No users", Toast.LENGTH_SHORT).show();
                }

                dialog.cancel();
                fireDatabase.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onUserSelectedListener(int position) {
        long i = sqliDatabase.addUser(getIntent().getIntExtra("id",0),
                list.get(position).getFirstName(),
                list.get(position).getLastName(),
                list.get(position).getPhoneNumber(),
                list.get(position).getEmailAddress());
        if (i > 0){
            Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
