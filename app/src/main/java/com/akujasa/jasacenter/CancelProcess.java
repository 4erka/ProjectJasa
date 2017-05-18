package com.akujasa.jasacenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
 * Created by Antonius on 18/05/2017.
 */

public class CancelProcess extends AsyncTask {
    private String status;
    private Context context;
    private String id;
    private String idToko;
    public CancelProcess(Context context){
        this.context = context;

    }

    @Override
    protected Object doInBackground(Object[] objects) {

        try{
            String psn_id = (String)objects[0];
            String stat_id = "9";
            //emails = email;
            String link = "http://rilokukuh.com/admin-jasa/android_pj_update_pesanan.php";
            String data = URLEncoder.encode("psn_id","UTF-8")+"="+URLEncoder.encode(psn_id,"UTF-8");
            data += "&"+URLEncoder.encode("stat_id","UTF-8")+"="+URLEncoder.encode(stat_id,"UTF-8");
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
            id = jsonOBJ.getString("id");
            return message;
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
            Toast.makeText(context,"Selamat Datang!",Toast.LENGTH_LONG).show();
            Intent intentku = new Intent(context, StatusPesananFragment.class);
            context.startActivity(intentku);
            ((Activity)context).finish();
        }

        else{
            Toast.makeText(context,"Koneksi terganggu/tidak ada!",Toast.LENGTH_LONG).show();
        }
    }


}
