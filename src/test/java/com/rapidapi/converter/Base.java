package com.rapidapi.converter;

import com.rapidapi.utils.Reports;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Base {
    SoftAssert softAssert = new SoftAssert();

    String apiKey;
    String host;
    String url;

    public static  final int OK_RESPONSE_CODE =200;
    public static  final int BAD_REQUEST_RESPONSE_CODE =400;
    public static  final int NOT_FOUND_RESPONSE_CODE =404;

    @BeforeSuite
    @Parameters({"environment"})
    public void setUpSuite(@Optional("qa") String environment) {
      getCredentials(environment);
    }


    @BeforeMethod
    public void setUpMethod(Method method) {
        Reports.start(method.getDeclaringClass().getName() + " : " + method.getName());
    }


    public void getCredentials(String environment) {
        try {
            String filename = "src/test/resources/credentials.properties";
            if (!Files.exists(Paths.get(filename))) {
                throw new RuntimeException("Data properties file is not found");
            }
            Properties properties = new Properties();
            properties.load(new FileInputStream(filename));
            if(environment.contains("qa")) {
                apiKey = properties.get("QA_API_KEY").toString();
                host = properties.get("QA_HOST").toString();
                url = properties.get("QA_URL").toString();
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to load property file");
        }
    }
    @AfterMethod
    public void tearDown(ITestResult testResult) {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            Reports.fail(testResult.getName());
        }
        Reports.stop();
    }

}
