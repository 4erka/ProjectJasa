package com.akujasa.jasacenter;

import android.content.Intent;
import android.media.Rating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class PesaninformasiActivity extends AppCompatActivity {
    private TextView tvNama;
    private RatingBar rbRating;
    private TextView tvEmail;
    private TextView tvAlamat;
    private TextView tvNohp;
    private ListView lvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesaninformasi);
        Intent intentku = getIntent();
        String id_jasa = intentku.getStringExtra("id_jasa");
        String nama_jasa=intentku.getStringExtra("nama_jasa");
        String alamat_jasa=intentku.getStringExtra("alamat_jasa");
        String email_jasa=intentku.getStringExtra("email_jasa");
        String nohp_jasa=intentku.getStringExtra("nohp_jasa");
        int rating_jasa = intentku.getIntExtra("rating_jasa",0);
        tvNama = (TextView)findViewById(R.id.pesannamajasa);
        tvEmail = (TextView)findViewById(R.id.pesanemail);
        tvAlamat = (TextView)findViewById(R.id.pesanalamat);
        tvNohp = (TextView)findViewById(R.id.pesannomor);
        rbRating = (RatingBar)findViewById(R.id.pesanrating);
        lvList = (ListView)findViewById(R.id.katalog);
        tvNama.setText(nama_jasa);
        tvEmail.setText(email_jasa);
        tvAlamat.setText(alamat_jasa);
        tvNohp.setText(nohp_jasa);
        rbRating.setRating(rating_jasa);
        new KatalogProcess(this,lvList).execute(id_jasa);
    }
}
