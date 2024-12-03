package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class OTPActivity extends AppCompatActivity {

    private EditText otpInput;
    private Button sendOtpButton, verifyOtpButton;
    private String generatedOtp;
    private String userEmail;
    private final String senderEmail = "";
    private final String senderPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);

        otpInput = findViewById(R.id.otpInput);
        sendOtpButton = findViewById(R.id.sendOtpButton);
        verifyOtpButton = findViewById(R.id.verifyOtpButton);
        TextView emailDisplay = findViewById(R.id.emailDisplay);

        Intent intent = getIntent();
        userEmail = intent.getStringExtra("email");

        emailDisplay.setText("OTP sent to: " + userEmail);

        sendOtpButton.setOnClickListener(v -> {
            if (!userEmail.isEmpty()) {
                generatedOtp = generateOtp();
                sendOtpEmail(userEmail, generatedOtp);
                sendOtpButton.setEnabled(false);
                Toast.makeText(this, "OTP sent", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            }
        });

        verifyOtpButton.setOnClickListener(v -> {
            String userEnteredOtp = otpInput.getText().toString().trim();
            if (userEnteredOtp.equals(generatedOtp)) {
                Toast.makeText(this, "OTP Verified!", Toast.LENGTH_SHORT).show();
                // Navigate to the next screen (e.g., HomeActivity)
                startActivity(new Intent(OTPActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    private void sendOtpEmail(String recipientEmail, String otp) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Your OTP Code");
            message.setText("Your OTP code is: " + otp);

            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(() -> Toast.makeText(this, "Failed to send OTP", Toast.LENGTH_SHORT).show());
        }
    }
}
