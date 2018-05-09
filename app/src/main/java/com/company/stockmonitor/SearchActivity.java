package com.company.stockmonitor;

import android.content.Context;
import android.content.SharedPreferences;
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

public class SearchActivity extends AppCompatActivity {

    private SharedPreferences sharedPref;
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

            Toast toast = Toast.makeText(this,"No result, try again.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP,0, 20);
            toast.show();
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
