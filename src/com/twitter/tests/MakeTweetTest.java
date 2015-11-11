package com.twitter.tests;

import com.twitter.Controls.ActionControls;
import com.twitter.Controls.PageControls;
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
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("H:mm:ss:SSS");

    @BeforeClass
/*    public void setUp() {
        generalActions = new GeneralActions(driver);
    }*/

    @DataProvider
    private Object[][] getUserData() {
        //for run in ant uncomment next line
        //String resDirPath = ".." + File.separatorChar + ".." + File.separatorChar;
        //for run in idea uncomment next line
        //String resDirPath = "";

        String resDirPath = System.getProperty("res.dir");

        return ExcelReader.getTableArray(resDirPath + File.separator + "Credentials.xls", "CredentialChrome", "User1-2");
    }

    @Test(dataProvider = "getUserData", description = "test try to login to page")
    public void loginTest(String login, String pass, String userName) {
        ActionControls.getGeneralAction().login(login, pass, userName);
    }

    @Test(dependsOnMethods = "loginTest", enabled = true)
    public void sendMessageTest() {
        int tweetsBeforeCount = PageControls.getMainPage().getCountOfTweetsOnMainPage();
        Reporter.log("Number of tweets before try to send new tweet: " + tweetsBeforeCount);
        ActionControls.getGeneralAction().sendMessage("Hello World! [" + FORMAT.format(System.currentTimeMillis()) + "]");
        PageControls.getMainPage().clickMyPageLink();
        int tweetsAfterCount = PageControls.getAllMyTweets().getCountOfAllTweetsOnMyPage();
        Reporter.log("Number of tweets after try to send new tweet: " + tweetsAfterCount);
        Assert.assertEquals(tweetsAfterCount, tweetsBeforeCount + 1, "ERROR: count of tweets incremented incorrectly");
    }

    @Test(dependsOnMethods = {"sendMessageTest"}, alwaysRun = true, description = "test try to logout from site")
    public void logoutTest() {
        ActionControls.getGeneralAction().logout();
    }

}