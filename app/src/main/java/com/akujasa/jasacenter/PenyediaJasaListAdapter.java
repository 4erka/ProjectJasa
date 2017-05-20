package com.akujasa.jasacenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonius on 14/05/2017.
 */

public class PenyediaJasaListAdapter extends ArrayAdapter<ItemPenyediaJasa> {

    private Context context;
    public PenyediaJasaListAdapter(Context context, int resource,
                       List<ItemPenyediaJasa> objects) {
        super(context, resource, objects);
        this.context=context;
    }
    public View getView(int position, View ConvertView,
                        ViewGroup parent){

        ItemPenyediaJasa dtjasa= getItem(position);
        if(ConvertView==null){
            ConvertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.cari_jasa_content,parent,false);
        }

        final TextView tvNama= (TextView) ConvertView.findViewById(R.id.listnamajasa);
        final RatingBar rbRating= (RatingBar) ConvertView.findViewById(R.id.rating);
        final ImageView ivKategori = (ImageView) ConvertView.findViewById(R.id.gambarlogo);
        if(dtjasa.getKategori().equals("1")) {
            ivKategori.setImageResource(R.drawable.foods);
        }
        else if(dtjasa.getKategori().equals("2")) {
            ivKategori.setImageResource(R.drawable.pakaian);
        }
        else if (dtjasa.getKategori().equals("3")) {
            ivKategori.setImageResource(R.drawable.elektronik);
        }
        else if (dtjasa.getKategori().equals("4")) {
            ivKategori.setImageResource(R.drawable.kendaraan);
        }
        else if (dtjasa.getKategori().equals("5")) {
            ivKategori.setImageResource(R.drawable.rumah_tangga);
        }
        tvNama.setText(dtjasa.getNama());
        rbRating.setRating(dtjasa.getRating());


        return ConvertView;

    }


}
