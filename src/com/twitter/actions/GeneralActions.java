package com.twitter.actions;

import com.twitter.pages.FollowPage;
import com.twitter.pages.LoginPage;
import com.twitter.pages.SendMessagePage;
import com.twitter.utils.Reporter;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Created
 * by:   Admin
 * date: 11.06.2015
 * time: 16:40
 */
public class GeneralActions {
    private RemoteWebDriver driver;
    private LoginPage loginPage;
    private SendMessagePage sendMessagePage;
    private FollowPage followPage;


    public GeneralActions(RemoteWebDriver driver) {
        this.driver = driver;
        loginPage = new LoginPage();
        sendMessagePage = new SendMessagePage();
        followPage = new FollowPage();
    }

    public void login(String login, String password) {
        Reporter.log("login on twitter");
        loginPage.open();
        loginPage.waitForMainPageLoad();
        loginPage.typeLogin(login);
        loginPage.typePassword(password);
        loginPage.submitLogin();

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
        sendMessagePage.openNewTweetWindow();
        sendMessagePage.waitForTextField();
        sendMessagePage.typeNewTweet(message);
        sendMessagePage.submitMessage();
        sendMessagePage.waitForMessageSendConfirm();
    }

    public int getNumberOfTweets() {
        return sendMessagePage.getNumberOfTweets();
    }

    public void ReloadPage() {
        Reporter.log("Reload Page");
        followPage.updateMainPage();
    }

    public void followSomeoneOnTwitter() {
        /*followPage.waitForFollowButton();
        followPage.followButtonClick();*/

        followPage.waitForSmallFollowButton();
        followPage.followSmallButtonClick();
    }

    public int getNumberOfFollowPersons() {
        return followPage.getNumberOfFollowing();
    }

    public void waitForFollowCounterLoad() {
        followPage.waitFollowCounterLoad();
    }
}
