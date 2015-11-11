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

/**
 * Created by Admin on 6/23/2015.
 */
public class FollowTest extends BaseTest {


    @BeforeClass
/*    public void setUp() {
        generalActions = new GeneralActions();
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

    @Test(dataProvider = "getUserData", description = "test try to login to site")
    public void loginTest(String login, String pass, String userName) {
        ActionControls.getGeneralAction().login(login, pass, userName);
    }

    @Test(dependsOnMethods = "loginTest", enabled = true, description = "test try to follow someone on twitter")
    public void followTest() {
        int followPersonBeforeCount = PageControls.getMainPage().getFollowingCount();
        Reporter.log("Number of persons i'm following before try to follow someone new: " + followPersonBeforeCount);
        ActionControls.getGeneralAction().followSomeoneOnTwitter();
        PageControls.getMainPage().waitForFollowingLink();
        PageControls.getMainPage().clickFollowingLink();
        int followPersonAfterCount = PageControls.getAllFollowPage().getFollowingCount();
        Reporter.log("Number of persons i'm following after try to follow someone new: " + followPersonAfterCount);
        Assert.assertEquals(followPersonAfterCount, followPersonBeforeCount + 1, "ERROR: count of following person incremented incorrectly");
    }

    @Test(dependsOnMethods = {"followTest"}, alwaysRun = true, description = "test try to logout from site")
    public void logoutTest() {
        ActionControls.getGeneralAction().logout();
    }
}
