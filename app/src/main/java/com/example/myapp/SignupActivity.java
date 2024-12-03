package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final EditText emailET = findViewById(R.id.emailET);
        final EditText mobileET = findViewById(R.id.mobileET);
        final EditText passwordET = findViewById(R.id.passwordET);
        final EditText conPasswordET = findViewById(R.id.conPasswordET);
        final Button signUpBtn = findViewById(R.id.signUpBtn);
        final TextView signInBtn = findViewById(R.id.signInBtn);

        signInBtn.setOnClickListener(v -> startActivity(new Intent(SignupActivity.this, LoginActivity.class)));

        signUpBtn.setOnClickListener(v -> {
            String email = emailET.getText().toString().trim();
            String mobile = mobileET.getText().toString().trim();
            String password = passwordET.getText().toString().trim();
            String conPassword = conPasswordET.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(mobile) || TextUtils.isEmpty(password) || TextUtils.isEmpty(conPassword)) {
                Toast.makeText(SignupActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(conPassword)) {
                Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else if (!isValidEmail(email)) {
                Toast.makeText(SignupActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SignupActivity.this, "Sign Up Successful!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();


            }
        });
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
