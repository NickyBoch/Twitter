package com.twitter.actions;

import com.twitter.pages.LoginPage;
import com.twitter.pages.MainPage;
import com.twitter.pages.SendMessageDialogPage;
import com.twitter.utils.Reporter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

/**
 * Created
 * by:   Admin
 * date: 11.06.2015
 * time: 16:40
 */
public class GeneralActions {
    private RemoteWebDriver driver;
    private LoginPage loginPage;
    private SendMessageDialogPage sendMessagePage;
    private MainPage mainPage;


    public GeneralActions(RemoteWebDriver driver) {
        this.driver = driver;
        loginPage = new LoginPage();
        sendMessagePage = new SendMessageDialogPage();
        mainPage = new MainPage();
    }

    public void login(String login, String password,String userName) {
        Reporter.log("login on twitter");
        loginPage.open();
        loginPage.waitForLoginPageLoad();
        loginPage.typeLogin(login);
        loginPage.typePassword(password);
        loginPage.submitLogin();
        String str = mainPage.getCurrentUserName();
        Assert.assertEquals(str, userName);
    }

    public void logout() {
        Reporter.log("logout from twitter");
        loginPage.waitForMenuLoad();
        //loginPage.mouseOverMenu();
        //loginPage.openUserMenu();
        //loginPage.waitForLogoutButton();
        loginPage.submitLogout();
    }

    public void sendMessage(String message) {
        mainPage.openNewTweetWindow();
        sendMessagePage.waitForTextField();
        sendMessagePage.typeNewTweet(message);
        sendMessagePage.submitMessage();
        mainPage.waitForMessageSendConfirm();
    }

    public int getNumberOfTweets() {
        return mainPage.getNumberOfTweets();
    }

    public void ReloadPage() {
        Reporter.log("Reload Page");
        mainPage.getDriver().navigate().refresh();
    }

    public void followSomeoneOnTwitter() {
        /*followPage.waitForFollowButton();
        followPage.followButtonClick();*/

        mainPage.waitForSmallFollowButton();
        mainPage.followSmallButtonClick();
    }

    public int getNumberOfFollowPersons() {
        return mainPage.getNumberOfFollowing();
    }

    public void waitForFollowCounterLoad() {
        mainPage.waitFollowCounterLoad();
    }
}
