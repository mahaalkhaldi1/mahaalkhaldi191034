package com.app.usersapp.Activities.SQLiteActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.usersapp.Activities.FirebaseActivities.FirebaseAddUserActivity;
import com.app.usersapp.Activities.FirebaseActivities.FirebaseMainActivity;
import com.app.usersapp.Adapters.UsersAdapter;
import com.app.usersapp.Interfaces.OnUserInteract;
import com.app.usersapp.Models.UserObject;
import com.app.usersapp.R;
import com.app.usersapp.Utils.Consts;
import com.app.usersapp.Utils.Database;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SQLiteMainActivity extends AppCompatActivity implements OnUserInteract {

    private Button add_user,add_user_by_firebase;
    private RecyclerView recycler;
    private List<UserObject> list;
    private UsersAdapter adapter;
    private LinearLayoutManager layoutManager;
    
    private Database database;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_main);

        add_user = findViewById(R.id.add_user);
        add_user_by_firebase = findViewById(R.id.add_user_by_firebase);
        recycler = findViewById(R.id.recycler);
        list = new ArrayList<>();
        adapter = new UsersAdapter(this,list,this);
        layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);

        database = new Database(this);

        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() > 0) {
                    Intent intent = new Intent(SQLiteMainActivity.this, SQLiteAddUserActivity.class);
                    intent.putExtra("id", list.get(list.size() - 1).getUserId() + 1);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(SQLiteMainActivity.this, SQLiteAddUserActivity.class);
                    intent.putExtra("id", 1);
                    startActivity(intent);
                }
            }
        });
        add_user_by_firebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() > 0) {
                    Intent intent = new Intent(SQLiteMainActivity.this, SQLiteAddUserByFirebaseActivity.class);
                    intent.putExtra("id", list.get(list.size() - 1).getUserId() + 1);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(SQLiteMainActivity.this, SQLiteAddUserByFirebaseActivity.class);
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
    
    private void getUsers(){
        list.clear();
        adapter = new UsersAdapter(SQLiteMainActivity.this,list,SQLiteMainActivity.this);
        recycler.setAdapter(adapter);
        List<UserObject> users = database.getAllUsers();
        if (users.size() > 0){
            for (UserObject user : users){
                list.add(user);
                adapter.notifyDataSetChanged();
            }
        }else {
            Toast.makeText(this, "No users", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUserEdit(int position) {
        Intent intent = new Intent(SQLiteMainActivity.this, SQLiteUpdateUserActivity.class);
        intent.putExtra("id",list.get(position).getUserId());
        startActivity(intent);
    }

    @Override
    public void onUserDelete(int position) {
        int i = database.deleteUser(list.get(position).getUserId());
        if (i > 0){
            Toast.makeText(this, "User deleted successfully", Toast.LENGTH_SHORT).show();
            list.remove(position);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onUserView(int position) {
        Toast.makeText(this, list.get(position).getFirstName()+" "+list.get(position).getLastName(), Toast.LENGTH_SHORT).show();
    }
}
