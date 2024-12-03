package com.example.myapp;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class VerifyActivity extends AppCompatActivity {

    private String correctOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        final EditText otpET = findViewById(R.id.otpET);
        final Button verifyOtpBtn = findViewById(R.id.verifyOtpBtn);

        Intent intent = getIntent();
        correctOtp = intent.getStringExtra("otp");

        verifyOtpBtn.setOnClickListener(v -> {
            String enteredOtp = otpET.getText().toString().trim();
            if (enteredOtp.equals(correctOtp)) {
                Toast.makeText(VerifyActivity.this, "OTP Verified Successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(VerifyActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(VerifyActivity.this, "Invalid OTP. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
