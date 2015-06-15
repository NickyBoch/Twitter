package com.twitter.pages;

import com.twitter.base.BasePage;
import com.twitter.utils.Reporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created
 * by:   Admin
 * date: 12.06.2015
 * time: 15:56
 */
public class FollowPage extends BasePage {
    private By followButton = By.xpath("//button[contains(@class,'user-actions-follow-button js-follow-btn follow-button btn')]");
    private By smallFollowButton = By.xpath("//button[contains(@class,'small-follow-btn follow-btn btn small follow-button js-recommended-item')]");
    private By followingCount = By.xpath("//div[contains(@class,'DashboardProfileCard-stats')]/ul/li[2]/a/span[last()]");

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

    public void updateMainPage() {
        Reporter.log("Update main page");
        getDriver().get("https://twitter.com/i/notifications");
        getDriver().get("https://twitter.com/");
    }
}
