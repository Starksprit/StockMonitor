package com.company.stockmonitor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class BackgroundHandler extends AsyncTask<Void,Void,Void> {


    private SharedPreferences sharedPref;
    private Editor editor;


    @Override
    protected Void doInBackground(Void... voids) {
        ArrayList<String> companyNames = new ArrayList<String>();
        ArrayList<String> symbols = new ArrayList<String>();

        APIHandler apiHandler = new APIHandler();
        sharedPref = SPHandler.getInstance().getSharedPref();

        try {
            companyNames = apiHandler.getStockNames();
            symbols = apiHandler.getStockSymbols();
        } catch (IOException e) {
            e.printStackTrace();
        }

        saveArrayList(companyNames,"companyNames");
        saveArrayList(symbols,"symbols");

        return null;
    }

    public void saveArrayList(ArrayList<String> list, String key){

        Gson gson = new Gson();
        String json = gson.toJson(list);

        editor = sharedPref.edit();
        editor.putString(key, json);
        editor.commit();
    }
}
