package com.akujasa.jasacenter;

/**
 * Created by erka on 4/18/2017.
 */


import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;


public class HttpHandler {

    String charset = "UTF-8";
    StringBuilder sbParams;
    String paramsString;
    DataOutputStream wr;

    private static final String TAG = HttpHandler.class.getSimpleName();

    public HttpHandler() {
    }

    public String makeServiceCall(String Urlreq, HashMap<String, String> params) {
        String res = null;
        sbParams = new StringBuilder();
        int i = 0;
        for (String key : params.keySet()) {
            try {
                if (i != 0){
                    sbParams.append("&");
                }
                sbParams.append(key).append("=")
                        .append(URLEncoder.encode(params.get(key), charset));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i++;
        }
        try {
            URL mUrl = new URL(Urlreq);
            HttpURLConnection httpURLConnection = (HttpURLConnection) mUrl.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("POST");
            //httpURLConnection.setRequestProperty("email", "gilang@aku-pintar.com");
            //httpURLConnection.setRequestProperty("password", "pass");
            httpURLConnection.setRequestProperty("Accept-Charset", charset);

            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);

            httpURLConnection.connect();

            paramsString = sbParams.toString();

            wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(paramsString);
            wr.flush();
            wr.close();

            InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            res = convertStreamToString(inputStream);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return res;
    }

    private String convertStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();

        //line
        String i;
        try {
            while ((i = bufferedReader.readLine()) != null) {
                sb.append(i).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}