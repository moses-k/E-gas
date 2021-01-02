package com.moscom.egas.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.moscom.egas.R;
import com.moscom.egas.fragment.FragmentGasProductNew12;
import com.moscom.egas.fragment.FragmentGasProductNew6;
import com.moscom.egas.fragment.FragmentGasProductNewAll;
import com.moscom.egas.fragment.FragmentProductGrid;
import com.moscom.egas.fragment.FragmentProductGrid12;
import com.moscom.egas.fragment.FragmentProductGridAll;
import com.moscom.egas.utilities.Tools;

import java.util.ArrayList;
import java.util.List;

public class buycylinder extends AppCompatActivity {
    public View parent_view;
    private ViewPager view_pager;
    private TabLayout tab_layout;
    private static final String className = refilgas.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_buycylinder);
            initToolbar();
            initComponent();

        }catch (Exception e){
            Log.i(className, "Exception in method onCreate occurred: " + e.getMessage());
        }

    }

    private void initToolbar() {
        try{
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Order new cylinder");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Tools.setSystemBarColor(this);
            //ADDED
            toolbar.setNavigationOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    finish();
                }
            });

        }catch (Exception e){
            Log.i(className, "Exception in method initToolbar occurred: " + e.getMessage());
        }

    }

    private void initComponent() {
        try{
            view_pager = (ViewPager) findViewById(R.id.view_pager);
            setupViewPager(view_pager);

            tab_layout = (TabLayout) findViewById(R.id.tab_layout);
            tab_layout.setupWithViewPager(view_pager);
        }catch (Exception e){
            Log.i(className, "Exception in method initComponent occurred: " + e.getMessage());
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        try {
            SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager(),1);
            adapter.addFragment(FragmentGasProductNew6.newInstance(), "6KG");
            adapter.addFragment(FragmentGasProductNew12.newInstance(), "12KG");
            adapter.addFragment(FragmentGasProductNewAll.newInstance(), "ALL TYPES");
            viewPager.setAdapter(adapter);

        }catch (Exception e){
            Log.i(className, "Exception in method setupViewPager occurred: " + e.getMessage());
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart_setting, menu);
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
    private class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(@NonNull FragmentManager manager,
                                    int behavior ) {
            super(manager, behavior);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
