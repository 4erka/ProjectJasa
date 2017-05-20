package com.akujasa.jasacenter;

import android.app.Activity;
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
 * Created by Antonius on 14/05/2017.
 */

public class CariProcess extends AsyncTask{
    private String status;
    private String id_jasa;
    private String nama_jasa;
    private String alamat_jasa;
    private String email_jasa;
    private String nohp_jasa;
    private String rating_jasa;
    private String kategori_jasa;
    private Activity context;
    private String id;
    private ArrayList<ItemPenyediaJasa> listku;
    private ListView lvList;
    public CariProcess(Activity context, ListView lvList){
        this.context = context;
        this.lvList=lvList;
    }


    @Override
    protected Object doInBackground(Object[] objects) {

        try{

            listku = new ArrayList<ItemPenyediaJasa>();
            id = (String)objects[0];
            String link = "http://rilokukuh.com/admin-jasa/android_ksm_get_penyedia_jasa.php";
            String data = URLEncoder.encode("jpj_id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8");
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
                id_jasa = jasa_info.getString("pj_id");
                String status_jasa = jasa_info.getString("pj_is_confirmed");
                nama_jasa = jasa_info.getString("pj_nama_jasa");
                email_jasa = jasa_info.getString("pj_email");
                alamat_jasa = jasa_info.getString("pj_alamat");
                nohp_jasa = jasa_info.getString("pj_nohp");
                rating_jasa = jasa_info.getString("pj_rating");
                kategori_jasa = jasa_info.getString("kpj_id");
                if(status_jasa.equals("1")) {
                    listku.add(new ItemPenyediaJasa(id_jasa, nama_jasa, email_jasa, alamat_jasa, nohp_jasa, kategori_jasa, Integer.parseInt(rating_jasa)));
                }
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
            PenyediaJasaListAdapter adapterku = new PenyediaJasaListAdapter(context, 0, listku);
            lvList.setAdapter(adapterku);
            lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String ids = listku.get(position).getId();
                    String nama = listku.get(position).getNama();
                    String alamat = listku.get(position).getAlamat();
                    String email = listku.get(position).getEmail();
                    String nohp = listku.get(position).getNohp();
                    int rating = listku.get(position).getRating();
                    Intent intentku = new Intent(context, PesaninformasiActivity.class);
                    intentku.putExtra("id_jasa", ids);
                    intentku.putExtra("nama_jasa", nama);
                    intentku.putExtra("email_jasa", email);
                    intentku.putExtra("alamat_jasa", alamat);
                    intentku.putExtra("nohp_jasa", nohp);
                    intentku.putExtra("rating_jasa", rating);
                    context.startActivity(intentku);
                }
            });
        }
        else{
            Toast.makeText(context,"Koneksi terganggu/tidak ada!",Toast.LENGTH_LONG).show();
        }
    }






}
