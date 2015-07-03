package com.twitter.tests;

import com.twitter.Controls.ActionControls;
import com.twitter.Controls.PageControls;
import com.twitter.base.BaseTest;
import com.twitter.utils.ExcelReader;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;


/**
 * Created by Admin on 7/1/2015.
 */
public class LanguageChangeTest extends BaseTest {

    @BeforeClass
/*    public void setUp() {
        generalActions = new GeneralActions();
    }*/

    @DataProvider
    private Object[][] getUserData() {
        //for run in ant uncomment next line
        //String resDirPath = ".." + File.separatorChar + ".." + File.separatorChar;
        //for run in idea uncomment next line
        String resDirPath = "";
        return ExcelReader.getTableArray(resDirPath + "resources" + File.separator + "Credentials.xls", "CredentialChrome", "User1-2");
    }

    @Test(dataProvider = "getUserData", description = "test try to login to site")
    public void loginTest(String login, String pass, String userName) {
        ActionControls.getGeneralAction().login(login, pass, userName);
    }

    @Test(dependsOnMethods = "loginTest", enabled = true, dataProvider = "getUserData", description = "test try to change UI language on twitter")
    public void languageChangeTest(String login, String pass, String userName) {
        PageControls.getMainPage().waitForMenuLoad();
        ActionControls.getGeneralAction().goToSettingsPage();
        String currentLang = PageControls.getSettingsPage().getCurrentUILanguage();
        String newLang = PageControls.getSettingsPage().getNewRandomLanguage(currentLang);
        ActionControls.getGeneralAction().setNewLanguage(newLang);
        ActionControls.getGeneralAction().confirmSettingsSave(pass);
    }

    @Test(dependsOnMethods = {"languageChangeTest"}, alwaysRun = true, description = "test try to logout from site")
    public void logoutTest() {
        ActionControls.getGeneralAction().logout();
    }
}
