package com.company.stockmonitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Apihandler {

    //search url: https://api.iextrading.com/1.0/ref-data/symbols

    public ArrayList<Float> getStockDailyValue(String stock) throws IOException {

        ArrayList<Float> listOfValue = new ArrayList<Float>();

        URL url = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + stock + "&interval=1min&apikey=KZDG4W2GA1YFWBIN");
        URLConnection urlcon = url.openConnection();
        InputStreamReader ins = new InputStreamReader(urlcon.getInputStream());
        BufferedReader bf = new BufferedReader(ins);

        String line = bf.readLine();
        String getValueAsString;
        float valueAsFloat;

        //while there is something to read
        while (line != null) {
            //just printing for editing purpose will be deleted later TODO
            //System.out.println(line);

            //here is where we pick something out from the current line
            if (line.contains("close\": ")) {
                getValueAsString = line;
                //extract the number from the line
                getValueAsString = getValueAsString.replaceAll("[^\\.0123456789]", "");
                //convert it to just the value
                getValueAsString = getValueAsString.substring(2);
                //from string to float
                valueAsFloat = Float.valueOf(getValueAsString);
                //add to list
                listOfValue.add(valueAsFloat);
            }
            line = bf.readLine();
        }

        return listOfValue;
    }

    public ArrayList<String> getStockNames() throws IOException {
        ArrayList<String> stockNames = new ArrayList<String>();


        URL url = new URL("https://api.iextrading.com/1.0/ref-data/symbols");
        URLConnection urlcon = url.openConnection();
        InputStreamReader ins = new InputStreamReader(urlcon.getInputStream());
        BufferedReader bf = new BufferedReader(ins);

        String line = bf.readLine();

        Pattern pattern = Pattern.compile("\"name\":\"(.*?)\",");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            stockNames.add(matcher.group(1));
        }

        return stockNames;
    }

    public ArrayList<String> getStockSymbols() throws IOException {
        ArrayList<String> stockNames = new ArrayList<String>();


        URL url = new URL("https://api.iextrading.com/1.0/ref-data/symbols");
        URLConnection urlcon = url.openConnection();
        InputStreamReader ins = new InputStreamReader(urlcon.getInputStream());
        BufferedReader bf = new BufferedReader(ins);

        String line = bf.readLine();

        Pattern pattern = Pattern.compile("symbol\":\"(.*?)\",\"");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            stockNames.add(matcher.group(1));
        }

        return stockNames;
    }
}



