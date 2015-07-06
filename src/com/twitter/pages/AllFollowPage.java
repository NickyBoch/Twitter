package com.twitter.pages;

import com.twitter.Controls.PageControls;
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
    private By followingCount = By.xpath("//li[contains(@class,'ProfileNav-item ProfileNav-item--following')]/a/span[2]");
    private By followersCount = By.xpath("//li[contains(@class,'ProfileNav-item ProfileNav-item--followers')]/a/span[2]");
    private By followLink =By.xpath("//div[contains(@class,'ProfileCard-content')]/a[contains(@class,'ProfileCard-avatarLink js-nav js-tooltip')]");

    /**
     * get count of following by me persons
     * @return - int - count of my following persons
     */
    public int getFollowingCount() {
        Reporter.log("get following count");
        return Integer.parseInt(getTextValueFromElement(followingCount));
    }

    /**
     * get count of following by me persons
     * @return - int - count of my following persons
     */
    public int getFollowersCount() {
        Reporter.log("get following count");
        return Integer.parseInt(getTextValueFromElement(followersCount));
    }

    /**
     * get list of all following person or followers
     * @return - List<WebElement> - collection of elements with all following or followers
     */
    public List<WebElement> getListOfAllUser() {
        waitForElementVisible(TimeoutSeconds, followLink);
        return getDriver().findElements(followLink);
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
        //WebElement elem = element.findElement(followLink);
        return getElementAttribute(element, "href");
    }

    /**
     * click on element contains link to my follow page
     * @param element - WebElement - contains link to follow page
     */
    public void clickFollowLink(WebElement element) {
        click("click follow link " + getFollowLink(element),element);
        //clickWithJS("click follow link with js "+ getFollowLink(element), element);
    }

    /**
     * get collection of elements of  all followers or following users
     * @param followCount - count of follow or following users from menu
     * @return - List<WebElement> - collection with all elements contains followers or following users
     */
    public List<WebElement> getAllUsers(int followCount) {
        int scrollCoef = 3;
        int scrollDelta = 2000;
        List<WebElement> usersList;
        do {
            PageControls.getAllFollowPage().mouseScrollWithJS(0, scrollCoef * scrollDelta);
            usersList = PageControls.getAllFollowPage().getListOfAllUser();
            scrollCoef++;
        } while (usersList.size() < followCount);
        return usersList;
    }
}
