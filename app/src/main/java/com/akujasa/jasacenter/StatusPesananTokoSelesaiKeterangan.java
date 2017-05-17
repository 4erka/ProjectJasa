package com.akujasa.jasacenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class StatusPesananTokoSelesaiKeterangan extends AppCompatActivity {

    TextView nama_toko;
    TextView nama_konsumen;
    TextView nomor_pesanan;
    TextView tanggal;
    TextView keterangan;
    TextView alamat;
    TextView nama_jasa;
    TextView pesanan_jumlah;
    TextView harga_jasa;

    String pesanan_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_pesanan_toko_selesai_keterangan);
        nama_konsumen = (TextView)findViewById(R.id.nama_konsumen_toko_selesai_keterangan);
        nomor_pesanan = (TextView)findViewById(R.id.nomor_jasa_toko_selesai_keterangan);
        tanggal = (TextView)findViewById(R.id.tanggal_toko_selesai_keterangan);
        keterangan = (TextView)findViewById(R.id.keterangan_toko_selesai_keterangan);
        alamat = (TextView)findViewById(R.id.alamat_toko_selesai_keterangan);
        nama_jasa = (TextView)findViewById(R.id.nama_jasa_toko_selesai_keterangan);
        pesanan_jumlah = (TextView)findViewById(R.id.jumlah_jasa_toko_selesai_keterangan);
        //harga_jasa = (TextView)findViewById(R.id.harga_toko_baru_keterangan);

        //int posisi = getIntent().getExtras().getInt("nama_jasa");
        String stnama_jasa = getIntent().getExtras().getString("nama_jasa");
        String stnama_konsumen = getIntent().getExtras().getString("nama_konsumen");
        String stnomor = getIntent().getExtras().getString("nomor_pesanan");
        String sttanggal = getIntent().getExtras().getString("tanggal");
        String stketerangan = getIntent().getExtras().getString("keterangan");
        String stalamat = getIntent().getExtras().getString("alamat");
        String stjumlah = getIntent().getExtras().getString("pesanan_jumlah");
        //String stharga = getIntent().getExtras().getString("harga_jasa");
        pesanan_id = getIntent().getExtras().getString("pesanan_id");
        //Log.e(TAG, "hasil : " + x);
        nama_jasa.setText(stnama_jasa);
        nama_konsumen.setText(stnama_konsumen);
        nomor_pesanan.setText(stnomor);
        tanggal.setText(sttanggal);
        keterangan.setText(stketerangan);
        alamat.setText(stalamat);
        pesanan_jumlah.setText(stjumlah);
        //harga_jasa.setText(stharga);
    }
}
