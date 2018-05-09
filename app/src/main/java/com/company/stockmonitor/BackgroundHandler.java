package com.company.stockmonitor;

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



    SharedPreferences sharedPref;
    Editor editor;


    @Override
    protected Void doInBackground(Void... voids) {
        ArrayList<String> companyNames = new ArrayList<String>();
        ArrayList<String> symbols = new ArrayList<String>();

        APIHandler apiHandler = new APIHandler();

        try {
            companyNames = apiHandler.getStockNames();
            symbols = apiHandler.getStockSymbols();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //saveArrayList(companyNames,"companyNames");
        //saveArrayList(symbols,"symbols");

        return null;
    }

    public void saveArrayList(ArrayList<String> list, String key){
        //sharedpref

        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    /*
    public ArrayList<String> getArrayList(String key){
        //sharedpref

        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }
*/
}
