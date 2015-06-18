package com.twitter.pages;

import com.twitter.base.BasePage;
import com.twitter.utils.Reporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created
 * by:   Admin
 * date: 12.06.2015
 * time: 14:50
 */
public class NewTweetDialogPage extends BasePage {
    private By textInputField = By.id("tweet-box-global");
    private By submitMessageButton = By.xpath("//button[contains(@class,'btn primary-btn tweet-action tweet-btn js-tweet-btn')]");

    public void typeNewTweet(String message) {
        type("Type new tweet", message, textInputField);
    }

    public void submitMessage() {
        click("submit new message",submitMessageButton);
    }

    public void waitForTextField() {
        Reporter.log("Wait for Text field to load");
        waitForElementVisible(TimeoutSeconds, textInputField);
    }
}
