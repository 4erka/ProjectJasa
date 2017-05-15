package com.akujasa.jasacenter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonius on 13/05/2017.
 */

public class KategoriAdapter extends ArrayAdapter<ItemKategori>{
    LayoutInflater flater;
    public KategoriAdapter(@NonNull Activity context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List<ItemKategori> objects) {
        super(context, resource, textViewResourceId, objects);
        flater = context.getLayoutInflater();
    }

    public View getDropDownView(int position, View ConvertView,
                        ViewGroup parent){

        ItemKategori dtkategori= getItem(position);
        if(ConvertView==null){
            ConvertView = flater.inflate(R.layout.item_kategori,parent,false);
        }

        final TextView Nama= (TextView) ConvertView.findViewById(R.id.tvNama);

        Nama.setText(dtkategori.getNama());
        return ConvertView;

    }
    public View getView(int position, View ConvertView,
                                ViewGroup parent){

        ItemKategori dtkategori= getItem(position);
        if(ConvertView==null){
            ConvertView = flater.inflate(R.layout.item_kategori,parent,false);
        }

        final TextView Nama= (TextView) ConvertView.findViewById(R.id.tvNama);

        Nama.setText(dtkategori.getNama());
        return ConvertView;

    }


}
