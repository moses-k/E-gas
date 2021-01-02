package com.moscom.egas.utilities;
import  com.moscom.egas.R;
import android.content.Context;
import android.content.res.TypedArray;

import com.moscom.egas.model.GasProduct;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {
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
