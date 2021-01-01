package com.moscom.egas.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.net.ConnectivityManagerCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.moscom.egas.Network.NetworkAsynckHander;
import com.moscom.egas.Network.OkHttpHandler;
import com.moscom.egas.R;
import com.moscom.egas.environment.EgasEnvironment;
import com.moscom.egas.utilities.NetworkCheck;

import org.json.JSONObject;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

import static android.widget.Toast.*;

public class loginPage extends AppCompatActivity implements View.OnClickListener {
    private static final String className = loginPage.class.getSimpleName();
    TextInputEditText username ;
    TextInputEditText password ;
    RadioButton usertype ;
    RadioGroup userTypeRadioGroup ;
    private Object String;
    public static final MediaType JSON   = MediaType.get("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        try{
            makeText(this, "inside onCreate", LENGTH_SHORT).show();

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login_page);
            username = (TextInputEditText) findViewById(R.id.loginusername);
            password = (TextInputEditText) findViewById(R.id.loginpassword);
            userTypeRadioGroup = (RadioGroup) findViewById(R.id.loginradiogroupusertype);
            userTypeRadioGroup.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
                   @Override
                   public void onCheckedChanged(RadioGroup group, int checkedId) {
                       usertype = (RadioButton) findViewById(checkedId);
                   }
               }
            );
            findViewById(R.id.loginbtn).setOnClickListener(this);
            findViewById(R.id.loginsign_up).setOnClickListener(this);

        }catch (Exception e){
            makeText(this, "Exception in method onCreate occurred: " + e.getMessage(), LENGTH_SHORT).show();
            Log.i(className, "Exception in method onCreate occurred: " + e.getMessage());
        }finally {
           // if(username != null) username = null;  if(password != null) password = null;

        }
    }

    @Override
    public void onClick(View view)  {
        switch(view.getId()){
            case R.id.loginbtn:
                try {
                    UserLogin(view);
                } catch (Exception e) {
                    makeText(this, "Exception in method onClick occurred: " + e, LENGTH_SHORT).show();
                    Log.i(className, "Exception in method onClick occurred: " + e.getStackTrace());
                }
                break;

            case  R.id.loginsign_up:

                break;

        }
    }
    public void UserLogin(View view) throws Exception {
        if (!NetworkCheck.isConnect(loginPage.this)) buildDialog(loginPage.this).show();
        else {
            String userName = null; String userpass =null;   String userType = null; String response = null;
           // try{
                //check if usertype is selected  //if(radioButton.isChecked()){}
                if (userTypeRadioGroup.getCheckedRadioButtonId() == -1)
                {
                    makeText(getApplicationContext(), "Please select UserType", LENGTH_SHORT).show();
                    Log.d(className, "UserType is Null");
                    return;
                }
                 userName = username.getText().toString();
                 userpass = password.getText().toString();
                 userType = usertype.getText().toString();
                 if(userType.startsWith("C")){
                     userType = "C";
                 }else if(userType.startsWith("V")){
                     userType = "V";
                 }

                //check if  is empty
                if (userName.isEmpty()) {
                    username.setError("username is required");
                    username.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(userName).matches()) {
                    username.setError("Please enter a valid email");
                    username.requestFocus();
                    return;
                }
                //check if password is empty
                if (userpass.isEmpty()) {
                    password.setError("Password is required");
                    password.requestFocus();
                    return;
                }
                if (password.length() < 4) {
                    password.setError("Minimun length of password should be 6");
                    password.requestFocus();
                    return;
                }
            makeText(getApplicationContext(), "good", LENGTH_SHORT).show();

         //  boolean result =  sendLoginPost1(userName, userpass, userType );
            String requestType = "login";
            NetworkAsynckHander networkRequest = new NetworkAsynckHander(this);
           // (String)UserLoginDao.class.getConstructor().newInstance().validateUser(userId, userPwd, userType);
          //  String status = (String)NetworkAsynckHander.class.getConstructor().newInstance().execute(requestType, userName, userpass, userType);
            String res = networkRequest.execute(requestType, userName, userpass, userType).get();
            makeText(getApplicationContext(), "good : res is "+ res, LENGTH_SHORT).show();
            if(res.equals("success")){
                Intent intent = new Intent(loginPage.this, dashboard.class);
                startActivity(intent);
            }else {
                makeText(getApplicationContext(), "login failed... Userid/Password incorrect ", LENGTH_SHORT).show();
            }



            // testservlet();

//                loginPage foor = new loginPage();
//                        Thread thread = new Thread((Runnable) foor);
//                        thread.start();
//                        thread.join();
//                        int value = foor.testservlet();

                /*if(result){
                    Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
                    Log.i(className, "Login success " );
                }else{
                   // Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
                    Log.i(className, "Login failed " );
                }*/

           /* }catch (Exception e){
                //Toast.makeText(getApplicationContext(),"Database........... Error ocured",Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, "Exception in method UserLogin main occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Exception in method UserLogin main occurred: "+ userName+ " "+userpass+ "  "+ userType+"  " + e.getMessage(), Toast.LENGTH_SHORT).show();

                Log.i(className, "Exception in method UserLogin occurred: " + e.getMessage());

            }finally {
             //   if(username !=null) username = null; if(password !=null) password = null;
               // if(userTypeRadioGroup !=null) userTypeRadioGroup = null; if(usertype !=null) usertype = null;

            }*/

        }
    }
    //check internet connection
//    public boolean isConnected(Context context) {
//        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netinfo = cm.getActiveNetworkInfo();
//
//        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
//            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
//                return true;
//            else return false;
//        } else
//            return false;
//    }
    //dialogbox
    public AlertDialog.Builder buildDialog(Context c) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                ;
                // finish();
            }
        });
        return builder;
    }
    public void testservlet() throws IOException {

        new Thread(new Runnable(){
            @Override
            public void run() {
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
                    Response  response = client.newCall(request).execute();
                  //  makeText(this, "jsonResponse is : " + response.toString(), LENGTH_SHORT).show();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                    //Toast.makeText(LoginPage.this, "inside testservlet: ", Toast.LENGTH_SHORT).show();

                }
            }
        }).start();

        //OkHttpHandler okHttpHandler = new OkHttpHandler();
       // okHttpHandler.execute("http://192.168.178.32:8080/egas/json");

///return response.toString();
    }

    public boolean sendLoginPost1(final String userName, final String userPassword, final String userType1) throws Exception {

        new Thread(new Runnable(){
            @Override
            public void run() {
                boolean result  = false; String json = null; String url = null; Request request = null; RequestBody body = null;
                String printRequestBody = null;String jsonResponse = null; RequestBody formBody = null;
                Response response = null;
                try{
                    url = EgasEnvironment.getJsonServer();
                    JSONObject jsonParam = new JSONObject();
                    JSONObject jsonParam1 = new JSONObject();
                    jsonParam.put("actioncode", EgasEnvironment.getJsonRequests());
                    jsonParam.put("funcode", "jcustlogin");
                    jsonParam.put("username", userName);
                    jsonParam.put("password", userPassword);
                    jsonParam.put("usertype", userType1);
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
                       // makeText(this, "Exception  in getting Response:  jsonResponse is : "+ jsonResponse +"  " + e.getMessage(), LENGTH_SHORT).show();
                    }
                    // jsonResponse = response.body().string();
                    printRequestBody = bodyToString(body);
                    if(jsonResponse !=null){
                       // makeText(this, "jsonResponse is : " + jsonResponse, LENGTH_SHORT).show();
                        JsonObject jsonObj = new Gson().fromJson(jsonResponse, JsonObject.class);
                        String error =  jsonObj.get("error").toString().replaceAll("\"", "");
                        if (error.equals("false")){
                            result = true;
                           // makeText(this, "Login success : " + jsonResponse, LENGTH_SHORT).show();
                        }else  if (error.equals("true")) {
                            result = false;
                           // makeText(this, "Login failed : " + jsonResponse, LENGTH_SHORT).show();
                        }
                    }else{
                       // makeText(this, "No response : " , LENGTH_SHORT).show();
                        throw  new Exception("No response");
                    }
                   // makeText(this, "inside everything seems good : "+ printRequestBody+"  "+ jsonResponse , LENGTH_SHORT).show();

                }catch(Exception e){
                    Log.i(className, "Exception in method sendLoginPost1 occurred : "+ e.getMessage());
                   // makeText(this, "Exception in method sendLoginPost1 occurred : "+formBody.toString() + " "+printRequestBody +" "+ jsonResponse+ " " +  e.getMessage(), LENGTH_SHORT).show();
                }
            }
        }).start();


        return true;
    }

    public String sendLoginPost3(final String userName, final String userPassword, final String userType1) throws Exception {
         String  jsonResponse = null;
        try {
            OkHttpClient client = new OkHttpClient();
            HttpUrl.Builder urlBuilder = HttpUrl.parse(EgasEnvironment.getJsonServer()).newBuilder();
            urlBuilder.addQueryParameter("qs", "lgt");
            urlBuilder.addQueryParameter("rules", "userlogin");
            urlBuilder.addQueryParameter("username", userName);
            urlBuilder.addQueryParameter("password", userPassword);
            urlBuilder.addQueryParameter("usertype", userType1);
            String url1 = urlBuilder.build().toString();

            Request request = new Request.Builder()
                    .url(url1)
                    .build();
            Response response = client.newCall(request).execute();

            jsonResponse = response.toString();
            makeText(this, "request is :" + request, LENGTH_SHORT).show();
            if(jsonResponse !=null){
                makeText(this, "jsonResponse is : " + jsonResponse, LENGTH_SHORT).show();
                /*JsonObject jsonObj = new Gson().fromJson(jsonResponse, JsonObject.class);
                String error =  jsonObj.get("error").toString().replaceAll("\"", "");
                if (error.equals("false")){
                    result = true;
                }else  if (error.equals("true")){
                  */

            }else{

                makeText(this, "No response : " , LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.i(className, "Exception in method sendLoginPost3 occurred : "+ e.getMessage());
            makeText(this, "Exception in method sendLoginPost3 occurred :" + jsonResponse +" "+ e.getMessage(), LENGTH_SHORT).show();
        }
        return  jsonResponse;
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

    public String HandleOkhttpRequests(String url, String json) throws Exception, IOException {
        String jsonResponse = null;
        try{
            makeText(this, "inside HandleOkhttpRequests : ", LENGTH_SHORT).show();

            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.Companion.create(json, JSON);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            jsonResponse = response.body().string();

        }catch (Exception e){
            Log.i(className, "Exception in method HandleOkhttpRequests occurred : "+ e.getMessage());
            makeText(this, "Exception in method HandleOkhttpRequests occurred : " + e.getMessage(), LENGTH_SHORT).show();
        }
        return jsonResponse;
    }

    public boolean sendLoginPost(final String userName, final String userPassword, final String userType1) throws IOException {
        boolean result = false;
        JSONObject jsonParam = new JSONObject(); HttpURLConnection conn = null;
        DataOutputStream os = null;
                try {
                    makeText(this, "inside sendLoginPost: " , LENGTH_SHORT).show();
                    URL url = new URL(EgasEnvironment.getJsonServer());
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    jsonParam = new JSONObject();
                    jsonParam.put("qs", "lgn");
                    jsonParam.put("rules", "userlogin");
                    jsonParam.put("username", userName);
                    jsonParam.put("password", userPassword);
                    jsonParam.put("usertype", userType1);

                    Log.i("JSON", jsonParam.toString());
                    makeText(this, "JSON is : " +jsonParam.toString(), LENGTH_SHORT).show();

                     os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    //Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    makeText(this, "STATUS is : " +conn.getResponseCode(), LENGTH_SHORT).show();

                    Log.i("MSG" , conn.getResponseMessage());
                    makeText(this, "MSG is : " +conn.getResponseMessage(), LENGTH_SHORT).show();

                    result =true;
                    //conn.disconnect();
                } catch (Exception e) {
                    result =false;
                    Log.i(className, "Exception in method sendLoginPost occurred v: "+ jsonParam.toString() + "  " + e.getMessage());
                    makeText(this, "Exception in method sendLoginPost occurred x: " + os.toString() + "  " + e.getMessage(), LENGTH_SHORT).show();

                }finally {

                }
        return result;
    }


/*    public boolean sendLoginPostr1(final String userName, final String userPassword, final String userType1) {
        boolean result = false;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                   *//* HttpPost post = new HttpPost(EgasEnvironment.getJsonServer());
                    // add request parameter, form parameter
                    List<NameValuePair> urlParameters = new ArrayList<>();
                    urlParameters.add(new BasicNameValuePair("qs", PPWalletEnvironment.getBioFrAction()));
                    urlParameters.add(new BasicNameValuePair("rules", PPWalletEnvironment.getBioFrAuthRule()));
                    urlParameters.add(new BasicNameValuePair("imagedetails", imageValue));
                    urlParameters.add(new BasicNameValuePair("accountid", accountid));

                    post.setEntity(new UrlEncodedFormEntity(urlParameters));

                    CloseableHttpClient httpClient = HttpClients.createDefault();
                    CloseableHttpResponse urlresponse = httpClient.execute(post);
                    sbResponse = EntityUtils.toString(urlresponse.getEntity());*//*
                   // PPWalletEnvironment.setComment(3, className,"***** after receiving "+ sbResponse);

                    URL url = new URL(EgasEnvironment.getJsonServer());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("qs", "lgn");
                    jsonParam.put("rules", "userlogin");
                    jsonParam.put("username", userName);
                    jsonParam.put("password", userPassword);
                    jsonParam.put("usertype", userType1);

                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());
                    //result =true;
                    conn.disconnect();
                } catch (Exception e) {
                    Log.i(className, "Exception in method sendLoginPost occurred: " + e.getMessage());
                    e.printStackTrace();
                }finally {

                }
            }
        });

        thread.start();
        result = true;
        return result;

    }*/
}
