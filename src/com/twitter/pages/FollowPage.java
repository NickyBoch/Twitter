package com.twitter.pages;

import com.twitter.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.twitter.utils.Reporter;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * Created
 * by:   Admin
 * tweetDateString: 16.06.2015
 * time: 18:10
 */
public class FollowPage extends BasePage {

    private By tweetsOnPageLocator = By.xpath("//li[contains(@id,'stream-item-tweet')]");
    private By isTweetButtonActive = By.xpath(".//div[contains(@class,'ProfileTweet-action ProfileTweet-action--retweet js-toggleState js-toggleRt')]/button[1]");
    private By tweetDateLocator = By.xpath(".//div[contains(@class,'stream-item-header')]/small/a");
    private By reTweetCount = By.xpath(".//div[contains(@class,'ProfileTweet-action ProfileTweet-action--retweet js-toggleState js-toggleRt')]/button[2]/span[3]/span");
    private By followLink = By.xpath("//a[contains(@class,'ProfileHeaderCard-screennameLink u-linkComplex js-nav')]");

    /**
     * getter for locator of all tweets on page
     * @return locator of all tweets on page
     */
    public By getTweetsOnPageLocator() {
        return tweetsOnPageLocator;
    }

    /**
     * getter for locator of tweet date field
     * @return locator of tweet date field
     */
    public By getTweetDateLocator() {
        return tweetDateLocator;
    }

    /**
     * getter for locator of retweet count field
     * @return locator of retweet count field
     */
    public By getReTweetCountLocator() {
        return reTweetCount;
    }

    /**
     * get link to follow page
     * @return - String - contains link to follow page
     */
    public String getFollowLink() {
        Reporter.log("Get follow link");
        return getElementAttribute(followLink, "href");
    }

    /**
     * get collection of tweets with proper date
     * @param elements - List<WebElement> - collection of all tweets from page
     * @param date - all tweet before this date will be returned in new collection
     * @return - List<WebElement> - collection of tweets with proper date
     */
    public List<WebElement> getListOfTweetsWithProperDate( List<WebElement> elements, LocalDate date) {
        Reporter.log("get list of tweets with proper date");
        String tweetDateString;
        List<WebElement> properDateList = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            WebElement element = elements.get(i);
            //Reporter.log("try to find tweet date field ");
            WebElement elDate = element.findElement(tweetDateLocator);
            //Reporter.log("try to find retweet button");
            tweetDateString = elDate.getText();
            String[] strArr = tweetDateString.split(" ");
            Reporter.log("date from tweet: " + tweetDateString);
            if (isProperDate(date, strArr)) {
                properDateList.add(element);
            }
        }
        return properDateList;
    }

    /**
     * get element from collection available for retweet
     * @param elements - List<WebElement> - collection of tweets from page
     * @return - WebElement - contains all info about tweet available for retweet
     */
    public WebElement getTweetForRetweet(List<WebElement> elements) {
        Reporter.log("try to find active retweet button");
        for (int i = 0; i < elements.size(); i++) {
            WebElement element = elements.get(i).findElement(isTweetButtonActive);
            if (element.isDisplayed()) {
                return elements.get(i);
            }
        }
        return null;
    }

    /**
     * compare tweet date and date
     * @param date - LocalDate - used to compare with tweet date
     * @param tweetDate - String - date from tweet
     * @return - true - if tweet date is before the date, false - if tweet date is after date or same
     */
    private boolean isProperDate(LocalDate date, String[] tweetDate) {
        //Reporter.log("check is tweetDateString of tweet proper for retweet");
        Month month = null;
        int day = Integer.parseInt(tweetDate[0]);

        if (tweetDate[1].startsWith("янв")) {
            month = Month.JANUARY;
        } else if (tweetDate[1].startsWith("фев")) {
            month = Month.FEBRUARY;
        } else if (tweetDate[1].startsWith("мар")) {
            month = Month.MARCH;
        } else if (tweetDate[1].startsWith("апр")) {
            month = Month.APRIL;
        } else if (tweetDate[1].startsWith("мая")) {
            month = Month.MAY;
        } else if (tweetDate[1].startsWith("июн")) {
            month = Month.JUNE;
        } else if (tweetDate[1].startsWith("июл")) {
            month = Month.JULY;
        } else if (tweetDate[1].startsWith("авг")) {
            month = Month.AUGUST;
        } else if (tweetDate[1].startsWith("сен")) {
            month = Month.SEPTEMBER;
        } else if (tweetDate[1].startsWith("окт")) {
            month = Month.OCTOBER;
        } else if (tweetDate[1].startsWith("ноя")) {
            month = Month.NOVEMBER;
        } else if (tweetDate[1].startsWith("дек")) {
            month = Month.DECEMBER;
        } else if (tweetDate[1].startsWith("ч") ||
                tweetDate[1].startsWith("мин") ||
                tweetDate[1].startsWith("c")) {
            return false;
        }
        if ((month.getValue() == date.getMonth().getValue() && day < date.getDayOfMonth()) ||
                month.getValue() <= date.getMonth().getValue())
            return true;

        return false;
    }

    /**
     * get button for retweet
     * @param element - WebElement - contains all info about tweet
     * @return - WebElement - contains button for retweet
     */
    public WebElement getReTweetButton(WebElement element) {
        Reporter.log("try to find retweet button");
        return element.findElement(isTweetButtonActive);
    }

    /**
     *  click button to make retweet
     * @param element - WebElement - button for retweet
     */
    public void clickRetweetButton(WebElement element) {
        click("click retweet button", element);
    }

}