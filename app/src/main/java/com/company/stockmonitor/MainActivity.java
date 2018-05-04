package com.company.stockmonitor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> stockslist = new ArrayList<>();

    ListView listView;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_stocks:
                    //when stocks button is pressed

                    loadList();

                    return true;
                case R.id.navigation_search:
                    //when search button is pressed

                    return true;
                case R.id.navigation_RSS:
                    //when RSS button is pressed

                    return true;
            }
            return false;

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        listView = (ListView) findViewById(R.id.stocksListView);

        loadList();

        listClickListener();
    }

    public void loadList() {

        stockslist.add("Stock 1");
        stockslist.add("Stock 2");
        stockslist.add("Stock 3");

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,
                                        android.R.layout.simple_list_item_1, stockslist);

        listView.setAdapter(stringArrayAdapter);

    }

    public void listClickListener() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {
                    System.out.println("Fisk");

                }
                else if (i == 1) {
                    System.out.println("KAtt");

                }
                else if (i == 2) {
                    System.out.println("räkor");

                }
            }
        });

    }


}
