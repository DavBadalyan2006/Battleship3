package com.example.battleship;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.battleship.gamelogic.User;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class OnlineUsersActivity extends AppCompatActivity {

    private RecyclerView onlineUsersRecyclerView;
    private OnlineUsersAdapter onlineUsersAdapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_users);

        onlineUsersRecyclerView = findViewById(R.id.onlineUsersRecyclerView);
        onlineUsersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        onlineUsersAdapter = new OnlineUsersAdapter(new ArrayList<User>(), this);
        onlineUsersRecyclerView.setAdapter(onlineUsersAdapter);

        db = FirebaseFirestore.getInstance();

        db.collection("users").whereEqualTo("isOnline", true)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.e("OnlineUsersActivity", "Error getting documents.", e);
                            return;
                        }

                        List<User> users = new ArrayList<>();
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                            User user = documentSnapshot.toObject(User.class);
                            users.add(user);
                        }

                        onlineUsersAdapter.updateUsers(users);
                    }
                });
    }
}
