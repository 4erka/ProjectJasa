package com.akujasa.jasacenter;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
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
 * Created by Antonius on 18/05/2017.
 */

public class ListOnProgressProcess extends AsyncTask{
    private String id_pesanan;
    private String id_penyedia;
    private String id_status;
    private String nama_penyedia;
    private String tanggal_jasa;
    private String harga_jasa;
    private String jumlah_jasa;
    private String alamat_jasa;
    private String nama_jasa;
    private String ket_jasa;
    private Activity context;
    private String status;
    private String ksm_id;
    private ArrayList<ItemPesanan> listku;
    private ListView lvList;
    public ListOnProgressProcess(Activity context, ListView lvList){
        this.context = context;
        this.lvList=lvList;
    }


    @Override
    protected Object doInBackground(Object[] objects) {

        try{

            listku = new ArrayList<ItemPesanan>();
            ksm_id = (String)objects[0];
            Log.e("Z",ksm_id);
            String stat_id="1";
            String link = "http://rilokukuh.com/admin-jasa/android_ksm_get_pesanan.php";
            String data = URLEncoder.encode("ksm_id","UTF-8")+"="+URLEncoder.encode(ksm_id,"UTF-8");
            data += "&" + URLEncoder.encode("stat_id","UTF-8")+"="+URLEncoder.encode(stat_id,"UTF-8");
            Log.e("C",data);
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

            Log.e("A",sb.toString());
            String status = jsonOBJ.getString("status");
            JSONArray listJasa = jsonOBJ.getJSONArray("data");
            for (int i = 0; i < listJasa.length(); i++) {
                JSONObject jasa_info = listJasa.getJSONObject(i);
                id_pesanan = jasa_info.getString("psn_id");
                id_penyedia = jasa_info.getString("pj_id");
                id_status = jasa_info.getString("stat_id");
                nama_penyedia = jasa_info.getString("pj_nama_jasa");
                tanggal_jasa = jasa_info.getString("psn_datetime");
                harga_jasa = jasa_info.getString("jasa_harga");
                jumlah_jasa = jasa_info.getString("psn_jumlah");
                alamat_jasa = jasa_info.getString("psn_alamat");
                ket_jasa = jasa_info.getString("psn_keterangan");
                nama_jasa = jasa_info.getString("jasa_nama");
                    listku.add(new ItemPesanan(id_pesanan, id_penyedia,id_status, nama_penyedia,tanggal_jasa,harga_jasa,jumlah_jasa, alamat_jasa,ket_jasa, nama_jasa));

            }
            if(status.equals("200")){
                stat_id="10";
                link = "http://rilokukuh.com/admin-jasa/android_ksm_get_pesanan.php";
                data = URLEncoder.encode("ksm_id","UTF-8")+"="+URLEncoder.encode(ksm_id,"UTF-8");
                data += "&"+URLEncoder.encode("stat_id","UTF-8")+"="+URLEncoder.encode(stat_id,"UTF-8");
                url = new URL(link);
                conn = url.openConnection();
                conn.setDoOutput(true);
                wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                sb = new StringBuilder();
                line = null;
                while((line=reader.readLine())!=null){
                    sb.append(line);
                    break;
                }
                jsonOBJ = new JSONObject(sb.toString());

                Log.e("A",sb.toString());
                status = jsonOBJ.getString("status");
                listJasa = jsonOBJ.getJSONArray("data");
                for (int i = 0; i < listJasa.length(); i++) {
                    JSONObject jasa_info = listJasa.getJSONObject(i);
                    id_pesanan = jasa_info.getString("psn_id");
                    id_penyedia = jasa_info.getString("pj_id");
                    id_status = jasa_info.getString("stat_id");
                    nama_penyedia = jasa_info.getString("pj_nama_jasa");
                    tanggal_jasa = jasa_info.getString("psn_datetime");
                    harga_jasa = jasa_info.getString("jasa_harga");
                    jumlah_jasa = jasa_info.getString("psn_jumlah");
                    alamat_jasa = jasa_info.getString("psn_alamat");
                    ket_jasa = jasa_info.getString("psn_keterangan");
                    nama_jasa = jasa_info.getString("jasa_nama");
                    listku.add(new ItemPesanan(id_pesanan, id_penyedia,id_status, nama_penyedia,tanggal_jasa,harga_jasa,jumlah_jasa, alamat_jasa,ket_jasa, nama_jasa));
                }
                return status;
            }



            else return status;
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
            PesananListAdapter adapterku = new PesananListAdapter(context, 0, listku);
            lvList.setAdapter(adapterku);
            lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String id_stat = listku.get(position).getId_status();
                    if(id_stat.equals("2")){
                        String stat = "Sudah disetujui penyedia jasa";
                        Intent intentku = new Intent(context, PesananInfo.class);
                        intentku.putExtra("data_pesanan", listku.get(position));
                        intentku.putExtra("status", stat);
                        context.startActivity(intentku);
                    }
                    else if(id_stat.equals("3")){
                        String stat = "Menuju tempat anda";
                        Intent intentku = new Intent(context, PesananInfo.class);
                        intentku.putExtra("data_pesanan", listku.get(position));
                        intentku.putExtra("status", stat);
                        context.startActivity(intentku);
                    }
                    else if(id_stat.equals("4")){
                        String stat = "Dalam Waiting List";
                        Intent intentku = new Intent(context, PesananInfo.class);
                        intentku.putExtra("data_pesanan", listku.get(position));
                        intentku.putExtra("status", stat);
                        context.startActivity(intentku);
                    }
                    else if(id_stat.equals("5")){
                        String stat = "Dalam proses pengerjaan";
                        Intent intentku = new Intent(context, PesananInfo.class);
                        intentku.putExtra("data_pesanan", listku.get(position));
                        intentku.putExtra("status", stat);
                        context.startActivity(intentku);
                    }
                    else if(id_stat.equals("6")){
                        String stat = "Mengantarkan hasil pesanan";
                        Intent intentku = new Intent(context, PesananInfo.class);
                        intentku.putExtra("data_pesanan", listku.get(position));
                        intentku.putExtra("status", stat);
                        context.startActivity(intentku);
                    }
                    else if(id_stat.equals("1")){
                        String stat = "Menunggu persetujuan penyedia jasa";
                        Intent intentku = new Intent(context, PesananInfoCancel.class);
                        intentku.putExtra("data_pesanan", listku.get(position));
                        intentku.putExtra("status", stat);
                        context.startActivity(intentku);
                    }

                }
            });
        }
        else{
            Toast.makeText(context,"Koneksi terganggu/tidak ada!",Toast.LENGTH_LONG).show();
        }
    }

}
