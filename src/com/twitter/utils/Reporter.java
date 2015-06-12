package com.twitter.utils;

import java.text.SimpleDateFormat;
/**
 * Created
 * by:   Admin
 * date: 11.06.2015
 * time: 17:06
 */
public class Reporter {
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("H:mm:ss:SSS");

    public static void log(String message) {
        org.testng.Reporter.log("[" + FORMAT.format(System.currentTimeMillis()) + "]:" + message + "</br></br>");
        System.out.println("[" + FORMAT.format(System.currentTimeMillis()) + "]:" + message);
    }
}
