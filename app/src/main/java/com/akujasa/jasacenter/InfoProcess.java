package com.akujasa.jasacenter;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Antonius on 13/05/2017.
 */

public class InfoProcess extends AsyncTask{
    private String nama;
    private String email;
    private String alamat;
    private String nohp;
    private String status;
    private Context context;
   private TextView tvNama;
    private TextView tvEmail;
    private TextView tvAlamat;
    private TextView tvNohp;
    public InfoProcess(Context context, TextView tvNama, TextView tvEmail, TextView tvAlamat, TextView tvNohp){
        this.context = context;
        this.tvNama = tvNama;
        this.tvEmail=tvEmail;
        this.tvAlamat = tvAlamat;
        this.tvNohp=tvNohp;
     }

    @Override
    protected Object doInBackground(Object[] objects) {

        try{
            String id = (String)objects[0];

            //emails = email;
            String link = "http://rilokukuh.com/admin-jasa/android_ksm_get_profile.php";
            String data = URLEncoder.encode("ksm_id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8");

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
            JSONObject datas = jsonOBJ.getJSONObject("data");
            nama = datas.getString("ksm_nama");
            email = datas.getString("ksm_email");
            alamat = datas.getString("ksm_alamat");
            nohp = datas.getString("ksm_nohp");
            String status = jsonOBJ.getString("status");
            return status;
        }
        catch (Exception e){
            return new String("Exception: "+e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(Object o) {
        this.status = (String)o;
      //  Toast.makeText(context,status,Toast.LENGTH_LONG).show();
        if(status.equals("200")) {
            tvNama.setText(nama);
            tvEmail.setText(email);
            tvAlamat.setText(alamat);
            tvNohp.setText(nohp);
        }
        else{
            Toast.makeText(context,"Koneksi terganggu/tidak ada!",Toast.LENGTH_LONG).show();
        }
    }



}
