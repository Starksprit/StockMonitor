package com.company.stockmonitor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class StockInfo extends AppCompatActivity {

    private TextView stockName;
    private TextView stockSymbol;
    private TextView stockPrice;
    private Button addStock;
    private Button removeStock;
    private SharedPreferences sharedPref;
    private ArrayList<Float> prices;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_info);
        stockName = (TextView) findViewById(R.id.stockName);
        stockSymbol = (TextView) findViewById(R.id.stockSymbol);
        stockPrice = (TextView) findViewById(R.id.stockValue);
        addStock = (Button) findViewById(R.id.addStock);
        removeStock = (Button) findViewById(R.id.removeStock);
        sharedPref = getSharedPreferences("stockmonitor", Context.MODE_PRIVATE);
        dbHandler = new DBHandler(StockInfo.this, null);

        loadInfo();

    }

    public void loadInfo() {

        stockName.setText(sharedPref.getString("selectedCompanyName", "Not found"));
        String symbol = sharedPref.getString("selectedSymbol", "Not found");
        stockSymbol.setText(symbol);

        new BackgroundPriceChecker().execute();

        final Handler priceHandler = new Handler();
        priceHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                prices = getArrayList();
                stockPrice.setText(String.valueOf(prices.get(0)));
                // Rita ut grafen här TODO
            }
        }, 4000);

    }

    public ArrayList<Float> getArrayList() {

        Gson gson = new Gson();
        String json = sharedPref.getString("dailyPrices", "Not found");
        Type type = new TypeToken<ArrayList<Float>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public void addStock(View view) {

        Toast toast = Toast.makeText(this, "Added to portfolio", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 150);
        toast.show();

        dbHandler.addStock(sharedPref.getString("selectedCompanyName", "Not found"),
                sharedPref.getString("selectedSymbol", "Not found"));

    }

    public void removetock(View view) {

        dbHandler.removeStock(sharedPref.getString("selectedCompanyName", "Not found"));
        finish();

    }


}
