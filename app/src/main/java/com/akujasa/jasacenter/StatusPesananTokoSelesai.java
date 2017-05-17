package com.akujasa.jasacenter;

/**
 * Created by erka on 3/19/2017.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class StatusPesananTokoSelesai extends Fragment{

    private String TAG = StatusPesananTokoProgress.class.getSimpleName();

    private ListView listView;
    private ProgressDialog progressDialog;
    //Context context;
    // JSON data url
    private static String Jsonurl = "http://rilokukuh.com/admin-jasa/android_pj_get_pesanan.php";
    ArrayList<HashMap<String, String>> pesananSelesaiJsonList;
    String idToko;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.status_pesanan_toko_selesai, container, false);
        idToko = getArguments().getString("idToko");
        Log.e(TAG, "status pesanan fragment3: " + idToko);
        listView = (ListView) rootView.findViewById(R.id.list_pesanan_selesai);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "onitem: berhasil" + position + pesananSelesaiJsonList.get(position).get("jasa_nama"));
                Intent pesananbaruketerangan = new Intent(getActivity(), StatusPesananTokoSelesaiKeterangan.class);
                //pesananbaruketerangan.putExtra("posisi", position);
                pesananbaruketerangan.putExtra("nama_jasa", pesananSelesaiJsonList.get(position).get("jasa_nama"));
                pesananbaruketerangan.putExtra("nama_konsumen", pesananSelesaiJsonList.get(position).get("ksm_nama"));
                pesananbaruketerangan.putExtra("nomor_pesanan", pesananSelesaiJsonList.get(position).get("psn_id"));
                pesananbaruketerangan.putExtra("tanggal", pesananSelesaiJsonList.get(position).get("psn_datetime"));
                pesananbaruketerangan.putExtra("keterangan", pesananSelesaiJsonList.get(position).get("psn_keterangan"));
                pesananbaruketerangan.putExtra("alamat", pesananSelesaiJsonList.get(position).get("psn_alamat"));
                pesananbaruketerangan.putExtra("pesanan_id", pesananSelesaiJsonList.get(position).get("psn_id"));
                pesananbaruketerangan.putExtra("pesanan_jumlah", pesananSelesaiJsonList.get(position).get("psn_jumlah"));
                //startActivityForResult(pesananbaruketerangan, 0); //I always put 0 for someIntValue
                startActivity(pesananbaruketerangan);
            }
        });
        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        pesananSelesaiJsonList = new ArrayList<>();
        new GetAPIPesananTokoSelesai().execute();
    }

    private class GetAPIPesananTokoSelesai extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            progressDialog = new ProgressDialog(getActivity());
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
            params.put("stat_id", "7");
            String jsonString = httpHandler.makeServiceCall(Jsonurl, params);

            //Log.e(TAG, "Response from url: " + jsonString);

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
                        String nama_jasa = c.getString("jasa_nama");
                        String nama_konsumen = c.getString("ksm_nama");
                        String nomor_pesanan = c.getString("psn_id");
                        String tanggal = c.getString("psn_datetime");
                        String keterangan = c.getString("psn_keterangan");
                        String alamat = c.getString("psn_alamat");
                        String pesananan_id = c.getString("psn_id");
                        String harga = c.getString("psn_jumlah");

                        // tmp hash map for single contact
                        HashMap<String, String> data = new HashMap<>();

                        // adding each child node to HashMap key => value
                        data.put("jasa_nama", nama_jasa);
                        data.put("ksm_nama", nama_konsumen);
                        data.put("psn_id", nomor_pesanan);
                        data.put("psn_datetime", tanggal);
                        data.put("psn_keterangan", keterangan);
                        data.put("psn_alamat", alamat);
                        data.put("psn_id", pesananan_id);
                        data.put("psn_jumlah", harga);

                        // adding contact to contact list
                        pesananSelesaiJsonList.add(data);
                        //Log.e(TAG, "Response json: " + KatalogJsonList);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Could not get json from server.");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity().getApplicationContext(),
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
            //Log.e(TAG, "test: " + pesananSelesaiJsonList);
            /**
             * Updating parsed JSON data into ListView
             * */
            /*ListAdapter adapter = new SimpleAdapter(
                    getActivity(), KatalogJsonList,
                    R.layout.katalog_jasa_content, new String[]{"jasa_nama"}, new int[]{R.id.listkatalogcontent});
            listView.setAdapter(adapter);*/

            // Keys used in Hashmap
            //String[] from = { "flag","txt","cur" };
            String[] from = { "jasa_nama", "ksm_nama"};

            // Ids of views in listview_layout
            //int[] to = { R.id.flag,R.id.txt,R.id.cur};
            int[] to = { R.id.nama_jasa_toko_selesai, R.id.nama_konsumen_toko_selesai};

            // Instantiating an adapter to store each items
            //R.layout.listview_layout defines the layout of each item
            SimpleAdapter adapter = new SimpleAdapter(getActivity(), pesananSelesaiJsonList, R.layout.status_pesanan_toko_selesai_content, from, to);

            listView.setAdapter(adapter);

            //String[] players={"Aaron Ramsey","Jack Wilshere","Mesut Ozil","Alexis Sanchez",
            //        "Per Metesacker","Keiron Gibbs","Laurent Koscielny","Olivier Giroud"};
            //Log.e(TAG, "test: " + players);
            //ListAdapter adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
            //setListAdapter(adapter);
        }
    }
}
