package com.akujasa.jasacenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    private EditText etNama;
    private EditText etEmail;
    private EditText etAlamat;
    private EditText etNohp;
    private EditText etPassword;
    private EditText etUlangiPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        etNama = (EditText)findViewById(R.id.regisnama);
        etEmail = (EditText)findViewById(R.id.regisemail);
        etAlamat = (EditText)findViewById(R.id.regisalamat);
        etNohp = (EditText)findViewById(R.id.regisnohp);
        etPassword = (EditText)findViewById(R.id.regispass);
        etUlangiPassword = (EditText)findViewById(R.id.regispass1);
    }

    public void onRegister(View view){
        String nama = etNama.getText().toString();
        String email = etEmail.getText().toString();
        String alamat = etAlamat.getText().toString();
        String nohp = etNohp.getText().toString();
        String password = etPassword.getText().toString();
        String ulangi_password = etUlangiPassword.getText().toString();
        new RegisterProcess(this).execute(nama,email,alamat,nohp,password,ulangi_password);
    }
}
