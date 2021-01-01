package com.moscom.egas.Network;

import android.os.AsyncTask;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpHandler  extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] objects) {
        Response response = null;
        try {
            //Toast.makeText(this, "inside testservlet: ", Toast.LENGTH_SHORT).show();
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.Companion.create( "{ \"Body\":{ \"actioncode\": \"json\",\"funcode\":\"jgetkey\",\"username\":\"moses\",\"password\":\"test\",\"usertype\":\"C\" } }", mediaType);
            Request request = new Request.Builder()
                    .url("http://192.168.178.32:8080/egas/json")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
             response = client.newCall(request).execute();
            //  makeText(this, "jsonResponse is : " + response.toString(), LENGTH_SHORT).show();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return response.toString();
    }
}
