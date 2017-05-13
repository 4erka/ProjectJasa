package com.akujasa.jasacenter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
    }

    public void onRegis(View view){
        Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(register);
    }

    public void onLogin(View view){
        Toast.makeText(LoginActivity.this,"Selamat Datang",Toast.LENGTH_LONG).show();
        Intent menut = new Intent(LoginActivity.this, MenuUtamaActivity.class);
        startActivity(menut);
    }
}
