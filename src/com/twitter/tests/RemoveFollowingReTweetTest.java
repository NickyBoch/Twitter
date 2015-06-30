package com.twitter.tests;

import com.twitter.Controls.PageControls;
import com.twitter.actions.GeneralActions;
import com.twitter.base.BaseTest;
import com.twitter.utils.ExcelReader;
import com.twitter.utils.Reporter;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;

/**
 * Created by Admin on 6/23/2015.
 */
public class RemoveFollowingReTweetTest extends BaseTest {
    GeneralActions generalActions;
    String tweetLink, followLink;

    @BeforeClass
    public void setUp() {
        generalActions = new GeneralActions(driver);
    }

    @DataProvider
    private Object[][] getUserData() {
        //for run in ant uncomment next line
        String resDirPath = ".." + File.separatorChar + ".." + File.separatorChar;
        //for run in idea uncomment next line
        //String resDirPath = "";
        return ExcelReader.getTableArray(resDirPath + "resources" + File.separator + "Credentials.xls", "CredentialChrome", "User1-2");
    }

    @Test(dataProvider = "getUserData")
    public void loginTest(String login, String pass, String userName) {
        generalActions.login(login, pass, userName);
    }

    @Test(dependsOnMethods = "loginTest", enabled = true)
    public void reTweetFollowingTest() {
        int tweetsBeforeCount = PageControls.getMainPage().getCountOfTweetsOnMainPage();
        Reporter.log("Number of tweets before try to retweet: " + tweetsBeforeCount);
        PageControls.getMainPage().clickFollowingLink();
        followLink = generalActions.openFollowPage();
        WebElement element = generalActions.getTweetForRetweet();

        int retweetBeforeCount = PageControls.getFollowPage().getNumberOfReTweets("try to find retweet count",element, PageControls.getFollowPage().getReTweetCountLocator());
        String tweetLinkBefore = PageControls.getFollowPage().getTweetLink("try to get tweet link",element, PageControls.getFollowPage().getTweetDateLocator());
        String dateBefore = PageControls.getFollowPage().getTweetDate(element, PageControls.getFollowPage().getTweetDateLocator());
        retweetBeforeCount = generalActions.makeRetweet(element, tweetLinkBefore, retweetBeforeCount);

        PageControls.getMainPage().openMainPage();

        generalActions.goToMyTweetsPage();
        //PageControls.getMainPage().waitPageLoadWithJS("wait page load complete", PageControls.getMainPage().ExtrasmallTimeoutSeconds);
        driver.navigate().refresh();
        int tweetsAfterCount = PageControls.getAllMyTweets().getCountOfAllTweetsOnMyPage();

        WebElement elem = generalActions.getReTweetElementOnMyAllTweetsPage(tweetLinkBefore);
        Assert.assertNotNull(elem, "assert retweet exist");

        int retweetAfterCount = PageControls.getAllMyTweets().getNumberOfReTweets("try to find retweet count",elem, PageControls.getAllMyTweets().getReTweetCountLocator());
        String tweetLinkAfter = PageControls.getAllMyTweets().getTweetLink("try to get tweet link",elem, PageControls.getAllMyTweets().getTweetDateLocator());
        String dateAfterOnMyPage = PageControls.getAllMyTweets().getTweetDate(elem, PageControls.getAllMyTweets().getTweetDateLocator());
        if (retweetAfterCount == -1) {
            retweetAfterCount = PageControls.getTweetPage().getNumberOfReTweets(tweetLinkAfter);
        }

        WebElement elem1 = generalActions.getReTweetElementOnFollowPage(tweetLinkAfter);
        String dateAfterOnFollowPage = PageControls.getFollowPage().getTweetDate(elem1, PageControls.getFollowPage().getTweetDateLocator());
        tweetLink = tweetLinkAfter;

        Reporter.log("-->retweet count BEFORE: " + retweetBeforeCount);
        Reporter.log("-->retweet link BEFORE: " + tweetLinkBefore);
        Reporter.log("-->retweet dateBefore BEFORE: " + dateBefore);
        Reporter.log("-->retweet count AFTER: " + retweetAfterCount);
        Reporter.log("-->retweet link AFTER: " + tweetLinkAfter);
        Reporter.log("-->retweet dateBefore AFTER ON MY PAGE: " + dateAfterOnMyPage);
        Reporter.log("-->retweet dateBefore AFTER ON FOLLOW PAGE: " + dateAfterOnFollowPage);

        Assert.assertEquals(tweetsAfterCount, tweetsBeforeCount + 1, "assert my tweet count incremented correctly");
        Assert.assertEquals(retweetAfterCount, retweetBeforeCount + 1, "assert retweet count incremented correctly");
        Assert.assertEquals(dateBefore, dateAfterOnMyPage, "assert retweet date still the same on my page after retweet");
        Assert.assertEquals(dateBefore, dateAfterOnFollowPage, "assert retweet date still the same on follow page after retweet");
    }

    @Test(dependsOnMethods = {"loginTest", "reTweetFollowingTest"}, enabled = true)
    public void removeReTweet() {
        String myPage = "myPage";
        String followPage = "followPage";

        PageControls.getMainPage().openMainPage();
        generalActions.goToMyTweetsPage();
        int myTweetsBeforeCount = PageControls.getAllMyTweets().getCountOfAllTweetsOnMyPage();
        WebElement element = generalActions.getTweetForDelete(tweetLink);

        int retweetBeforeCount = PageControls.getAllMyTweets().getNumberOfReTweets("try to find retweet count",element, PageControls.getAllMyTweets().getReTweetCountLocator());
        String tweetLinkBefore = PageControls.getAllMyTweets().getTweetLink("try to get tweet link",element, PageControls.getAllMyTweets().getTweetDateLocator());
        String dateBefore = PageControls.getAllMyTweets().getTweetDate(element, PageControls.getAllMyTweets().getTweetDateLocator());
        retweetBeforeCount = generalActions.removeReTweet(element, tweetLinkBefore, retweetBeforeCount);
        PageControls.getMainPage().openMainPage();
        generalActions.goToMyTweetsPage();
        driver.navigate().refresh();
        Assert.assertFalse(generalActions.isTweetStillOnPage(myPage, tweetLink), "assert retweet still exists on my page");
        int myTweetsAfterCount = PageControls.getAllMyTweets().getCountOfAllTweetsOnMyPage();
        Assert.assertEquals(myTweetsAfterCount, myTweetsBeforeCount - 1,"assert retweets count on my page decremented properly");


        Reporter.log("open follow page: " + followLink);
        driver.get(followLink);
        Assert.assertTrue(generalActions.isTweetStillOnPage(followPage, tweetLink), "assert tweet still on follower page");
        element = generalActions.getReTweetElementOnFollowPage(tweetLink);

        int retweetAfterCount = PageControls.getAllMyTweets().getNumberOfReTweets("try to find retweet count",element, PageControls.getAllMyTweets().getReTweetCountLocator());
        String tweetLinkAfter = PageControls.getFollowPage().getTweetLink("try to get tweet link",element, PageControls.getFollowPage().getTweetDateLocator());
        String dateAfter = PageControls.getFollowPage().getTweetDate(element, PageControls.getFollowPage().getTweetDateLocator());


        if (retweetAfterCount == -1) {
            retweetAfterCount = PageControls.getTweetPage().getNumberOfReTweets(tweetLinkAfter);
        }

        Reporter.log("-->tweet count on my page BEFORE DELETE retweet: " + myTweetsBeforeCount);
        Reporter.log("-->retweet link BEFORE: " + tweetLinkBefore);
        Reporter.log("-->retweet dateBefore BEFORE: " + dateBefore);
        Reporter.log("-->retweet count BEFORE: " + retweetBeforeCount);
        Reporter.log("-->tweet count on my page AFTER DELETE retweet: " + myTweetsAfterCount);
        Reporter.log("-->retweet link AFTER: " + tweetLinkAfter);
        Reporter.log("-->retweet dateBefore AFTER: " + dateAfter);
        Reporter.log("-->retweet count AFTER: " + retweetAfterCount);

        Assert.assertTrue(dateBefore.equals(dateAfter), "assert date of tweet on follow page still the same");
        Assert.assertEquals(retweetAfterCount, retweetBeforeCount - 1, "assert retweets count on follow page decremented properly");
    }

    @Test(dependsOnMethods = {"removeReTweet"}, alwaysRun = true)
    public void logoutTest() {
        generalActions.logout();
    }

}
