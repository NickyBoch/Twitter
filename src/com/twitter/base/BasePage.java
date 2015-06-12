package com.twitter.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.twitter.utils.Reporter;

import java.util.concurrent.TimeUnit;

/**
 * Created
 * by:   Admin
 * date: 11.06.2015
 * time: 16:43
 */
public class BasePage {
    public final int TimeoutSeconds = 60;
    public final int NormalTimeoutSeconds = 30;
    public final int SmallTimeoutSeconds = 15;
    public final int ExtrasmallTimeoutSeconds = 5;
    public final int MicroTimeoutSeconds = 2;
    private RemoteWebDriver _driver;

    public RemoteWebDriver getDriver() {
        if (_driver == null) {
            _driver = BaseTest.driver;
        }
        return _driver;
    }

    protected void waitForElementPresent(By element) {
        waitForElementPresent(TimeoutSeconds, element);
    }

    protected void waitForElementPresent(int timeout, By element) {
        getDriver().manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
        wait.until(ExpectedConditions.presenceOfElementLocated(element));
        getDriver().manage().timeouts().implicitlyWait(TimeoutSeconds, TimeUnit.SECONDS);
    }

    protected void waitForElementVisible(int timeout, By element) {
        getDriver().manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        getDriver().manage().timeouts().implicitlyWait(TimeoutSeconds, TimeUnit.SECONDS);
    }

    protected void type(String logMessage, String typeMessage, By element) {
        Reporter.log(logMessage);
        WebElement input = getElement(element);
        input.clear();
        if (typeMessage.isEmpty() != true) {
            input.sendKeys(typeMessage);
        }
    }

    private WebElement getElement(By element) {
        return getDriver().findElement(element);
    }

    protected void click(String logMessage, By element) {
        Reporter.log(logMessage);
        WebElement webElement = getDriver().findElement(element);
        webElement.click();
    }

    protected void clickWithJS(String logMessage, WebElement element) {
        Reporter.log(logMessage);
        getDriver().executeScript("arguments[0].click()", element);
    }

}
