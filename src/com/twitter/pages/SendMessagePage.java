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
public class SendMessagePage extends BasePage {
    private By newTweet = By.id("global-new-tweet-button");
    private By numberOfTweets = By.className("DashboardProfileCard-statValue");
    private By textInputField = By.id("tweet-box-global");
    private By submitMessageButton = By.xpath("//button[contains(@class,'btn primary-btn tweet-action tweet-btn js-tweet-btn')]");
    private By confirmMessageSend = By.className("message-text");


    public void openNewTweetWindow() {
        click("Click new tweet button", newTweet);
    }

    public void typeNewTweet(String message) {
        type("Type new tweet", message, textInputField);
    }

    public void submitMessage() {
        WebElement element = getDriver().findElement(submitMessageButton);
        clickWithJS("submit new message", element);
    }

    public void waitForMessageSendConfirm() {
        Reporter.log("Wait for message send confirm");
        waitForElementVisible(TimeoutSeconds, confirmMessageSend);
    }

    public int getNumberOfTweets() {
        Reporter.log("Get Number of Tweets");
        waitForElementVisible(TimeoutSeconds, numberOfTweets);
        WebElement element = getDriver().findElement(numberOfTweets);
        String str = element.getText();
        return Integer.decode(str);
    }

    public void waitForTextField() {
        Reporter.log("Wait for Text field to load");
        waitForElementVisible(TimeoutSeconds, textInputField);
    }
}
