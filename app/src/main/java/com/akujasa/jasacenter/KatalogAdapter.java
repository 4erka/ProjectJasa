package com.akujasa.jasacenter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Antonius on 14/05/2017.
 */

public class KatalogAdapter extends ArrayAdapter<ItemKatalog>{
    public KatalogAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ItemKatalog> objects) {
        super(context, resource, objects);
    }

    public View getView(int position, View ConvertView,
                        ViewGroup parent){

        final ItemKatalog dtkatalog= getItem(position);
        if(ConvertView==null){
            ConvertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.item_katalog,parent,false);
        }

        final TextView tvNama= (TextView) ConvertView.findViewById(R.id.nama);
        final TextView tvDeskripsi= (TextView) ConvertView.findViewById(R.id.deskripsi);
        final TextView tvHarga= (TextView) ConvertView.findViewById(R.id.harga);
        final Button btLanjut = (Button)ConvertView.findViewById(R.id.pesanlanjut);
        final EditText etJumlah = (EditText)ConvertView.findViewById(R.id.jumlah);
        tvNama.setText(dtkatalog.getNama());
        tvDeskripsi.setText(dtkatalog.getDeskripsi());
        tvHarga.setText(dtkatalog.getHarga());
        btLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jumlah = etJumlah.getText().toString();
                Intent intentku = new Intent(getContext(), PesananMaps.class);
                intentku.putExtra("katalog",dtkatalog);
                intentku.putExtra("jumlah",jumlah);
                getContext().startActivity(intentku);
            }
        });



        return ConvertView;
    }
}
