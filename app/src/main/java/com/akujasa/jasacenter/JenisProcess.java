package com.akujasa.jasacenter;

import android.app.Activity;
import android.content.Context;
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
 * Created by Antonius on 14/05/2017.
 */

public class JenisProcess extends AsyncTask {
    private String status;
    private Button btCari;
    private Spinner spJenis;
    private Activity context;
    private String id;
    private int positions;
    private ArrayList<ItemJenis> listku;
    private ListView lvList;
    public JenisProcess(Activity context, Spinner spJenis, Button btCari, ListView lvList){
        this.context = context;
        this.spJenis=spJenis;
        this.btCari = btCari;
        this.lvList = lvList;
    }


    @Override
    protected Object doInBackground(Object[] objects) {

        try{
            listku = new ArrayList<ItemJenis>();
            id = (String)objects[0];
            String link = "http://rilokukuh.com/admin-jasa/android_ksm_get_jenis.php";
            String data = URLEncoder.encode("kpj_id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8");
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
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
            JSONArray listJasa = jsonOBJ.getJSONArray("data");
            for (int i = 0; i < listJasa.length(); i++) {
                JSONObject jasa_info = listJasa.getJSONObject(i);
                String nama_jenis = jasa_info.getString("jpj_nama");
                String id_jenis = jasa_info.getString("jpj_id");
                listku.add(new ItemJenis(nama_jenis,id_jenis));
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
            JenisAdapter adapterku = new JenisAdapter(context,R.layout.item_jenis,R.id.tvNama,listku);
            spJenis.setAdapter(adapterku);
            spJenis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected (AdapterView<?> parent,
                                            View v, int position, long id){
                    positions=position;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            btCari.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id_jenis = listku.get(positions).getId();
                    new CariProcess(context,lvList).execute(id_jenis);
                }
            });
        }

        else{
            Toast.makeText(context,"Koneksi terganggu/tidak ada!",Toast.LENGTH_LONG).show();
        }
    }


}
