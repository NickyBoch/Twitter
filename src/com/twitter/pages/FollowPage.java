package com.twitter.pages;

import com.twitter.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.twitter.utils.Reporter;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

/**
 * Created
 * by:   Admin
 * tweetDateString: 16.06.2015
 * time: 18:10
 */
public class FollowPage extends BasePage {

    private String tweetDateString = "";
    private String tweetHref = "";
    private int reTweetsCountInt = 0;

    private By tweets = By.xpath("//li[contains(@id,'stream-item-tweet')]");
    private By isTweetPossible = By.xpath(".//div[contains(@class,'ProfileTweet-action ProfileTweet-action--retweet js-toggleState js-toggleRt')]/button[1]");
    private By tweetDate = By.xpath(".//div[contains(@class,'stream-item-header')]/small/a");
    private By reTweetCount = By.xpath(".//div[contains(@class,'ProfileTweet-action ProfileTweet-action--retweet js-toggleState js-toggleRt')]/button[2]/span[3]/span");


    public WebElement chooseTweetAndReturnRetweetButton() {
        int scrollCoef = 3;
        WebElement elementIsTweetPossible = null;
        List<WebElement> listWebElements = null;
        LocalDate localDate = LocalDate.now();
        Reporter.log("wait for tweets elements visible");
        waitForElementVisible(TimeoutSeconds, tweets);

        while (true) {
            listWebElements = getDriver().findElements(tweets);
            int count = listWebElements.size();
            Reporter.log("Number of visible tweets: " + Integer.toString(count));

            Reporter.log("search for tweet to retweet");
            for (int i = 0; i < count - 1; i++) {
                try {
                    WebElement element = listWebElements.get(i);
                    Reporter.log("try to find tweet dateg field ");
                    WebElement elDate = element.findElement(tweetDate);
                    Reporter.log("try to find retweet button");
                    elementIsTweetPossible = element.findElement(isTweetPossible);
                    Reporter.log("try to find retweet count");
                    WebElement elementReTweetCounter = element.findElement(reTweetCount);
                    if (!elementIsTweetPossible.isDisplayed())
                        continue;
                    tweetDateString = elDate.getText();
                    String[] strArr = tweetDateString.split(" ");
                    Reporter.log("date from tweet: " + tweetDateString);
                    //Reporter.log("text day from tweet: " + strArr[0]);
                    //Reporter.log("text month from tweet: " + strArr[1]);

                    if (isProperDate(localDate, strArr)) {
                        String tmp = super.getTextWithJS(elementReTweetCounter);
                        if (tmp.equals("")) {
                            reTweetsCountInt = 0;
                        } else {
                            try {
                                reTweetsCountInt = Integer.parseInt(tmp);
                            } catch (Exception ex) {
                                reTweetsCountInt = -1;
                                //System.out.println("--->" + ex.getMessage());
                            }
                        }
                        Reporter.log("date from tweet: " + tweetDateString);
                        Reporter.log("retweets count before: " + reTweetsCountInt);
                        tweetHref = elDate.getAttribute("href");
                        Reporter.log("href tweet: " + tweetHref);
                        if (elementIsTweetPossible != null) return elementIsTweetPossible;
                        else {
                            scrollWithJS(0, 2000 * scrollCoef);
                            scrollCoef++;
                            listWebElements = getDriver().findElements(tweets);
                        }
                    }
                } catch (Exception ex) {
                    scrollWithJS(0, 2000 * scrollCoef);
                    scrollCoef++;
                    listWebElements = getDriver().findElements(tweets);
                    i = 0;
                    Reporter.log("----->" + ex.getMessage());
                }
            }
            scrollWithJS(0, 2000 * scrollCoef);
            scrollCoef++;
        }
        //return null;
    }

    private boolean isProperDate(LocalDate localDate, String[] strArr) {
        //Reporter.log("check is tweetDateString of tweet proper for retweet");
        Month month = null;
        int day = 0;
        try {
            day = Integer.parseInt(strArr[0]);
        } catch (Exception ex) {
            Reporter.log(ex.getMessage());
        }

        if (strArr[1].startsWith("янв")) {
            month = Month.JANUARY;
        } else if (strArr[1].startsWith("фев")) {
            month = Month.FEBRUARY;
        } else if (strArr[1].startsWith("мар")) {
            month = Month.MARCH;
        } else if (strArr[1].startsWith("апр")) {
            month = Month.APRIL;
        } else if (strArr[1].startsWith("мая")) {
            month = Month.MAY;
        } else if (strArr[1].startsWith("июн")) {
            month = Month.JUNE;
        } else if (strArr[1].startsWith("июл")) {
            month = Month.JULY;
        } else if (strArr[1].startsWith("авг")) {
            month = Month.AUGUST;
        } else if (strArr[1].startsWith("сен")) {
            month = Month.SEPTEMBER;
        } else if (strArr[1].startsWith("окт")) {
            month = Month.OCTOBER;
        } else if (strArr[1].startsWith("ноя")) {
            month = Month.NOVEMBER;
        } else if (strArr[1].startsWith("дек")) {
            month = Month.DECEMBER;
        } else if (strArr[1].startsWith("ч") ||
                strArr[1].startsWith("мин") ||
                strArr[1].startsWith("c")) {
            return false;
        }

        if ((month.getValue() == localDate.getMonth().getValue() && day + 1 <= localDate.getDayOfMonth()) ||
                month.getValue() <= localDate.getMonth().getValue())
            return true;

        return false;
    }

    public int getNumberOfReTweets() {
        return reTweetsCountInt;
    }

    public String getTweetDateString() {
        return tweetDateString;
    }

    public void click(WebElement element) {
        click("click for retweet", element);
    }

    public String getTweetPath() {
        return tweetHref;
    }

    public void scrollWithJS(int x, int y) {
        mouseScrollWithJS("scroll page with js", x, y);
    }

    public void scrollIntoView(WebElement element) {
        scrollToElementWithJS("scroll to element with javascript", element);
    }

    public void waitForElement(WebElement element) {
        Reporter.log("wait for retweet button visibility");
        waitForElementVisible(TimeoutSeconds, element);
    }

    public boolean checkTweetExistence(String tweetHref) {
        waitForElementVisible(TimeoutSeconds, tweets);
        return isTweetExists("searching is retweet still on following page", tweetHref, tweets, tweetDate);
    }

    public void getDataFromTweet(String followPath,String tweetPath) {
        getDriver().get(followPath);
        Reporter.log("check for retweet: " + tweetPath);
        waitForElementVisible(TimeoutSeconds, tweets);
        boolean bFlag = false;

        List<WebElement> listWebElements = getDriver().findElements(tweets);
        int count = listWebElements.size();
        Reporter.log("tweets count: " + count);
        for (int i = 0; i < count; i++) {
            WebElement element = listWebElements.get(i);
            WebElement elDate = element.findElement(tweetDate);

            tweetHref = elDate.getAttribute("href");
            Reporter.log("href tweet: " + tweetHref);

            if (tweetHref.equals(tweetPath) == true)
                tweetDateString = elDate.getText();
            return;
        }
    }

}

