package com.moscom.egas.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.moscom.egas.R;
import com.moscom.egas.adapter.AdapterCartProductCard;
import com.moscom.egas.adapter.AdapterGridShopProductCard;
import com.moscom.egas.model.GasProduct;
import com.moscom.egas.utilities.DataGenerator;
import com.moscom.egas.utilities.Tools;
import com.moscom.egas.widget.SpacingItemDecoration;

import java.util.List;

public class shopping_cart extends AppCompatActivity {
    private  String className = shopping_cart.class.getSimpleName();
    private View parent_view;
    private RecyclerView recyclerView;
    AdapterCartProductCard mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_shopping_cart);
            parent_view = findViewById(R.id.parent_view);

            initToolbar();
            initComponent();
        }catch(Exception e){
            Log.i(className, "Exception in method onCreate occurred: " + e.getMessage());
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



            List<GasProduct> items = DataGenerator.getShoppingProductrefill(this);

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
        }catch(Exception e){
            Log.i(className, "Exception in method initComponent occurred: " + e.getStackTrace());
        }

    }
}
