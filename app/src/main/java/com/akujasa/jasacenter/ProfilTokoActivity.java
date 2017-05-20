package com.akujasa.jasacenter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfilTokoActivity extends AppCompatActivity {
    private String TAG = StatusPesananTokoBaruKeterangan.class.getSimpleName();

    private ProgressDialog progressDialog;
    private static String Jsonurl = "http://rilokukuh.com/admin-jasa/android_pj_get_profile.php";
    ArrayList<HashMap<String, String>> profilTokoJsonList;
    HashMap<String, String> params = new HashMap<>();

    TextView tvNama;
    TextView tvJenis;
    TextView tvAlamat;

    String stLintang;
    String stBujur;

    String idToko;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil_toko_activity);

        idToko = getIntent().getExtras().getString("idToko");

        tvNama=(TextView)findViewById(R.id.nama_profil_toko);
        tvJenis=(TextView)findViewById(R.id.jenis_profil_toko);
        tvAlamat=(TextView)findViewById(R.id.alamat_profil_toko);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        super.onResume();
        profilTokoJsonList = new ArrayList<>();
        new GetAPIProfilToko().execute();
    }

    public void onLihatLokasi(View view){
        Intent lihat = new Intent(ProfilTokoActivity.this, PesananTokoMapActivity.class);
        lihat.putExtra("lintang", stLintang);
        lihat.putExtra("bujur", stBujur);
        startActivity(lihat);
    }

    private class GetAPIProfilToko extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            progressDialog = new ProgressDialog(ProfilTokoActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler httpHandler = new HttpHandler();

            // request to json data url and getting response
            HashMap<String, String> params = new HashMap<>();
            params.put("pj_id", idToko);
            String jsonString = httpHandler.makeServiceCall(Jsonurl, params);

            Log.e(TAG, "Response from url: " + jsonString);

            if (jsonString != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    // Getting JSON Array node
                    //JSONArray listMapel = jsonObject.getJSONArray("user");

                    //String status = jsonObject.getString("status");
                    //String message = jsonObject.getString("message");
                    JSONArray listData = jsonObject.getJSONArray("data");

                    for (int i = 0; i < listData.length(); i++) {
                        JSONObject c = listData.getJSONObject(i);
                        String pj_nama_jasa = c.getString("pj_nama_jasa");
                        String pj_alamat = c.getString("pj_alamat");
                        String jpj_nama = c.getString("jpj_nama");
                        String pj_lintang = c.getString("pj_lintang");
                        String pj_bujur = c.getString("pj_bujur");

                        // tmp hash map for single contact
                        HashMap<String, String> data = new HashMap<>();

                        // adding each child node to HashMap key => value
                        data.put("pj_nama_jasa", pj_nama_jasa);
                        data.put("pj_alamat", pj_alamat);
                        data.put("jpj_nama", jpj_nama);
                        data.put("pj_lintang", pj_lintang);
                        data.put("pj_bujur", pj_bujur);

                        // adding contact to contact list
                        profilTokoJsonList.add(data);
                        //Log.e(TAG, "Response json: " + KatalogJsonList);
                    }
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
            Log.e(TAG, "test: " + profilTokoJsonList);
            /**
             * Updating parsed JSON data into ListView
             * */
            tvNama.setText(profilTokoJsonList.get(0).get("pj_nama_jasa"));
            tvJenis.setText(profilTokoJsonList.get(0).get("jpj_nama"));
            tvAlamat.setText(profilTokoJsonList.get(0).get("pj_alamat"));
            stLintang = profilTokoJsonList.get(0).get("pj_lintang");
            stBujur = profilTokoJsonList.get(0).get("pj_bujur");
        }
    }
}
