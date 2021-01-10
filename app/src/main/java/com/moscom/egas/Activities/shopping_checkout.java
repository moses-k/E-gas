package com.moscom.egas.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.moscom.egas.Network.NetworkAsynckHander;
import com.moscom.egas.R;
import com.moscom.egas.adapter.AdapterCheckoutProductCard;
import com.moscom.egas.model.GasProduct;
import com.moscom.egas.utilities.DataGenerator;
import com.moscom.egas.utilities.Tools;
import com.moscom.egas.widget.SpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class shopping_checkout extends AppCompatActivity implements  View.OnClickListener {
    private  String className = shopping_checkout.class.getSimpleName();
    private View parent_view;
    private RecyclerView recyclerView;
    TextView checkoutTotalPrice;
    AdapterCheckoutProductCard mAdapter;
    private Button mpesaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_shopping_checkout);
            parent_view = findViewById(R.id.parent_view2);
            checkoutTotalPrice = findViewById(R.id.checkouttotalprice);
            mpesaButton = (Button) findViewById(R.id.mpesapaybtn);

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(shopping_checkout.this); //Get the preferences
            String cartTotal = prefs.getString("carttotal", "0.00"); //get a String
            checkoutTotalPrice.setText("Ksh "+cartTotal);
            initToolbar();
            initComponent();
            Log.i(className, "checkoutTotalPrice: " + checkoutTotalPrice);
            Snackbar.make(findViewById(android.R.id.content), "checkoutTotalPrice "+ checkoutTotalPrice, Snackbar.LENGTH_SHORT).show();

            findViewById(R.id.mpesapaybtn).setOnClickListener((View.OnClickListener) this);
        }catch(Exception e){
            Log.i(className, "Exception in method onCreate occurred: " + e.getMessage());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mpesapaybtn:
                try {
                    payViaMpesa(view);
                } catch (Exception e) {
                    Log.i(className, "Exception in method onClick - payViaMpesa occurred: " + e.getMessage());
                }
                break;
        }
    }
    private void payViaMpesa(View view) throws Exception {
        try {
            //get the cart oder details
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(shopping_checkout.this); //Get the preferences
            String mpesaAmount = prefs.getString("carttotal", "0.00");
            String custref = prefs.getString("custref", null);
            String usercontact = prefs.getString("usercontact", null);
            String cartprod = prefs.getString("cartprod", null);
            String ordernumber = prefs.getString("orderNumber", null);

            Log.i(className, "inside payViaMpesa ordernumber : " + ordernumber + " cartprod "+  cartprod);


            String requestType = "mpesapayment";
            NetworkAsynckHander networkRequest = new NetworkAsynckHander(this);
            String res = networkRequest.execute(requestType, mpesaAmount, custref, usercontact, cartprod, ordernumber ).get();

            Snackbar.make(findViewById(android.R.id.content), "Payment Done  ordernumber:  "+  ordernumber, Snackbar.LENGTH_SHORT).show();


        }catch (Exception e){
            Log.i(className, "Exception in method payViaMpesa occurred: " + e.getMessage());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(shopping_checkout.this); //Get the preferences
            String cartprod = prefs.getString("cartprod", null); //get a String
            if(cartprod !=null){
                JSONArray jsoncartarray = (JSONArray) new JSONObject(cartprod).get("cart");
                for (int i = 0; i < jsoncartarray.length(); i++) {
                    JSONObject jsonobject = jsoncartarray.getJSONObject(i);
                    String productName = jsonobject.getString("name");
                }
                Log.i(className, "cartprod saved is  "+ cartprod.toString());

                Snackbar.make(findViewById(android.R.id.content), "products size is "+ jsoncartarray.length(), Snackbar.LENGTH_SHORT).show();
            }else{
                Snackbar.make(findViewById(android.R.id.content), "No data in the checkout ", Snackbar.LENGTH_SHORT).show();


            }
        } catch (JSONException e) {
            Log.i(className, "Exception in method onStart when getting cart details : " + e.getMessage());

        }
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Checkout");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
        //ADDED
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }
    private void initComponent() {
        try{
            recyclerView = (RecyclerView) findViewById(R.id.checkoutrecyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
            recyclerView.addItemDecoration(new SpacingItemDecoration(1, Tools.dpToPx(this, 8), true));
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);



            //List<GasProduct> items = DataGenerator.getShoppingProductrefill(this);
            List<GasProduct> items = DataGenerator.getShoppingCartProduct(this);
            if(items !=null){
                //set data and list adapter
                mAdapter = new AdapterCheckoutProductCard(this, items);
                recyclerView.setAdapter(mAdapter);

                // on item list clicked
                mAdapter.setOnItemClickListener(new AdapterCheckoutProductCard.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, GasProduct obj, int position) {
                        Snackbar.make(parent_view, "Item " + obj.title + " clicked", Snackbar.LENGTH_SHORT).show();
                    }
                });

                mAdapter.setOnMoreButtonClickListener(new AdapterCheckoutProductCard.OnMoreButtonClickListener() {
                    @Override
                    public void onItemClick(View view, GasProduct obj, MenuItem item) {
                        Snackbar.make(parent_view, obj.title + " (" + item.getTitle() + ") clicked", Snackbar.LENGTH_SHORT).show();
                    }
                });


            }


        }catch(Exception e){
            Log.i(className, "Exception in method initComponent occurred: " + e.getStackTrace());
        }

    }


}
