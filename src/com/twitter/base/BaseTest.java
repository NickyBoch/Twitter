package com.twitter.base;


import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;


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
        //String resDirPath = ".." + File.separatorChar + ".." + File.separatorChar;
        //for run in idea uncomment next line
        //String resDirPath="";

        String resDirPath = System.getProperty("res.dir");

        String browser = System.getProperty("browser");
        if (browser == null || browser.isEmpty()) {
            browser = "";
        }

        //System.setProperty("webdriver.chrome.driver", resDirPath + "resources" + File.separator + "chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", resDirPath+File.separator +  "chromedriver.exe");
        DesiredCapabilities cap;
        switch (browser) {
            case "chrome":
                cap=new DesiredCapabilities("chrome","45", Platform.WINDOWS);
                try {
                    driver = new RemoteWebDriver(new URL("http://192.168.0.77:4444/wd/hub"),cap);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            case "firefox":
                cap=new DesiredCapabilities("firefox","39", Platform.WINDOWS);
                try {
                    driver = new RemoteWebDriver(new URL("http://192.168.0.77:4444/wd/hub"),cap);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            case "ie":
                driver = new InternetExplorerDriver();
                break;
            default:
                cap=new DesiredCapabilities("chrome","45", Platform.WINDOWS);
                try {
                    driver = new RemoteWebDriver(new URL("http://192.168.0.77:4444/wd/hub"),cap);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
        }
        driver.manage().window().maximize();
    }

    @AfterClass
    public void driverTearDown() {
        driver.quit();
    }

}
