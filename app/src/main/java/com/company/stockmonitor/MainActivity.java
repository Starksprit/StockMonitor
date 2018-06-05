package com.company.stockmonitor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> stockNames = new ArrayList<>();
    private ArrayList<String> stockSymbols = new ArrayList<>();
    private SharedPreferences sharedPref;
    private DBHandler dbhandler;
    private Editor edit;
    private ListView listView;
    private SearchView searchView;
    private BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_stocks:
                    //when stocks button is pressed

                    return true;
                case R.id.navigation_search:
                    //when search button is pressed
                    Intent intent = new Intent(MainActivity.this, AboutActivity.class );
                    startActivity(intent);

                    return true;
                case R.id.navigation_RSS:
                    //when RSS button is pressed
                    Intent intent2 = new Intent(MainActivity.this, Rssfeed.class );
                    startActivity(intent2);

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = getSharedPreferences("stockmonitor", Context.MODE_PRIVATE);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        SPHandler.getInstance().setSharedPref(sharedPref);

        listView = (ListView) findViewById(R.id.stocksListView);
        searchView = (SearchView) findViewById(R.id.searchViewBar);

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {

                    @Override
                    public boolean onQueryTextChange(String s) {

                        //Apply search filtering here
                        return false;
                    }
                    @Override
                    public boolean onQueryTextSubmit(String s) {

                        edit = sharedPref.edit();
                        edit.putString("searchedItem", searchView.getQuery().toString() );
                        edit.commit();

                        Intent intent = new Intent(MainActivity.this, SearchActivity.class );
                        startActivity(intent);

                        return false;
                    }
                }
        );

        dbhandler = new DBHandler(this, null);
        new BackgroundInitializer().execute();

        loadList();
        listClickListener();
    }

    @Override
    public void onResume() {
        super.onResume();

        loadList();
        navigation.setSelectedItemId(R.id.navigation_stocks);
        searchView.setQuery("", false);
        searchView.clearFocus();
        searchView.setIconified(true);
        navigation.clearFocus();
        // TODO ta bort vit tint
    }

    public void loadList() {

        stockNames.clear();

        ArrayList<String> list = dbhandler.getStocks();

        for (int i = 0; i < list.size(); i++) {

            if (i % 2 == 0) {
                stockNames.add(list.get(i));
            } else {
                stockSymbols.add(list.get(i));
            }
        }

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, stockNames);

        listView.setAdapter(stringArrayAdapter);

    }

    public void listClickListener() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                edit = sharedPref.edit();
                edit.putString("selectedCompanyName", stockNames.get(i));
                edit.putString("selectedSymbol", stockSymbols.get(i));
                edit.commit();

                Intent intent = new Intent(MainActivity.this, StockInfo.class);
                startActivity(intent);
            }
        });
    }
}
