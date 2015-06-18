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
 * date: 16.06.2015
 * time: 16:22
 */
public class AllFollowersPage extends BasePage {
    private By followerLinkCount = By.xpath("//div[contains(@id,'stream-item-user')]/div/a");

    public String clickUser() {
        waitForElementVisible(TimeoutSeconds, followerLinkCount);
        List<WebElement> listFollowers = getDriver().findElements(followerLinkCount);
        Reporter.log("Number of followers: " + listFollowers.size());
        Random rand = new Random();
        int index = rand.nextInt(listFollowers.size());
        WebElement element = listFollowers.get(index);

        String followerLink = element.getAttribute("href");
        Reporter.log("follower name: " + followerLink);
        click("go to follower page", element);
        return followerLink;
    }

}
