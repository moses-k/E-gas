package com.moscom.egas.fragment;

import android.content.Intent;
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
import com.moscom.egas.Activities.dashboard;
import com.moscom.egas.Activities.loginPage;
import com.moscom.egas.Activities.refillgas;
import com.moscom.egas.Activities.shopping_cart;
import com.moscom.egas.Network.NetworkAsynckHander;
import com.moscom.egas.R;
import com.moscom.egas.adapter.AdapterGridShopProductCard;
import com.moscom.egas.environment.EgasEnvironment;
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

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class FragmentProductGrid extends Fragment {
    private static final String className = FragmentProductGrid.class.getSimpleName();


    public FragmentProductGrid() {
    }

    public static FragmentProductGrid newInstance() {
        FragmentProductGrid fragment = new FragmentProductGrid();
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

//        try {
//            String requestType = "getproducts";
//            NetworkAsynckHander networkRequest = new NetworkAsynckHander(this);
//            String products = networkRequest.execute(requestType).get();
//            if(products != null){
//                Log.i(className, "products are : "+ products + "products length  "+ products.length());
//                for(int i = 0; i > products.length(); ){
//                    //Log.i(className, "products are : "+ products.productCode );
//
//                }
//            }else{
//                makeText(getActivity(), "An Error occurred:  ", LENGTH_SHORT).show();
//            }
//
//        } catch (ExecutionException e) {
//            Log.i(className, "Exception in getting products is : "+ e.getMessage());
//           // e.printStackTrace();
//        } catch (InterruptedException e) {
//            Log.i(className, "Exception in  getting products is : "+ e.getMessage());
//           // e.printStackTrace();
//        }

        //List<GasProduct> items = DataGenerator.getShoppingProduct(getActivity());
        List<GasProduct> items = DataGenerator.getShoppingProductrefill6(getActivity());
        Collections.shuffle(items);

        //set data and list adapter
        AdapterGridShopProductCard mAdapter = new AdapterGridShopProductCard(getActivity(), items);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterGridShopProductCard.OnItemClickListener() {
            @Override
            public void onItemClick(View view, GasProduct obj, int position) {
                //Intent intent = new Intent(this, shopping_cart.class);
               // startActivity(intent);

                Snackbar.make(root, "Item " + obj.price + " clicked", Snackbar.LENGTH_SHORT).show();
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

                //String requestType = "addcart";
                try {
                   // NetworkAsynckHander networkRequest = new NetworkAsynckHander(this);
                    //String result = networkRequest.execute(requestType, name, price).get();
                    //store the cart details in the SharedPreferences
                    int carttotal  = 0;
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext()); //Get the preferences
                    String cartprod = prefs.getString("cartprod", null);
                    JSONObject obj1 = null;
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("name", name);
                    jsonParam.put("price", price);
                    jsonParam.put("type", "refill");

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
                         Log.i(className, "cart products " + obj.title + " added to cart" +cartprod);
                            Snackbar.make(root, "products " + obj.title + " added to cart", Snackbar.LENGTH_SHORT).show();
                    }else{
                        Snackbar.make(root, "An error occured: No response", Snackbar.LENGTH_SHORT).show();
                        Log.i(className, "An error occured: No response ");
                    }

                } catch (JSONException e) {
                    Log.i(className, "Exception in storing order details in sharedpreference is "+ e.getMessage());
                    Snackbar.make(root, "An error occured", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

}