package com.twitter.tests;

import com.twitter.Controls.PageControls;
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
public class FollowTest extends BaseTest {
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
    public void followTest() {
        int followPersonBeforeCount = PageControls.getMainPage().getFollowingCount();
        Reporter.log("Number of persons i'm following before try to follow someone new: " + followPersonBeforeCount);
        generalActions.followSomeoneOnTwitter();
        PageControls.getMainPage().clickFollowingLink();
        PageControls.getMainPage().getDriver().navigate().refresh();
        int followPersonAfterCount = PageControls.getAllFollowPage().getFollowCount();
        Reporter.log("Number of persons i'm following after try to follow someone new: " + followPersonAfterCount);
        Assert.assertEquals(followPersonAfterCount, followPersonBeforeCount + 1, "assert count of following person incremented properly");
    }

    @Test(dependsOnMethods = {"followTest"})
    public void logoutTest() {
        generalActions.logout();
    }
}
