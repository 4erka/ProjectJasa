package com.akujasa.jasacenter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Antonius on 18/05/2017.
 */

public class PesananListAdapter extends ArrayAdapter<ItemPesanan> {
    private Context context;

    public PesananListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ItemPesanan> objects) {
        super(context, resource, objects);

    }

    public View getView(int position, View ConvertView,
                        ViewGroup parent){

        ItemPesanan dtpesanan= getItem(position);
        if(ConvertView==null){
            ConvertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.item_pesanan,parent,false);
        }

        final TextView tvNama= (TextView) ConvertView.findViewById(R.id.nama_jasa);
        final TextView tvTanggal= (TextView) ConvertView.findViewById(R.id.tanggal_jasa);
        final TextView tvStatus= (TextView) ConvertView.findViewById(R.id.status_jasa);
        final ImageView ivKategori = (ImageView) ConvertView.findViewById(R.id.gambarjasa);
        if(dtpesanan.getId_kategori().equals("1")) {
            ivKategori.setImageResource(R.drawable.foods);
        }
        else if(dtpesanan.getId_kategori().equals("2")) {
            ivKategori.setImageResource(R.drawable.pakaian);
        }
        else if (dtpesanan.getId_kategori().equals("3")) {
            ivKategori.setImageResource(R.drawable.elektronik);
        }
        else if (dtpesanan.getId_kategori().equals("4")) {
            ivKategori.setImageResource(R.drawable.kendaraan);
        }
        else if (dtpesanan.getId_kategori().equals("5")) {
            ivKategori.setImageResource(R.drawable.rumah_tangga);
        }

        tvNama.setText(dtpesanan.getNama_penyedia());
        tvTanggal.setText(dtpesanan.getTanggal_jasa());
        tvStatus.setText(dtpesanan.getStatus_jasa());


        return ConvertView;

    }
}
