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
public class MakeFollowingReTweetTest extends BaseTest {
    GeneralActions generalActions;

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
        generalActions.openFollowPage();
        WebElement element = generalActions.getTweetForRetweet();


        int retweetBeforeCount = PageControls.getFollowPage().getNumberOfReTweets("try to find retweet count",element, PageControls.getFollowPage().getReTweetCountLocator());
        String tweetLinkBefore = PageControls.getFollowPage().getTweetLink("try to get tweet link",element, PageControls.getFollowPage().getTweetDateLocator());
        String dateBefore = PageControls.getFollowPage().getTweetDate(element, PageControls.getFollowPage().getTweetDateLocator());
        retweetBeforeCount = generalActions.makeRetweet(element, tweetLinkBefore, retweetBeforeCount);

        PageControls.getMainPage().openMainPage();

        generalActions.goToMyTweetsPage();
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

    @Test(dependsOnMethods = {"reTweetFollowingTest"})
    public void logoutTest() {
        generalActions.logout();
    }
}