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
 * Created by Antonius on 13/05/2017.
 */

public class EditProcess extends AsyncTask {
    private String status;
    private Context context;
    private String id;
    public EditProcess(Context context){
        this.context = context;

    }

    @Override
    protected Object doInBackground(Object[] objects) {

        try{
            String nama = (String)objects[0];
            String alamat = (String)objects[2];
            String email = (String)objects[1];
            String nohp = (String)objects[3];
            String id_konsumen = (String)objects[4];
            //emails = email;
            String link = "http://rilokukuh.com/admin-jasa/android_ksm_update_profile.php";
            String data = URLEncoder.encode("ksm_nama","UTF-8")+"="+URLEncoder.encode(nama,"UTF-8");
            data += "&"+URLEncoder.encode("ksm_alamat","UTF-8")+"="+URLEncoder.encode(alamat,"UTF-8");
            data += "&"+URLEncoder.encode("ksm_email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8");
            data += "&"+URLEncoder.encode("ksm_nohp","UTF-8")+"="+URLEncoder.encode(nohp,"UTF-8");
            data += "&"+URLEncoder.encode("ksm_id","UTF-8")+"="+URLEncoder.encode(id_konsumen,"UTF-8");
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
            Toast.makeText(context,"Edit profil berhasil!",Toast.LENGTH_LONG).show();
            Intent intentku = new Intent(context , ProfilActivity.class);
            context.startActivity(intentku);
            ((Activity)context).finish();
        }

        else{
            Toast.makeText(context,status,Toast.LENGTH_LONG).show();
        }
    }
}

