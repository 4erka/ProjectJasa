package com.akujasa.jasacenter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatusPesananTokoProgressKeterangan extends AppCompatActivity {
    private String TAG = StatusPesananTokoProgressKeterangan.class.getSimpleName();

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
    String stlintang;
    String stbujur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_pesanan_toko_progress_keterangan);
        nama_konsumen = (TextView)findViewById(R.id.nama_konsumen_toko_progress_keterangan);
        nomor_pesanan = (TextView)findViewById(R.id.nomor_jasa_toko_progress_keterangan);
        tanggal = (TextView)findViewById(R.id.tanggal_toko_progress_keterangan);
        keterangan = (TextView)findViewById(R.id.keterangan_toko_progress_keterangan);
        alamat = (TextView)findViewById(R.id.alamat_toko_progress_keterangan);
        nama_jasa = (TextView)findViewById(R.id.nama_jasa_toko_progress_keterangan);
        pesanan_jumlah = (TextView)findViewById(R.id.jumlah_jasa_toko_progress_keterangan);
        //harga_jasa = (TextView)findViewById(R.id.harga_toko_baru_keterangan);

        //int posisi = getIntent().getExtras().getInt("nama_jasa");
        String stnama_jasa = getIntent().getExtras().getString("nama_jasa");
        String stnama_konsumen = getIntent().getExtras().getString("nama_konsumen");
        String stnomor = getIntent().getExtras().getString("nomor_pesanan");
        String sttanggal = getIntent().getExtras().getString("tanggal");
        String stketerangan = getIntent().getExtras().getString("keterangan");
        String stalamat = getIntent().getExtras().getString("alamat");
        stlintang = getIntent().getExtras().getString("lintang");
        stbujur = getIntent().getExtras().getString("bujur");
        String stjumlah = getIntent().getExtras().getString("pesanan_jumlah");
        //String stharga = getIntent().getExtras().getString("harga_jasa");
        pesanan_id = getIntent().getExtras().getString("pesanan_id");
        Log.e(TAG, "id : " + pesanan_id);
        nama_jasa.setText(stnama_jasa);
        nama_konsumen.setText(stnama_konsumen);
        nomor_pesanan.setText(stnomor);
        tanggal.setText(sttanggal);
        keterangan.setText(stketerangan);
        alamat.setText(stalamat);
        pesanan_jumlah.setText(stjumlah);
        //harga_jasa.setText(stharga);

        pesananARJsonList = new ArrayList<>();

        Spinner spinner = (Spinner) findViewById(R.id.spinner_toko_progress_keterangan);
        // Spinner click listener
        //spinner.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("on the way");
        categories.add("waiting list");
        categories.add("on progress");
        categories.add("sent back");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //String selectedItem = parent.getItemAtPosition(position).toString();
                Log.e(TAG, "spinner: " + position);
                //if(selectedItem.equals("sent back"))
                //{
                //}
                if(position == 0){
                    params.put("stat_id","3");
                }
                else if(position == 1) {
                    params.put("stat_id", "4");
                }
                else if(position == 2) {
                    params.put("stat_id", "5");
                }
                else if(position == 3) {
                    params.put("stat_id", "6");
                }

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

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

    public void onGantiStatus(View view){
        params.put("psn_id", pesanan_id);
        new GetAPIGantiStatus().execute();
    }

    public void onJasSel(View view){
        params.put("psn_id", pesanan_id);
        params.put("stat_id", "7");
        new GetAPIGantiStatus().execute();
        onBackPressed();
    }

    public void onLihatLokasi(View view){
        Intent lihat = new Intent(StatusPesananTokoProgressKeterangan.this, PesananTokoMapActivity.class);
        lihat.putExtra("lintang", stlintang);
        lihat.putExtra("bujur", stbujur);
        startActivity(lihat);
    }

    private class GetAPIGantiStatus extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            progressDialog = new ProgressDialog(StatusPesananTokoProgressKeterangan.this);
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
                Toast.makeText(getApplicationContext(),
                        "Ganti Status Berhasil",
                        Toast.LENGTH_LONG)
                        .show();
                //onBackPressed();
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
