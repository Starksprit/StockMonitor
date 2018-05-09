package com.company.stockmonitor;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABSE_NAME = "STOCKS.db";
    private static final String TABLE_NAME = "stocks";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "names";
    private static final String COLUMN_SYMBOL = "symbol";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABSE_NAME, factory, DATABASE_VERSION);
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
        db.insert(TABLE_NAME,null,values);
    }

    public void clearDB() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
