package com.akujasa.jasacenter;

/**
 * Created by erka on 3/19/2017.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StatusPesananSelesai extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.status_pesanan_selesai, container, false);
        return rootView;
    }
}
