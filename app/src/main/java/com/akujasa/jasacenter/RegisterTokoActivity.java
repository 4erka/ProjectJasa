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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegisterTokoActivity extends AppCompatActivity {
    private String TAG = StatusPesananTokoBaruKeterangan.class.getSimpleName();

    private ProgressDialog progressDialog;
    private static String Jsonurl = "http://rilokukuh.com/admin-jasa/android_register.php";
    ArrayList<HashMap<String, String>> registerTokoJsonList;
    HashMap<String, String> params = new HashMap<>();
    String idKonsumen;

    EditText nama_toko;
    EditText alamat;
    String stNama_toko;
    String stAlamat;
    public static String lintang;
    public static String bujur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_toko_activity);
        idKonsumen = getIntent().getExtras().getString("idKonsumen");
        nama_toko = (EditText)findViewById(R.id.nama_toko_register_toko) ;
        alamat = (EditText)findViewById(R.id.alamat_toko_register_toko);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        registerTokoJsonList = new ArrayList<>();

        Spinner spinner = (Spinner) findViewById(R.id.spinner_register_toko);
        // Spinner click listener
        //spinner.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Katering");
        categories.add("Laundry");
        categories.add("Jahit");
        categories.add("Servis Elektronik");
        categories.add("Cuci");
        categories.add("Servis Kendaraan");
        categories.add("Asisten Rumah");
        categories.add("Pengasuh Bayi");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String selectedItem = parent.getItemAtPosition(position).toString();
                Log.e(TAG, "spinner: " + position);
                //if(selectedItem.equals("sent back"))
                //{
                //}
                if (position == 0) {
                    params.put("jpj_id", "1");
                } else if (position == 1) {
                    params.put("jpj_id", "2");
                } else if (position == 2) {
                    params.put("jpj_id", "3");
                } else if (position == 3) {
                    params.put("jpj_id", "4");
                } else if (position == 4) {
                    params.put("jpj_id", "5");
                } else if (position == 5) {
                    params.put("jpj_id", "6");
                } else if (position == 6) {
                    params.put("jpj_id", "8");
                } else if (position == 6) {
                    params.put("jpj_id", "9");
                }

            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

    public void onLokasiTokoRegister(View view){
        Intent lihat = new Intent(RegisterTokoActivity.this, RegisterTokoMap.class);
        startActivity(lihat);
    }

    public void onRegisterToko(View view){
        stNama_toko = nama_toko.getText().toString();
        stAlamat = alamat.getText().toString();
        Log.e(TAG, "Response from url: " + lintang + bujur);
        new GetAPIRegisterToko().execute();
    }

    private class GetAPIRegisterToko extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            progressDialog = new ProgressDialog(RegisterTokoActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler httpHandler = new HttpHandler();

            params.put("ksm_id", idKonsumen);
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

                    registerTokoJsonList.add(katalog);
                    Log.e(TAG, "Response json: " + registerTokoJsonList);
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
            Log.e(TAG, "test: " + registerTokoJsonList.get(0).get("message"));
            if(registerTokoJsonList.get(0).get("status").equals("200")){
                Toast.makeText(getApplicationContext(),
                        "Register berhasil",
                        Toast.LENGTH_LONG)
                        .show();
                onBackPressed();
            }
            else{
                Toast.makeText(getApplicationContext(),
                        "Register gagal",
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
