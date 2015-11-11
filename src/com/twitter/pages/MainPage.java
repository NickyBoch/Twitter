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
    private By dropDownUserMenuButton = By.xpath("//li[@id='user-dropdown']");
    private By followButton = By.xpath("//button[contains(@class,'user-actions-follow-button js-follow-btn follow-button btn')]");
    private By followersLink = By.xpath("//a[@href='/followers']");
    private By followingLink = By.xpath("//a[@href='/following']");
    private By followingCount = By.xpath("//div[contains(@class,'DashboardProfileCard-stats')]/ul/li[2]/a/span[last()]");
    private By linkToMyPage = By.xpath("//a[contains(@class,'DashboardProfileCard-screennameLink u-linkComplex u-linkClean')]");
    private By logoutButton = By.xpath("//li[@class='js-signout-button']/button");
    private By newTweetButton = By.id("global-new-tweet-button");
    private By newTweetWindow = By.xpath("//div[@id='global-tweet-dialog' and @class='modal-container']");
    private By numberOfTweetsField = By.className("DashboardProfileCard-statValue");
    private By smallFollowButton = By.xpath("//button[contains(@class,'small-follow-btn follow-btn btn small follow-button js-recommended-item')]");
    private By userName = By.className("u-textInheritColor");
    private By settingsButton=By.xpath("//ul[@class='nav right-actions']/li/div[@class='dropdown-menu']/ul/li[8]/a");
    private By dropDownMenu=By.xpath("//ul [@role='menu']");

    /**
     * click new tweet button
     */
    public void clickMakeNewTweetButton() {
        click("Click new tweet button", newTweetButton);
    }

    /**
     * click logout button
     */
    public void clickLogoutButton() {
        click("click logout button", logoutButton);
    }

    /**
     * click link to my page
     */
    public void clickMyPageLink() {
        click("click tweets page link", linkToMyPage);
    }

    /**
     * click followers link
     */
    public void clickFollowersLink() {
        click("click followers page link", followersLink);
    }

    /**
     * click following link
     */
    public void clickFollowingLink() {
        click("click following page link", followingLink);
    }

    /**
     * click small follow button
     */
    public void clickSmallFollowButton() {
        click("Click small follow button", smallFollowButton);
    }

    /**
     * get count of following users
     *
     * @return - int - count of following users
     */
    public int getFollowingCount() {
        Reporter.log("Get Number of follow person");
        return Integer.parseInt(getTextValueFromElement(followingCount));
    }

    /**
     * get my user name
     *
     * @return - String - contains my user name
     */
    public String getCurrentUserName() {
        Reporter.log("Get user name");
        return getTextValueFromElement(userName);
    }

    /**
     * get count of tweets from field on my main page
     *
     * @return - int - count of tweets from field on my main page
     */
    public int getCountOfTweetsOnMainPage() {
        Reporter.log("Get number of tweets on main page");
        return Integer.parseInt(getTextValueFromElement(numberOfTweetsField));
    }

    /**
     * wait for follow button
     * need only when user has less then 3 following users
     */
    public void waitForFollowButton() {
        Reporter.log("wait for follow button");
        waitForElementVisible(TimeoutSeconds, followButton);
    }

    /**
     * click follow button
     * need only when user has less then 3 following users
     */
    public void followButtonClick() {
        click("Click follow button", followButton);
    }

    /**
     * wait for small follow button
     */
    public void waitForSmallFollowButton() {
        Reporter.log("wait for small follow button");
        waitForElementVisible(TimeoutSeconds, smallFollowButton);
    }

    /**
     * wait for visibility of message send confirm window
     */
    public void waitForMessageSendConfirm() {
        Reporter.log("Wait for message send confirm");
        waitForElementVisible(TimeoutSeconds, confirmMessageSend);
    }

    /**
     * wait for element(dropdown menu button) present
     */
    public void waitForMenuLoad() {
        Reporter.log("Wait for dropdown menu load");
        waitForElementPresent(dropDownUserMenuButton);
    }

    /**
     * Wait for dropdown menu visibility
     */
    public  void waitForMenuVisibility() {
        Reporter.log("Wait for dropdown menu visibility");
        waitForElementVisible(TimeoutSeconds, dropDownMenu);
    }

    /**
     * get dropdown menu button
     *
     * @return - WebElement - contains dropdown menu button
     */
    public WebElement getUserMenuButton() {
        Reporter.log("Try to get dropdown menu button");
        return getDriver().findElement(dropDownUserMenuButton);
    }

    /**
     * set element attribute with javascript
     *
     * @param element - WebElement - in which attribute need to be changed
     */
    public void setAttributeForDropDownMenuButton(WebElement element) {
        setElementAttributeWithJS("class", "me dropdown session js-session open", element);
    }

    /**
     * wait for appearance of new tweet window
     */
    public void waitForNewTweetWindow() {
        Reporter.log("wait for appearance of new tweet window");
        waitForElementVisible(TimeoutSeconds, newTweetWindow);
    }

    /**
     * wait for disappearance of new tweet window
     */
    public void waitForNewTweetWindowDisappear() {
        Reporter.log("wait for disappearance of new tweet window");
        waitForElementInvisible(TimeoutSeconds, newTweetWindow);
    }

    /**
     * get link from elements attribute
     * @return - String - link
     */
    public String getSettingsLink() {
        WebElement element = getElement(settingsButton);
        String str = element.getAttribute("href");
        return str;
    }

    /**
     * clock on settings menu item
     */
    public void clickSettingsButton() {
        click("click settings button", settingsButton);
    }

    public void waitForFollowingLink() {
        Reporter.log("wait for following link");
        waitForElementVisible(TimeoutSeconds, followingLink);
    }
}