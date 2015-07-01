package com.twitter.base;

import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Created by Admin on 7/1/2015.
 */
public class BaseAction {
    public static RemoteWebDriver driver;

    public BaseAction() {
        driver = BaseTest.driver;
    }
}
