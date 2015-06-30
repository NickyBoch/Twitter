package com.twitter.base;


import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import java.io.File;


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
        //for run in ant uncomment next line
        String resDirPath = ".." + File.separatorChar + ".." + File.separatorChar;
        //for run in idea uncomment next line
        //String resDirPath="";

        String browser = System.getProperty("browser");
        if (browser == null || browser.isEmpty()) {
            browser = "";
        }

        System.setProperty("webdriver.chrome.driver", resDirPath + "resources" + File.separator + "chromedriver.exe");

        switch (browser) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "ie":
                driver = new InternetExplorerDriver();
                break;
            default:
                driver = new ChromeDriver();
                break;
        }
        driver.manage().window().maximize();
    }

    @AfterClass
    public void driverTearDown() {
        driver.quit();
    }

}
