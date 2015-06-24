package com.twitter.pages;

import com.twitter.base.BasePage;
import com.twitter.utils.Reporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


/**
 * Created
 * by:   Admin
 * date: 17.06.2015
 * time: 13:49
 */
public class TweetPage extends BasePage {
    private String tweetDateString = "";
    private String tweetHref = "";
    private int reTweetsCountInt = 0;

    private By retweetButton = By.xpath("//div[contains(@class,'ProfileTweet-action ProfileTweet-action--retweet js-toggleState js-toggleRt')]");
    private By retweetCanselButton = By.xpath("//div[contains(@class,'ProfileTweet-action ProfileTweet-action--retweet js-toggleState js-toggleRt')]/button[2]/span[3]/span");
    private By dateField = By.xpath("//div[@class='client-and-actions']/span/span");
    //private By reTweetsCounter = By.xpath("//li[contains(@class,'js-stat-count js-stat-retweets stat-count')]/a/strong");

    public String getDate() {
        Reporter.log("get date of the tweet");
        return getDriver().findElement(dateField).getText();
    }


    public int getNumberOfReTweets(String tweetHref) {
        Reporter.log("get numder of retweets");
        getDriver().get(tweetHref);
        waitForElementPresent(TimeoutSeconds,retweetCanselButton );
        WebElement element = getDriver().findElement(retweetCanselButton);
        String tmp = getTextWithJS(element);
        tmp = tmp.replace(" ", "");
        int count = 0;
        if (tmp.equals("")) {
            count = 0;
        } else {
            try {
                count = Integer.parseInt(tmp);
            } catch (Exception ex) {
                //System.out.println("--->" + ex.getMessage());
            }
        }

        return count;
    }

    public void makeReTweet() {
        click("try to retweeet (click on tweet page)", retweetButton);
    }

}
