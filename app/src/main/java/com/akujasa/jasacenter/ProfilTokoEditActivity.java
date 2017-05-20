package com.akujasa.jasacenter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfilTokoEditActivity extends AppCompatActivity {
    private String TAG = ProfilTokoEditActivity.class.getSimpleName();

    private ProgressDialog progressDialog;
    private static String Jsonurl = "http://rilokukuh.com/admin-jasa/android_pj_update_profile.php";
    ArrayList<HashMap<String, String>> editTokoJsonList;
    HashMap<String, String> params = new HashMap<>();

    String stNama_toko;
    String stAlamat;
    public static String lintang;
    public static String bujur;

    EditText etNama;
    EditText etAlamat;

    String idToko;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil_toko_edit_activity);

        etNama = (EditText)findViewById(R.id.nama_toko_edit);
        etAlamat = (EditText)findViewById(R.id.alamat_toko_edit);

        editTokoJsonList = new ArrayList<>();

        idToko = getIntent().getExtras().getString("idToko");
    }

    public void onLokasiTokoEdit(View view){
        Intent lihat = new Intent(ProfilTokoEditActivity.this, ProfilTokoEditMap.class);
        startActivity(lihat);
    }

    public void onSimpanToko(View view){
        stNama_toko = etNama.getText().toString();
        stAlamat = etAlamat.getText().toString();
        Log.e(TAG, "Response from url: " + lintang + bujur);
        new GetAPIEditToko().execute();
    }

    private class GetAPIEditToko extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            progressDialog = new ProgressDialog(ProfilTokoEditActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler httpHandler = new HttpHandler();

            params.put("pj_id", idToko);
            params.put("pj_nama_jasa", stNama_toko);
            params.put("pj_alamat", stAlamat);
            params.put("pj_lintang", lintang);
            params.put("pj_bujur", bujur);

            // request to json data url and getting response
            String jsonString = httpHandler.makeServiceCall(Jsonurl, params);

            Log.e(TAG, "Response from url: " + jsonString);

            if (jsonString != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    // Getting JSON Array node
                    //JSONArray listMapel = jsonObject.getJSONArray("user");

                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    HashMap<String, String> katalog = new HashMap<>();

                    katalog.put("status", status);
                    katalog.put("message", message);

                    editTokoJsonList.add(katalog);
                    Log.e(TAG, "Response json: " + editTokoJsonList);
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Could not get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Could not get json from server.",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            Log.e(TAG, "test: " + editTokoJsonList.get(0).get("message"));
            if(editTokoJsonList.get(0).get("status").equals("200")){
                Toast.makeText(getApplicationContext(),
                        "Edit berhasil",
                        Toast.LENGTH_LONG)
                        .show();
                onBackPressed();
            }
            else{
                Toast.makeText(getApplicationContext(),
                        "Edit gagal",
                        Toast.LENGTH_LONG)
                        .show();
            }
            /**
             * Updating parsed JSON data into ListView
             * */

        }
    }
}
