package com.moscom.egas.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.moscom.egas.Network.NetworkAsynckHander;
import com.moscom.egas.R;
import com.moscom.egas.adapter.AdapterGridShopProductCard;
import com.moscom.egas.model.GasProduct;
import com.moscom.egas.utilities.DataGenerator;
import com.moscom.egas.utilities.Tools;
import com.moscom.egas.widget.SpacingItemDecoration;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FragmentProductGrid12 extends Fragment {
    private  String className = FragmentProductGrid12.class.getSimpleName();

    public FragmentProductGrid12() {
    }

    public static FragmentProductGrid12 newInstance() {
        FragmentProductGrid12 fragment = new FragmentProductGrid12();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_product_grid, container, false);

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(getActivity(), 8), true));
        recyclerView.setHasFixedSize(true);

        //inject data from the database
        List<GasProduct> items = DataGenerator.getShoppingProductrefill12(getActivity());
        Collections.shuffle(items);

        //set data and list adapter
        AdapterGridShopProductCard mAdapter = new AdapterGridShopProductCard(getActivity(), items);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterGridShopProductCard.OnItemClickListener() {
            @Override
            public void onItemClick(View view, GasProduct obj, int position) {
                Snackbar.make(root, "Item " + obj.title + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });

        mAdapter.setOnMoreButtonClickListener(new AdapterGridShopProductCard.OnMoreButtonClickListener() {
            @Override
            public void onItemClick(View view, GasProduct obj, MenuItem item) {
                Snackbar.make(root, obj.title + " (" + item.getTitle() + ") clicked", Snackbar.LENGTH_SHORT).show();
                String name = obj.title;
                String price = obj.price;
                String requestType = "addcart";
                try {
                    NetworkAsynckHander networkRequest = new NetworkAsynckHander(this);
                    String result = networkRequest.execute(requestType, name, price).get();
                    if(result !=null){
                        if(result.equals("success")){
                            Log.i(className, "products " + obj.title + " added to cart");
                            Snackbar.make(root, "products " + obj.title + " added to cart", Snackbar.LENGTH_SHORT).show();

                        }else {
                            Log.i(className, "failed to add to cart,  product: " + obj.title + " ");
                            Snackbar.make(root, "failed to add to cart " + obj.title + " ", Snackbar.LENGTH_SHORT).show();
                        }
                    }else{
                        Log.i(className, "An error occured: No response ");
                    }

                } catch (ExecutionException e) {
                    Log.i(className, "Exception in adding cart is : "+ e.getMessage());
                    // e.printStackTrace();
                } catch (InterruptedException e) {
                    Log.i(className, "Exception in adding cart is : "+ e.getMessage());
                    // e.printStackTrace();
                }
            }
        });

        return root;
    }
}