package com.company.stockmonitor;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class BackgroundPriceChecker extends AsyncTask<Void, Void, Void> {

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private ArrayList<Float> stockPrices;

    @Override
    protected Void doInBackground(Void... voids) {

        APIHandler apiHandler = new APIHandler();
        sharedPref = SPHandler.getInstance().getSharedPref();
        stockPrices = apiHandler.getStockDailyValue(sharedPref.getString("selectedSymbol", "Not found"));

        Gson gson = new Gson();
        String json = gson.toJson(stockPrices);

        editor = sharedPref.edit();
        editor.putString("dailyPrices", json);
        editor.commit();

        return null;
    }
}
