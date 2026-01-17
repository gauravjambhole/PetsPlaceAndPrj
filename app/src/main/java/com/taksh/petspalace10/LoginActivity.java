package com.taksh.petspalace10;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView welcomeText, tvNewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Views
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        welcomeText = findViewById(R.id.welcome_text);
        tvNewUser = findViewById(R.id.tvNewUser);

        // Start animations
        startEntranceAnimations();

        // Login Logic
        btnLogin.setOnClickListener(v -> validateAndLogin());

        // Navigation to Registration with Shared Element Logo
        tvNewUser.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);

            // Create the transition animation for the logo
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    LoginActivity.this,
                    findViewById(R.id.login_logo), "logo_transform");

            startActivity(intent, options.toBundle());
        });
    }

    private void startEntranceAnimations() {
        int duration = 800;
        welcomeText.animate().alpha(1f).translationY(0).setDuration(duration).setStartDelay(400).start();
        etUsername.animate().alpha(1f).translationY(0).setDuration(duration).setStartDelay(500).start();
        etPassword.animate().alpha(1f).translationY(0).setDuration(duration).setStartDelay(600).start();
        btnLogin.animate().alpha(1f).translationY(0).setDuration(duration).setStartDelay(700).start();
        tvNewUser.animate().alpha(1f).translationY(0).setDuration(duration).setStartDelay(800).start();
    }

    private void validateAndLogin() {
        String user = etUsername.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();

        if (user.length() < 8) {
            etUsername.setError("Username must be at least 8 characters");
            return;
        }

        if (!pass.matches(".*\\d.*") || !pass.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
            etPassword.setError("Password needs 1 number & 1 special character");
            return;
        }

        // Logic for successful login
        Toast.makeText(this, "Welcome Back!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
}