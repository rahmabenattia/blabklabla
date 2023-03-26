package com.isi.myfinelproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class SignupActivity<string> extends AppCompatActivity implements  View.OnClickListener {

    EditText signupUsername, signupEmail, signupPassword, signupAge;
    TextView loginRedirectText;
    Button signupButton;
    ProgressBar progressBar;
    RadioGroup radioGroup;
    RadioButton radio1, radio2;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        loginRedirectText = (TextView) findViewById(R.id.loginRedirectText);
        loginRedirectText.setOnClickListener(this);

        signupButton = (Button) findViewById(R.id.signup_button);
        signupButton.setOnClickListener(this);


        signupUsername = findViewById(R.id.signup_username);
        signupUsername.setOnClickListener(this);

        signupEmail = findViewById(R.id.signup_email);
        signupEmail.setOnClickListener(this);

        signupPassword = findViewById(R.id.signup_password);
        signupPassword.setOnClickListener(this);

        signupAge = findViewById(R.id.signup_age);
        signupAge.setOnClickListener(this);

        radio1 = (RadioButton) findViewById(R.id.radio1);
        radio1.setOnClickListener(this);

        radio2 = (RadioButton) findViewById(R.id.radio2);
        radio2.setOnClickListener(this);

        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnClickListener(this);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginRedirectText:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.signup_button:
                signup();
                break;

        }

    }

    private void signup() {
        String username = signupUsername.getText().toString().trim();
        String email = signupEmail.getText().toString().trim();
        String password = signupPassword.getText().toString().trim();
        Integer age = Integer.valueOf(signupAge.getText().toString().trim());


        if (username.isEmpty()) {
            signupUsername.setError("username is required!");
            signupUsername.requestFocus();
            return;
        }
        String ageString = Integer.toString(age);

        if (ageString.isEmpty()) {
            signupAge.setError("Age is required!");
            signupAge.requestFocus();
            return;
        }


        if ((age < 18) || (age > 60)) {
            signupAge.setError("Your age doesn't allow you to register");
            signupAge.requestFocus();
        }

        if (email.isEmpty()) {

            signupEmail.setError("email is required!");
            signupEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signupEmail.setError("please provide valid email");
            signupEmail.requestFocus();
            return;

        }
        if (password.isEmpty()) {

            signupPassword.setError("password is required!");
            signupPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            signupPassword.setError("min password lenght should be 6 characters!");
            signupPassword.requestFocus();
            return;
        }
        int radioId = radioGroup.getCheckedRadioButtonId();

        if (radioId == -1) {
            Toast.makeText(this, "Please select one of the radio buttons", Toast.LENGTH_SHORT).show();
            return;
        }
        if (radio1.isChecked()) {
            radio1.setError("You can't register");
            radio1.requestFocus();
            return;
        } else if (radio2.isChecked()) {
            String rad = radio2.getText().toString().trim();
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                User user = new User(username, age, email, password, rad);
                                FirebaseDatabase.getInstance().getReference("users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(SignupActivity.this, "you have been regester with succses", Toast.LENGTH_LONG).show();
                                                    progressBar.setVisibility(View.GONE);
                                                    // REDIRECT TO LOGIN LAYOUT!
                                                   startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                                } else {
                                                    Toast.makeText(SignupActivity.this, "Failed to register, please try again", Toast.LENGTH_LONG).show();
                                                    progressBar.setVisibility(View.GONE);
                                                }
                                            }
                                        });

                            } else {
                                Toast.makeText(SignupActivity.this, "Failed to register, please try again", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    });
        } else {
            Toast.makeText(SignupActivity.this, "Please select a gender", Toast.LENGTH_LONG).show();
        }
    }
}