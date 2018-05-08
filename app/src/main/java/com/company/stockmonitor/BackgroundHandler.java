package com.company.stockmonitor;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;

public class BackgroundHandler extends AsyncTask<Void,Void,Void> {


    @Override
    protected Void doInBackground(Void... voids) {
        ArrayList<String> companyNames = new ArrayList<String>();
        ArrayList<String> symbols = new ArrayList<String>();

        Apihandler apihandler = new Apihandler();

        try {
            companyNames = apihandler.getStockNames();
            symbols = apihandler.getStockSymbols();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //HERE WE WANT TO ADD THE INFORMATION FROM OUR ARRAYLISTS TO THE SHARED PREFERENCES TODO

        return null;
    }
}
