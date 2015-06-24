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
public class MakeTweetTest extends BaseTest {
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
        String resDirPath = ".." + File.separatorChar + ".." + File.separatorChar;
        //idea
        //String resDirPath = "";
        return ExcelReader.getTableArray(resDirPath + "resources" + File.separator + "Credentials.xls", "CredentialChrome", "User1-2");
    }

    @Test(dataProvider = "getUserData")
    public void loginTest(String login, String pass, String userName) {
        generalActions.login(login, pass, userName);
    }

    @Test(dependsOnMethods = "loginTest", enabled = true)
    public void sendMessageTest() {
        tweetsBeforeCount = generalActions.getNumberOfTweetsOnMainPage();
        Reporter.log("Number of tweets before try to send new tweet: " + tweetsBeforeCount);
        generalActions.sendMessage("Hello World! [" + FORMAT.format(System.currentTimeMillis()) + "]");
        generalActions.ReloadPage("tweets");
        tweetsAfterCount = generalActions.getNumberOfTweetsOnMainPage();
        Reporter.log("Number of tweets after try to send new tweet: " + tweetsAfterCount);
        Assert.assertEquals(tweetsAfterCount, tweetsBeforeCount + 1);
    }

    @Test(dependsOnMethods = {"sendMessageTest"})
    public void logoutTest() {
        generalActions.logout();
    }

}
