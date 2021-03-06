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
    private By reTweetConfirmButton = By.xpath("//div[@id='retweet-tweet-dialog-dialog']/div[2]/form/div[2]/div[3]/button");

    /**
     * click retweet confirm button
     */
    public void clickReTweetConfirmButton() {
        click("click retweet confirm button", reTweetConfirmButton);
    }

    /**
     * wait for retweet button
     */
    public void waitForRetweetButton() {
        Reporter.log("wait for retweet button");
        waitForElementVisible(TimeoutSeconds, reTweetConfirmButton);
    }
}
