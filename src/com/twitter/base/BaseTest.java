package com.twitter.base;

import com.twitter.utils.Reporter;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        //ant
        String resDirPath=".." + File.separatorChar + ".."+ File.separatorChar;
        //idea
        //String resDirPath="";
        System.setProperty("webdriver.chrome.driver", resDirPath + "resources" + File.separator + "chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterClass
    public void driverTearDown() {
        driver.quit();
    }

}
