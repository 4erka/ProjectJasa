package com.akujasa.jasacenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Antonius on 16/05/2017.
 */

public class PesanformProcess extends AsyncTask{
    private String status;
    private Context context;
    private String id;
    public PesanformProcess(Context context){
        this.context = context;

    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(context,"Pesanan sedang dikirim...",Toast.LENGTH_LONG).show();
    }

    @Override

    protected Object doInBackground(Object[] objects) {

        try{
            String jasa_id = (String)objects[0];
            String ksm_id = (String)objects[1];
            String ket = (String)objects[2];
            String lintang = (String)objects[3];
            String bujur = (String)objects[4];
            String alamat = (String)objects[5];
            String jumlah = (String)objects[5];
            //emails = email;
            String link = "http://rilokukuh.com/admin-jasa/android_ksm_create_pesanan.php";
            String data = URLEncoder.encode("jasa_id","UTF-8")+"="+URLEncoder.encode(jasa_id,"UTF-8");
            data += "&"+URLEncoder.encode("ksm_id","UTF-8")+"="+URLEncoder.encode(ksm_id,"UTF-8");
            data += "&"+URLEncoder.encode("psn_keterangan","UTF-8")+"="+URLEncoder.encode(ket,"UTF-8");
            data += "&"+URLEncoder.encode("psn_lintang","UTF-8")+"="+URLEncoder.encode(lintang,"UTF-8");
            data += "&"+URLEncoder.encode("psn_bujur","UTF-8")+"="+URLEncoder.encode(bujur,"UTF-8");
            data += "&"+URLEncoder.encode("psn_alamat","UTF-8")+"="+URLEncoder.encode(alamat,"UTF-8");
            data += "&"+URLEncoder.encode("psn_jumlah","UTF-8")+"="+URLEncoder.encode(jumlah,"UTF-8");
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
            String message = jsonOBJ.getString("message");

            return status;
        }
        catch (Exception e){
            return new String("Exception: "+e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(Object o) {
        this.status = (String)o;

        if(status.equals("200")) {
            Toast.makeText(context,"Pesanan berhasil dikirim!",Toast.LENGTH_LONG).show();
            Intent intentku = new Intent(context, MenuUtamaActivity.class);
            intentku.putExtra("pencari_id",id);
            context.startActivity(intentku);
            ((Activity)context).finish();
        }

        else{
            Toast.makeText(context,"Koneksi terganggu/tidak ada!",Toast.LENGTH_LONG).show();
        }
    }

}
