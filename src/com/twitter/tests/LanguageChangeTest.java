package com.twitter.tests;

import com.twitter.Controls.ActionControls;
import com.twitter.Controls.PageControls;
import com.twitter.base.BaseTest;
import com.twitter.utils.ExcelReader;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

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
        PageControls.getSettingsPage().clickLanguageCombobox();
        List<WebElement> elements = PageControls.getSettingsPage().getAllLanguagesFromCombobox();
        WebElement element = PageControls.getSettingsPage().getLanguageItem(elements, "en");
        PageControls.getSettingsPage().clickLanguageItem(element);

        //TODO save changes button non clickable after select new item in language combobox


        //PageControls.getSettingsPage().clickLanguageItem(element);
        //PageControls.getSettingsPage().clickLanguageCombobox();
/*        PageControls.getSettingsPage().setNewLanguage("en");
        PageControls.getSettingsPage().clickLanguageCombobox();*/
        PageControls.getSettingsPage().setAttribute();
        element = PageControls.getSettingsPage().getSettingsSaveButton();
        PageControls.getSettingsPage().scrollToElementWithJS(element);
        PageControls.getSettingsPage().waitForSaveSettingsButtonToBeClickable();
        PageControls.getSettingsPage().clickSaveSettingsButton();

        PageControls.getPasswordInputDialogPage().waitPasswordDialogVisibility();
        PageControls.getPasswordInputDialogPage().waitPasswordInputField();
        PageControls.getPasswordInputDialogPage().typePassword(pass);
        PageControls.getPasswordInputDialogPage().clickSaveChangesButton();
    }

    @Test(dependsOnMethods = {"languageChangeTest"}, alwaysRun = true, description = "test try to logout from site")
    public void logoutTest() {
        ActionControls.getGeneralAction().logout();
    }
}
