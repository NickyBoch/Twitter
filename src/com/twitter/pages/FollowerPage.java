package com.twitter.pages;

import com.twitter.base.BasePage;
import com.twitter.utils.Reporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

/**
 * Created
 * by:   Admin
 * date: 16.06.2015
 * time: 18:10
 */
public class FollowerPage extends BasePage {

    private String date = "";
    private String tweetHref = "";
    private int reTweetsCountInt = 0;

    private By tweets = By.xpath("//li[contains(@id,'stream-item-tweet')]");
    private By isTweetPossible = By.xpath(".//div[contains(@class,'ProfileTweet-action ProfileTweet-action--retweet js-toggleState js-toggleRt')]/button[1]");
    private By tweetDate = By.xpath("./div/div[2]/div[1]/small/a");
    private By reTweetCount = By.xpath(".//div[contains(@class,'ProfileTweet-action ProfileTweet-action--retweet js-toggleState js-toggleRt')]/button[2]/span[3]/span");

    public WebElement chooseTweetAndReTweet() {
        LocalDate localDate = LocalDate.now();
        waitForElementVisible(TimeoutSeconds, tweets);

        List<WebElement> listWebElements = getDriver().findElements(tweets);
        int count = listWebElements.size();
        Reporter.log("Number of visible tweets: "+Integer.toString(count));

        Reporter.log("search for tweet to retweet");
        for (int i = 0; i < count; i++) {
            WebElement element = listWebElements.get(i);
            WebElement elDate = element.findElement(tweetDate);
            //Reporter.log("try to find retweet button");
            WebElement elementIsTweetPossible = element.findElement(isTweetPossible);
            //Reporter.log("try to find retweet count");
            WebElement elementReTweetCounter = element.findElement(reTweetCount);
            if (!elementIsTweetPossible.isDisplayed())
                continue;
            date = elDate.getText();
            String[] strArr = date.split(" ");
            Reporter.log("date from tweet: " + date);
/*            Reporter.log("text day from tweet: " + strArr[0]);
            Reporter.log("text month from tweet: " + strArr[1]);*/

            if (isProperDate(localDate, strArr)) {
                String tmp = super.getText(elementReTweetCounter);
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
                Reporter.log("retweets count before: " + reTweetsCountInt);
                tweetHref = elDate.getAttribute("href");
                Reporter.log("href tweet: " + tweetHref);
                return elementIsTweetPossible;
            }
        }
        return null;
    }

    private boolean isProperDate(LocalDate localDate, String[] strArr) {
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
        }

        if ((month.getValue() == localDate.getMonth().getValue() && day + 1 <= localDate.getDayOfMonth()) ||
                month.getValue() <= localDate.getMonth().getValue())
            return true;

        return false;
    }

    public int getNumberOfReTweets() {
        return reTweetsCountInt;
    }

    public String getDate() {
        return date;
    }

    public void click(WebElement element) {
        click("click for retweet", element);
    }

    public String getTweetPath() {
        return tweetHref;
    }
}
