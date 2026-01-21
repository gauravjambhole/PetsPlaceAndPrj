package com.taksh.petspalace10;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.taksh.petspalace10.Common.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView welcomeText, tvNewUser;
    private CheckBox cbShowPassword;

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
        cbShowPassword = findViewById(R.id.cbShowPasswordLogin);

        // Start animations
        startEntranceAnimations();

        // Checkbox logic to show/hide password and change text
        cbShowPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                cbShowPassword.setText("Hide Password");
            } else {
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                cbShowPassword.setText("Show Password");
            }
            etPassword.setSelection(etPassword.getText().length());
        });

        // Login Logic
        btnLogin.setOnClickListener(v -> validateAndLogin());

        // Navigation to Registration with Shared Element Logo
        tvNewUser.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
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
        cbShowPassword.animate().alpha(1f).translationY(0).setDuration(duration).setStartDelay(650).start();
        btnLogin.animate().alpha(1f).translationY(0).setDuration(duration).setStartDelay(700).start();
        tvNewUser.animate().alpha(1f).translationY(0).setDuration(duration).setStartDelay(800).start();
    }

    private void validateAndLogin() {
        String user = etUsername.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();

        if (user.isEmpty()) {
            etUsername.setError("Enter Username");
            return;
        }
        if (pass.isEmpty()) {
            etPassword.setError("Enter Password");
            return;
        }

        loginOnServer(user, pass);
    }

    private void loginOnServer(String user, String pass) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username", user);
        params.put("password", pass);

        // Show a "Logging in..." message
        Toast.makeText(this, "Verifying credentials...", Toast.LENGTH_SHORT).show();

        client.post(Urls.LoginUserWebService, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    // Check if success is "1" from your loginUser.php
                    if (response.getString("success").equals("1")) {
                        Toast.makeText(LoginActivity.this, "Welcome Back!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        // Show error message from PHP (e.g., "Invalid Username or Password")
                        Toast.makeText(LoginActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.e("JSON_ERROR", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("SERVER_ERROR", "Code: " + statusCode);
                Toast.makeText(LoginActivity.this, "Network Error. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}