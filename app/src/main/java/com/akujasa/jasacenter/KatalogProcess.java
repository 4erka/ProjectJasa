package com.akujasa.jasacenter;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
 * Created by Antonius on 15/05/2017.
 */

public class KatalogProcess extends AsyncTask {
    private String status;
    private String id_katalog;
    private String nama_katalog;
    private String deskripsi_katalog;
    private String harga_katalog;

    private Activity context;
    private String id;
    private ArrayList<ItemKatalog> listku;
    private ListView lvList;
    public KatalogProcess(Activity context, ListView lvList){
        this.context = context;
        this.lvList=lvList;
    }


    @Override
    protected Object doInBackground(Object[] objects) {

        try{

            listku = new ArrayList<ItemKatalog>();
            id = (String)objects[0];
            String link = "http://rilokukuh.com/admin-jasa/android_get_katalog.php";
            String data = URLEncoder.encode("pj_id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8");
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
            JSONArray listKatalog = jsonOBJ.getJSONArray("data");
            for (int i = 0; i < listKatalog.length(); i++) {
                JSONObject jasa_info = listKatalog.getJSONObject(i);
                id_katalog = jasa_info.getString("jasa_id");
                //String status_jasa = jasa_info.getString("pj_is_confirmed");
                nama_katalog = jasa_info.getString("jasa_nama");
                deskripsi_katalog = jasa_info.getString("jasa_deskripsi");
                harga_katalog = jasa_info.getString("jasa_harga");
                //if(status_jasa.equals("1")) {
                    listku.add(new ItemKatalog(id_katalog,nama_katalog,deskripsi_katalog,harga_katalog));
                //}
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
            KatalogAdapter adapterku = new KatalogAdapter(context, 0, listku);
            lvList.setAdapter(adapterku);

        }
        else{
            Toast.makeText(context,"Koneksi terganggu/tidak ada!",Toast.LENGTH_LONG).show();
        }
    }





}
