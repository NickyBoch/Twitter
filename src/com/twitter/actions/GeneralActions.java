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
        mainPage.waitForMenuLoad();
        mainPage.clickUserMenuButton();
        mainPage.submitLogout();
        loginPage.waitForLogoutComplete();
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

    public void ReloadPage(String tweetsOrFollows) {
        Reporter.log("Reload Page");
        int beforeCount = 0;
        int afterCount = 0;
        switch (tweetsOrFollows) {
            case "tweets":
                beforeCount = mainPage.getNumberOfTweets();
                break;
            case "follows":
                beforeCount = mainPage.getNumberOfFollowing();
                break;
            default:
                break;
        }

        while (true) {
            mainPage.getDriver().navigate().refresh();
            switch (tweetsOrFollows) {
                case "tweets":
                    afterCount = mainPage.getNumberOfTweets();
                    break;
                case "follows":
                    afterCount = mainPage.getNumberOfFollowing();
                    break;
                default:
                    break;
            }

            if (afterCount > beforeCount) break;
            else {
                try {
                    Thread.sleep((long) 10);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
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
