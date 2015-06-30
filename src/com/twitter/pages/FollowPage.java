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

    public By getTweetsOnPageLocator() {
        return tweetsOnPageLocator;
    }

    public By getTweetDateLocator() {
        return tweetDateLocator;
    }

    public By getReTweetCountLocator() {
        return reTweetCount;
    }

    public String getFollowLink() {
        Reporter.log("Get follow link");
        return getElementAttribute(followLink, "href");
    }

    public List<WebElement> getListOfTweetsWithProperDate(String logMessage, List<WebElement> elements, LocalDate date) {
        Reporter.log(logMessage);
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

    public WebElement getTweetForRetweet(String logMessage, List<WebElement> elements) {
        Reporter.log(logMessage);
        for (int i = 0; i < elements.size(); i++) {
            WebElement element = elements.get(i).findElement(isTweetButtonActive);
            if (element.isDisplayed()) {
                return elements.get(i);
            }
        }
        return null;
    }

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

    public WebElement getReTweetButton(WebElement element) {
        Reporter.log("try to find retweet button");
        return element.findElement(isTweetButtonActive);
    }

    public void clickRetweetButton(WebElement element) {
        click("click retweet button", element);
    }

}