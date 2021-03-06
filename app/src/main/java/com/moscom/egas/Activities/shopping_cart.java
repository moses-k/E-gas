package com.moscom.egas.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.moscom.egas.R;
import com.moscom.egas.adapter.AdapterCartProductCard;
import com.moscom.egas.adapter.AdapterGridShopProductCard;
import com.moscom.egas.model.GasProduct;
import com.moscom.egas.utilities.DataGenerator;
import com.moscom.egas.utilities.Tools;
import com.moscom.egas.widget.SpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class shopping_cart extends AppCompatActivity implements View.OnClickListener {
    private  String className = shopping_cart.class.getSimpleName();
    private View parent_view;
    private RecyclerView recyclerView;
    TextView cartTotalPrice;
    AdapterCartProductCard mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_shopping_cart);
            parent_view = findViewById(R.id.parent_view);
            cartTotalPrice = findViewById(R.id.carttotalprice);

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(shopping_cart.this); //Get the preferences
            String cartTotal = prefs.getString("carttotal", "0.00"); //get a String
            cartTotalPrice.setText("Ksh "+cartTotal);
            initToolbar();
            initComponent();

            findViewById(R.id.checkout).setOnClickListener(this);

        }catch(Exception e){
            Log.i(className, "Exception in method onCreate occurred: " + e.getMessage());
        }

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.checkout:
                try {
                    Intent intent = new Intent(shopping_cart.this, shopping_checkout.class);
                    startActivity(intent);
                   // makeText(this, "checkout: " , LENGTH_SHORT).show();
                } catch (Exception e) {
                    makeText(this, "Exception in method onClick occurred: " + e, LENGTH_SHORT).show();
                    Log.i(className, "Exception in method onClick occurred: " + e.getMessage());
                }
                break;
        }

    }
    @Override
    protected void onStart() {
        super.onStart();

        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(shopping_cart.this); //Get the preferences
            String cartprod = prefs.getString("cartprod", null); //get a String
            if(cartprod !=null){
                JSONArray jsoncartarray = (JSONArray) new JSONObject(cartprod).get("cart");
                for (int i = 0; i < jsoncartarray.length(); i++) {
                    JSONObject jsonobject = jsoncartarray.getJSONObject(i);
                    String productName = jsonobject.getString("name");
                }
                Log.i(className, "cartprod saved is  "+ cartprod.toString());
               // Snackbar.make(findViewById(android.R.id.content), "products size is "+ jsoncartarray.length(), Snackbar.LENGTH_SHORT).show();
            }else{
                Snackbar.make(findViewById(android.R.id.content), "No data in the cart ", Snackbar.LENGTH_SHORT).show();


            }
        } catch (JSONException e) {
            Log.i(className, "Exception in method onStart when getting cart details : " + e.getMessage());

        }

    }

    private void initToolbar() {
        try{
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_menu);
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_ATOP);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Cart");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Tools.setSystemBarColor(this, R.color.grey_5);
            Tools.setSystemBarLight(this);
        }catch(Exception e){
            Log.i(className, "Exception in method initToolbar occurred: " + e.getMessage());
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        Tools.changeMenuIconColor(menu, getResources().getColor(R.color.grey_60));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initComponent() {
        try{
            recyclerView = (RecyclerView) findViewById(R.id.cartrecyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
            recyclerView.addItemDecoration(new SpacingItemDecoration(1, Tools.dpToPx(this, 8), true));
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);



            //List<GasProduct> items = DataGenerator.getShoppingProductrefill(this);
            List<GasProduct> items = DataGenerator.getShoppingCartProduct(this);
            if(items !=null){
                //set data and list adapter
                mAdapter = new AdapterCartProductCard(this, items);
                recyclerView.setAdapter(mAdapter);

                // on item list clicked
                mAdapter.setOnItemClickListener(new AdapterCartProductCard.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, GasProduct obj, int position) {
                        Snackbar.make(parent_view, "Item " + obj.title + " clicked", Snackbar.LENGTH_SHORT).show();
                    }
                });

                mAdapter.setOnMoreButtonClickListener(new AdapterCartProductCard.OnMoreButtonClickListener() {
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
