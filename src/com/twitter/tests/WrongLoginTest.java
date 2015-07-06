package com.twitter.tests;

import com.twitter.Controls.ActionControls;
import com.twitter.base.BaseTest;
import com.twitter.utils.ExcelReader;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;

/**
 * Created by Admin on 7/3/2015.
 */
public class WrongLoginTest extends BaseTest {

    @BeforeClass
/*    public void setUp() {
        generalActions = new GeneralActions();
    }*/

    @DataProvider
    private Object[][] getUserData() {
        //for run in ant uncomment next line
        String resDirPath = ".." + File.separatorChar + ".." + File.separatorChar;
        //for run in idea uncomment next line
        //String resDirPath = "";
        return ExcelReader.getTableArray(resDirPath + "resources" + File.separator + "Credentials.xls", "CredentialChrome", "User1-2");
    }

    @Test(dataProvider = "getUserData", enabled = true, description = "test try to login with wrong password")
    public void loginTestWithWrongData(String login, String pass, String userName) {
        ActionControls.getGeneralAction().loginWithWrongData(login, login, userName);

    }

    @Test(dataProvider = "getUserData",dependsOnMethods = {"loginTestWithWrongData"},enabled = true,description = "test try to login to site from error login page")
    public void loginTestFromErrorPage(String login, String pass, String userName) {
        ActionControls.getGeneralAction().loginFromErrorPage(login, pass, userName);
    }

    @Test(dependsOnMethods = {"loginTestFromErrorPage"}, alwaysRun = true, description = "test try to logout from site")
    public void logoutTest() {
        ActionControls.getGeneralAction().logout();
    }

}
