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
    private By followLinkCount = By.xpath("//div[contains(@id,'stream-item-user')]/div/a");
    private By followingCount = By.xpath("//li[contains(@class,'ProfileNav-item ProfileNav-item--following is-active')]/a/span[2]");

    /**
     * get count of following by me persons
     * @return - int - count of my following persons
     */
    public int getFollowingCount() {
        Reporter.log("get following count");
        return Integer.parseInt(getTextValueFromElement(followingCount));
    }

    /**
     * get list of all following person or followers
     * @return - List<WebElement> - collection of elements with all following or followers
     */
    public List<WebElement> getListOfAllUser() {
        waitForElementVisible(TimeoutSeconds, followLinkCount);
        return getDriver().findElements(followLinkCount);
    }

    /**
     * select random element contains user info from collection of elements with users info
     * @param listFollowers - List<WebElement> - collection of elements with users
     * @return - WebElement - contains user info
     */
    public WebElement selectRandomUser(List<WebElement> listFollowers) {
        Random rand = new Random();
        int index = rand.nextInt(listFollowers.size());
        return listFollowers.get(index);
    }

    /**
     * get link of follow user
     * @param element - WebElement - contains all info about user
     * @return  - String - with link to user page
     */
    public String getFollowLink(WebElement element) {
        Reporter.log("Get follow link");
        return getElementAttribute(element, "href");
    }

    /**
     * click on element contains link to my follow page
     * @param element - WebElement - contains link to follow page
     */
    public void clickUserLink(WebElement element) {
        click("go to follow page", element);
    }

}
