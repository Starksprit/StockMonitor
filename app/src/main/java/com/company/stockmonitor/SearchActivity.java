package com.company.stockmonitor;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    SharedPreferences sharedPref;
    Editor edit;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        sharedPref = getSharedPreferences("stockmonitor", Context.MODE_PRIVATE);
        listView = (ListView) findViewById(R.id.resultListView);

        loadList();
        listClickListener();
    }

    public void loadList() {

        ArrayList<String> stockslist = new ArrayList<>(); //<---
        stockslist.add(sharedPref.getString("searchedItem", "Not available"));
        stockslist.add("Två");
        stockslist.add("Tre");


        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, stockslist); //<---

        listView.setAdapter(stringArrayAdapter);

    }

    public void listClickListener() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {
                    System.out.println("First item");

                }
                else if (i == 1) {
                    System.out.println("Second item");

                }
                else if (i == 2) {
                    System.out.println("Third Item");

                }
            }
        });

    }
}
