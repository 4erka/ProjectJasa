package com.akujasa.jasacenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PesananInfoCancel extends AppCompatActivity {

    private TextView tvIdPesanan;
    private TextView tvNamaPenyedia;
    private TextView tvAlamat;
    private TextView tvHarga;
    private TextView tvKet;
    private TextView tvJumlah;
    private TextView tvNamaJasa;
    private TextView tvStatus;
    private TextView tvTotal;
    private Button btCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan_info_cancel);
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
        btCancel = (Button)findViewById(R.id.btCancel);
        tvIdPesanan.setText(data_pesanan.getId_pesanan());
        tvNamaPenyedia.setText(data_pesanan.getNama_penyedia());
        tvAlamat.setText(data_pesanan.getAlamat_jasa());
        tvHarga.setText(data_pesanan.getHarga_jasa());
        tvKet.setText(data_pesanan.getKet_jasa());
        tvJumlah.setText(data_pesanan.getJumlah_jasa());
        tvNamaJasa.setText(data_pesanan.getNama_jasa());
        tvStatus.setText(status);
        tvTotal.setText(String.valueOf(Integer.parseInt(tvHarga.getText().toString())*Integer.parseInt(tvJumlah.getText().toString())));
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        new CancelProcess(PesananInfoCancel.this).execute(tvIdPesanan.getText().toString());

            }
        });
    }
}
