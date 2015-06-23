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
 * Created
 * by:   Admin
 * date: 11.06.2015
 * time: 17:32
 */
public class TwitterTest extends BaseTest {
    GeneralActions generalActions;
    int tweetsBeforeCount, tweetsAfterCount, followPersonBeforeCount, followPersonAfterCount;
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("H:mm:ss:SSS");

    @BeforeClass
    public void setUp() {
        generalActions = new GeneralActions(driver);
    }

    @DataProvider
    private Object[][] getUserData() {
        //ant
        //String resDirPath = ".." + File.separatorChar + ".." + File.separatorChar;
        //idea
        String resDirPath = "";
        String[][] userData = ExcelReader.getTableArray(resDirPath + "resources" + File.separator + "Credentials.xls", "CredentialChrome", "User1-2");
        return userData;
    }

    @Test(dataProvider = "getUserData")
    public void loginTest(String login, String pass, String userName) {
        generalActions.login(login, pass, userName);
    }

    @Test(dependsOnMethods = "loginTest", enabled = false)
    public void sendMessageTest() {
        tweetsBeforeCount = generalActions.getNumberOfTweetsOnMainPage();
        Reporter.log("Number of tweets before try to send new tweet: " + tweetsBeforeCount);
        generalActions.sendMessage("Hello World! [" + FORMAT.format(System.currentTimeMillis()) + "]");
        generalActions.ReloadPage("tweets");
        tweetsAfterCount = generalActions.getNumberOfTweetsOnMainPage();
        Reporter.log("Number of tweets after try to send new tweet: " + tweetsAfterCount);
        Assert.assertEquals(tweetsAfterCount, tweetsBeforeCount + 1);
    }

    @Test(dependsOnMethods = "loginTest", enabled = false)
    public void followTest() {
        followPersonBeforeCount = generalActions.getNumberOfFollowPersons();
        Reporter.log("Number of persons i'm following before try to follow someone new: " + followPersonBeforeCount);
        generalActions.followSomeoneOnTwitter();
        generalActions.ReloadPage("follows");
        followPersonAfterCount = generalActions.getNumberOfFollowPersons();
        Reporter.log("Number of persons i'm following after try to follow someone new: " + followPersonAfterCount);
        Assert.assertEquals(followPersonAfterCount, followPersonBeforeCount + 1);
    }

    @Test(dependsOnMethods = "loginTest", enabled = false)
    public void reTweetFollowerTest() {
        tweetsBeforeCount = generalActions.getNumberOfTweetsOnMainPage();
        Reporter.log("Number of tweets before try to retweet: " + tweetsBeforeCount);
        generalActions.openFollowersPage();
        generalActions.chooseFollower();
        generalActions.makeReTweet();
        generalActions.goToMainPage();
        generalActions.ReloadPage("tweets");
        generalActions.isReTweeted();
        tweetsAfterCount = generalActions.getNumberOfTweetsOnMainPage();
        Reporter.log("Number of tweets after try to retweet: " + tweetsAfterCount);
        Assert.assertEquals(tweetsAfterCount, tweetsBeforeCount + 1);
        generalActions.isReTweetedCounterIncrementCorrectly();
        Assert.assertTrue(generalActions.isDateOnFollowerPageStillTheSame());
    }

    @Test(dependsOnMethods = "loginTest", enabled = true)
    public void reTweetFollowingTest() {
        tweetsBeforeCount = generalActions.getNumberOfTweetsOnMainPage();
        Reporter.log("Number of tweets before try to retweet: " + tweetsBeforeCount);
        generalActions.openFollowingPage();
        generalActions.chooseFollower();
        generalActions.makeReTweet();
        generalActions.goToMainPage();
        generalActions.goToMyTweetsPage();
        generalActions.isReTweeted();
        tweetsAfterCount = generalActions.getNumberOfTweetsOnAllMyTweetsPage();
        Reporter.log("Number of tweets after try to retweet: " + tweetsAfterCount);
        Assert.assertEquals(tweetsAfterCount, tweetsBeforeCount + 1);
        generalActions.isReTweetedCounterIncrementCorrectly();
        generalActions.isDateOnFollowerPageStillTheSame();
        generalActions.openMainPage();
    }

    @Test(dependsOnMethods = {/*"sendMessageTest", "followTest","reTweetFollowerTest",*/"reTweetFollowingTest"})
    public void logoutTest() {
        generalActions.logout();
    }

}
