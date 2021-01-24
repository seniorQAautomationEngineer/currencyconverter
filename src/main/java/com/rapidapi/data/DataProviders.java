package com.rapidapi.data;

import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name = "getAllCurrency")
    public static Object[][] getAllCurrency() {
        return new Object[][]{

                {"SEK", "SEK Sweden, kronor"},
                {"ATS", "ATS Austria, shilling"},
                {"AUD", "AUD Australian, dollar"},
                {"BEF", "BEF Belgien, franc"},
                {"BRL", "BRL Brazilien, real"},
                {"CAD", "CAD Canada, dollar"},
                {"CHF", "CHF Switzerland, francs"},
                {"CNY", "CNY China, yuan renminbi"}
        };

    }

    @DataProvider(name = "convertCurrency")
    public static Object[][] convertCurrency() {
        return new Object[][]{

                {"1", "SEK", "AUD"},
                {"100", "ATS", "BEF"},
                {"5000", "AUD", "BRL"},
                {"1.0", "BEF", "CAD"},
                {"0.36", "CAD", "ATS"},
                {"90", "CHF", "SEK"},
                {"10", "CNY", "BEF"},
                {"3883838383838", "BRL", "CNY"}
        };

    }

    @DataProvider(name = "negativeConvertCurrency")
    public static Object[][] negativeConvertCurrency() {
        return new Object[][]{

                {"-1", "SEK", "Ii"},
                {"0", "ATS", "BEF"},
                {"-0.5", "AUD", "//BRL"},
                {"//", "BEF", "CAD"},
                {"0.36", "#//CAD", "ATS"},
                {"", "CHF", "??SEK"},
                {" ", "CNY", "&&"},
                {"10000000000000000000000000000000", "BRL", "CNY"}
        };

    }
}
