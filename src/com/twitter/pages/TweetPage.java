package com.twitter.pages;

import com.twitter.base.BasePage;
import com.twitter.utils.Reporter;
import org.openqa.selenium.By;


/**
 * Created
 * by:   Admin
 * date: 17.06.2015
 * time: 13:49
 */
public class TweetPage extends BasePage {
    private By retweetButton = By.xpath("//div[contains(@class,'ProfileTweet-action ProfileTweet-action--retweet js-toggleState js-toggleRt')]");
    private By retweetCanselButton = By.xpath("//div[contains(@class,'ProfileTweet-action ProfileTweet-action--retweet js-toggleState js-toggleRt')]");
    private By dateField = By.xpath("//div[@class='client-and-actions']/span/span");
    private By reTweetsCounter = By.xpath("//li[contains(@class,'js-stat-count js-stat-retweets stat-count')]/a/strong");

    public String getDate() {
        Reporter.log("get date of the tweet");
        return getDriver().findElement(dateField).getText();
    }


    public int getNumberOfReTweets(String tweetHref) {
        Reporter.log("get numder of retweets");
        getDriver().get(tweetHref);
        String str = getDriver().findElement(reTweetsCounter).getText();
        //System.out.println("--->>>" + str);
        str = str.replace(" ", "");
        return Integer.parseInt(str);
    }

    public void makeReTweet() {
        click("try to retweeet (click on tweet page)", retweetButton);
    }

}
