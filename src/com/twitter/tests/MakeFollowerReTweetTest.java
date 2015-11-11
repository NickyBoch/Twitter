package com.twitter.tests;

import com.twitter.Controls.ActionControls;
import com.twitter.Controls.PageControls;
import com.twitter.base.BaseTest;
import com.twitter.utils.ExcelReader;
import com.twitter.utils.Reporter;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;

/**
 * Created by Admin on 6/23/2015.
 */
public class MakeFollowerReTweetTest extends BaseTest {

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

    @Test(dataProvider = "getUserData", description = "test try to login on site")
    public void loginTest(String login, String pass, String userName) {
        ActionControls.getGeneralAction().login(login, pass, userName);
    }

    @Test(dependsOnMethods = "loginTest", enabled = true, description = "test try to make retweet from my follower")
    public void reTweetFollowerTest() {
        PageControls.getMainPage().waitForMenuLoad();
        ActionControls.getGeneralAction().goToSettingsPage();
        String currentLang = PageControls.getSettingsPage().getCurrentUILanguage();
        PageControls.getMainPage().openMainPage();
        PageControls.getMainPage().waitForMenuLoad();

        int tweetsBeforeCount = PageControls.getMainPage().getCountOfTweetsOnMainPage();
        Reporter.log("Number of tweets before try to retweet: " + tweetsBeforeCount);
        PageControls.getMainPage().clickFollowersLink();
        ActionControls.getGeneralAction().openFollowPage("follower");
        WebElement element = ActionControls.getGeneralAction().getTweetForRetweet(currentLang);

        int retweetBeforeCount = PageControls.getFollowPage().getCountOfReTweets(element, PageControls.getFollowPage().getReTweetCountLocator());
        String tweetLinkBefore = PageControls.getFollowPage().getTweetLink(element, PageControls.getFollowPage().getTweetDateLocator());
        String dateBefore = PageControls.getFollowPage().getTweetDate(element, PageControls.getFollowPage().getTweetDateLocator());
        retweetBeforeCount = ActionControls.getGeneralAction().makeRetweet(element, tweetLinkBefore, retweetBeforeCount);

        PageControls.getMainPage().openMainPage();

        ActionControls.getGeneralAction().goToMyTweetsPage();
        //driver.navigate().refresh();
        int tweetsAfterCount = PageControls.getAllMyTweets().getCountOfAllTweetsOnMyPage();

        WebElement elem = ActionControls.getGeneralAction().getReTweetElementOnMyAllTweetsPage(tweetLinkBefore);
        Assert.assertNotNull(elem, "assert retweet exist");

        int retweetAfterCount = PageControls.getAllMyTweets().getCountOfReTweets(elem, PageControls.getAllMyTweets().getReTweetCountLocator());
        String tweetLinkAfter = PageControls.getAllMyTweets().getTweetLink(elem, PageControls.getAllMyTweets().getTweetDateLocator());
        String dateAfterOnMyPage = PageControls.getAllMyTweets().getTweetDate(elem, PageControls.getAllMyTweets().getTweetDateLocator());
        WebElement elem1 = ActionControls.getGeneralAction().getReTweetElementOnFollowPage(tweetLinkAfter);
        String dateAfterOnFollowPage = PageControls.getFollowPage().getTweetDate(elem1, PageControls.getFollowPage().getTweetDateLocator());
        if (retweetAfterCount == -1) {
            driver.get(tweetLinkAfter);
            retweetAfterCount = PageControls.getTweetPage().getCountOfReTweets();
        }

/*        Reporter.log("-->retweet count BEFORE: " + retweetBeforeCount);
        Reporter.log("-->retweet link BEFORE: " + tweetLinkBefore);
        Reporter.log("-->retweet dateBefore BEFORE: " + dateBefore);
        Reporter.log("-->retweet count AFTER: " + retweetAfterCount);
        Reporter.log("-->retweet link AFTER: " + tweetLinkAfter);
        Reporter.log("-->retweet dateBefore AFTER ON MY PAGE: " + dateAfterOnMyPage);
        Reporter.log("-->retweet dateBefore AFTER ON FOLLOW PAGE: " + dateAfterOnFollowPage);*/

        Assert.assertEquals(tweetsAfterCount, tweetsBeforeCount + 1, "ERROR: my tweet count incremented incorrectly");
        Assert.assertEquals(retweetAfterCount, retweetBeforeCount + 1, "ERROR: retweet count incremented incorrectly");
        Assert.assertEquals(dateBefore, dateAfterOnMyPage, "ERROR: retweet date on my page after retweet changed");
        Assert.assertEquals(dateBefore, dateAfterOnFollowPage, "ERROR: retweet date on follow page after retweet changed");
    }

    @Test(dependsOnMethods = {"reTweetFollowerTest"}, alwaysRun = true, description = "test ry to logout from site")
    public void logoutTest() {
        ActionControls.getGeneralAction().logout();
    }

}
