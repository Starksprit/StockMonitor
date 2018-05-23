package com.company.stockmonitor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.ResultSet;
import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "STOCKS.db";
    private static final String TABLE_NAME = "stocks";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "names";
    private static final String COLUMN_SYMBOL = "symbol";
    private final SQLiteDatabase db = getWritableDatabase();

    public DBHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_NAME + " VARCHAR(40) PRIMARY KEY, " +
                COLUMN_SYMBOL + " VARCHAR(40) " +
                ");";

        db.execSQL(query);
    }

    public void addStock(String stockName, String symbol) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, stockName);
        values.put(COLUMN_SYMBOL,symbol);

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, values); // TODO fix error message when duplicate found!

    }

    public void removeStock(String stock) {

        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + "='" + stock + "'");
    }

    public ArrayList<String> getStocks() {

        ArrayList<String> list = new ArrayList<>();
        String query = "SELECT * from " + TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {

            list.add(cursor.getString(0));
            list.add(cursor.getString(1));

        }

        return list;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
