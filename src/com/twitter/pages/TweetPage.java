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
    private By retweetCancelButton = By.xpath("//div[contains(@class,'ProfileTweet-actionButtonUndo js-actionButton js-actionRetweet js-tooltip')]");
    private By retweetCount = By.xpath("//li[contains(@class,'js-stat-count js-stat-retweets stat-count')]/a/strong");
    private By dateField = By.xpath("//div[@class='client-and-actions']/span/span");


    public int getNumberOfReTweets(String tweetLink) {
        Reporter.log("get numder of retweets");
        getDriver().get(tweetLink);
        waitForElementPresent(TimeoutSeconds, retweetCount);
        String tmp = getTextWithJS(getDriver().findElement(retweetCount));
        tmp = tmp.replaceAll("\\u00a0", "");
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

    public void clickReTweetButton() {
        click("click retweeet button (click on tweet page)", retweetButton);
    }

    public void clickCancelReTweetButton() {
        click("click cancel retweeet button (click on tweet page)", retweetCancelButton);
    }

    public void waitForCancelRetweetButton() {
        Reporter.log("wait for retweet cancel button visibility");
        waitForElementVisible(TimeoutSeconds, retweetCancelButton);
    }

    public void waitForRetweetButton() {
        Reporter.log("wait for retweet cancel button visibility");
        waitForElementVisible(TimeoutSeconds, retweetButton);
    }

}
