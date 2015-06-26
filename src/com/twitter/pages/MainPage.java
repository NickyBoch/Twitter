package com.twitter.pages;

import com.twitter.base.BasePage;
import com.twitter.utils.Reporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created
 * by:   Admin
 * date: 15.06.2015
 * time: 10:47
 */
public class MainPage extends BasePage {

    private By confirmMessageSend = By.className("message-text");
    private By dropDownUserMenu = By.xpath("//li[@id='user-dropdown']");
    private By followButton = By.xpath("//button[contains(@class,'user-actions-follow-button js-follow-btn follow-button btn')]");
    private By followersLink = By.xpath("//a[@href='/followers']");
    private By followingLink = By.xpath("//a[@href='/following']");
    private By followingCount = By.xpath("//div[contains(@class,'DashboardProfileCard-stats')]/ul/li[2]/a/span[last()]");
    private By logoutButton = By.xpath("//li[@class='js-signout-button']/button");
    private By newTweetButton = By.id("global-new-tweet-button");
    private By numberOfTweetsField = By.className("DashboardProfileCard-statValue");
    private By linkToMyPage=By.xpath("//a[contains(@class,'DashboardProfileCard-screennameLink u-linkComplex u-linkClean')]");
    private By smallFollowButton = By.xpath("//button[contains(@class,'small-follow-btn follow-btn btn small follow-button js-recommended-item')]");
    private By userName = By.className("u-textInheritColor");


    public void clickNewTweetWindowButton() {
        click("Click new tweet button", newTweetButton);
    }

    public void clickLogoutButton() {
        click("click logout button", logoutButton);
    }

    public void clickMyPageLink() {
        click("click tweets page link", linkToMyPage);
    }

    public void clickFollowersLink() {
        click("click followers page link", followersLink);
    }

    public void clickFollowingLink() {
        click("click following page link", followingLink);
    }

    public void clickSmallFollowButton() {
        click("Click small follow button", smallFollowButton);
    }

    public int getFollowingCount() {
        Reporter.log("Get Number of follow person");
        WebElement element = getDriver().findElement(followingCount);
        String str = element.getText();
        return Integer.valueOf(str);
    }

    public String getCurrentUserName() {
        Reporter.log("Get user name");
        WebElement element = getDriver().findElement(userName);
        return element.getText();
    }

    public int getCountOfTweetsOnMainPage() {
        Reporter.log("Get Number of Tweets on main page");
        waitForElementVisible(TimeoutSeconds, numberOfTweetsField);
        WebElement element = getDriver().findElement(numberOfTweetsField);
        String str = element.getText();
        return Integer.valueOf(str);
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

    public void waitForMessageSendConfirm() {
        Reporter.log("Wait for message send confirm");
        waitForElementVisible(TimeoutSeconds, confirmMessageSend);
    }

    public void waitFollowCounterLoad() {
        Reporter.log("Wait for follow counter to load");
        waitForElementVisible(TimeoutSeconds, followingCount);
    }

    public void waitForMenuLoad() {
        Reporter.log("Wait for dropdown menu load");
        waitForElementPresent(dropDownUserMenu);
    }

    public void clickUserMenuButton() {
        Reporter.log("Try to change attribute by javascript");
        WebElement element = getDriver().findElement(dropDownUserMenu);
        setElementAttributeWithJS("set attribute with javascript", "class", "me dropdown session js-session open", element);
    }

}
