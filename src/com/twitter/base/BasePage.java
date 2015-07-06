package com.twitter.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.twitter.utils.Reporter;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created
 * by:   Admin
 * date: 11.06.2015
 * time: 16:43
 */
public class BasePage {
    private RemoteWebDriver driver;

    private final String baseURL = "https://twitter.com/";

    //Timeouts
    public final static int TimeoutSeconds = 60;
    public final static int NormalTimeoutSeconds = 30;
    public final static int SmallTimeoutSeconds = 15;
    public final static int ExtrasmallTimeoutSeconds = 5;
    public final static int MicroTimeoutSeconds = 2;


    /**
     * getter of RemoteWebDriver
     * @return - RemoteWebDriver
     */
    public RemoteWebDriver getDriver() {
        if (driver == null) {
            driver = BaseTest.driver;
        }
        return driver;
    }

    /**
     * open page by base URL
     */
    public void openMainPage() {
        open(baseURL);
    }

    /**
     * open page by url
     * @param linkToOpen - url of page to open
     */
    public void open(String linkToOpen) {
        Reporter.log("open page: " + linkToOpen);
        getDriver().get(linkToOpen);
        waitPageLoadWithJS("wait " + linkToOpen + " load", TimeoutSeconds);
    }

    /**
     * wait for element presence
     * @param locator of element
     */
    protected void waitForElementPresent(By locator) {
        waitForElementPresent(TimeoutSeconds, locator);
    }

    /**
     * wait for element presence with timeout
     * @param timeout - waiting time for element presence
     * @param locator of element
     */
    protected void waitForElementPresent(int timeout, By locator) {
        getDriver().manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        getDriver().manage().timeouts().implicitlyWait(TimeoutSeconds, TimeUnit.SECONDS);
    }

    /**
     * wait for element for visibility with timeout
     * @param timeout - waiting time for element visibility
     * @param locator of element
     */
    protected void waitForElementVisible(int timeout, By locator) {
        getDriver().manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        getDriver().manage().timeouts().implicitlyWait(TimeoutSeconds, TimeUnit.SECONDS);
    }

    /**
     * wait for element for be clickable with timeout
     * @param timeout - waiting time for element be clickable
     * @param locator of element
     */
    public void waitForElementBeClickable(int timeout, By locator) {
        getDriver().manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        getDriver().manage().timeouts().implicitlyWait(TimeoutSeconds, TimeUnit.SECONDS);
    }

    public void waitForElementBeClickable(int timeout, WebElement element) {
        getDriver().manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        getDriver().manage().timeouts().implicitlyWait(TimeoutSeconds, TimeUnit.SECONDS);
    }

    /**
     * wait for element for invisibility with timeout
     * @param timeout - waiting time for element invisibility
     * @param locator of element
     */
    protected void waitForElementInvisible(int timeout, By locator) {
        getDriver().manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        getDriver().manage().timeouts().implicitlyWait(TimeoutSeconds, TimeUnit.SECONDS);
    }

    /**
     * wait for element for visibility with timeout
     * @param timeout - waiting time for element visibility
     * @param elemeht to wait
     */
    protected void waitForElementVisible(int timeout, WebElement elemeht) {
        getDriver().manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
        wait.until(ExpectedConditions.visibilityOf(elemeht));
        getDriver().manage().timeouts().implicitlyWait(TimeoutSeconds, TimeUnit.SECONDS);
    }

    /**
     * type message into text input field
     * @param logMessage - message for log
     * @param typeMessage - message to type into text input field
     * @param locator of text input field
     */
    protected void type(String logMessage, String typeMessage, By locator) {
        Reporter.log(logMessage);
        WebElement input = getElement(locator);
        input.clear();
        if (typeMessage.isEmpty() != true) {
            input.sendKeys(typeMessage);
        }
    }

    /**
     * get element by locator
     * @param locator of element
     * @return - WebElement - find by locator
     */
    protected WebElement getElement(By locator) {
        return getDriver().findElement(locator);
    }

    /**
     * click element by locator
     * @param logMessage - message for log
     * @param locator of element to click
     */
    protected void click(String logMessage, By locator) {
        Reporter.log(logMessage);
        WebElement webElement = getDriver().findElement(locator);
        webElement.click();
    }

    /**
     * click element
     * @param logMessage - message for log
     * @param element - which need to be clicked
     */
    protected void click(String logMessage, WebElement element) {
        Reporter.log(logMessage);
        element.click();
    }

    /**
     * click element with java script
     * @param logMessage - message for log
     * @param element - which need to be clicked
     */
    protected void clickWithJS(String logMessage, WebElement element) {
        Reporter.log(logMessage);
        getDriver().executeScript("arguments[0].click()", element);
    }

    /**
     * set element attribute with javascript
     * @param attributeName - attribute which need to be changed
     * @param attributeValue - new attribute value
     * @param element which attribute need to be changed
     */
    protected void setElementAttributeWithJS(String attributeName, String attributeValue, WebElement element) {
        Reporter.log("set attribute with javascript");
        getDriver().executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, attributeName,
                attributeValue);
    }

    /**
     *  get text from element with javascript
     * @param element - get text from this element
     * @return - String - text from element
     */
    protected String getTextWithJS(WebElement element) {
        Reporter.log("get text from element with javascript");
        return (String) getDriver().executeScript("return arguments[0].innerText;", element);
    }

    /**
     * scroll page with javascript
     * @param x - value of horizontal scroll
     * @param y - value of vertical scroll
     */
    public void mouseScrollWithJS(int x, int y) {
        Reporter.log("scroll page with js");
        String javaScript = "window.scrollBy(" + x + "," + y + ");";
        getDriver().executeScript(javaScript);
    }

    /**
     * scroll to element with javascript
     * @param element
     */
    public void scrollToElementWithJS(WebElement element) {
        Reporter.log("scroll to element with js");
        String javaScript = "arguments[0].scrollIntoView(false);";
        getDriver().executeScript(javaScript, element);
    }

    /**
     * get all tweet elements on page
     * @param locator of tweet elements
     * @return - List<WebElement> - collection of all tweets from page
     */
    public List<WebElement> getAllTweetsOnPage( By locator) {
        Reporter.log("trying to get all tweets on page");
        waitForElementVisible(TimeoutSeconds, locator);
        List<WebElement> listWebElements = getDriver().findElements(locator);
        return listWebElements;
    }

    /**
     * get count of retweets
     * @param element - WebElement - contains all info about tweet
     * @param locator of field with count ot retweets
     * @return - int - count of retweets
     */
    public int getCountOfReTweets(WebElement element, By locator) {
        Reporter.log("trying to get count of retweet");
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

    /**
     * get tweet link
     * @param element - contains all tweet info
     * @param locator - of element contains link
     * @return - String - contains tweet link
     */
    public String getTweetLink(WebElement element, By locator) {
        Reporter.log("try to get tweet link");
        WebElement elDate = element.findElement(locator);
        String tweetLink = elDate.getAttribute("href");
        Reporter.log("tweet link: " + tweetLink);
        return tweetLink;
    }

    /**
     * get tweet date
     * @param element - from which need to get date
     * @param locator - of element contains date
     * @return - String - date
     */
    public String getTweetDate(WebElement element, By locator) {
        String tweetDateString;
        WebElement elDate = element.findElement(locator);
        Reporter.log("try to find retweet button");
        tweetDateString = elDate.getText();
        Reporter.log("date from tweet: " + tweetDateString);
        return tweetDateString;
    }

    /**
     * get tweet element from collection comparing tweets link from collection with in param
     * @param elements - List<WebElement> - collection of tweets elements
     * @param link - search proper tweet comparing with link
     * @param locator - to search link field in tweet element
     * @return - WebElement - tweet element if it exists in collection
     */
    public WebElement getTweetByLink(List<WebElement> elements, String link, By locator) {
        String tweetLink;
        Reporter.log("getting tweet on page by link");

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

    /**
     * wait page load with javascript
     * @param logMessage - message for log
     * @param timeoutSeconds - wait time in seconds
     * @return true - if page loaded,false - if page failed to load
     */
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

    /**
     * get text value from element
     * @param locator of element from which text need to get
     * @return - String - text from element
     */
    protected String getTextValueFromElement(By locator) {
        waitForElementVisible(TimeoutSeconds, locator);
        WebElement element = getDriver().findElement(locator);
        return element.getText();
    }

    /**
     * get element attribute
     * @param locator - of element which attribute value need to get
     * @param attribute - which need to be get
     * @return
     */
    public String getElementAttribute(By locator, String attribute) {
        waitForElementPresent(locator);
        WebElement element = getElement(locator);
        return getElementAttribute(element, attribute);
    }

    /**
     * get element attribute
     * @param element - which attribute value need to get
     * @param attribute - which need to be get
     * @return
     */
    public String getElementAttribute(WebElement element, String attribute) {
        return element.getAttribute(attribute);
    }



    public void mouseOverWithJS(String logMessage, WebElement webElement)
    {
        Reporter.log(logMessage);
        final String javaScript = "if(document.createEvent){"+
                                        "var evObj = document.createEvent('MouseEvents');"+
                                        "evObj.initEvent('mouseover', true, false);"+"" +
                                        "arguments[0].dispatchEvent(evObj);"+
                                    "} else if(document.createEventObject){"+
                                        "arguments[0].fireEvent('onmouseover');"+
                                    "}";
        getDriver().executeScript(javaScript, webElement);
    }


}