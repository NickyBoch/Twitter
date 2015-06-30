package com.twitter.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.twitter.utils.Reporter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created
 * by:   Admin
 * date: 11.06.2015
 * time: 16:43
 */
public class BasePage {
    private final String baseURL = "https://twitter.com/";

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

    public void openMainPage() {
        open(baseURL);
    }

    public void open(String linkToOpen) {
        Reporter.log("open page: " + linkToOpen);
        getDriver().get(linkToOpen);
        waitPageLoadWithJS("wait baseURL load", TimeoutSeconds);
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

    protected void waitForElementVisible(int timeout, WebElement elemeht) {
        getDriver().manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
        wait.until(ExpectedConditions.visibilityOf(elemeht));
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

    protected WebElement getElement(By locator) {
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

    protected void setElementAttributeWithJS(String logMessage, String attributeName, String attributeValue, WebElement element) {
        Reporter.log(logMessage);
        getDriver().executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, attributeName,
                attributeValue);
    }

    protected String getTextWithJS(WebElement element) {
        Reporter.log("get text from element with javascript");
        return (String) getDriver().executeScript("return arguments[0].innerText;", element);
    }

    public void mouseScrollWithJS(String logMessage, int x, int y) {
        Reporter.log(logMessage);
        String javaScript = "window.scrollBy(" + x + "," + y + ");";
        getDriver().executeScript(javaScript);
    }

    public void scrollToElementWithJS(String logMessage, WebElement element) {
        Reporter.log(logMessage);
        String javaScript = "arguments[0].scrollIntoView(false);";
        getDriver().executeScript(javaScript, element);
    }

    public List<WebElement> getAllTweetsOnPage(String logMessage, By locator) {
        Reporter.log(logMessage);
        waitForElementVisible(TimeoutSeconds, locator);
        List<WebElement> listWebElements = getDriver().findElements(locator);
        return listWebElements;
    }

    public int getNumberOfReTweets(String logMessage, WebElement element, By locator) {
        Reporter.log(logMessage);
        int reTweetsCountInt;
        WebElement counter = element.findElement(locator);
        String tmp = getTextWithJS(counter);
        if (tmp.equals("")) {
            reTweetsCountInt = 0;
        } else {
            try {
                reTweetsCountInt = Integer.parseInt(tmp);
            } catch (Exception ex) {
                reTweetsCountInt = -1;
            }
        }
        return reTweetsCountInt;
    }

    public String getTweetLink(String logMessage, WebElement element, By locator) {
        Reporter.log(logMessage);
        WebElement elDate = element.findElement(locator);
        String tweetLink = elDate.getAttribute("href");
        Reporter.log("tweet link: " + tweetLink);
        return tweetLink;
    }

    public String getTweetDate(WebElement element, By locator) {
        String tweetDateString;
        WebElement elDate = element.findElement(locator);
        Reporter.log("try to find retweet button");
        tweetDateString = elDate.getText();
        Reporter.log("date from tweet: " + tweetDateString);
        return tweetDateString;
    }

    public WebElement getTweetByLink(String logMessage, List<WebElement> elements, String link, By locator) {
        String tweetLink;
        Reporter.log(logMessage);

        for (int i = 0; i < elements.size(); i++) {
            WebElement element = elements.get(i);
            WebElement elDate = element.findElement(locator);

            tweetLink = elDate.getAttribute("href");
            Reporter.log("link tweet: " + tweetLink);
            if (tweetLink.equals(link)) {
                return element;
            }
        }
        return null;
    }

    public boolean waitPageLoadWithJS(String logMessage, int timeoutSeconds) {
        Reporter.log(logMessage);
        do {
            String result = (String.valueOf(getDriver().executeScript("return document.readyState")));
            if (result.equals("complete")) {
                return true;
            }
        } while (System.currentTimeMillis() < System.currentTimeMillis() + timeoutSeconds * 1000);
        return false;
    }

    protected String getTextValueFromElement(By locator) {
        waitForElementVisible(TimeoutSeconds, locator);
        WebElement element = getDriver().findElement(locator);
        return element.getText();
    }

    public String getElementAttribute(By locator, String attribute) {
        waitForElementPresent(locator);
        WebElement element = getDriver().findElement(locator);
        return getElementAttribute(element, attribute);
    }

    public String getElementAttribute(WebElement element, String attribute) {
        return element.getAttribute(attribute);
    }

}