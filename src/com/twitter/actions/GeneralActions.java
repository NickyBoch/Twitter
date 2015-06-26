package com.twitter.actions;

import com.twitter.Controls.PageControls;
import com.twitter.pages.*;
import com.twitter.utils.Reporter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import java.time.LocalDate;
import java.util.List;

/**
 * Created
 * by:   Admin
 * dateOfTheTweet: 11.06.2015
 * time: 16:40
 */
public class GeneralActions {
    private RemoteWebDriver driver;
    //
    private AllFollowPage allFollowPage;
    private AllMyTweets allMyTweets;
    private FollowPage followPage;
    private LoginPage loginPage;
    private LogoutPage logoutPage;
    private MainPage mainPage;
    private NewTweetDialogPage newMessagePage;
    private ReTweetConfirmPage reTweetConfirmPage;
    private TweetPage tweetPage;


    public GeneralActions(RemoteWebDriver driver) {
        this.driver = driver;
        allFollowPage = new AllFollowPage();
        allMyTweets = new AllMyTweets();
        followPage = new FollowPage();
        loginPage = new LoginPage();
        logoutPage = new LogoutPage();
        mainPage = new MainPage();
        newMessagePage = new NewTweetDialogPage();
        reTweetConfirmPage = new ReTweetConfirmPage();
        tweetPage = new TweetPage();
    }

    public void login(String login, String password, String userName) {
        Reporter.log("login on twitter");
        loginPage.openMainPage();
        loginPage.waitForLoginPageLoad();
        loginPage.typeLogin(login);
        loginPage.typePassword(password);
        loginPage.submitLogin();
        Assert.assertEquals(mainPage.getCurrentUserName(), userName, "assert login successful");
    }

    public void logout() {
        Reporter.log("logout from twitter");
        mainPage.openMainPage();
        mainPage.waitForMenuLoad();
        mainPage.clickUserMenuButton();
        mainPage.clickLogoutButton();
        logoutPage.waitForLogoutComplete();
    }

    public void sendMessage(String message) {
        mainPage.clickNewTweetWindowButton();
        newMessagePage.waitForTextField();
        newMessagePage.typeNewTweet(message);
        newMessagePage.submitMessage();
        mainPage.waitForMessageSendConfirm();
    }

    public void followSomeoneOnTwitter() {
        /*followPage.waitForFollowButton();
        followPage.followButtonClick();*/

        mainPage.waitForSmallFollowButton();
        mainPage.clickSmallFollowButton();
    }

    public void openFollowPage() {
        List<WebElement> usersList = allFollowPage.getListOfAllUser();
        WebElement user = allFollowPage.selectRandomUser(usersList);
        String linkBefore = allFollowPage.getFollowLink(user);
        allFollowPage.clickUserLink(user);
        String link = followPage.getFollowLink();
        Assert.assertEquals(linkBefore, link, "assert correct user link open");
    }

    public WebElement getTweetForRetweet() {
        int scrollCoef = 3;
        int scrollDelta = 2000;
        List<WebElement> elements;
        WebElement element=null;

        do {
            try {
                PageControls.getMainPage().mouseScrollWithJS("scroll page down with js", 0, scrollCoef * scrollDelta);
                elements = PageControls.getFollowPage().getAllTweetsOnPage(PageControls.getFollowPage().getTweetsOnPageLocator());
                Reporter.log("Number of tweets on follow page: " + elements.size());
                elements = PageControls.getFollowPage().getListOfTweetsWithProperDate(elements, LocalDate.now());
                Reporter.log("Number of tweets with proper date on follow page: " + elements.size());
                element = PageControls.getFollowPage().getTweetForRetweet(elements);
                scrollCoef++;
            }
            catch(Exception ex)
            {
                Reporter.log(ex.getMessage());
            }
        } while (element == null);
        return element;
    }

    public int makeRetweet(WebElement element, String tweetLink, int retweetCount) {
        if (retweetCount == -1) {
            retweetCount = tweetPage.getNumberOfReTweets(tweetLink);
            tweetPage.clickReTweetButton();
            //reTweetConfirmPage.waitForElement();
            reTweetConfirmPage.confirmReTweet();
            return retweetCount;
        } else {
            WebElement button=followPage.getReTweetButton(element);
            followPage.scrollToElementWithJS("scroll to element with js", button);
            followPage.clickRetweetButton(button);
            reTweetConfirmPage.waitForElement();
            reTweetConfirmPage.confirmReTweet();
            return retweetCount;
        }
    }

    public void goToMyTweetsPage() {
        String href = allMyTweets.getMyTweetLink();
        allMyTweets.open(href);
    }

    public WebElement getReTweetElementOnMyAllTweetsPage(String tweetLink) {
        List<WebElement> elements = allMyTweets.getAllTweetsOnPage(allMyTweets.getAllTweetItemsOnPageLocator());
        return allMyTweets.getTweetByLink(elements,tweetLink,allMyTweets.getTweetDateLocator());
    }


    public WebElement getReTweetElementOnFollowPage(String tweetLink) {
        List<WebElement> elements = followPage.getAllTweetsOnPage(followPage.getTweetsOnPageLocator());
        return allMyTweets.getTweetByLink(elements,tweetLink,followPage.getTweetDateLocator());
    }


}