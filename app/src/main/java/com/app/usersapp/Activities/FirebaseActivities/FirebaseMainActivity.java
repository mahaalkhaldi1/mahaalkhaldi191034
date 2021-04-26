package com.app.usersapp.Activities.FirebaseActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.usersapp.Adapters.UsersAdapter;
import com.app.usersapp.Interfaces.OnUserInteract;
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

import java.util.ArrayList;
import java.util.List;

public class FirebaseMainActivity extends AppCompatActivity implements OnUserInteract {

    private Button add_user;
    private RecyclerView recycler;
    private List<UserObject> list;
    private UsersAdapter adapter;
    private LinearLayoutManager layoutManager;

    private DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_main);

        add_user = findViewById(R.id.add_user);
        recycler = findViewById(R.id.recycler);
        list = new ArrayList<>();
        adapter = new UsersAdapter(this,list,this);
        layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);

        database = FirebaseDatabase.getInstance(Consts.database_url).getReference("Users");

        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() > 0) {
                    Intent intent = new Intent(FirebaseMainActivity.this, FirebaseAddUserActivity.class);
                    intent.putExtra("id", list.get(list.size() - 1).getUserId() + 1);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(FirebaseMainActivity.this, FirebaseAddUserActivity.class);
                    intent.putExtra("id", 1);
                    startActivity(intent);
                }
            }
        });
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

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                adapter = new UsersAdapter(FirebaseMainActivity.this,list,FirebaseMainActivity.this);
                recycler.setAdapter(adapter);
                for (DataSnapshot userSnap : snapshot.getChildren()){
                    UserObject user = userSnap.getValue(UserObject.class);
                    list.add(user);
                    adapter.notifyDataSetChanged();
                }

                if (list.size() == 0){
                    Toast.makeText(FirebaseMainActivity.this, "No users", Toast.LENGTH_SHORT).show();
                }

                dialog.cancel();
                database.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onUserEdit(int position) {
        Intent intent = new Intent(FirebaseMainActivity.this, FirebaseUpdateUserActivity.class);
        intent.putExtra("id",list.get(position).getUserId());
        startActivity(intent);
    }

    @Override
    public void onUserDelete(final int position) {
        database.child(String.valueOf(list.get(position).getUserId())).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(FirebaseMainActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                    list.remove(position);
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(FirebaseMainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onUserView(int position) {
        Intent intent = new Intent(FirebaseMainActivity.this, FirebaseViewUserActivity.class);
        intent.putExtra("id",list.get(position).getUserId());
        startActivity(intent);
    }
}
