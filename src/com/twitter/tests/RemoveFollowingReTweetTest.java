package com.twitter.tests;

import com.twitter.actions.GeneralActions;
import com.twitter.base.BaseTest;
import com.twitter.utils.ExcelReader;
import com.twitter.utils.Reporter;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Created by Admin on 6/23/2015.
 */
public class RemoveFollowingReTweetTest extends BaseTest {
    GeneralActions generalActions;
    int myTweetsBeforeCount, myTweetsAfterCount, tweetsBeforeRemoveRetweetCount, tweetsAfterRemoveRetweetCount;
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("H:mm:ss:SSS");

    @BeforeClass
    public void setUp() {
        generalActions = new GeneralActions(driver);
    }

    @DataProvider
    private Object[][] getUserData() {
        //ant
        String resDirPath = ".." + File.separatorChar + ".." + File.separatorChar;
        //idea
        //String resDirPath = "";
        return ExcelReader.getTableArray(resDirPath + "resources" + File.separator + "Credentials.xls", "CredentialChrome", "User1-2");
    }

    @Test(dataProvider = "getUserData")
    public void loginTest(String login, String pass, String userName) {
        generalActions.login(login, pass, userName);
    }

  /*  @Test(dependsOnMethods = "loginTest", enabled = true)
    public void reTweetFollowingTest() {
        myTweetsBeforeCount = generalActions.getNumberOfTweetsOnMainPage();
        Reporter.log("Number of tweets before try to retweet: " + myTweetsBeforeCount);
        generalActions.openFollowingPage();
        generalActions.openFollowPage();
        generalActions.makeReTweet();
        generalActions.goToMainPage();
        generalActions.goToMyTweetsPage();
        Assert.assertTrue(generalActions.isReTweeted());
        Assert.assertTrue(generalActions.isDateSameOnMyAllTweetsPage());
        myTweetsAfterCount = generalActions.getNumberOfTweetsOnAllMyTweetsPage();
        Reporter.log("Number of tweets after try to retweet after retweet: " + myTweetsAfterCount);
        Assert.assertEquals(myTweetsAfterCount, myTweetsBeforeCount + 1);
        generalActions.isReTweetedCounterIncrementCorrectly();
        Assert.assertTrue(generalActions.isDateOnFollowerPageStillTheSame());
        tweetsBeforeRemoveRetweetCount = generalActions.getNumberOfReTweetsOnTweetPage();
        Reporter.log("--->Number of retweets of retweeted tweet: " + tweetsBeforeRemoveRetweetCount);
        generalActions.openMainPage();
    }

    @Test(dependsOnMethods = {"loginTest", "reTweetFollowingTest"}, enabled = true)
    public void removeReTweet() {
        generalActions.goToMyTweetsPage();
        myTweetsBeforeCount = generalActions.getNumberOfTweetsOnAllMyTweetsPage();
        Reporter.log("Number of tweets before try to retweet: " + myTweetsBeforeCount);
        generalActions.removeReTweet();
        Assert.assertFalse(generalActions.isReTweetExistOnMyPage());
        myTweetsAfterCount = generalActions.getNumberOfTweetsOnAllMyTweetsPage();
        Reporter.log("Number of tweets after try to retweet: " + myTweetsAfterCount);
        Assert.assertEquals(myTweetsAfterCount, myTweetsBeforeCount - 1);
        Assert.assertTrue(generalActions.isTweetStillOnFollowingPage());
        Assert.assertTrue(generalActions.isDateOnFollowerPageStillTheSame());
        tweetsAfterRemoveRetweetCount = generalActions.getNumberOfReTweetsOnTweetPage();
        Reporter.log("Number of retweets of retweeted tweet after delete of the retweet: " + tweetsAfterRemoveRetweetCount);
        Assert.assertEquals(tweetsAfterRemoveRetweetCount, tweetsBeforeRemoveRetweetCount - 1);
    }*/

    @Test(dependsOnMethods = {"removeReTweet"})
    public void logoutTest() {
        generalActions.logout();
    }
}
