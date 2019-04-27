package com.example.design.restoff;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Screen extends AppCompatActivity implements View.OnClickListener {
    private Button signup;
    private Button login;
    private TextView forgotpass;
    private EditText email;
    private EditText password;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(Login_Screen.this, MainActivity.class));
            finish();
        }
        setContentView(R.layout.activity_login__screen);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("It Just Take Few Seconds Wait..");
        firebaseAuth=FirebaseAuth.getInstance();
        signup=findViewById(R.id.login_button_logup);
        login=findViewById(R.id.login_button_login);
        forgotpass=findViewById(R.id.login_textview_forgotpass);
        email=findViewById(R.id.login_edittext_email);
        password=findViewById(R.id.login_edittext_pass);

        signup.setOnClickListener(this);
        login.setOnClickListener(this);
        forgotpass.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id)
        {
            case R.id.login_button_login:
                login();
                break;
            case R.id.login_button_logup:
                User_sigup();
                break;
            case R.id.login_textview_forgotpass:
                forgotpassword();
                break;
        }
    }
    private void login() {
        String useremail = email.getText().toString();
        final String userpassword = password.getText().toString();
        if (TextUtils.isEmpty(useremail)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userpassword)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(useremail,userpassword).addOnCompleteListener(Login_Screen.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()) {
                    // there was an error
                    if (password.length() < 6) {
                        Toast.makeText(Login_Screen.this,"Password is Wrong", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Login_Screen.this,"Login Fail", Toast.LENGTH_LONG).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Intent intent = new Intent(Login_Screen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    private void User_sigup() {
        Intent registrationactivity=new Intent(Login_Screen.this,Registration.class);
        startActivity(registrationactivity);
        finish();
    }
    private void forgotpassword() {
        startActivity(new Intent(Login_Screen.this,Forgotpassword_screen.class));
    }

}
