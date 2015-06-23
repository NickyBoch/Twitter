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
public class MakeFollowingReTweetTest extends BaseTest {
    GeneralActions generalActions;
    int tweetsBeforeCount, tweetsAfterCount;
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
        return ExcelReader.getTableArray(resDirPath + "resources" + File.separator + "Credentials.xls", "CredentialChrome", "User1-2");
    }

    @Test(dataProvider = "getUserData")
    public void loginTest(String login, String pass, String userName) {
        generalActions.login(login, pass, userName);
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

    @Test(dependsOnMethods = {"reTweetFollowingTest"})
    public void logoutTest() {
        generalActions.logout();
    }
}
