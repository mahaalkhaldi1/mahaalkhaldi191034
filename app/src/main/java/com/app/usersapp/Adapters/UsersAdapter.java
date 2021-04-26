package com.app.usersapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.usersapp.Activities.FirebaseActivities.FirebaseViewUserActivity;
import com.app.usersapp.Interfaces.OnUserInteract;
import com.app.usersapp.Models.UserObject;
import com.app.usersapp.R;
import com.app.usersapp.Activities.FirebaseActivities.FirebaseUpdateUserActivity;
import com.app.usersapp.Utils.Consts;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>{
    private Context context;
    private List<UserObject> list;
    private OnUserInteract i;



    public UsersAdapter(Context context,List<UserObject> list,OnUserInteract i) {
        this.context = context;
        this.list = list;
        this.i = i;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView first_name,last_name,phone,email;
        private RelativeLayout edit,delete;
        private LinearLayout layout;

        public ViewHolder(View view) {
            super(view);
            first_name = view.findViewById(R.id.first_name);
            last_name = view.findViewById(R.id.last_name);
            phone = view.findViewById(R.id.phone);
            email = view.findViewById(R.id.email);
            edit = view.findViewById(R.id.edit);
            delete = view.findViewById(R.id.delete);
            layout = view.findViewById(R.id.layout);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final UserObject p = list.get(position);

        holder.first_name.setText(p.getFirstName());
        holder.last_name.setText(p.getLastName());
        holder.phone.setText(p.getPhoneNumber());
        holder.email.setText(p.getEmailAddress());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.onUserDelete(position);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.onUserEdit(position);
            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.onUserView(position);
            }
        });
    }
}
