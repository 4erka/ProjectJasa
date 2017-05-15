package com.akujasa.jasacenter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PesanformActivity extends AppCompatActivity {
    private TextView tvTotal;
    private TextView tvJasa;
    private TextView tvJumlah;
    private TextView tvHarga;
    private EditText etTambahan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = this.getSharedPreferences("pencari_info",MODE_PRIVATE);
        String id_pencari = sp.getString("pencari_id","a");
        Toast.makeText(this, id_pencari, Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_pesanform);
        Intent intentku = getIntent();
        String jumlah = intentku.getStringExtra("jumlah");
        ItemKatalog katalog = (ItemKatalog)intentku.getSerializableExtra("katalog");
    }
}
