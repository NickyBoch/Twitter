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
public class AllFollowPage extends BasePage {
    private By followerLinkCount = By.xpath("//div[contains(@id,'stream-item-user')]/div/a");
    private By followCount = By.xpath("//li[contains(@class,'ProfileNav-item ProfileNav-item--following is-active')]/a/span[2]");

    public int getFollowCount() {
        Reporter.log("get following count");
        return Integer.parseInt(getTextValueFromElement(followCount));
    }

    public List<WebElement> getListOfAllUser() {
        waitForElementVisible(TimeoutSeconds, followerLinkCount);
        return getDriver().findElements(followerLinkCount);
    }

    public WebElement selectRandomUser(List<WebElement> listFollowers) {
        Random rand = new Random();
        int index = rand.nextInt(listFollowers.size());
        return listFollowers.get(0);
    }

    public String getFollowLink(WebElement element) {
        Reporter.log("Get follow link");
        return getElementAttribute(element, "href");
    }

    public void clickUserLink(WebElement element) {
        click("go to follower page", element);
    }

}
