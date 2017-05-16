package com.akujasa.jasacenter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class KatalogJasaTambah extends AppCompatActivity {

    private String TAG = KatalogJasaTambah.class.getSimpleName();

    private ProgressDialog progressDialog;
    // JSON data url
    private static String Jsonurl = "http://rilokukuh.com/admin-jasa/android_create_katalog.php";
    ArrayList<HashMap<String, String>> KatalogJsonList;
    String idToko;
    EditText etNama;
    EditText etKeterangan;
    EditText etHarga;
    String stNama;
    String stKeterangan;
    String stHarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.katalog_jasa_tambah);
        KatalogJsonList = new ArrayList<>();
        etNama = (EditText)findViewById(R.id.nama_katalog_jasa_tambah);
        etKeterangan = (EditText)findViewById(R.id.keterangan_katalog_jasa_tambah);
        etHarga = (EditText)findViewById(R.id.harga_katalog_jasa_tambah);
        idToko = getIntent().getExtras().getString("idToko");
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

    public void onJasTam(View view){
        stNama = etNama.getText().toString();
        stKeterangan = etKeterangan.getText().toString();
        stHarga = etHarga.getText().toString();
        new KatalogJasaTambah.GetAPIKatalogTambah().execute();
    }

    private class GetAPIKatalogTambah extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            progressDialog = new ProgressDialog(KatalogJasaTambah.this);
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
            params.put("jasa_nama", stNama);
            params.put("jasa_deskripsi", stKeterangan);
            params.put("jasa_harga", stHarga);
            String jsonString = httpHandler.makeServiceCall(Jsonurl, params);

            Log.e(TAG, "Response from url: " + jsonString);

            if (jsonString != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    // Getting JSON Array node
                    //JSONArray listMapel = jsonObject.getJSONArray("user");

                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    /*for (int i = 0; i < listMapel.length(); i++) {
                        JSONObject c = listMapel.getJSONObject(i);
                        String nama = c.getString("nama");
                        String nama_sekolah = c.getString("nama_sekolah");

                        // tmp hash map for single contact
                        HashMap<String, String> mapel = new HashMap<>();

                        // adding each child node to HashMap key => value
                        mapel.put("nama", nama);
                        mapel.put("nama_sekolah", nama_sekolah);

                        // adding contact to contact list
                        loginJsonList.add(mapel);
                        Log.e(TAG, "Response json: " + loginJsonList);
                    }*/

                    HashMap<String, String> katalog = new HashMap<>();

                    katalog.put("status", status);
                    katalog.put("message", message);

                    KatalogJsonList.add(katalog);
                    Log.e(TAG, "Response json: " + KatalogJsonList);
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
            Log.e(TAG, "test: " + KatalogJsonList.get(0).get("message"));
            if(KatalogJsonList.get(0).get("message").equals("create katalog success")){
                onBackPressed();
            }
            else{
                Toast.makeText(getApplicationContext(),
                        "Username / Password Salah",
                        Toast.LENGTH_LONG)
                        .show();
            }
            /**
             * Updating parsed JSON data into ListView
             * */

        }
    }

    public void onBackPressed()
    {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
    }
}
