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

public class LoginProcess extends AsyncTask {
    private String status;
    private Context context;
    private String id;
    private String idToko;
    public LoginProcess(Context context, String status){
        this.context = context;
        this.status=status;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        try{
            String email = (String)objects[0];
            String password = (String)objects[1];
            String idToko = (String)objects[2];
            //emails = email;
            String link = "http://rilokukuh.com/admin-jasa/android_ksm_login.php";
            String data = URLEncoder.encode("ksm_email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8");
            data += "&"+URLEncoder.encode("ksm_password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
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
        if(status.equals("success")) {
            SharedPreferences sp = context.getSharedPreferences("pencari_info",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("pencari_id",id);
            editor.commit();
            Toast.makeText(context,"Selamat Datang!",Toast.LENGTH_LONG).show();
            Intent intentku = new Intent(context, MenuUtamaActivity.class);
            intentku.putExtra("pencari_id",id);
            intentku.putExtra("idToko", idToko);
            context.startActivity(intentku);
            ((Activity)context).finish();
        }
        else if(status.equals("access denied")){
            Toast.makeText(context,"Email/password salah!",Toast.LENGTH_SHORT).show();
        }
          else{
            Toast.makeText(context,"Koneksi terganggu/tidak ada!",Toast.LENGTH_LONG).show();
        }
    }
}
