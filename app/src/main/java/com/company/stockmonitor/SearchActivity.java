package com.company.stockmonitor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

import android.content.SharedPreferences.Editor;

public class SearchActivity extends AppCompatActivity {

    private SharedPreferences sharedPref;
    private Editor edit;
    private ListView listView;
    private ArrayList<String> companyNames = new ArrayList<>();
    private ArrayList<String> symbols = new ArrayList<>();
    private ArrayList<String> companyNamesSearchResult = new ArrayList<>();
    private ArrayList<String> symbolsSearchResult = new ArrayList<>();

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

        String searchedItem = sharedPref.getString("searchedItem", "Not available").toLowerCase();
        companyNames = getArrayList("companyNames");
        symbols = getArrayList("symbols");

        for (int i = 0; i < companyNames.size(); i++) {

            if (companyNames.get(i).toLowerCase().contains(searchedItem)) {
                symbolsSearchResult.add(symbols.get(i));
                companyNamesSearchResult.add(companyNames.get(i));
            }
        }

        if(!companyNamesSearchResult.isEmpty()) {

            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, companyNamesSearchResult);

            listView.setAdapter(stringArrayAdapter);

        } else {

            Toast toast = Toast.makeText(this, "No result, try again.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP,0, 20);
            toast.show();

            final Handler priceHandler = new Handler();
            priceHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 2000);
        }
    }

    public ArrayList<String> getArrayList(String key){

        Gson gson = new Gson();
        String json = sharedPref.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void listClickListener() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                edit = sharedPref.edit();
                edit.putString("selectedCompanyName", companyNamesSearchResult.get(i));
                edit.putString("selectedSymbol", symbolsSearchResult.get(i));
                edit.commit();

                Intent intent = new Intent(SearchActivity.this, StockInfo.class);
                startActivity(intent);
            }
        });
    }
}
