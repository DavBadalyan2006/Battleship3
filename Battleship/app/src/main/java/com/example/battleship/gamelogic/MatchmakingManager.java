package com.example.battleship.gamelogic;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MatchmakingManager {

    private static final String TAG = "MatchmakingManager";
    private DatabaseReference mMatchmakingRef;
    private ValueEventListener mMatchmakingListener;
    private String mUserId;

    public interface MatchmakingCallback {
        void onMatchFound(String roomId);
    }

    public MatchmakingManager(String userId) {
        mUserId = userId;
        mMatchmakingRef = FirebaseDatabase.getInstance().getReference("matchmaking");
    }

    public void findMatch(final MatchmakingCallback callback) {
        mMatchmakingListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String roomId = snapshot.getKey();
                    String opponentId = snapshot.getValue(String.class);

                    // If the room is occupied by someone else
                    if (!opponentId.equals(mUserId)) {
                        // Assign user to the room and remove it from available matchmaking rooms
                        mMatchmakingRef.child(roomId).setValue(mUserId);
                        mMatchmakingRef.removeEventListener(this);
                        callback.onMatchFound(roomId);
                        return;
                    }
                }

                // If no available room found, create a new one
                String roomId = mMatchmakingRef.push().getKey();
                mMatchmakingRef.child(roomId).setValue(mUserId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Matchmaking onCancelled", databaseError.toException());
            }
        };

        mMatchmakingRef.addListenerForSingleValueEvent(mMatchmakingListener);
    }


    public void cancelMatchmaking() {
        if (mMatchmakingListener != null) {
            mMatchmakingRef.removeEventListener(mMatchmakingListener);
            mMatchmakingListener = null;
        }
    }
}
