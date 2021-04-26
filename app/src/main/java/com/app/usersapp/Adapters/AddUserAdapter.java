package com.app.usersapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.usersapp.Interfaces.OnUserInteract;
import com.app.usersapp.Interfaces.OnUserSelect;
import com.app.usersapp.Models.UserObject;
import com.app.usersapp.R;
import com.app.usersapp.Utils.Consts;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddUserAdapter extends RecyclerView.Adapter<AddUserAdapter.ViewHolder>{
    private Context context;
    private List<UserObject> list;
    private OnUserSelect i;



    public AddUserAdapter(Context context,List<UserObject> list,OnUserSelect i) {
        this.context = context;
        this.list = list;
        this.i = i;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView first_name,last_name,phone,email;
        private RelativeLayout select;
        private LinearLayout layout;

        public ViewHolder(View view) {
            super(view);
            first_name = view.findViewById(R.id.first_name);
            last_name = view.findViewById(R.id.last_name);
            phone = view.findViewById(R.id.phone);
            email = view.findViewById(R.id.email);
            select = view.findViewById(R.id.select);
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
                .inflate(R.layout.item_add_user, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final UserObject p = list.get(position);

        holder.first_name.setText(p.getFirstName());
        holder.last_name.setText(p.getLastName());
        holder.phone.setText(p.getPhoneNumber());
        holder.email.setText(p.getEmailAddress());

        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.onUserSelectedListener(position);
            }
        });


    }
}
