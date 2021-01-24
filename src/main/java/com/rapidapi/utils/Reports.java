package com.rapidapi.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reports {
  private  static final boolean jenkinsOption = true;
  public static ExtentHtmlReporter htmlReporter;
  public static ExtentReports extent;
  private static ExtentTest currentTest;
  private static String lastAction;
  private static final String ROOT_PATH = "reports/";
  private static String currentTestSuiteResultsPath;

  static {
    createReportFolder();
    LocalDateTime ldt = LocalDateTime.now();
    String formatttedDate = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"));

    if(jenkinsOption){
      currentTestSuiteResultsPath = "Suite/";
    }else {
      currentTestSuiteResultsPath = "Suite " + formatttedDate + "/";
    }

    new File(ROOT_PATH + currentTestSuiteResultsPath).mkdir();
    htmlReporter = new ExtentHtmlReporter(ROOT_PATH + currentTestSuiteResultsPath + "report.html");
    extent = new ExtentReports();
    extent.attachReporter(htmlReporter);
  }

  public static void start(String testName) {
    lastAction = null;
    currentTest = extent.createTest(testName);
  }

  public static void stop() {
    extent.flush();
  }

  public static void log(String message) {
    if(lastAction!=null) {
      currentTest.log(Status.PASS,lastAction);
      System.out.println(message);
    }
    lastAction = message;
  }

  public static void fail(String methodName) {

    try {
      currentTest.fail("Failed step: " + lastAction);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  public static void  createReportFolder() {
    Path path = Paths.get("reports");
    if (!Files.exists(path)) {
      try {
        Files.createDirectories(path);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}

