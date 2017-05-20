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

public class KatalogJasaEdit extends AppCompatActivity {

    private String TAG = KatalogJasaEdit.class.getSimpleName();

    private ProgressDialog progressDialog;
    // JSON data url
    private static String Jsonurl = "http://rilokukuh.com/admin-jasa/android_update_katalog.php";
    ArrayList<HashMap<String, String>> KatalogJsonList;
    String stid;

    String idToko;
    String namaJasa;
    String keteranganJasa;
    String hargaJasa;
    TextView tvNama;
    EditText etKeterangan;
    EditText etHarga;
    TextView tvKeterangan;
    TextView tvHarga;
    String stKeterangan;
    String stHarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.katalog_jasa_edit);
        KatalogJsonList = new ArrayList<>();

        stid = getIntent().getExtras().getString("jasa_id");
        tvNama = (TextView)findViewById(R.id.nama_katalog_jasa_edit);
        tvKeterangan = (TextView)findViewById(R.id.keterangan_katalog_edit);
        tvHarga = (TextView)findViewById(R.id.harga_katalog_edit);

        etKeterangan = (EditText)findViewById(R.id.keterangan_katalog_jasa_edit);
        etHarga = (EditText)findViewById(R.id.harga_katalog_jasa_edit);

        idToko = getIntent().getExtras().getString("idToko");
        namaJasa = getIntent().getExtras().getString("jasa_nama");
        keteranganJasa = getIntent().getExtras().getString("jasa_deskripsi");
        hargaJasa = getIntent().getExtras().getString("jasa_harga");

        tvNama.setText(namaJasa);
        tvKeterangan.setText(keteranganJasa);
        tvHarga.setText(hargaJasa);

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

    public void onJasEd(View view){
        stKeterangan = etKeterangan.getText().toString();
        stHarga = etHarga.getText().toString();
        new GetAPIKatalogEdit().execute();
    }

    private class GetAPIKatalogEdit extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            progressDialog = new ProgressDialog(KatalogJasaEdit.this);
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
            params.put("jasa_id", stid);
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
            if(KatalogJsonList.get(0).get("message").equals("update success")){
                Toast.makeText(getApplicationContext(),
                        "Edit Berhasil",
                        Toast.LENGTH_LONG)
                        .show();
                onBackPressed();
            }
            else{
                Toast.makeText(getApplicationContext(),
                        "Edit Gagal",
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
