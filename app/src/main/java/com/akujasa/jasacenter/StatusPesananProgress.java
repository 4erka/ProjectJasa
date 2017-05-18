package com.akujasa.jasacenter;

/**
 * Created by erka on 3/19/2017.
 */

import android.content.SharedPreferences;
        import android.support.v4.app.Fragment;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ListView;

        import static android.content.Context.MODE_PRIVATE;

public class StatusPesananProgress extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences sp = getActivity().getSharedPreferences("pencari_info",MODE_PRIVATE);
        String id_pencari = sp.getString("pencari_id","a");
        Log.e("A",id_pencari);
        View rootView = inflater.inflate(R.layout.status_pesanan_progress, container, false);
        ListView listku = (ListView) rootView.findViewById(R.id.listprogress);
        new ListOnProgressProcess(getActivity(),listku).execute(id_pencari);
        return rootView;
    }
}
