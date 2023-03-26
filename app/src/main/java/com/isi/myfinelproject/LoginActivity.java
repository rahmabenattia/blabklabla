package com.isi.myfinelproject;


import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity<string> extends AppCompatActivity implements View.OnClickListener {
    EditText loginUsername, loginPassword;
    Button loginButton;
    TextView register, forgotpassword;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUsername = findViewById(R.id.signup_email);
        loginUsername.setOnClickListener(this);


        loginPassword = findViewById(R.id.login_password);
        loginPassword.setOnClickListener(this);


        register = findViewById(R.id.register);
        register.setOnClickListener(this);

        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);

        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        forgotpassword = findViewById(R.id.forgotPassword);
        forgotpassword.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                startActivity(new Intent(this, com.isi.myfinelproject.SignupActivity.class));
                break;
            case R.id.login_button:
                userLogin();
                break;
            case R.id.forgotPassword:
                startActivity(new Intent(this, Forgotpassword.class));
                break;

        }

    }

    private void userLogin() {
        String signup_email = loginUsername.getText().toString().trim();
        String login_password = loginPassword.getText().toString().trim();

        if (signup_email.isEmpty()) {
            loginUsername.setError("username is required");
            loginPassword.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(signup_email).matches()) {
            loginUsername.setError("please enter valid email!");
            loginUsername.requestFocus();
            return;

        }
        if (login_password.isEmpty()) {
            loginPassword.setError("password is required");
            loginPassword.requestFocus();
            return;
        }
        if (login_password.length() < 6) {
            loginPassword.setError("min password lenght is 6 characters");
            loginPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(signup_email, login_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    assert user != null;
                    if (user.isEmailVerified()) {
                        //redirect to user profile
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        user.sendEmailVerification();
                        Toast.makeText(LoginActivity.this, "Check your email to verifies account", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "failed to Login", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}