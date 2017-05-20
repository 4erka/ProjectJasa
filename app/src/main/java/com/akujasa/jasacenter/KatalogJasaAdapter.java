package com.akujasa.jasacenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by erka on 5/15/2017.
 */

public class KatalogJasaAdapter extends BaseAdapter implements ListAdapter {
    private String TAG = KatalogJasaAdapter.class.getSimpleName();

    private ProgressDialog progressDialog;
    // JSON data url
    private static String JsonurlHapus = "http://rilokukuh.com/admin-jasa/android_delete_katalog.php";
    ArrayList<HashMap<String, String>> katalogHapusJsonList;

    private ArrayList<String> list = new ArrayList<String>();
    private Context context;
    private ArrayList<HashMap<String, String>> katalogIdJasa;
    String id;
    String idToko;
    String namaJasa;
    String keteranganJasa;
    String hargaJasa;
    private ArrayList<HashMap<String, String>> dataJasaJson;
    private ArrayList<HashMap<String, String>> dataNamaJasaJson;

    public KatalogJasaAdapter(ArrayList<String> list, Context context, ArrayList<HashMap<String, String>> katalogIdJasa, ArrayList<HashMap<String, String>> dataJasaJson, ArrayList<HashMap<String, String>> dataNamaJasaJson,String idToko) {
        this.list = list;
        this.context = context;
        this.katalogIdJasa = katalogIdJasa;
        this.idToko = idToko;
        this.dataJasaJson = dataJasaJson;
        this.dataNamaJasaJson = dataNamaJasaJson;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        //return list.get(pos).getId();
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.katalog_jasa_content, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.listkatalogcontent);
        listItemText.setText(list.get(position));

        //Handle buttons and add onClickListeners
        Button edit = (Button)view.findViewById(R.id.katalog_edit);
        Button delete = (Button)view.findViewById(R.id.katalog_hapus);

        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                Intent tambah = new Intent(context, KatalogJasaEdit.class);
                namaJasa = dataNamaJasaJson.get(position).get("jasa_nama");
                keteranganJasa = dataJasaJson.get(position).get("jasa_deskripsi");
                hargaJasa = dataJasaJson.get(position).get("jasa_harga");
                id = katalogIdJasa.get(position).get("jasa_id");
                tambah.putExtra("jasa_id", id);
                tambah.putExtra("idToko", idToko);
                tambah.putExtra("jasa_nama", namaJasa);
                tambah.putExtra("jasa_deskripsi", keteranganJasa);
                tambah.putExtra("jasa_harga", hargaJasa);
                context.startActivity(tambah);
                notifyDataSetChanged();
            }
        });

        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                id = katalogIdJasa.get(position).get("jasa_id");
                katalogHapusJsonList = new ArrayList<>();
                new GetAPIKatalogHapus().execute();
                list.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });

        return view;
    }

    private class GetAPIKatalogHapus extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            progressDialog = new ProgressDialog(context);
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
            params.put("jasa_id", id);
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
                    /*runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });*/

                }
            } else {
                Log.e(TAG, "Could not get json from server.");
                /*runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Could not get json from server.",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });*/

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
                Toast.makeText(context,
                        "Hapus Berhasil",
                        Toast.LENGTH_LONG)
                        .show();
            }
            else{
                Toast.makeText(context,
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
