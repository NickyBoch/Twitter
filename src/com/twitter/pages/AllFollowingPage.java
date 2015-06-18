package com.twitter.pages;

import com.twitter.base.BasePage;
import com.twitter.utils.Reporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

/**
 * Created
 * by:   Admin
 * date: 18.06.2015
 * time: 10:18
 */
public class AllFollowingPage extends BasePage {
    private By followerLinkCount = By.xpath("//div[contains(@id,'stream-item-user')]/div/a");

    public void clickUser() {
        waitForElementVisible(TimeoutSeconds, followerLinkCount);
        List<WebElement> listFollowings = getDriver().findElements(followerLinkCount);
        Reporter.log("Number of following: " + listFollowings.size());
        Random rand = new Random();
        int index = rand.nextInt(listFollowings.size());
        WebElement element = listFollowings.get(index);

        String followingLink = element.getAttribute("href");
        Reporter.log("following name: " + followingLink);
        click("click follower", element);
    }

}
