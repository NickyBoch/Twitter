package com.twitter.base;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * Created
 * by:   Admin
 * date: 11.06.2015
 * time: 16:42
 */
public class BaseTest {
    public static RemoteWebDriver driver;

    @BeforeClass
    public void driverSetUp() {

        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterClass
    public void driverTearDown() {
        //driver.quit();
    }
}
