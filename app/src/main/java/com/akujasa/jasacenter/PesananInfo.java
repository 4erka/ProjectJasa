package com.akujasa.jasacenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import static com.akujasa.jasacenter.R.id.tvAlamat;
import static com.akujasa.jasacenter.R.id.tvHarga;
import static com.akujasa.jasacenter.R.id.tvIdPesanan;
import static com.akujasa.jasacenter.R.id.tvJumlah;
import static com.akujasa.jasacenter.R.id.tvKet;
import static com.akujasa.jasacenter.R.id.tvNama;
import static com.akujasa.jasacenter.R.id.tvNamaJasa;
import static com.akujasa.jasacenter.R.id.tvStatus;
import static com.akujasa.jasacenter.R.id.tvTotal;

public class PesananInfo extends AppCompatActivity {

    private TextView tvIdPesanan;
    private TextView tvNamaPenyedia;
    private TextView tvAlamat;
    private TextView tvHarga;
    private TextView tvKet;
    private TextView tvJumlah;
    private TextView tvNamaJasa;
    private TextView tvStatus;
    private TextView tvTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan_info);
        Intent intentku = getIntent();
        ItemPesanan data_pesanan =(ItemPesanan) intentku.getSerializableExtra("data_pesanan");
        String status = intentku.getStringExtra("status");

        tvIdPesanan = (TextView)findViewById(R.id.tvIdPesanan);
        tvNamaPenyedia = (TextView)findViewById(R.id.tvNamaPenyedia);
        tvAlamat = (TextView)findViewById(R.id.tvAlamat);
        tvHarga = (TextView)findViewById(R.id.tvHarga);
        tvKet = (TextView)findViewById(R.id.tvKet);
        tvJumlah = (TextView)findViewById(R.id.tvJumlah);
        tvNamaJasa = (TextView)findViewById(R.id.tvNamaJasa);
        tvStatus = (TextView)findViewById(R.id.tvStatus);
        tvTotal = (TextView)findViewById(R.id.tvTotal);
        tvIdPesanan.setText(data_pesanan.getId_pesanan());
        tvNamaPenyedia.setText(data_pesanan.getNama_penyedia());
        tvAlamat.setText(data_pesanan.getAlamat_jasa());
        tvHarga.setText(data_pesanan.getHarga_jasa());
        tvKet.setText(data_pesanan.getKet_jasa());
        tvJumlah.setText(data_pesanan.getJumlah_jasa());
        tvNamaJasa.setText(data_pesanan.getNama_jasa());
        tvStatus.setText(status);
        tvTotal.setText(String.valueOf(Integer.parseInt(tvHarga.getText().toString())*Integer.parseInt(tvJumlah.getText().toString())));
    }
}
