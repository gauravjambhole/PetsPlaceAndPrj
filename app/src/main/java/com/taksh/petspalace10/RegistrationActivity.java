package com.taksh.petspalace10;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    private EditText etName, etMobile, etEmail, etUsername, etPassword;
    private Button btnRegister;
    private TextView regTitle, tvAlreadyAccount;
    private View layoutMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Initialize Views
        regTitle = findViewById(R.id.reg_title);
        etName = findViewById(R.id.etRegName);
        layoutMobile = findViewById(R.id.layoutMobile);
        etMobile = findViewById(R.id.etRegMobile);
        etEmail = findViewById(R.id.etRegEmail);
        etUsername = findViewById(R.id.etRegUsername);
        etPassword = findViewById(R.id.etRegPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvAlreadyAccount = findViewById(R.id.tvAlreadyAccount);

        startStaggeredAnimation();

        // Glide back to Login
        tvAlreadyAccount.setOnClickListener(v -> finishAfterTransition());

        btnRegister.setOnClickListener(v -> validateAndRegister());
    }

    private void startStaggeredAnimation() {
        int duration = 600;
        int d = 100; // delay increment

        regTitle.animate().alpha(1f).translationY(0).setDuration(duration).setStartDelay(200).start();
        etName.animate().alpha(1f).translationY(0).setDuration(duration).setStartDelay(200 + d).start();
        layoutMobile.animate().alpha(1f).translationY(0).setDuration(duration).setStartDelay(200 + (d * 2)).start();
        etEmail.animate().alpha(1f).translationY(0).setDuration(duration).setStartDelay(200 + (d * 3)).start();
        etUsername.animate().alpha(1f).translationY(0).setDuration(duration).setStartDelay(200 + (d * 4)).start();
        etPassword.animate().alpha(1f).translationY(0).setDuration(duration).setStartDelay(200 + (d * 5)).start();

        btnRegister.animate().alpha(1f).translationY(0).setDuration(duration)
                .setInterpolator(new DecelerateInterpolator()).setStartDelay(200 + (d * 6)).start();

        tvAlreadyAccount.animate().alpha(1f).translationY(0).setDuration(duration).setStartDelay(200 + (d * 7)).start();
    }

    private void validateAndRegister() {
        String name = etName.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String user = etUsername.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();

        if (name.isEmpty()) { etName.setError("Name required"); return; }

        // India 10-digit validation
        if (mobile.length() != 10) {
            etMobile.setError("Enter exactly 10 digits");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Invalid Email"); return;
        }

        if (user.length() < 8) {
            etUsername.setError("Username must be 8+ characters"); return;
        }

        String specialChars = ".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*";
        if (!pass.matches(".*\\d.*") || !pass.matches(specialChars)) {
            etPassword.setError("Need 1 number and 1 special character"); return;
        }

        // Logic to save data would go here (e.g., Firebase or SQLite)
        Toast.makeText(this, "Account Created for " + user, Toast.LENGTH_SHORT).show();
        finishAfterTransition();
    }
}