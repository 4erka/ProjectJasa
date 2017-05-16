package com.akujasa.jasacenter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class PesanformActivity extends AppCompatActivity {
    private TextView tvTotal;
    private TextView tvJasa;
    private TextView tvJumlah;
    private TextView tvHarga;
    private TextView tvAlamat;
    private EditText etTambahan;
    private Button btSubmit;
    private String alamat;
    private ItemKatalog katalog;
    private double latitude;
    private double longitude;
    private int total;
    private String id_pencari;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = this.getSharedPreferences("pencari_info",MODE_PRIVATE);
        id_pencari = sp.getString("pencari_id","a");
        Toast.makeText(this, id_pencari, Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_pesanform);
        Intent intentku = getIntent();
        String jumlah = intentku.getStringExtra("jumlah");
        katalog = (ItemKatalog)intentku.getSerializableExtra("katalog");
         alamat = intentku.getStringExtra("alamat");
        tvJasa = (TextView)findViewById(R.id.nama);
        tvJumlah = (TextView)findViewById(R.id.jumlah);
        tvHarga = (TextView)findViewById(R.id.harga);
        tvTotal = (TextView)findViewById(R.id.total);
        tvAlamat = (TextView)findViewById(R.id.alamat);
        latitude = intentku.getDoubleExtra("latitude",0.0);
        longitude = intentku.getDoubleExtra("longitude",0.0);
        tvJumlah.setText(jumlah);
        tvJasa.setText(katalog.getNama());
        tvHarga.setText(katalog.getHarga());
        tvAlamat.setText(alamat);
        etTambahan = (EditText)findViewById(R.id.pesaninformasiketerangan);
        total = (Integer.parseInt(jumlah))*(Integer.parseInt(tvHarga.getText().toString()));
        tvTotal.setText(String.valueOf(total));
        btSubmit = (Button)findViewById(R.id.pesan);

    }
    public void onPesan (View view){
        new PesanformProcess(this).execute(katalog.getId(),id_pencari,etTambahan.getText().toString(),String.valueOf(latitude),String.valueOf(longitude),tvAlamat.getText().toString(),tvJumlah.getText().toString());
    }

}
