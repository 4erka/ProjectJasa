package com.akujasa.jasacenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

        tvNama.setText(dtjasa.getNama());
        rbRating.setRating(dtjasa.getRating());


        return ConvertView;

    }


}
