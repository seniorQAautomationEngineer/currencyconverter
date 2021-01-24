package com.rapidapi.converter;

import com.rapidapi.data.DataProviders;
import com.rapidapi.utils.Reports;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConverterTests extends Base {
    String idJs;
    String descriptionJs;


    @Test(dataProvider = "convertCurrency", dataProviderClass = DataProviders.class)
    public void positiveConvertCurrency(String amount, String currencyFrom, String currencyTo) {
        String endpoint = url + "/?from_amount=" + amount + "&from=" + currencyFrom + "&to=" + currencyTo;
        Reports.log("Send request: " + endpoint);
        RequestSpecification httpRequest = RestAssured.given()
                .header("x-rapidapi-key", apiKey)
                .header("x-rapidapi-host", host)
                .when();
        Response response = httpRequest.get(endpoint);
        Reports.log("Expected response code: " + OK_RESPONSE_CODE + " .Actual response code: " + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), OK_RESPONSE_CODE);
        Assert.assertEquals(response.jsonPath().get("from"), currencyFrom);
        Assert.assertEquals(response.jsonPath().get("to"), currencyTo);
        Assert.assertEquals(response.jsonPath().get("from_amount"), amount);
        Assert.assertNotNull(response.jsonPath().get("to_amount"));
        Reports.log("JSON body: " + response.getBody().prettyPrint());
    }

    @Test(dataProvider = "getAllCurrency", dataProviderClass = DataProviders.class)
    public void verificationsAllAvailableCurrency(String id, String description) {
        Map<String, String> map = new HashMap<>();
        map.put(id, description);
        String endpoint = url + "/availablecurrencies";
        Reports.log("Send request: " + endpoint);
        RequestSpecification httpRequest = RestAssured.given()
                .relaxedHTTPSValidation()
                .header("x-rapidapi-key", apiKey)
                .header("x-rapidapi-host", host)
                .when();
        Response response = httpRequest.get(endpoint);
        Reports.log("Expected response code: " + OK_RESPONSE_CODE + " .Actual response code: " + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), OK_RESPONSE_CODE);
        Reports.log("JSON body: " + response.getBody().prettyPrint());
        List<Map<String, String>> jsonResponse = response.jsonPath().getList("$");
        for (int i = 0; i < jsonResponse.size(); i++) {
            idJs = jsonResponse.get(i).get("id");
            descriptionJs = jsonResponse.get(i).get("description");
            map.put(idJs, descriptionJs);
        }
        Assert.assertEquals(map.get(id), description);
    }

    @Test(dataProvider = "negativeConvertCurrency", dataProviderClass = DataProviders.class)
    public void negativeConvertCurrency(String amount, String currencyFrom, String currencyTo) {
        String endpoint = url + "/?from_amount=" + amount + "&from=" + currencyFrom + "&to=" + currencyTo;
        Reports.log("Send request: " + endpoint);
        RequestSpecification httpRequest = RestAssured.given()
                .relaxedHTTPSValidation()
                .header("x-rapidapi-key", apiKey);
        Response response = httpRequest.get(endpoint);
        Reports.log("Expected response code: " + BAD_REQUEST_RESPONSE_CODE + " .Actual response code: " + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), BAD_REQUEST_RESPONSE_CODE);
        Assert.assertEquals(response.jsonPath().get("from"), currencyFrom);
        Assert.assertEquals(response.jsonPath().get("to"), currencyTo);
        Assert.assertEquals(response.jsonPath().get("from_amount"), amount);
        Assert.assertNotNull(response.jsonPath().get("to_amount"));
        Reports.log("JSON body: " + response.getBody().prettyPrint());
    }
}