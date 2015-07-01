package com.twitter.actions;

import com.twitter.Controls.PageControls;
import com.twitter.base.BaseAction;
import com.twitter.utils.Reporter;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.time.LocalDate;
import java.util.List;

/**
 * Created
 * by:   Admin
 * dateOfTheTweet: 11.06.2015
 * time: 16:40
 */
public class GeneralActions extends BaseAction {

    /**
     *  Action tries to login to site with given data
     * @param login - String with login data
     * @param password - String with password data
     * @param userName - String with user name data
     */
    public void login(String login, String password, String userName) {
        Reporter.log("ACTION START:+ login on twitter");
        PageControls.getLoginPage().openMainPage();
        PageControls.getLoginPage().waitForLoginPageLoad();
        PageControls.getLoginPage().typeLogin(login);
        PageControls.getLoginPage().typePassword(password);
        PageControls.getLoginPage().clickLoginButton();
        Assert.assertEquals(PageControls.getMainPage().getCurrentUserName(), userName, "ERROR: login failed");
    }

    /**
     * Action tries to logout from site
     */
    public void logout() {
        Reporter.log("ACTION START: logout from twitter");
        PageControls.getMainPage().openMainPage();
        PageControls.getMainPage().waitForMenuLoad();
        WebElement element = PageControls.getMainPage().getUserMenuButton();
        PageControls.getMainPage().setAttributeForElement(element);
        PageControls.getMainPage().clickLogoutButton();
        PageControls.getLogoutPage().waitForLogoutComplete();
    }

    /**
     * Action tries to send new tweet
     * @param message - String with new message which will be send
     */
    public void sendMessage(String message) {
        Reporter.log("ACTION START: send new tweet");
        PageControls.getMainPage().clickMakeNewTweetButton();
        PageControls.getMainPage().waitForNewTweetWindow();
        PageControls.getNewMessagePage().waitForTextField();
        PageControls.getNewMessagePage().typeNewTweet(message);
        PageControls.getNewMessagePage().clickTweetButton();
        PageControls.getMainPage().waitForNewTweetWindowDisappear();
        PageControls.getMainPage().waitForMessageSendConfirm();
    }

    /**
     * Action tries follow someone on twitter
     */
    public void followSomeoneOnTwitter() {
        Reporter.log("ACTION START: follow someone on twitter");
        /*followPage.waitForFollowButton();
        followPage.followButtonClick();*/

        PageControls.getMainPage().waitForSmallFollowButton();
        PageControls.getMainPage().clickSmallFollowButton();
    }

    /**
     * Action tries to open follow page
     * @return - String with link to follow page
     */
    public String openFollowPage() {
        Reporter.log("ACTION START: open follow page");
        List<WebElement> usersList = PageControls.getAllFollowPage().getListOfAllUser();
        WebElement user = PageControls.getAllFollowPage().selectRandomUser(usersList);
        String linkBefore = PageControls.getAllFollowPage().getFollowLink(user);
        PageControls.getAllFollowPage().clickUserLink(user);
        String link = PageControls.getFollowPage().getFollowLink();
        Assert.assertEquals(linkBefore, link, "ERROR: wrong user link open");
        return link;
    }

    /**
     * Action tries  get tweet available for retweet from page with timelimit
     * @return - WebElement contains tweet with proper date and available for retweet
     */
    public WebElement getTweetForRetweet() {
        int scrollCoef = 3;
        int scrollDelta = 2000;
        List<WebElement> elements;
        WebElement element = null;
        long startTime = System.currentTimeMillis();

        Reporter.log("ACTION START: trying get tweets from page with timelimit");
        do {
            try {
                Reporter.log("trying get tweets from page");
                PageControls.getFollowPage().mouseScrollWithJS(0, scrollCoef * scrollDelta);
                elements = PageControls.getFollowPage().getAllTweetsOnPage(PageControls.getFollowPage().getTweetsOnPageLocator());
                Reporter.log("Number of tweets on follow page: " + elements.size());
                elements = PageControls.getFollowPage().getListOfTweetsWithProperDate(elements, LocalDate.now());
                Reporter.log("Number of tweets with proper date on follow page: " + elements.size());
                element = PageControls.getFollowPage().getTweetForRetweet(elements);
                scrollCoef++;
            } catch (StaleElementReferenceException ex) {
                Reporter.log("EXCEPTION: " + ex.getMessage());
            }
        }
        while (element == null && (System.currentTimeMillis() - startTime) < PageControls.getMainPage().TimeoutSeconds * 1000);
        Assert.assertNotNull(element, "ERROR: element not found");
        return element;
    }

    /**
     * Action tries to make retweet, if necessary navigate to tweets page get count of retweets and then make retweet
     * @param element - tweet with proper date available for retweet
     * @param tweetLink - use to navigate to tweet page if necessary
     * @param retweetCount - in param if == -1 need to navigate to tweet page to get retweets count, else return unchanged
     * @return count of retweets after retweet made
     */
    public int makeRetweet(WebElement element, String tweetLink, int retweetCount) {
        Reporter.log("ACTION START: trying make retweet");
        if (retweetCount == -1) {
            driver.get(tweetLink);
            retweetCount = PageControls.getTweetPage().getCountOfReTweets();
            PageControls.getTweetPage().waitForRetweetButton();
            PageControls.getTweetPage().clickReTweetButton();
            PageControls.getReTweetConfirmPage().clickReTweetConfirmButton();
            return retweetCount;
        } else {
            WebElement button = PageControls.getFollowPage().getReTweetButton(element);
            PageControls.getFollowPage().scrollToElementWithJS(button);
            PageControls.getFollowPage().clickRetweetButton(button);
            PageControls.getReTweetConfirmPage().waitForRetweetButton();
            PageControls.getReTweetConfirmPage().clickReTweetConfirmButton();
            return retweetCount;
        }
    }

    /**
     * Action tries to get webelement and then click it to navigate my tweets page
     */
    public void goToMyTweetsPage() {
        Reporter.log("ACTION START: go to all my tweets page");
        WebElement element = PageControls.getAllMyTweets().getMyTweetLink();
        PageControls.getAllMyTweets().clickMyTweetLink(element);
    }

    /**
     * Action tries to get retweet element on my all tweets page
     * @param tweetLink - String with tweet link. need to find tweet in collection by link
     * @return WebElement with retweet from my page
     */
    public WebElement getReTweetElementOnMyAllTweetsPage(String tweetLink) {
        Reporter.log("ACTION START: trying get retweet element on my all tweets page");
        List<WebElement> elements = PageControls.getAllMyTweets().getAllTweetsOnPage(PageControls.getAllMyTweets().getAllTweetItemsOnPageLocator());
        return PageControls.getAllMyTweets().getTweetByLink(elements, tweetLink, PageControls.getAllMyTweets().getTweetDateLocator());
    }

    /**
     * Action tries to get retweet element on follow page
     * @param tweetLink - String with tweet link. need to find tweet in collection by link
     * @return WebElement with retweet from my page
     */
    public WebElement getReTweetElementOnFollowPage(String tweetLink) {
        Reporter.log("ACTION START: trying get retweet element on follow page");
        List<WebElement> elements = PageControls.getFollowPage().getAllTweetsOnPage(PageControls.getFollowPage().getTweetsOnPageLocator());
        return PageControls.getFollowPage().getTweetByLink(elements, tweetLink, PageControls.getFollowPage().getTweetDateLocator());
    }

    /**
     * Action tries to get tweet from my page with timelimit by tweet link
     * @param tweetLink - String with tweet link. need to find tweet in collection by link
     * @return WebElement with retweet from my page
     */
    public WebElement getTweetForDelete(String tweetLink) {
        int scrollCoef = 3;
        int scrollDelta = 2000;
        List<WebElement> elements;
        WebElement element = null;
        long startTime = System.currentTimeMillis();

        Reporter.log("ACTION START: trying get tweet from my page with timelimit");
        do {
            try {
                PageControls.getMainPage().mouseScrollWithJS(0, scrollCoef * scrollDelta);
                elements = PageControls.getAllMyTweets().getAllTweetsOnPage(PageControls.getAllMyTweets().getAllTweetItemsOnPageLocator());
                Reporter.log("Number of tweets on all my tweets page: " + elements.size());
                element = PageControls.getAllMyTweets().getTweetByLink(elements, tweetLink, PageControls.getAllMyTweets().getTweetDateLocator());
                scrollCoef++;
            } catch (StaleElementReferenceException ex) {
                Reporter.log("EXCEPTION: " + ex.getMessage());
            }
        }
        while (element == null && (System.currentTimeMillis() - startTime) < PageControls.getMainPage().TimeoutSeconds * 1000);
        Assert.assertNotNull(element, "ERROR: element not found");
        return element;
    }

    /**
     * Action tries to remove retweet, if necessary navigate to tweets page get count of retweets and then remove retweet
     * @param element - tweet with proper date available for retweet
     * @param tweetLink - use to navigate to tweet page if necessary
     * @param retweetCount - in param if ==-1 need to navigate to tweet page to get retweets count, else return unchanged
     * @return count of retweets after remove retweet
     */
    public int removeReTweet(WebElement element, String tweetLink, int retweetCount) {
        Reporter.log("ACTION START: trying remove retweet");
        if (retweetCount == -1) {
            driver.get(tweetLink);
            retweetCount = PageControls.getTweetPage().getCountOfReTweets();
            PageControls.getTweetPage().waitForCancelRetweetButton();
            PageControls.getTweetPage().clickCancelReTweetButton();
            return retweetCount;
        } else {
            WebElement button = PageControls.getAllMyTweets().getCancelReTweetButton(element);
            PageControls.getFollowPage().scrollToElementWithJS(button);
            PageControls.getAllMyTweets().clickRemoveReTweetButton(button);
            return retweetCount;
        }
    }

    /**
     * Action tries to check is tweet is still on the page
     * @param pageType - search depends from this param. could search on different pages
     * @param tweetLink - use to find tweet in collection by link
     * @return true - if tweet is still on the page, false - if there is no tweet on the page
     */
    public boolean isTweetStillOnPage(String pageType, String tweetLink) {
        Reporter.log("ACTION START: trying to find tweet on " + pageType);
        By locator = null;
        if (pageType == "myPage") {
            locator = PageControls.getAllMyTweets().getTweetDateLocator();
        } else if (pageType == "followPage") {
            locator = PageControls.getFollowPage().getTweetDateLocator();
        }

        List<WebElement> elements = PageControls.getAllMyTweets().getAllTweetsOnPage(PageControls.getAllMyTweets().getAllTweetItemsOnPageLocator());
        if (PageControls.getAllMyTweets().getTweetByLink(elements, tweetLink, locator) != null) {
            return true;
        }
        return false;
    }

}