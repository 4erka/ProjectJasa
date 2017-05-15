package com.akujasa.jasacenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Antonius on 13/05/2017.
 */

public class KategoriProcess extends AsyncTask {
    private String status;
    private Spinner spKategori;
    private Spinner spJenis;
    private Button btCari;
    private Activity context;
    private String id;
    private ArrayList<ItemKategori> listku;
    private ListView lvList;
    public KategoriProcess(Activity context, Spinner spKategori, Spinner spJenis, Button btCari, ListView lvList){
        this.context = context;
        this.spKategori=spKategori;
        this.spJenis=spJenis;
        this.btCari=btCari;
        this.lvList=lvList;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        try{
            listku = new ArrayList<ItemKategori>();
            String link = "http://rilokukuh.com/admin-jasa/android_ksm_get_kategori.php";
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while((line=reader.readLine())!=null){
                sb.append(line);
                break;
            }
            JSONObject jsonOBJ = new JSONObject(sb.toString());
            String status = jsonOBJ.getString("status");
            JSONArray listKategori = jsonOBJ.getJSONArray("data");
            for (int i = 0; i < listKategori.length(); i++) {
                JSONObject kategori_info = listKategori.getJSONObject(i);
                String nama_kategori = kategori_info.getString("kpj_nama");
                String id_kategori = kategori_info.getString("kpj_id");
                listku.add(new ItemKategori(nama_kategori,id_kategori));
            }


            return status;
        }
        catch (Exception e){
            return new String("Exception: "+e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(Object o) {
        this.status = (String)o;
        //
        if(status.equals("200")) {
            KategoriAdapter adapterku = new KategoriAdapter(context,R.layout.item_kategori,R.id.tvNama,listku);
            spKategori.setAdapter(adapterku);
            spKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected (AdapterView<?> parent,
                                        View v, int position, long id){

                    String id_kategori = listku.get(position).getId();
                    new JenisProcess(context,spJenis,btCari,lvList).execute(id_kategori);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        else{
            Toast.makeText(context,"Koneksi terganggu/tidak ada!",Toast.LENGTH_LONG).show();
        }
    }




}
