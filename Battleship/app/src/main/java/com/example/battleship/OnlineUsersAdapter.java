package com.example.battleship;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.battleship.gamelogic.User;
import java.util.List;

public class OnlineUsersAdapter extends RecyclerView.Adapter<OnlineUsersAdapter.OnlineUserViewHolder> {

    private List<User> usersList;
    private Context context;

    public OnlineUsersAdapter(List<User> usersList, Context context) {
        this.usersList = usersList;
        this.context = context;
    }

    @NonNull
    @Override
    public OnlineUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new OnlineUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OnlineUserViewHolder holder, int position) {
        User user = usersList.get(position);
        holder.usernameTextView.setText(user.getUsername()); // Display username instead of email
        holder.statusImageView.setImageResource(user.isOnline() ? R.drawable.online_status : R.drawable.offline_status);
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public void updateUsers(List<User> users) {
        this.usersList = users;
        notifyDataSetChanged();
    }

    public static class OnlineUserViewHolder extends RecyclerView.ViewHolder {
        TextView usernameTextView; // Rename to usernameTextView
        ImageView statusImageView;

        public OnlineUserViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView); // Update the ID to R.id.usernameTextView
            statusImageView = itemView.findViewById(R.id.statusImageView);
        }
    }
}
