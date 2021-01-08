package com.moscom.egas.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.moscom.egas.R;
import com.moscom.egas.utilities.Utilities;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class dashboard extends AppCompatActivity  implements View.OnClickListener{
    private static final String className = dashboard.class.getSimpleName();
    TextView welcomeMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        welcomeMsg = (TextView) findViewById(R.id.welcomemsg);

        findViewById(R.id.refilbtn).setOnClickListener(this);
        findViewById(R.id.buynewbtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.refilbtn:
                try {
                    Intent intent = new Intent(dashboard.this, refillgas.class);
                    startActivity(intent);
                }catch (Exception e){
                    Log.i(className, "Exception in method onClick refilbtn occurred: " + e.getMessage());
                }
                break;
            case R.id.buynewbtn:
                try {
                    Intent intent = new Intent(dashboard.this, buycylinder.class);
                    startActivity(intent);
                }catch (Exception e){
                    Log.i(className, "Exception in method onClick buynewbtn occurred: " + e.getMessage());
                }
                break;

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(dashboard.this); //Get the preferences
        String userid = prefs.getString("custuserid", null); //get a String
        String orderNumber = prefs.getString("orderNumber", null); //get a String
        welcomeMsg.setText(userid);

        if(orderNumber == null){
            String  neworderNumber = Utilities.generateOrderNo(5);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(dashboard.this); //Get the preferences
            SharedPreferences.Editor edit = sharedPreferences.edit(); //Needed to edit the preferences
            edit.putString("orderNumber", neworderNumber);  //add a String
            edit.commit();
        }
        } catch (Exception e) {
            Log.i(className, "Exception in method onStart while checking order number occurred: " + e.getMessage());
        }
    }
}
