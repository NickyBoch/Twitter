package com.twitter.pages;

import com.twitter.base.BasePage;
import com.twitter.utils.Reporter;
import org.openqa.selenium.By;

/**
 * Created
 * by:   Admin
 * date: 17.06.2015
 * time: 14:14
 */
public class ReTweetConfirmPage extends BasePage {
    private By reTweetConfirm = By.xpath("//div[@id='retweet-tweet-dialog-dialog']/div[2]/form/div[2]/div[3]/button");

    public void confirmReTweet() {
        click("confirm retweet", reTweetConfirm);
    }

    public void waitForRetweetButton() {
        Reporter.log("wait for confirm button");
        waitForElementVisible(TimeoutSeconds, reTweetConfirm);
    }
}
