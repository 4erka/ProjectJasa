package com.akujasa.jasacenter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {


    private EditText etEmail;
    private EditText etPassword;
    private String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        etEmail = (EditText)findViewById(R.id.inputemail);
        etPassword = (EditText)findViewById(R.id.inputpass);
    }

    public void onRegis(View view){
        Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(register);
    }

    public void onLogin(View view){
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        new LoginProcess(this,status).execute(email,password);

    }
}
