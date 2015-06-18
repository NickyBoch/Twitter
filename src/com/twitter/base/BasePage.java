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

    protected void waitForElementPresent(By locator) {
        waitForElementPresent(TimeoutSeconds, locator);
    }

    protected void waitForElementPresent(int timeout, By locator) {
        getDriver().manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        getDriver().manage().timeouts().implicitlyWait(TimeoutSeconds, TimeUnit.SECONDS);
    }

    protected void waitForElementVisible(int timeout, By locator) {
        getDriver().manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        getDriver().manage().timeouts().implicitlyWait(TimeoutSeconds, TimeUnit.SECONDS);
    }

    protected void type(String logMessage, String typeMessage, By locator) {
        Reporter.log(logMessage);
        WebElement input = getElement(locator);
        input.clear();
        if (typeMessage.isEmpty() != true) {
            input.sendKeys(typeMessage);
        }
    }

    private WebElement getElement(By locator) {
        return getDriver().findElement(locator);
    }

    protected void click(String logMessage, By locator) {
        Reporter.log(logMessage);
        WebElement webElement = getDriver().findElement(locator);
        webElement.click();
    }

    protected void click(String logMessage, WebElement element) {
        Reporter.log(logMessage);
        element.click();
    }

    protected void clickWithJS(String logMessage, WebElement element) {
        Reporter.log(logMessage);
        getDriver().executeScript("arguments[0].click()", element);
    }

    protected void setElementAttributeWithJS(String logMessage, String attributeName, String attributeValue,WebElement element)
    {
        Reporter.log(logMessage);
        getDriver().executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, attributeName,
                attributeValue);
    }

    protected String getText( WebElement element){
        return (String)getDriver().executeScript("return jQuery(arguments[0]).text();", element);
    }
}
