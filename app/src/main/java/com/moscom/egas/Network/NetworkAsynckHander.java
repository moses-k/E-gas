package com.moscom.egas.Network;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;


import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.moscom.egas.Activities.dashboard;
import com.moscom.egas.Activities.loginPage;
import com.moscom.egas.Activities.shopping_checkout;
import com.moscom.egas.R;
import com.moscom.egas.adapter.AdapterGridShopProductCard;
import com.moscom.egas.environment.EgasEnvironment;
import com.moscom.egas.fragment.FragmentProductGrid;
import com.moscom.egas.model.GasProduct;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

import static android.provider.Settings.System.getString;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class NetworkAsynckHander extends AsyncTask<String,Void,String> {
    private static final String className = NetworkAsynckHander.class.getSimpleName();
    public static final MediaType JSON   = MediaType.get("application/json; charset=utf-8");
    private static final int LENGTH_SHORT = 0;
    public static final int LENGTH_LONG = 1;
    //ProgressDialog asyncDialog = new ProgressDialog(NetworkAsynckHander.this);


    Context context;
    AlertDialog alertDialog;
    public NetworkAsynckHander(Context ctx) {
        context = ctx;

    }

    FragmentProductGrid context2;
    AdapterGridShopProductCard.OnMoreButtonClickListener context3;
    public NetworkAsynckHander(FragmentProductGrid fragmentProductGrid) {
        context2 = fragmentProductGrid;
    }

    public NetworkAsynckHander(AdapterGridShopProductCard.OnMoreButtonClickListener onMoreButtonClickListener) {
        context3 = onMoreButtonClickListener;
    }


    @Override
    protected String doInBackground(String... params) {
        String jsonResponse = null; Request request = null; Response response = null;
        String requestType = params[0];
        switch (requestType){
            case "login":
                String result  = null; String json = null; String url = null;  RequestBody body = null;
                String printRequestBody = null; RequestBody formBody = null;

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
                            String custref = jsonObj.get("custrefno").toString().replaceAll("\"", "");
                            String custuserid = jsonObj.get("custuserid").toString().replaceAll("\"", "");
                            String usercontact = jsonObj.get("usercontact").toString().replaceAll("\"", "");

//                            SharedPreferences prefs = getSharedPreferences("logindetail", 0);
//                            SharedPreferences.Editor edit = prefs.edit();
//                            edit.putString("userLoginStatus", "yes");
//                            edit.commit();

//                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(NetworkAsynckHander.this); //Get the preferences
//                            SharedPreferences.Editor edit = prefs.edit(); //Needed to edit the preferences
//                            edit.putString("name", "myname");  //add a String
//                            edit.putBoolean("rememberCredentials", true); //add a boolean
//                            edit.commit();  // save the edits.s



                            result = "success"+","+custref+ ","+custuserid+","+usercontact;
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

                        }else if(error.equals("s")){
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
                    try{
                        return  result;
                    }finally {
                        if(jsonResponse !=null)  jsonResponse = null; if(response !=null)  response = null;
                        if(request !=null)  request = null;
                    }

                }catch(Exception e){
                    Log.i(className, "Exception in method sendLoginPost1 occurred : "+ e.getMessage());
                  //  alertDialog.setMessage("Exception in method sendLoginPost1 occurred : "+ e.getMessage() );
                   // alertDialog.show();
                   // Toast.makeText(this, "Exception in method sendLoginPost1 occurred : "+formBody.toString() + " "+printRequestBody +" "+ jsonResponse+ " " +  e.getMessage(), LENGTH_SHORT).show();
                }
                break;

            case "getproducts":
               // String result  = null; String json = null; String url = null; Request request = null; RequestBody body = null;
               // String printRequestBody = null; RequestBody formBody = null;
                Response response1 = null; String jsonResponse1 = null; String arrProducts = null;

                try{
                    url = EgasEnvironment.getJsonServer();
                    JSONObject jsonParam = new JSONObject();
                    JSONObject jsonParam1 = new JSONObject();
                    jsonParam.put("actioncode", EgasEnvironment.getJsonRequests());
                    jsonParam.put("funcode", "getproducts");
                    jsonParam1.put("Body", jsonParam);
                    json   = jsonParam1.toString();
                    //{ "Body":{ "actioncode": "json","funcode":"getproducts" } }

                    OkHttpClient client = new OkHttpClient();
                    body = RequestBody.Companion.create(json, JSON);
                    request = new Request.Builder()
                            .url(url)
                            .post(body)
                            .build();
                    try {
                        response1 = client.newCall(request).execute();
                        jsonResponse1 = response1.body().string();
                    } catch (Exception e) {
                        Log.i(className, "Exception  in getting Response:  jsonResponse is : "+ jsonResponse1 +"  " + e.getMessage());
                        //alertDialog.setMessage("Exception  in getting Response:  jsonResponse is : "+ jsonResponse +"  " + e.getMessage());
                        ///alertDialog.show();
                        //  makeText(this, "Exception  in getting Response:  jsonResponse is : "+ jsonResponse +"  " + e.getMessage(), LENGTH_SHORT).show();
                    }
                    // jsonResponse = response.body().string();
                    printRequestBody = bodyToString(body);
                    if(jsonResponse1 !=null){
                        // makeText(this, "jsonResponse is : " + jsonResponse, LENGTH_SHORT).show();
                        JsonObject jsonObj = new Gson().fromJson(jsonResponse1, JsonObject.class);
                        String error =  jsonObj.get("error").toString().replaceAll("\"", "");
                        if (error.equals("false")){
                             arrProducts =  jsonObj.get("arrproducts").toString().replaceAll("\"", "");

                            result = arrProducts;
                            Log.i(className, "products fetched successfully:  arrProducts is : "+ arrProducts );
                            // alertDialog.setMessage("Login success : "+ jsonResponse );
                            // alertDialog.show();

                            //  Intent intent = new Intent(NetworkAsynckHander.this, dashboard.class);
                            // startActivity(intent);
                            //  makeText(this, "Login success : " + jsonResponse, LENGTH_SHORT).show();
                        }else  if (error.equals("true")) {
                            Log.i(className, "fail:  jsonResponse is : "+ jsonResponse1 );
                            arrProducts = "failed";
                            //  alertDialog.setMessage("Login failed : "+ jsonResponse );
                            // alertDialog.show();
                            //makeText(this, "Login failed : " + jsonResponse, LENGTH_SHORT).show();
                        }
                    }else{
                        // Toast.makeText(NetworkAsynckHander.this, "No response : " , LENGTH_SHORT).show();
                        //  alertDialog.setMessage("No response " );
                        // alertDialog.show();
                        throw  new Exception("No response");
                    }
                    //  makeText(this, "inside everything seems good : "+ printRequestBody+"  "+ jsonResponse , LENGTH_SHORT).show();

                    try{
                        return  arrProducts;
                    }finally {
                        if(jsonResponse !=null)  jsonResponse = null; if(response !=null)  response = null;
                        if(request !=null)  request = null;
                    }
                }catch(Exception e){
                    Log.i(className, "Exception in getproducts occurred : "+ e.getMessage());
                    //  alertDialog.setMessage("Exception in method sendLoginPost1 occurred : "+ e.getMessage() );
                    // alertDialog.show();
                    // Toast.makeText(this, "Exception in method sendLoginPost1 occurred : "+formBody.toString() + " "+printRequestBody +" "+ jsonResponse+ " " +  e.getMessage(), LENGTH_SHORT).show();
                }
                break;

            case "addcart":
                try{
                    String result1 = null;  String productNAme = params[1];   String productPrice = params[2]; //Ksh 800
                    String [] arrPrice = productPrice.split(" ");
                    productPrice = arrPrice[1].replace(",", ""); //1,000 to 1000
                    String userContact = params[3];
                    String orderNumber = params[4];
                    url = EgasEnvironment.getJsonServer();
                    JSONObject jsonParam = new JSONObject();
                    JSONObject jsonParam1 = new JSONObject();
                    jsonParam.put("actioncode", EgasEnvironment.getJsonRequests());
                    jsonParam.put("funcode", "addcart");
                    jsonParam.put("productname", productNAme);
                    jsonParam.put("price", productPrice);
                    jsonParam.put("usercontact", userContact);
                    jsonParam.put("ordernumber", orderNumber);
                    jsonParam1.put("Body", jsonParam);
                    json   = jsonParam1.toString();
                    //{ "Body":{ "actioncode": "json","funcode":"getproducts" } }

                    OkHttpClient client = new OkHttpClient();
                    body = RequestBody.Companion.create(json, JSON);
                    request = new Request.Builder()
                            .url(url)
                            .post(body)
                            .build();
                    try {
                        response1 = client.newCall(request).execute();
                        jsonResponse = response1.body().string();
                    } catch (Exception e) {
                        Log.i(className, "Exception  in getting Response:  jsonResponse is : "+ jsonResponse +"  " + e.getMessage());
                        //  makeText(this, "Exception  in getting Response:  jsonResponse is : "+ jsonResponse +"  " + e.getMessage(), LENGTH_SHORT).show();
                    }
                    if(jsonResponse !=null){
                        // makeText(this, "jsonResponse is : " + jsonResponse, LENGTH_SHORT).show();
                        JsonObject jsonObj = new Gson().fromJson(jsonResponse, JsonObject.class);
                        String error =  jsonObj.get("error").toString().replaceAll("\"", "");
                        if (error.equals("false")){
                            result1 = "success";
                            Log.i(className, "products fetched successfully:   " );

                        }else  if (error.equals("true")) {
                            result1 = "failed";
                            Log.i(className, "fail:  jsonResponse is : "+ jsonResponse );
                        }
                    }else{
                        throw  new Exception("No response");
                    }
                    //  makeText(this, "inside everything seems good : "+ printRequestBody+"  "+ jsonResponse , LENGTH_SHORT).show();
                    try{
                        return  result1;
                    }finally {
                        if(jsonResponse !=null)  jsonResponse = null; if(response !=null)  response = null;
                        if(request !=null)  request = null;
                    }
                }catch(Exception e){
                    Log.i(className, "Exception in adding to cart occurred : "+ e.getMessage());
                }
                break;
            case "mpesapayment":
                try{
                    String result1 = null;  String mpesaAmount = params[1];   String custref = params[2]; //Ksh 800
                    String phoneNumber = params[3]; String jsoncart = params[4]; String orderNumber = params[5];

                    //String [] arrmpesaAmount = mpesaAmount.split(" ");
                    //mpesaAmount = arrmpesaAmount[1].replace(",", ""); //1,000 to 1000

                    ArrayList<GasProduct> arrproducts = new ArrayList<>();
                    Log.i(className, "json mpesaAmount is : "+ mpesaAmount + " custref is : "+ custref + " phoneNumber is : "+ phoneNumber
                            + " jsoncart is : "+ jsoncart + "  orderNumber "+ orderNumber);

                    url = EgasEnvironment.getJsonServer();
                    JSONObject jsonParam = new JSONObject();
                    JSONObject jsonParam1 = new JSONObject();
                    jsonParam.put("actioncode", EgasEnvironment.getJsonRequests());
                    jsonParam.put("funcode", "mpesapayment");
                    jsonParam.put("phonenumber", phoneNumber);
                    jsonParam.put("mpesaamount", mpesaAmount);
                    jsonParam.put("custref", custref);
                   jsonParam.put("jsoncart", jsoncart); // .replaceAll("","")
                   jsonParam.put("ordernumber", orderNumber);
                    jsonParam1.put("Body", jsonParam);
                    json   = jsonParam1.toString();
                    //{ "Body":{ "actioncode": "json","funcode":"getproducts" } }
                    Log.i(className, "json Request is : "+ json);

                    OkHttpClient client = new OkHttpClient();
                    body = RequestBody.Companion.create(json, JSON);
                    request = new Request.Builder()
                            .url(url)
                            .post(body)
                            .build();
                    try {
                        response1 = client.newCall(request).execute();
                        jsonResponse = response1.body().string();
                    } catch (Exception e) {
                        Log.i(className, "Exception  in getting Response:  jsonResponse is : "+ jsonResponse +"  " + e.getMessage());
                        //  makeText(this, "Exception  in getting Response:  jsonResponse is : "+ jsonResponse +"  " + e.getMessage(), LENGTH_SHORT).show();
                    }
                    if(jsonResponse !=null){
                        // makeText(this, "jsonResponse is : " + jsonResponse, LENGTH_SHORT).show();
                        JsonObject jsonObj = new Gson().fromJson(jsonResponse, JsonObject.class);
                        String error =  jsonObj.get("error").toString().replaceAll("\"", "");
                        if (error.equals("false")){

                            result1 = "success";
                            Log.i(className, "payment success : " + jsonResponse);

                        }else  if (error.equals("true")) {
                            result1 = "failed";
                            Log.i(className, "payment failed:  jsonResponse is : "+ jsonResponse );
                        }
                    }else{
                        throw  new Exception("No response");
                    }
                    //  makeText(this, "inside everything seems good : "+ printRequestBody+"  "+ jsonResponse , LENGTH_SHORT).show();
                    try{
                        return  result1;
                    }finally {
                        if(jsonResponse !=null)  jsonResponse = null; if(response !=null)  response = null;
                        if(request !=null)  request = null;
                    }
                }catch(Exception e){
                    Log.i(className, "Exception in mpesa payment : "+ e.getMessage());
                }
                break;

        }
        return null;
    }

    @Override
    protected void onPreExecute () {

        //set message of the dialog
      //  asyncDialog.setMessage("Loading");
        //show dialog
       //asyncDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        //hide the dialog
        //asyncDialog.dismiss();
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
