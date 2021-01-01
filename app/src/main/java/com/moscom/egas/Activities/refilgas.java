package com.moscom.egas.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.moscom.egas.R;
import com.moscom.egas.adapter.AdapterGridShopProductCard;
import com.moscom.egas.model.GasProduct;
import com.moscom.egas.utilities.DataGenerator;
import com.moscom.egas.utilities.Tools;
import com.moscom.egas.widget.SpacingItemDecoration;

import java.util.List;

public class refilgas extends AppCompatActivity {
    private static final String className = refilgas.class.getSimpleName();
    private View parent_view;
    private RecyclerView recyclerView;
    private AdapterGridShopProductCard mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_refilgas);
            parent_view = findViewById(R.id.parent_view);

            initToolbar();
            initComponent();
        }catch(Exception e){
            Log.i(className, "Exception in method onCreate occurred: " + e.getStackTrace());
        }
    }

    private void initToolbar() {
        try{
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_menu);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Products");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Tools.setSystemBarColor(this);
        }catch(Exception e){
            Log.i(className, "Exception in method initToolbar occurred: " + e.getMessage());
        }

    }

    private void initComponent() {
        try{
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(this, 8), true));
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);

            List<GasProduct> items = DataGenerator.getShoppingProduct(this);

            //set data and list adapter
            mAdapter = new AdapterGridShopProductCard(this, items);
            recyclerView.setAdapter(mAdapter);

            // on item list clicked
            mAdapter.setOnItemClickListener(new AdapterGridShopProductCard.OnItemClickListener() {
                @Override
                public void onItemClick(View view, GasProduct obj, int position) {
                    Snackbar.make(parent_view, "Item " + obj.title + " clicked", Snackbar.LENGTH_SHORT).show();
                }
            });

            mAdapter.setOnMoreButtonClickListener(new AdapterGridShopProductCard.OnMoreButtonClickListener() {
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
