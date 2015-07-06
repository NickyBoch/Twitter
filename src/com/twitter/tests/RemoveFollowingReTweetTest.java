package com.twitter.tests;

import com.twitter.Controls.ActionControls;
import com.twitter.Controls.PageControls;
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
    //GeneralActions generalActions;
    String tweetLink, followLink;

    @BeforeClass
/*    public void setUp() {
        generalActions = new GeneralActions(driver);
    }*/

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
        ActionControls.getGeneralAction().login(login, pass, userName);
    }

    @Test(dependsOnMethods = "loginTest", enabled = true)
    public void reTweetFollowingTest() {
        PageControls.getMainPage().waitForMenuLoad();
        ActionControls.getGeneralAction().goToSettingsPage();
        String currentLang = PageControls.getSettingsPage().getCurrentUILanguage();
        PageControls.getMainPage().openMainPage();
        PageControls.getMainPage().waitForMenuLoad();
        //
        int tweetsBeforeCount = PageControls.getMainPage().getCountOfTweetsOnMainPage();
        Reporter.log("Number of tweets before try to retweet: " + tweetsBeforeCount);
        PageControls.getMainPage().clickFollowingLink();
        followLink = ActionControls.getGeneralAction().openFollowPage("following");
        WebElement element = ActionControls.getGeneralAction().getTweetForRetweet(currentLang);

        int retweetBeforeCount = PageControls.getFollowPage().getCountOfReTweets(element, PageControls.getFollowPage().getReTweetCountLocator());
        String tweetLinkBefore = PageControls.getFollowPage().getTweetLink(element, PageControls.getFollowPage().getTweetDateLocator());
        String dateBefore = PageControls.getFollowPage().getTweetDate(element, PageControls.getFollowPage().getTweetDateLocator());
        retweetBeforeCount = ActionControls.getGeneralAction().makeRetweet(element, tweetLinkBefore, retweetBeforeCount);

        PageControls.getMainPage().openMainPage();

        ActionControls.getGeneralAction().goToMyTweetsPage();
        //PageControls.getMainPage().waitPageLoadWithJS("wait page load complete", PageControls.getMainPage().ExtrasmallTimeoutSeconds);
        driver.navigate().refresh();
        int tweetsAfterCount = PageControls.getAllMyTweets().getCountOfAllTweetsOnMyPage();

        WebElement elem = ActionControls.getGeneralAction().getReTweetElementOnMyAllTweetsPage(tweetLinkBefore);
        Assert.assertNotNull(elem, "assert retweet exist");

        int retweetAfterCount = PageControls.getAllMyTweets().getCountOfReTweets(elem, PageControls.getAllMyTweets().getReTweetCountLocator());
        String tweetLinkAfter = PageControls.getAllMyTweets().getTweetLink(elem, PageControls.getAllMyTweets().getTweetDateLocator());
        String dateAfterOnMyPage = PageControls.getAllMyTweets().getTweetDate(elem, PageControls.getAllMyTweets().getTweetDateLocator());
        if (retweetAfterCount == -1) {
            driver.get(tweetLinkAfter);
            retweetAfterCount = PageControls.getTweetPage().getCountOfReTweets();
        }

        WebElement elem1 = ActionControls.getGeneralAction().getReTweetElementOnFollowPage(tweetLinkAfter);
        String dateAfterOnFollowPage = PageControls.getFollowPage().getTweetDate(elem1, PageControls.getFollowPage().getTweetDateLocator());
        tweetLink = tweetLinkAfter;

/*        Reporter.log("-->retweet count BEFORE: " + retweetBeforeCount);
        Reporter.log("-->retweet link BEFORE: " + tweetLinkBefore);
        Reporter.log("-->retweet dateBefore BEFORE: " + dateBefore);
        Reporter.log("-->retweet count AFTER: " + retweetAfterCount);
        Reporter.log("-->retweet link AFTER: " + tweetLinkAfter);
        Reporter.log("-->retweet dateBefore AFTER ON MY PAGE: " + dateAfterOnMyPage);
        Reporter.log("-->retweet dateBefore AFTER ON FOLLOW PAGE: " + dateAfterOnFollowPage);*/

        Assert.assertEquals(tweetsAfterCount, tweetsBeforeCount + 1, "ERROR: my tweets count incremented incorrectly");
        Assert.assertEquals(retweetAfterCount, retweetBeforeCount + 1, "ERROR: retweet count incremented incorrectly");
        Assert.assertEquals(dateBefore, dateAfterOnMyPage, "ERROR: retweet date on my page changed after retweet");
        Assert.assertEquals(dateBefore, dateAfterOnFollowPage, "ERROR: retweet date on follow page changed after retweet");
    }

    @Test(dependsOnMethods = {"loginTest", "reTweetFollowingTest"}, enabled = true)
    public void removeReTweet() {
        String myPage = "myPage";
        String followPage = "followPage";

        PageControls.getMainPage().openMainPage();
        ActionControls.getGeneralAction().goToMyTweetsPage();
        int myTweetsBeforeCount = PageControls.getAllMyTweets().getCountOfAllTweetsOnMyPage();
        WebElement element = ActionControls.getGeneralAction().getTweetForDelete(tweetLink);

        int retweetBeforeCount = PageControls.getAllMyTweets().getCountOfReTweets(element, PageControls.getAllMyTweets().getReTweetCountLocator());
        String tweetLinkBefore = PageControls.getAllMyTweets().getTweetLink(element, PageControls.getAllMyTweets().getTweetDateLocator());
        String dateBefore = PageControls.getAllMyTweets().getTweetDate(element, PageControls.getAllMyTweets().getTweetDateLocator());
        retweetBeforeCount = ActionControls.getGeneralAction().removeReTweet(element, tweetLinkBefore, retweetBeforeCount);
        PageControls.getMainPage().openMainPage();
        ActionControls.getGeneralAction().goToMyTweetsPage();
        driver.navigate().refresh();
        Assert.assertFalse(ActionControls.getGeneralAction().isTweetStillOnPage(myPage, tweetLink), "ERROR: retweet still exists on my page");
        int myTweetsAfterCount = PageControls.getAllMyTweets().getCountOfAllTweetsOnMyPage();
        Assert.assertEquals(myTweetsAfterCount, myTweetsBeforeCount - 1, "ERROR: retweets count on my page decremented incorrectly");


        Reporter.log("open follow page: " + followLink);
        driver.get(followLink);
        Assert.assertTrue(ActionControls.getGeneralAction().isTweetStillOnPage(followPage, tweetLink), "ERROR: there is no such tweet on follower page");
        element = ActionControls.getGeneralAction().getReTweetElementOnFollowPage(tweetLink);

        int retweetAfterCount = PageControls.getAllMyTweets().getCountOfReTweets(element, PageControls.getAllMyTweets().getReTweetCountLocator());
        String tweetLinkAfter = PageControls.getFollowPage().getTweetLink(element, PageControls.getFollowPage().getTweetDateLocator());
        String dateAfter = PageControls.getFollowPage().getTweetDate(element, PageControls.getFollowPage().getTweetDateLocator());


        if (retweetAfterCount == -1) {
            driver.get(tweetLinkAfter);
            retweetAfterCount = PageControls.getTweetPage().getCountOfReTweets();
        }

        /*
        Reporter.log("-->tweet count on my page BEFORE DELETE retweet: " + myTweetsBeforeCount);
        Reporter.log("-->retweet link BEFORE: " + tweetLinkBefore);
        Reporter.log("-->retweet dateBefore BEFORE: " + dateBefore);
        Reporter.log("-->retweet count BEFORE: " + retweetBeforeCount);
        Reporter.log("-->tweet count on my page AFTER DELETE retweet: " + myTweetsAfterCount);
        Reporter.log("-->retweet link AFTER: " + tweetLinkAfter);
        Reporter.log("-->retweet dateBefore AFTER: " + dateAfter);
        Reporter.log("-->retweet count AFTER: " + retweetAfterCount);
        */

        Assert.assertTrue(dateBefore.equals(dateAfter), "ERROR: date of tweet on follow page changed");
        Assert.assertEquals(retweetAfterCount, retweetBeforeCount - 1, "ERROR: retweets count on follow page decremented incorrectly");
    }

    @Test(dependsOnMethods = {"removeReTweet"}, alwaysRun = true, description = "test try to logout from site")
    public void logoutTest() {
        ActionControls.getGeneralAction().logout();
    }

}
