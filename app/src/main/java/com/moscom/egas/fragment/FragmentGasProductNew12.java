package com.moscom.egas.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FragmentGasProductNew12 extends Fragment {
    private  String className = FragmentGasProductNew12.class.getSimpleName();


    public FragmentGasProductNew12() {
    }

    public static FragmentGasProductNew12 newInstance() {
        FragmentGasProductNew12 fragment = new FragmentGasProductNew12();
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
        List<GasProduct> items = DataGenerator.getShoppingProductnew12(getActivity());
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
                String[] arrprice = price.split(" ");
                price = arrprice[1].replace(",","");

                SharedPreferences prefs1 = PreferenceManager.getDefaultSharedPreferences(view.getContext()); //Get the preferences
                String usercontact = prefs1.getString("custuserid", null); //get a String
                String orderNumber = prefs1.getString("orderNumber", null); //get a String
                String requestType = "addcart";;
                try {
                    NetworkAsynckHander networkRequest = new NetworkAsynckHander(this);
                    String result = networkRequest.execute(requestType, name, price,usercontact,orderNumber).get();
                    if(result != null){
                        Log.i(className, "cart products " + obj.title + " added to cart" );
                    }
                    //store the cart details in the SharedPreferences
                    int carttotal  = 0;
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext()); //Get the preferences
                    String cartprod = prefs.getString("cartprod", null);
                    JSONObject obj1 = null;
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("name", name);
                    jsonParam.put("price", price);
                    jsonParam.put("type", "new");

                    if(cartprod !=null){ // append the json object\
                        carttotal = Integer.parseInt(prefs.getString("carttotal", null) ) ;
                        carttotal = carttotal + Integer.parseInt(price);
                        obj1 = new JSONObject(cartprod);
                        JSONArray arr = (JSONArray) obj1.get("cart");
                        arr.put(jsonParam);
                        Log.i(className, "prodDetailsArr legth  " + arr.length() +arr);

                    }else{ //create new json array
                        carttotal = Integer.parseInt(price);
                        JSONArray jsonarr = new JSONArray();
                        jsonarr.put(jsonParam);
                        obj1 = new JSONObject();
                        obj1.put("cart", jsonarr);
                    }

                    SharedPreferences.Editor edit = prefs.edit(); //Needed to edit the preferences
                    edit.putString("cartprod", obj1.toString());  //add a String
                    edit.putString("carttotal", String.valueOf(carttotal));  //add a String
                    edit.commit();  // save the edits.

                    cartprod = prefs.getString("cartprod", null);

                    //JSONArray jsonarray = new JSONArray(cartprod);
                    JSONArray jsonarray = (JSONArray) new JSONObject(cartprod).get("cart");
                    String addedToCart = null;
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        String productName = jsonobject.getString("name");
                        Log.i(className, "productName  " + i+ " " +productName +cartprod);
                        if(productName .equals(name)){
                            addedToCart = obj.title ;
                        }
                    }

                    if(addedToCart !=null){
                        Log.i(className, "products " + obj.title + " added to cart" +cartprod);
                        Snackbar.make(root, "products " + obj.title + " added to cart", Snackbar.LENGTH_SHORT).show();
                    }else{
                        Snackbar.make(root, "An error occured: No response", Snackbar.LENGTH_SHORT).show();
                        Log.i(className, "An error occured: No response ");
                    }

                } catch (JSONException | ExecutionException | InterruptedException e) {
                    Log.i(className, "Exception in storing order details in sharedpreference is "+ e.getMessage());
                    Snackbar.make(root, "An error occured", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }
}