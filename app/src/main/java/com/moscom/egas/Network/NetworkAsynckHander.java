package com.moscom.egas.Network;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.moscom.egas.Activities.dashboard;
import com.moscom.egas.Activities.loginPage;
import com.moscom.egas.environment.EgasEnvironment;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class NetworkAsynckHander extends AsyncTask<String,Void,String> {
    private static final String className = loginPage.class.getSimpleName();
    public static final MediaType JSON   = MediaType.get("application/json; charset=utf-8");
    private static final int LENGTH_SHORT = 0;
    public static final int LENGTH_LONG = 1;

    Context context;
    AlertDialog alertDialog;
    public NetworkAsynckHander(Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String requestType = params[0];
        switch (requestType){
            case "login":
                String result  = null; String json = null; String url = null; Request request = null; RequestBody body = null;
                String printRequestBody = null;String jsonResponse = null; RequestBody formBody = null;
                Response response = null;
                String userName = params[1];
                String userPassword = params[2];
                String userType = params[3];

                try{
                    url = EgasEnvironment.getJsonServer();
                    JSONObject jsonParam = new JSONObject();
                    JSONObject jsonParam1 = new JSONObject();
                    jsonParam.put("actioncode", EgasEnvironment.getJsonRequests());
                    jsonParam.put("funcode", "jcustlogin");
                    jsonParam.put("username", userName);
                    jsonParam.put("password", userPassword);
                    jsonParam.put("usertype", userType);
                    jsonParam1.put("Body", jsonParam);
                    json   = jsonParam1.toString();

                    //{ "Body":{ "actioncode": "json","funcode":"custlogin","username":"moses","password":"test","usertype":"C" } }

                    // String jsonResponse = HandleOkhttpRequests(url, json);
                    //body = jsonParam.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                    OkHttpClient client = new OkHttpClient();
                 /* body = RequestBody.create(
                    MediaType.parse("application/json"), json);*/
                    body = RequestBody.Companion.create(json, JSON);
                    request = new Request.Builder()
                            .url(url)
                            .post(body)
                            .build();
                    // Response response = client.newCall(request).execute();
                    try {
                        response = client.newCall(request).execute();
                        jsonResponse = response.body().string();
                    } catch (Exception e) {
                        Log.i(className, "Exception  in getting Response:  jsonResponse is : "+ jsonResponse +"  " + e.getMessage());
                        //alertDialog.setMessage("Exception  in getting Response:  jsonResponse is : "+ jsonResponse +"  " + e.getMessage());
                        ///alertDialog.show();
                        //  makeText(this, "Exception  in getting Response:  jsonResponse is : "+ jsonResponse +"  " + e.getMessage(), LENGTH_SHORT).show();
                    }
                    // jsonResponse = response.body().string();
                    printRequestBody = bodyToString(body);
                    if(jsonResponse !=null){
                        // makeText(this, "jsonResponse is : " + jsonResponse, LENGTH_SHORT).show();
                        JsonObject jsonObj = new Gson().fromJson(jsonResponse, JsonObject.class);
                        String error =  jsonObj.get("error").toString().replaceAll("\"", "");
                        if (error.equals("false")){
                            result = "success";
                            Log.i(className, "success:  jsonResponse is : "+ jsonResponse );
                            // alertDialog.setMessage("Login success : "+ jsonResponse );
                           // alertDialog.show();

                          //  Intent intent = new Intent(NetworkAsynckHander.this, dashboard.class);
                           // startActivity(intent);
                           //  makeText(this, "Login success : " + jsonResponse, LENGTH_SHORT).show();
                        }else  if (error.equals("true")) {
                            Log.i(className, "fail:  jsonResponse is : "+ jsonResponse );
                            result = "failed";
                          //  alertDialog.setMessage("Login failed : "+ jsonResponse );
                           // alertDialog.show();
                            //makeText(this, "Login failed : " + jsonResponse, LENGTH_SHORT).show();
                        }else if(error.equals("locked")){
                            Log.i(className, "fail:  jsonResponse is : "+ jsonResponse );
                            result = "failed";

                        }else if(error.equals("invaliduserid")){
                            Log.i(className, "fail:  jsonResponse is : "+ jsonResponse );
                            result = "failed";

                        }else if(error.equals("invalidpass")){
                            Log.i(className, "fail:  jsonResponse is : "+ jsonResponse );
                            result = "failed";
                        }
                    }else{
                        // Toast.makeText(NetworkAsynckHander.this, "No response : " , LENGTH_SHORT).show();
                      //  alertDialog.setMessage("No response " );
                       // alertDialog.show();
                        throw  new Exception("No response");
                    }
                   //  makeText(this, "inside everything seems good : "+ printRequestBody+"  "+ jsonResponse , LENGTH_SHORT).show();
                    return  result;
                }catch(Exception e){
                    Log.i(className, "Exception in method sendLoginPost1 occurred : "+ e.getMessage());
                  //  alertDialog.setMessage("Exception in method sendLoginPost1 occurred : "+ e.getMessage() );
                   // alertDialog.show();
                   // Toast.makeText(this, "Exception in method sendLoginPost1 occurred : "+formBody.toString() + " "+printRequestBody +" "+ jsonResponse+ " " +  e.getMessage(), LENGTH_SHORT).show();
                }
                break;
        }
        return null;
    }
    @Override
    protected void onPreExecute () {
        alertDialog = new AlertDialog.Builder( context ).create();
       alertDialog.setTitle( "Login Status" );

    }

    @Override
    protected void onPostExecute(String result) {
       // alertDialog.setMessage(result);
       // alertDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        //alertDialog.setMessage("onProgressUpdate");
       // alertDialog.show();
    }

    private static String bodyToString(final RequestBody request){
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            copy.writeTo(buffer);
            return buffer.readUtf8();
        }
        catch (final IOException e) {
            return "did not work";
        }
    }



}
