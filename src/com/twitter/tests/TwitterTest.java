package com.twitter.tests;

import com.twitter.actions.GeneralActions;
import com.twitter.base.BaseTest;
import com.twitter.utils.ExcelReader;
import com.twitter.utils.Reporter;
import com.twitter.utils.UserData;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
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

    @Test
    public void loginTest() {
        String[][] userData = ExcelReader.getTableArray("resources" + File.separator + "Credentials.xls", "CredentialChrome", "User1-2");
        UserData user = new UserData(userData[0][0], userData[0][1], userData[0][2]);
        generalActions.login(user.getLogin(), user.getPassword(), user.getUserName());
    }

    @Test(dependsOnMethods = "loginTest")
    public void sendMessageTest() {
        tweetsBeforeCount = generalActions.getNumberOfTweets();
        Reporter.log("Number of tweets before try to send new tweet: " + tweetsBeforeCount);
        generalActions.sendMessage("Hello World! [" + FORMAT.format(System.currentTimeMillis()) + "]");
        generalActions.ReloadPage("tweets");
        tweetsAfterCount = generalActions.getNumberOfTweets();
        Reporter.log("Number of tweets after try of send new tweet: " + tweetsAfterCount);
        Assert.assertEquals(tweetsAfterCount, tweetsBeforeCount + 1);
    }

    @Test(dependsOnMethods = "loginTest")
    public void followTest() {
        followPersonBeforeCount = generalActions.getNumberOfFollowPersons();
        Reporter.log("Number of persons i'm following before try to follow someone new: " + followPersonBeforeCount);
        generalActions.followSomeoneOnTwitter();
        generalActions.ReloadPage("follows");
        followPersonAfterCount = generalActions.getNumberOfFollowPersons();
        Reporter.log("Number of persons i'm following after try to follow someone new: " + followPersonAfterCount);
        Assert.assertEquals(followPersonAfterCount, followPersonBeforeCount + 1);
    }

    @Test(dependsOnMethods = {"sendMessageTest", "followTest"})
    public void logoutTest() {
        generalActions.logout();
    }
}
