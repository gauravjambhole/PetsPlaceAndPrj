package com.taksh.petspalace10;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
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

public class RegistrationActivity extends AppCompatActivity {

    EditText etName, etMobile, etEmail, etUsername, etPassword;
    Button btnRegister;
    TextView regTitle, tvAlreadyAccount;
    View layoutMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

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

        tvAlreadyAccount.setOnClickListener(v ->
                startActivity(new Intent(this, LoginActivity.class)));

        btnRegister.setOnClickListener(v -> validate());
    }

    private void startStaggeredAnimation() {
        int d = 600;
        regTitle.animate().alpha(1).translationY(0).setDuration(d).setStartDelay(200).start();
        etName.animate().alpha(1).translationY(0).setDuration(d).setStartDelay(300).start();
        layoutMobile.animate().alpha(1).translationY(0).setDuration(d).setStartDelay(400).start();
        etEmail.animate().alpha(1).translationY(0).setDuration(d).setStartDelay(500).start();
        etUsername.animate().alpha(1).translationY(0).setDuration(d).setStartDelay(600).start();
        etPassword.animate().alpha(1).translationY(0).setDuration(d).setStartDelay(700).start();
        btnRegister.animate().alpha(1).translationY(0)
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(d).setStartDelay(800).start();
        tvAlreadyAccount.animate().alpha(1).translationY(0)
                .setDuration(d).setStartDelay(900).start();
    }

    private void validate() {

        String name = etName.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String user = etUsername.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();

        if (name.isEmpty()) { etName.setError("Required"); return; }
        if (mobile.length() != 10) { etMobile.setError("10 digits"); return; }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) { etEmail.setError("Invalid"); return; }
        if (user.length() < 5) { etUsername.setError("Min 5"); return; }
        if (pass.isEmpty()) { etPassword.setError("Required"); return; }

        register(name, mobile, email, user, pass);
    }

    private void register(String name, String mobile, String email, String user, String pass) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(15000);

        RequestParams params = new RequestParams();
        params.put("name", name);
        params.put("mobileno", mobile);
        params.put("emailid", email);
        params.put("username", user);
        params.put("password", pass);

        client.post(Urls.RegisterUserWebService, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getString("success").equals("1")) {
                        Toast.makeText(RegistrationActivity.this,
                                "Registration Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(RegistrationActivity.this,
                                response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.e("JSON", e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(RegistrationActivity.this,
                        "Server Error " + statusCode, Toast.LENGTH_SHORT).show();
            }
        });
    }
}