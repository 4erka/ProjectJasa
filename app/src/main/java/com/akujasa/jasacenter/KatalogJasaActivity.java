package com.akujasa.jasacenter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by erka on 3/4/2017.
 */

public class KatalogJasaActivity extends AppCompatActivity{

    private String TAG = KatalogJasaActivity.class.getSimpleName();

    private ProgressDialog progressDialog;
    private ListView listView;
    // JSON data url
    private static String Jsonurl = "http://rilokukuh.com/admin-jasa/android_get_katalog.php";
    private static String JsonurlHapus = "http://rilokukuh.com/admin-jasa/android_delete_katalog.php";
    ArrayList<HashMap<String, String>> katalogNamaJsonList;
    ArrayList<HashMap<String, String>> katalogJsonList;
    ArrayList<HashMap<String, String>> katalogIdJsonList;
    ArrayList<HashMap<String, String>> katalogHapusJsonList;
    ArrayList<String> namaKatalogJasa;
    String idToko;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.katalog_jasa_activity);
        idToko = getIntent().getExtras().getString("idToko");
        Log.e(TAG, "idToko3: " + idToko);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onResume(){
        super.onResume();
        listView = (ListView) findViewById(R.id.listkatalog);
        katalogNamaJsonList = new ArrayList<>();
        katalogJsonList = new ArrayList<>();
        katalogIdJsonList = new ArrayList<>();
        katalogHapusJsonList = new ArrayList<>();
        new GetAPIKatalog().execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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

    public void onJasaTambah(View view){
        Intent tambah = new Intent(KatalogJasaActivity.this, KatalogJasaTambah.class);
        tambah.putExtra("idToko", idToko);
        startActivity(tambah);
    }

    private class GetAPIKatalog extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            progressDialog = new ProgressDialog(KatalogJasaActivity.this);
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
                        String id_jasa = c.getString("jasa_id");
                        String nama_jasa = c.getString("jasa_nama");
                        String deskripsi_jasa = c.getString("jasa_deskripsi");
                        String harga_jasa = c.getString("jasa_harga");

                        // tmp hash map for single contact
                        HashMap<String, String> data_katalog = new HashMap<>();
                        HashMap<String, String> data_id_katalog = new HashMap<>();
                        HashMap<String, String> data_nama_katalog = new HashMap<>();

                        // adding each child node to HashMap key => value
                        data_id_katalog.put("jasa_id", id_jasa);
                        data_nama_katalog.put("jasa_nama", nama_jasa);
                        data_katalog.put("jasa_deskripsi", deskripsi_jasa);
                        data_katalog.put("jasa_harga", harga_jasa);

                        // adding contact to contact list
                        katalogJsonList.add(data_katalog);
                        katalogIdJsonList.add(data_id_katalog);
                        katalogNamaJsonList.add(data_nama_katalog);
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
            Log.e(TAG, "test: " + katalogJsonList);
            /**
             * Updating parsed JSON data into ListView
             * */
            /*ListAdapter adapter = new SimpleAdapter(
                    getActivity(), KatalogJsonList,
                    R.layout.katalog_jasa_content, new String[]{"jasa_nama"}, new int[]{R.id.listkatalogcontent});
            listView.setAdapter(adapter);*/

            namaKatalogJasa = new ArrayList<String>();
            for (HashMap<String, String> hash : katalogNamaJsonList) {
                for (String current : hash.values()) {
                    namaKatalogJasa.add(current);
                }
            }

            // Keys used in Hashmap
            String[] from = { "jasa_nama" };

            // Ids of views in listview_layout
            int[] to = { R.id.listkatalogcontent};

            // Instantiating an adapter to store each items
            //R.layout.listview_layout defines the layout of each item
            KatalogJasaAdapter adapter = new KatalogJasaAdapter(namaKatalogJasa, KatalogJasaActivity.this, katalogIdJsonList, katalogJsonList, katalogNamaJsonList,idToko);

            // Instantiating an adapter to store each items
            //R.layout.listview_layout defines the layout of each item
            //SimpleAdapter adapter = new SimpleAdapter(KatalogJasaActivity.this, katalogJsonList, R.layout.katalog_jasa_content, from, to);

            listView.setAdapter(adapter);
        }
    }

    private class GetAPIKatalogHapus extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            progressDialog = new ProgressDialog(KatalogJasaActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler httpHandler = new HttpHandler();

            // request to json data url and getting response
            HashMap<String, String> params = new HashMap<>();
            params.put("pj_id", "100");
            params.put("jasa_id", "112");
            String jsonString = httpHandler.makeServiceCall(JsonurlHapus, params);

            Log.e(TAG, "Response from url: " + jsonString);

            if (jsonString != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    // Getting JSON Array node
                    //JSONArray listMapel = jsonObject.getJSONArray("user");

                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    HashMap<String, String> mapel = new HashMap<>();

                    mapel.put("status", status);
                    mapel.put("message", message);

                    katalogHapusJsonList.add(mapel);
                    Log.e(TAG, "Response json: " + katalogHapusJsonList);

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
            Log.e(TAG, "test: " + katalogHapusJsonList);
            /**
             * Updating parsed JSON data into ListView
             * */
            if(katalogHapusJsonList.get(0).get("message").equals("delete success")){
                Toast.makeText(getApplicationContext(),
                        "Hapus Berhasil",
                        Toast.LENGTH_LONG)
                        .show();
                onResume();
            }
            else{
                Toast.makeText(getApplicationContext(),
                        "Hapus Gagal",
                        Toast.LENGTH_LONG)
                        .show();
            }
            /*ListAdapter adapter = new SimpleAdapter(
                    getActivity(), KatalogJsonList,
                    R.layout.katalog_jasa_content, new String[]{"jasa_nama"}, new int[]{R.id.listkatalogcontent});
            listView.setAdapter(adapter);*/
        }
    }
}
