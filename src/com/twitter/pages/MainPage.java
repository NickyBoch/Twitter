package com.twitter.pages;

import com.twitter.base.BasePage;
import com.twitter.utils.Reporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.util.List;

/**
 * Created
 * by:   Admin
 * date: 15.06.2015
 * time: 10:47
 */
public class MainPage extends BasePage {
    private String tweetHref = "";
    private String tweetDateString="";

    private By tweets = By.xpath("//li[contains(@id,'stream-item-tweet')]");
    private By tweetDate = By.xpath("./div/div[2]/div[1]/small/a");

    private By numberOfTweets = By.className("DashboardProfileCard-statValue");
    private By newTweetButton = By.id("global-new-tweet-button");
    private By confirmMessageSend = By.className("message-text");
    private By followButton = By.xpath("//button[contains(@class,'user-actions-follow-button js-follow-btn follow-button btn')]");
    private By smallFollowButton = By.xpath("//button[contains(@class,'small-follow-btn follow-btn btn small follow-button js-recommended-item')]");
    private By followingCount = By.xpath("//div[contains(@class,'DashboardProfileCard-stats')]/ul/li[2]/a/span[last()]");
    private By userName = By.className("u-textInheritColor");
    private By dropDownMenu = By.id("user-dropdown-toggle");
    private By dropDownUserMenu = By.xpath("//li[@id='user-dropdown']");
    private By logoutButton = By.xpath("//li[@class='js-signout-button']/button");
    private By followersLink = By.xpath("//a[@href='/followers']");


    public void openNewTweetWindow() {
        click("Click new tweet button", newTweetButton);
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

    public void waitForFollowButton() {
        Reporter.log("wait for follow button");
        waitForElementVisible(TimeoutSeconds, followButton);
    }

    public void followButtonClick() {
        click("Click follow button", followButton);
    }

    public void waitForSmallFollowButton() {
        Reporter.log("wait for small follow button");
        waitForElementVisible(TimeoutSeconds, smallFollowButton);
    }

    public void followSmallButtonClick() {
        click("Click small follow button", smallFollowButton);
    }

    public void waitFollowCounterLoad() {
        Reporter.log("Wait for follow counter to load");
        waitForElementVisible(TimeoutSeconds, followingCount);
    }

    public int getNumberOfFollowing() {
        Reporter.log("Get Number of follow person");
        WebElement element = getDriver().findElement(followingCount);
        String str = element.getText();
        return Integer.decode(str);
    }

    public String getCurrentUserName() {
        Reporter.log("Get user name");
        WebElement element = getDriver().findElement(userName);
        return element.getText();
    }

    public void waitForMenuLoad() {
        Reporter.log("Wait for dropdown menu load");
        waitForElementPresent(dropDownMenu);
    }

    public void clickUserMenuButton() {
        Reporter.log("Try to change attribute by javascript");
        WebElement element = getDriver().findElement(dropDownUserMenu);
        setElementAttributeWithJS("set attribute with javascript", "class", "me dropdown session js-session open", element);
    }

    public void submitLogout() {
        click("click logout button", logoutButton);
    }

    public void openFollowersPage() {
        click("click followers page link", followersLink);
    }

    public boolean isReTweeted(String tweetPath) {
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
            tweetDateString = elDate.getText();
            bFlag = tweetHref.equals(tweetPath);
            if (bFlag == true) return bFlag;
        }
        return bFlag;
    }

    public boolean isSameDateOfTweetOnMyPage(String date) {
        Reporter.log("check is date the same on my page after retweet");
        return date.equals(tweetDateString);
    }
}
