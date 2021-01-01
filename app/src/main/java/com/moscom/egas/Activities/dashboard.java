package com.moscom.egas.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.moscom.egas.R;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class dashboard extends AppCompatActivity  implements View.OnClickListener{
    private static final String className = dashboard.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        findViewById(R.id.refilbtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.refilbtn:
                try {
                    Intent intent = new Intent(dashboard.this, refillgas.class);
                    startActivity(intent);
                }catch (Exception e){
                    Log.i(className, "Exception in method onClick occurred: " + e.getMessage());
                }
                break;

        }

    }
}
