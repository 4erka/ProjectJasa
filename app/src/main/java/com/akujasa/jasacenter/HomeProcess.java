package com.akujasa.jasacenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class HomeProcess extends AsyncTask{
    private String nama;
    private String email;
    private String status;
    private Context context;
   private TextView tvNama;
    private TextView tvEmail;
    public HomeProcess(Context context, TextView tvNama, TextView tvEmail){
        this.context = context;
        this.tvNama = tvNama;
        this.tvEmail=tvEmail;
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
        }
        else{
            Toast.makeText(context,"Koneksi terganggu/tidak ada!",Toast.LENGTH_LONG).show();
        }
    }



}
