package com.moscom.egas.utilities;
import com.google.android.material.snackbar.Snackbar;
import com.moscom.egas.Activities.shopping_cart;
import  com.moscom.egas.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.PreferenceManager;
import android.util.Log;

import com.moscom.egas.model.GasProduct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class DataGenerator  {
    private static String className = DataGenerator.class.getSimpleName();
    /**
     * Generate dummy data shopping category
     *
     * @param ctx android context
     * @return list of object
     */
//    public static List<ShopCategory> getShoppingCategory(Context ctx) {
//        List<ShopCategory> items = new ArrayList<>();
//        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.shop_category_icon);
//        TypedArray drw_arr_bg = ctx.getResources().obtainTypedArray(R.array.shop_category_bg);
//        String title_arr[] = ctx.getResources().getStringArray(R.array.shop_category_title);
//        String brief_arr[] = ctx.getResources().getStringArray(R.array.shop_category_brief);
//        for (int i = 0; i < drw_arr.length(); i++) {
//            ShopCategory obj = new ShopCategory();
//            obj.image = drw_arr.getResourceId(i, -1);
//            obj.image_bg = drw_arr_bg.getResourceId(i, -1);
//            obj.title = title_arr[i];
//            obj.brief = brief_arr[i];
//            obj.imageDrw = AppCompatResources.getDrawable(ctx, obj.image);
//            items.add(obj);
//        }
//        return items;
//    }

    /**
     * Generate dummy data shopping product
     *
     * @param ctx android context
     * @return list of object
     */
    public static List<GasProduct> getShoppingProductnew6(Context ctx) {
        List<GasProduct> items = new ArrayList<>();
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.shop_product_image6);
        String title_arr[] = ctx.getResources().getStringArray(R.array.shop_product_title6);
        String price_arr[] = ctx.getResources().getStringArray(R.array.shop_product_price_new6);
        for (int i = 0; i < drw_arr.length(); i++) {
            GasProduct obj = new GasProduct();
            obj.image = drw_arr.getResourceId(i, -1);
            obj.title = title_arr[i];
            obj.price = price_arr[i];
            obj.imageDrw = ctx.getResources().getDrawable(obj.image);
            items.add(obj);
        }
        return items;
    }
    public static List<GasProduct> getShoppingCartProduct(Context ctx) {
        List<GasProduct> items = new ArrayList<>();
        try {
            int total6kgR = 0; int total6kgNew = 0; int total12kgR = 0; int total12kgNew = 0;
            int pro6kgR = 0; int pro6kgNew = 0; int pro12kgR = 0; int pro12kgNew = 0;
            int hashi6kgR = 0; int hashi6kgNew = 0; int hashi12kgR = 0; int hashi12kgNew = 0;
            int supa6kgR = 0; int supa6kgNew = 0; int supa12kgR = 0; int supa12kgNew = 0;
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx); //Get the preferences
            String cartprod = prefs.getString("cartprod", null); //get a String
            if(cartprod !=null){
                JSONArray jsoncartarray = (JSONArray) new JSONObject(cartprod.toString()).get("cart");
                for (int i = 0; i < jsoncartarray.length(); i++) {
                    JSONObject jsonobject = jsoncartarray.getJSONObject(i);
                    String productName = jsonobject.getString("name");
                    String type = jsonobject.getString("type");
                    if(productName.equals("Total gas 6Kg")){
                        if(type.equals("refill")){
                            total6kgR = total6kgR+1;
                        }else if(type.equals("new")){
                            total6kgNew = total6kgNew+1;
                        }
                    }else  if(productName.equals("Total gas 12Kg")){
                        if(type.equals("refill")){
                            total12kgR = total12kgR+1;
                        }else if(type.equals("new")){
                            total12kgNew = total12kgNew+1;
                        }
                    }else  if(productName.equals("Hashi 6Kg")){
                        if(type.equals("refill")){
                            hashi6kgR = hashi6kgR+1;
                        }else if(type.equals("new")){
                            hashi6kgNew = hashi6kgNew+1;
                        }
                    }else  if(productName.equals("Hashi 12Kg")){
                        if(type.equals("refill")){
                            hashi12kgR = hashi12kgR+1;
                        }else if(type.equals("new")){
                            hashi12kgNew = hashi12kgNew+1;
                        }
                    }else  if(productName.equals("Supa Gas 6Kg")){
                        if(type.equals("refill")){
                            supa6kgR = supa6kgR+1;
                        }else if(type.equals("new")){
                            supa6kgNew = supa6kgNew+1;
                        }
                    }else  if(productName.equals("Supa Gas 12Kg")){
                        if(type.equals("refill")){
                            supa12kgR = supa12kgR+1;
                        }else if(type.equals("new")){
                            supa12kgNew = supa12kgNew+1;
                        }
                    }else  if(productName.equals("Pro Gas 6Kg")){
                        if(type.equals("refill")){
                            pro6kgR = pro6kgR+1;
                        }else if(type.equals("new")){
                            pro6kgNew = pro6kgNew+1;
                        }
                    }else  if(productName.equals("Pro Gas 12Kg")){
                        if(type.equals("refill")){
                            pro12kgR = pro12kgR+1;
                        }else if(type.equals("new")){
                            pro12kgNew = pro12kgNew+1;
                        }
                    }

                }//end of loop
               // Snackbar.make(findViewById(android.R.id.content), "products size is "+ jsoncartarray.length(), Snackbar.LENGTH_SHORT).show();
                if(total6kgR >0){
                    GasProduct obj = new GasProduct();
                    TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.shop_product_totalsmal);
                    String title_arr [] = ctx.getResources().getStringArray(R.array.shop_product_totalsmaltitle);
                    String price_arr[] = ctx.getResources().getStringArray(R.array.shop_product_price_totalsmal);
                    for (int i = 0; i < drw_arr.length(); i++) {
                        obj.image = drw_arr.getResourceId(i, -1);
                        obj.title = title_arr[i];
                        obj.price = price_arr[i];
                        obj.imageDrw = ctx.getResources().getDrawable(obj.image);
                        items.add(obj);
                    }
                }
                if(total6kgNew >0){
                    GasProduct obj = new GasProduct();
                    TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.shop_product_totalsmal);
                    String title_arr [] = ctx.getResources().getStringArray(R.array.shop_product_totalsmaltitle);
                    String price_arr[] = ctx.getResources().getStringArray(R.array.shop_product_price_totalsmalnew);
                    for (int i = 0; i < drw_arr.length(); i++) {
                        obj.image = drw_arr.getResourceId(i, -1);
                        obj.title = title_arr[i];
                        obj.price = price_arr[i];
                        obj.imageDrw = ctx.getResources().getDrawable(obj.image);
                        items.add(obj);
                    }
                }
                if(total12kgR > 0 ){
                    GasProduct obj = new GasProduct();
                    TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.shop_product_totallarge);
                    String title_arr [] = ctx.getResources().getStringArray(R.array.shop_product_totallargetitle);
                    String price_arr[] = ctx.getResources().getStringArray(R.array.shop_product_price_totallarge);
                    for (int i = 0; i < drw_arr.length(); i++) {
                        obj.image = drw_arr.getResourceId(i, -1);
                        obj.title = title_arr[i];
                        obj.price = price_arr[i];
                        obj.imageDrw = ctx.getResources().getDrawable(obj.image);
                        items.add(obj);
                    }
                }
                if(total12kgNew > 0 ){
                    GasProduct obj = new GasProduct();
                    TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.shop_product_totallarge);
                    String title_arr [] = ctx.getResources().getStringArray(R.array.shop_product_totallargetitle);
                    String price_arr[] = ctx.getResources().getStringArray(R.array.shop_product_price_totallargenew);
                    for (int i = 0; i < drw_arr.length(); i++) {
                        obj.image = drw_arr.getResourceId(i, -1);
                        obj.title = title_arr[i];
                        obj.price = price_arr[i];
                        obj.imageDrw = ctx.getResources().getDrawable(obj.image);
                        items.add(obj);
                    }
                }
                if(hashi6kgR > 0 ){
                    GasProduct obj = new GasProduct();
                    TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.shop_product_hashismal);
                    String title_arr [] = ctx.getResources().getStringArray(R.array.shop_product_hashismaltitle);
                    String price_arr[] = ctx.getResources().getStringArray(R.array.shop_product_price_hashismal);
                    for (int i = 0; i < drw_arr.length(); i++) {
                        obj.image = drw_arr.getResourceId(i, -1);
                        obj.title = title_arr[i];
                        obj.price = price_arr[i];
                        obj.imageDrw = ctx.getResources().getDrawable(obj.image);
                        items.add(obj);
                    }
                }
                if(hashi6kgNew > 0 ){
                    GasProduct obj = new GasProduct();
                    TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.shop_product_hashismal);
                    String title_arr [] = ctx.getResources().getStringArray(R.array.shop_product_hashismaltitle);
                    String price_arr[] = ctx.getResources().getStringArray(R.array.shop_product_price_hashismalnew);
                    for (int i = 0; i < drw_arr.length(); i++) {
                        obj.image = drw_arr.getResourceId(i, -1);
                        obj.title = title_arr[i];
                        obj.price = price_arr[i];
                        obj.imageDrw = ctx.getResources().getDrawable(obj.image);
                        items.add(obj);
                    }
                }
                if(hashi12kgR > 0 ){
                    GasProduct obj = new GasProduct();
                    TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.shop_product_hashilarge);
                    String title_arr [] = ctx.getResources().getStringArray(R.array.shop_product_hashilargetitle);
                    String price_arr[] = ctx.getResources().getStringArray(R.array.shop_product_price_hashilarge);
                    for (int i = 0; i < drw_arr.length(); i++) {
                        obj.image = drw_arr.getResourceId(i, -1);
                        obj.title = title_arr[i];
                        obj.price = price_arr[i];
                        obj.imageDrw = ctx.getResources().getDrawable(obj.image);
                        items.add(obj);
                    }
                }
                if(hashi12kgNew > 0 ){
                    GasProduct obj = new GasProduct();
                    TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.shop_product_hashilarge);
                    String title_arr [] = ctx.getResources().getStringArray(R.array.shop_product_hashilargetitle);
                    String price_arr[] = ctx.getResources().getStringArray(R.array.shop_product_price_hashilargenew);
                    for (int i = 0; i < drw_arr.length(); i++) {
                        obj.image = drw_arr.getResourceId(i, -1);
                        obj.title = title_arr[i];
                        obj.price = price_arr[i];
                        obj.imageDrw = ctx.getResources().getDrawable(obj.image);
                        items.add(obj);
                    }
                }
                if(supa6kgR > 0 ){
                    GasProduct obj = new GasProduct();
                    TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.shop_product_supasmal);
                    String title_arr [] = ctx.getResources().getStringArray(R.array.shop_product_supasmaltitle);
                    String price_arr[] = ctx.getResources().getStringArray(R.array.shop_product_price_supasmal);
                    for (int i = 0; i < drw_arr.length(); i++) {
                        obj.image = drw_arr.getResourceId(i, -1);
                        obj.title = title_arr[i];
                        obj.price = price_arr[i];
                        obj.imageDrw = ctx.getResources().getDrawable(obj.image);
                        items.add(obj);
                    }
                }
                if(supa6kgNew > 0 ){
                    GasProduct obj = new GasProduct();
                    TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.shop_product_supasmal);
                    String title_arr [] = ctx.getResources().getStringArray(R.array.shop_product_supasmaltitle);
                    String price_arr[] = ctx.getResources().getStringArray(R.array.shop_product_price_supasmalnew);
                    for (int i = 0; i < drw_arr.length(); i++) {
                        obj.image = drw_arr.getResourceId(i, -1);
                        obj.title = title_arr[i];
                        obj.price = price_arr[i];
                        obj.imageDrw = ctx.getResources().getDrawable(obj.image);
                        items.add(obj);
                    }
                }
                if(supa12kgR > 0 ){
                    GasProduct obj = new GasProduct();
                    TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.shop_product_supalarge);
                    String title_arr [] = ctx.getResources().getStringArray(R.array.shop_product_supalargetitle);
                    String price_arr[] = ctx.getResources().getStringArray(R.array.shop_product_price_supalarge);
                    for (int i = 0; i < drw_arr.length(); i++) {
                        obj.image = drw_arr.getResourceId(i, -1);
                        obj.title = title_arr[i];
                        obj.price = price_arr[i];
                        obj.imageDrw = ctx.getResources().getDrawable(obj.image);
                        items.add(obj);
                    }
                }
                if(supa12kgNew > 0 ){
                    GasProduct obj = new GasProduct();
                    TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.shop_product_supalarge);
                    String title_arr [] = ctx.getResources().getStringArray(R.array.shop_product_supalargetitle);
                    String price_arr[] = ctx.getResources().getStringArray(R.array.shop_product_price_supalargenew);
                    for (int i = 0; i < drw_arr.length(); i++) {
                        obj.image = drw_arr.getResourceId(i, -1);
                        obj.title = title_arr[i];
                        obj.price = price_arr[i];
                        obj.imageDrw = ctx.getResources().getDrawable(obj.image);
                        items.add(obj);
                    }
                }
                if(pro6kgR > 0 ){
                    GasProduct obj = new GasProduct();
                    TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.shop_product_prosmal);
                    String title_arr [] = ctx.getResources().getStringArray(R.array.shop_product_prosmaltitle);
                    String price_arr[] = ctx.getResources().getStringArray(R.array.shop_product_price_prosmal);
                    for (int i = 0; i < drw_arr.length(); i++) {
                        obj.image = drw_arr.getResourceId(i, -1);
                        obj.title = title_arr[i];
                        obj.price = price_arr[i];
                        obj.imageDrw = ctx.getResources().getDrawable(obj.image);
                        items.add(obj);
                    }
                }
                if(pro6kgNew > 0 ){
                    GasProduct obj = new GasProduct();
                    TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.shop_product_prosmal);
                    String title_arr [] = ctx.getResources().getStringArray(R.array.shop_product_prosmaltitle);
                    String price_arr[] = ctx.getResources().getStringArray(R.array.shop_product_price_prosmalnew);
                    for (int i = 0; i < drw_arr.length(); i++) {
                        obj.image = drw_arr.getResourceId(i, -1);
                        obj.title = title_arr[i];
                        obj.price = price_arr[i];
                        obj.imageDrw = ctx.getResources().getDrawable(obj.image);
                        items.add(obj);
                    }
                }
                if(pro12kgR > 0 ){
                    GasProduct obj = new GasProduct();
                    TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.shop_product_prolarge);
                    String title_arr [] = ctx.getResources().getStringArray(R.array.shop_product_prolargetitle);
                    String price_arr[] = ctx.getResources().getStringArray(R.array.shop_product_price_prolarge);
                    for (int i = 0; i < drw_arr.length(); i++) {
                        obj.image = drw_arr.getResourceId(i, -1);
                        obj.title = title_arr[i];
                        obj.price = price_arr[i];
                        obj.imageDrw = ctx.getResources().getDrawable(obj.image);
                        items.add(obj);
                    }
                }
                if(pro12kgNew > 0 ){
                    GasProduct obj = new GasProduct();
                    TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.shop_product_prolarge);
                    String title_arr [] = ctx.getResources().getStringArray(R.array.shop_product_prolargetitle);
                    String price_arr[] = ctx.getResources().getStringArray(R.array.shop_product_price_prolargenew);
                    for (int i = 0; i < drw_arr.length(); i++) {
                        obj.image = drw_arr.getResourceId(i, -1);
                        obj.title = title_arr[i];
                        obj.price = price_arr[i];
                        obj.imageDrw = ctx.getResources().getDrawable(obj.image);
                        items.add(obj);
                    }
                }
            }else{
                //Snackbar.make(ctx, "no product added to cart ", Snackbar.LENGTH_SHORT).show();
                makeText(ctx, "no product added to cart: ", LENGTH_SHORT).show();
                Log.i(className, "no product added to cart  " );
            }

        } catch (JSONException e) {
            Log.i(className, "Exception in method onStart when getting cart details : " + e.getMessage());

        }
        Log.i(className, "cart items is   "+ items.size() );

        return items;
    }
    public static List<GasProduct> getShoppingProductnew12(Context ctx) {
        List<GasProduct> items = new ArrayList<>();
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.shop_product_image12);
        String title_arr[] = ctx.getResources().getStringArray(R.array.shop_product_title12);
        String price_arr[] = ctx.getResources().getStringArray(R.array.shop_product_price_new12);
        for (int i = 0; i < drw_arr.length(); i++) {
            GasProduct obj = new GasProduct();
            obj.image = drw_arr.getResourceId(i, -1);
            obj.title = title_arr[i];
            obj.price = price_arr[i];
            obj.imageDrw = ctx.getResources().getDrawable(obj.image);
            items.add(obj);
        }
        return items;
    }
    public static List<GasProduct> getShoppingProductnew(Context ctx) {
        List<GasProduct> items = new ArrayList<>();
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.shop_product_imageall);
        String title_arr[] = ctx.getResources().getStringArray(R.array.shop_product_titleall);
        String price_arr[] = ctx.getResources().getStringArray(R.array.shop_product_price_newall);
        for (int i = 0; i < drw_arr.length(); i++) {
            GasProduct obj = new GasProduct();
            obj.image = drw_arr.getResourceId(i, -1);
            obj.title = title_arr[i];
            obj.price = price_arr[i];
            obj.imageDrw = ctx.getResources().getDrawable(obj.image);
            items.add(obj);
        }
        return items;
    }
    public static List<GasProduct> getShoppingProductrefill(Context ctx) {
        List<GasProduct> items = new ArrayList<>();
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.shop_product_imageall);
        String title_arr[] = ctx.getResources().getStringArray(R.array.shop_product_titleall);
        String price_arr[] = ctx.getResources().getStringArray(R.array.shop_product_price_refillall);
        for (int i = 0; i < drw_arr.length(); i++) {
            GasProduct obj = new GasProduct();
            obj.image = drw_arr.getResourceId(i, -1);
            obj.title = title_arr[i];
            obj.price = price_arr[i];
            obj.imageDrw = ctx.getResources().getDrawable(obj.image);
            items.add(obj);
        }
        return items;
    }
    public static List<GasProduct> getShoppingProductrefill6(Context ctx) {
        List<GasProduct> items = new ArrayList<>();
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.shop_product_image6);
        String title_arr[] = ctx.getResources().getStringArray(R.array.shop_product_title6);
        String price_arr[] = ctx.getResources().getStringArray(R.array.shop_product_price_refill6);
        for (int i = 0; i < drw_arr.length(); i++) {
            GasProduct obj = new GasProduct();
            obj.image = drw_arr.getResourceId(i, -1);
            obj.title = title_arr[i];
            obj.price = price_arr[i];
            obj.imageDrw = ctx.getResources().getDrawable(obj.image);
            items.add(obj);
        }
        return items;
    }
    public static List<GasProduct> getShoppingProductrefill12(Context ctx) {
        List<GasProduct> items = new ArrayList<>();
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.shop_product_image12);
        String title_arr[] = ctx.getResources().getStringArray(R.array.shop_product_title12);
        String price_arr[] = ctx.getResources().getStringArray(R.array.shop_product_price_refill12);
        for (int i = 0; i < drw_arr.length(); i++) {
            GasProduct obj = new GasProduct();
            obj.image = drw_arr.getResourceId(i, -1);
            obj.title = title_arr[i];
            obj.price = price_arr[i];
            obj.imageDrw = ctx.getResources().getDrawable(obj.image);
            items.add(obj);
        }
        return items;
    }


}
