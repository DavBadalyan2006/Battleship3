package com.example.battleship.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.battleship.MainMenuActivity;
import com.example.battleship.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);

        // Add an input filter to restrict input to alphanumeric characters
        usernameEditText.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetterOrDigit(src.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        }});

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usernameEditText.getText().toString().trim();
                final String email = emailEditText.getText().toString().trim();
                final String password = passwordEditText.getText().toString().trim();

                if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    checkUsernameAvailability(username, new OnUsernameAvailabilityCheckedListener() {
                        @Override
                        public void onChecked(boolean isAvailable) {
                            if (isAvailable) {
                                registerUser(username, email, password);
                            } else {
                                Toast.makeText(RegisterActivity.this, "Username is already taken.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }


    private void checkUsernameAvailability(String username, final OnUsernameAvailabilityCheckedListener listener) {
        firebaseFirestore.collection("users")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                                listener.onChecked(true);
                            } else {
                                listener.onChecked(false);
                                Toast.makeText(RegisterActivity.this, "Username is already taken.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            listener.onChecked(false);
                            Toast.makeText(RegisterActivity.this, "Error checking username availability.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void registerUser(final String username, String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username).build();
                            user.updateProfile(profileUpdates);
                            createUserInFirestore(username);
                            startActivity(new Intent(RegisterActivity.this, MainMenuActivity.class));
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void createUserInFirestore(String username) {
        String uid = firebaseAuth.getCurrentUser().getUid();
        Map<String, Object> user = new HashMap<>();
        user.put("uid", uid);
        user.put("username", usernameEditText.getText().toString());
        user.put("email", emailEditText.getText().toString());
        user.put("password", passwordEditText.getText().toString());

        firebaseFirestore.collection("users").document(uid).set(user);
    }

    interface OnUsernameAvailabilityCheckedListener {
        void onChecked(boolean isAvailable);
    }
}