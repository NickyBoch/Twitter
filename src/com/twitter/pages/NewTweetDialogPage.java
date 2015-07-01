package com.twitter.pages;

import com.twitter.base.BasePage;
import com.twitter.utils.Reporter;
import org.openqa.selenium.By;


/**
 * Created
 * by:   Admin
 * date: 12.06.2015
 * time: 14:50
 */
public class NewTweetDialogPage extends BasePage {
    private By textInputField = By.id("tweet-box-global");
    private By sendNewTweetButton = By.xpath("//button[contains(@class,'btn primary-btn tweet-action tweet-btn js-tweet-btn')]");

    /**
     * type new tweet into the text input field
     *
     * @param message - String - contains new tweet
     */
    public void typeNewTweet(String message) {
        type("Type new tweet", message, textInputField);
    }

    /**
     * click send new tweet button
     */
    public void clickTweetButton() {
        click("click send new tweet button", sendNewTweetButton);
    }

    /**
     * wait for text input field
     */
    public void waitForTextField() {
        Reporter.log("Wait for Text field to load");
        waitForElementVisible(TimeoutSeconds, textInputField);
    }
}
