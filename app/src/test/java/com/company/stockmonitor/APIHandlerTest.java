package com.company.stockmonitor;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class APIHandlerTest {
    APIHandler api = new APIHandler();

    @Test
    public void getStockDailyValue() {
        ArrayList<Float> list = api.getStockDailyValue("FB");

        if (list.size()>0) {
            //Do nothing since the method is working as intended and got the available Values
        }
        else {
            //If the array list return empty, its not working as intended, fail the test
            fail();
        }
    }

    @Test
    public void getStockNames() throws IOException {
        ArrayList<String> list = api.getStockNames();

        if (list.size()>0) {
            //Do nothing since the method is working as intended and got the available Names
        }
        else {
            //If the array list return empty, its not working as intended, fail the test
            fail();
        }
    }

    @Test
    public void getStockSymbols() throws IOException {
        ArrayList<String> list = api.getStockSymbols();

        if (list.size()>0) {
            //Do nothing since the method is working as intended and got the available Symbols
        }
        else {
            //If the array list return empty, its not working as intended, fail the test
            fail();
        }
    }
}