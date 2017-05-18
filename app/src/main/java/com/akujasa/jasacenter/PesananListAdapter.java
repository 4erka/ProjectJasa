package com.akujasa.jasacenter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
        final TextView tvAlamat= (TextView) ConvertView.findViewById(R.id.alamat_jasa);
        tvNama.setText(dtpesanan.getNama_penyedia());
        tvTanggal.setText(dtpesanan.getTanggal_jasa());
        tvAlamat.setText(dtpesanan.getAlamat_jasa());


        return ConvertView;

    }
}
