package com.akujasa.jasacenter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    private String TAG = LoginActivity.class.getSimpleName();
    private ProgressDialog progressDialog;
    private static String Jsonurl = "http://rilokukuh.com/admin-jasa/android_login.php";
    ArrayList<HashMap<String, String>> loginJsonList;
    HashMap<String, String> params = new HashMap<>();
    String idToko;

    private EditText etEmail;
    private EditText etPassword;
    private String status;

    String email;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        etEmail = (EditText)findViewById(R.id.inputemail);
        etPassword = (EditText)findViewById(R.id.inputpass);
        loginJsonList = new ArrayList<>();
    }

    public void onRegis(View view){
        Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(register);
    }

    public void onLogin(View view){
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        new GetAPILoginToko().execute();
        Log.e(TAG, "idToko1.5 : " + idToko);
        //Intent register = new Intent(LoginActivity.this, MenuUtamaActivity.class);
        //startActivity(register);

    }

    private class GetAPILoginToko extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler httpHandler = new HttpHandler();

            // request to json data url and getting response
            params.put("pj_email",email);
            params.put("pj_password", password);
            String jsonString = httpHandler.makeServiceCall(Jsonurl, params);

            Log.e(TAG, "Response from url: " + jsonString);

            if (jsonString != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    String id = jsonObject.getString("id");

                    HashMap<String, String> katalog = new HashMap<>();

                    katalog.put("status", status);
                    katalog.put("message", message);
                    katalog.put("id", id);

                    loginJsonList.add(katalog);
                    Log.e(TAG, "Response json: " + loginJsonList);
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
            Log.e(TAG, "test: " + loginJsonList.get(0).get("message"));
            if(loginJsonList.get(0).get("message").equals("success")){
                idToko = loginJsonList.get(0).get("id");
                Log.e(TAG, "idToko1: " + idToko);
            }
            else{
            }
            /**
             * Updating parsed JSON data into ListView
             * */
            new LoginProcess(LoginActivity.this, status).execute(email,password,idToko);
        }
    }
}
