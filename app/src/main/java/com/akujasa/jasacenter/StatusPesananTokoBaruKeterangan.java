package com.akujasa.jasacenter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class StatusPesananTokoBaruKeterangan extends AppCompatActivity {
    private String TAG = StatusPesananTokoBaruKeterangan.class.getSimpleName();

    private ProgressDialog progressDialog;
    private static String Jsonurl = "http://rilokukuh.com/admin-jasa/android_pj_update_pesanan.php";
    ArrayList<HashMap<String, String>> pesananARJsonList;
    HashMap<String, String> params = new HashMap<>();

    TextView nama_toko;
    TextView nama_konsumen;
    TextView nomor_pesanan;
    TextView tanggal;
    TextView keterangan;
    TextView alamat;
    TextView nama_jasa;
    TextView pesanan_jumlah;
    TextView harga_jasa;
    ArrayList<HashMap<String, String>> dataPesananBaru;

    String pesanan_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_pesanan_toko_baru_keterangan);
        nama_konsumen = (TextView)findViewById(R.id.nama_konsumen_toko_baru_keterangan);
        nomor_pesanan = (TextView)findViewById(R.id.nomor_jasa_toko_baru_keterangan);
        tanggal = (TextView)findViewById(R.id.tanggal_toko_baru_keterangan);
        keterangan = (TextView)findViewById(R.id.keterangan_toko_baru_keterangan);
        alamat = (TextView)findViewById(R.id.alamat_toko_baru_keterangan);
        nama_jasa = (TextView)findViewById(R.id.nama_jasa_toko_baru_keterangan);
        pesanan_jumlah = (TextView)findViewById(R.id.jumlah_jasa_toko_baru_keterangan);
        //harga_jasa = (TextView)findViewById(R.id.harga_toko_baru_keterangan);

        //int posisi = getIntent().getExtras().getInt("nama_jasa");
        String stnama_jasa = getIntent().getExtras().getString("nama_jasa");
        String stnama_konsumen = getIntent().getExtras().getString("nama_konsumen");
        String stnomor = getIntent().getExtras().getString("nomor_pesanan");
        String sttanggal = getIntent().getExtras().getString("tanggal");
        String stketerangan = getIntent().getExtras().getString("keterangan");
        String stalamat = getIntent().getExtras().getString("alamat");
        String stjumlah = getIntent().getExtras().getString("pesanan_jumlah");
        //String stharga = getIntent().getExtras().getString("harga_jasa");
        pesanan_id = getIntent().getExtras().getString("pesanan_id");
        //Log.e(TAG, "hasil : " + x);
        nama_jasa.setText(stnama_jasa);
        nama_konsumen.setText(stnama_konsumen);
        nomor_pesanan.setText(stnomor);
        tanggal.setText(sttanggal);
        keterangan.setText(stketerangan);
        alamat.setText(stalamat);
        pesanan_jumlah.setText(stjumlah);
        //harga_jasa.setText(stharga);

        pesananARJsonList = new ArrayList<>();

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

    public void onTerima(View view){
        params.put("psn_id", pesanan_id);
        params.put("stat_id", "2");
        new GetAPIPesananAR().execute();
    }

    public void onTolak(View view){
        params.put("psn_id", pesanan_id);
        params.put("stat_id", "8");
        new GetAPIPesananAR().execute();
    }

    private class GetAPIPesananAR extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            progressDialog = new ProgressDialog(StatusPesananTokoBaruKeterangan.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler httpHandler = new HttpHandler();

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

                    pesananARJsonList.add(katalog);
                    Log.e(TAG, "Response json: " + pesananARJsonList);
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
            Log.e(TAG, "test: " + pesananARJsonList.get(0).get("message"));
            if(pesananARJsonList.get(0).get("message").equals("update status success")){
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
