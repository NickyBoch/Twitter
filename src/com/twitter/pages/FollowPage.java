package com.twitter.pages;

import com.twitter.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.twitter.utils.Reporter;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    String dateAttr="data-original-title";

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
    public List<WebElement> getListOfTweetsWithProperDate( List<WebElement> elements, LocalDate date,String localeString) {
        Reporter.log("get list of tweets with proper date");
        String tweetDateString;
        String fullTweetDateTime;
        List<WebElement> properDateList = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            WebElement element = elements.get(i);
            //Reporter.log("try to find tweet date field ");
            WebElement elDate = element.findElement(tweetDateLocator);
            //Reporter.log("try to find retweet button");
            tweetDateString = elDate.getText();
            fullTweetDateTime = elDate.getAttribute(dateAttr);
            Reporter.log("date from tweet ATTRIBUTE: " + fullTweetDateTime);
            //String[] strArr = tweetDateString.split(" ");

            //
            String tweetDateParsed = parseStringToProperDateFormat(tweetDateString);
            LocalDate tweetDate = convertStringToDate(tweetDateParsed, localeString);
            //

            Reporter.log("date from tweet: " + tweetDateString);
            if (tweetDate != null) {
                if (isProperDate(date, tweetDate)) {
                    properDateList.add(element);
                }
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
     * @param currentDate - LocalDate - used to compare with tweet date
     * @param tweetDate - LocalDate - date from tweet
     * @return  - true - if tweet date is before the date, false - if tweet date is after date or same
     */
    private boolean isProperDate(LocalDate currentDate, LocalDate tweetDate) {

        if ((tweetDate.getMonth().getValue() == currentDate.getMonth().getValue() && tweetDate.getDayOfMonth() < currentDate.getDayOfMonth()) ||
                tweetDate.getMonth().getValue() <= currentDate.getMonth().getValue())
            return true;

        return false;
    }

    /**
     * compare tweet date and date
     * @param date - LocalDate - used to compare with tweet date
     * @param tweetDateString - String - date from tweet
     * @return - true - if tweet date is before the date, false - if tweet date is after date or same
     */
    private boolean isProperDate(LocalDate date, String tweetDateString) {
        //Reporter.log("check is tweetDateString of tweet proper for retweet");
        String[] tweetDate = tweetDateString.split(" ");

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
     * parse in string to proper date format string
     * @param inDateString - String to parse
     * @return - String - in proper format
     */
    private static String parseStringToProperDateFormat(String inDateString) {
        String rez = "";
        String year = "0001";
        String str = inDateString.replace(".", "");
        str = str.replace("de ", "");
        String[] strArr = str.split(" ");
        if (strArr[0].matches("[0-9]+")) {
            rez += strArr[0] + " " + strArr[1].substring(0, 3) + ", " + year;
        } else {
            rez += strArr[1] + " " + strArr[0].substring(0, 3) + ", " + year;
        }
        return rez;
    }

    /**
     * convert string to date with DateTimeFormatter
     * @param dateIn - String - contains string to convert
     * @param locale - String - locale of the date
     * @return - LocalDate - contains date converted from string
     */
    public LocalDate convertStringToDate(String dateIn, String locale) {
        Locale local = Locale.forLanguageTag(locale);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM, yyyy", local);
        LocalDate date;
        try {
            date = LocalDate.parse(dateIn, formatter);
        } catch (DateTimeParseException ex) {
            date = null;
        }
        return date;
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