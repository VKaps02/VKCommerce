package com.app.vkcommerce.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.vkcommerce.R;
import com.app.vkcommerce.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();
        setupListeners();
    }

    private void setupListeners() {
        Button loginButton = findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(view -> {
            doRegister();
        });
    }

    private void doRegister() {
        TextInputEditText editTextEmail = findViewById(R.id.editTextEmail);
        String email = Objects.requireNonNull(editTextEmail.getText()).toString();

        TextInputEditText editTextPassword = findViewById(R.id.editTextPassword);
        String password = Objects.requireNonNull(editTextPassword.getText()).toString();

        Log.d("SRD", "Email : " + email);
        Log.d("SRD", "password : " + password);

        registerUserInFirebase(email, password);

    }

    private void registerUserInFirebase(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("SRD", "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        //createUserCollection(user);
                        startHomeActivity();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("SRD", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void createUserCollection(FirebaseUser user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(user.getUid())
                .set(new User(user.getUid(), user.getEmail()));
    }

    private void startHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}