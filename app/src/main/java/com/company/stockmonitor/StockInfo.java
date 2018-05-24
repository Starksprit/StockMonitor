package com.company.stockmonitor;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
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
    private LineChart lineChart;

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
        lineChart = findViewById(R.id.graph);
        lineChart.setNoDataText("Loading graph...");
        lineChart.setNoDataTextColor(Color.WHITE);
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
                updateGraph();
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

    public void updateGraph() {
        // todo Fix stocks where array out of bounds
        int counter = 90;
        ArrayList<Entry> data = new ArrayList<>();
        for (int i = 0; i < 90; i++) {
            data.add(new Entry(i, prices.get(counter)));
            counter--;
        }

        LineDataSet dataset = new LineDataSet(data, "Price");
        dataset.setColor(Color.CYAN);
        dataset.setDrawFilled(true);
        dataset.setLineWidth(1);
        dataset.setDrawCircles(false);
        dataset.setDrawValues(false);

        LineData dataforchart = new LineData(dataset);
        lineChart.setData(dataforchart);

        Description description = new Description();
        description.setText("Days");
        description.setTextColor(Color.WHITE);
        lineChart.setDescription(description);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getAxisLeft().setEnabled(false);
        lineChart.getAxisRight().setTextColor(Color.WHITE);
        lineChart.getXAxis().setTextColor(Color.WHITE);
        lineChart.getLegend().setTextColor(Color.WHITE);
        /*
        lineChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                return getDates().get((int) value);
            }
        });
        */
        // TODO fix xaxis so its in correct order
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
    }

    public ArrayList<String> getDates() {
        ArrayList<String> list = new ArrayList<>();
        list.add("80");
        list.add("70");
        list.add("60");
        list.add("50");
        list.add("40");
        list.add("30");
        list.add("20");
        list.add("10");
        list.add("0");
        return list;
    }


}
