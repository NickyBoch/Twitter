package com.twitter.actions;

import com.twitter.pages.*;
import com.twitter.utils.Reporter;
import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.WebElement;
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

    private AllFollowersPage allFollowersPage;
    private AllFollowingPage allFollowingPage;
    private FollowerPage followerPage;
    private LoginPage loginPage;
    private MainPage mainPage;
    private NewTweetDialogPage newMessagePage;
    private ReTweetConfirmPage reTweetConfirmPage;
    private TweetPage tweetPage;

    //
    String followerHref;
    private String date;
    private String tweetHref;
    private int numberOfReTweets;

    //
    public String getDate() {
        return date;
    }

    public String getTweetHref() {
        return tweetHref;
    }

    public int getNumberOfReTweets() {
        return numberOfReTweets;
    }

    public GeneralActions(RemoteWebDriver driver) {
        this.driver = driver;
        allFollowersPage = new AllFollowersPage();
        allFollowingPage = new AllFollowingPage();
        followerPage = new FollowerPage();
        loginPage = new LoginPage();
        mainPage = new MainPage();
        newMessagePage = new NewTweetDialogPage();
        reTweetConfirmPage = new ReTweetConfirmPage();
        tweetPage = new TweetPage();
    }

    public void login(String login, String password, String userName) {
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
        newMessagePage.waitForTextField();
        newMessagePage.typeNewTweet(message);
        newMessagePage.submitMessage();
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

        StopWatch stopwatch = new StopWatch();
        stopwatch.start();
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

            if (afterCount > beforeCount || stopwatch.getTime() >= 60 * 1000) {
                //Reporter.log("Stopwatch 5 sec warning");
                stopwatch.stop();
                break;
            } else {
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

    public void openFollowersPage() {
        mainPage.openFollowersPage();
    }

    public void chooseFollower() {
        followerHref = allFollowersPage.clickUser();
    }

    public void makeReTweet() {
        Reporter.log("Try to retweet");
        WebElement element = followerPage.chooseTweetAndReTweet();

        date = followerPage.getDate();
        Reporter.log("tweet day: " + date);
        tweetHref = followerPage.getTweetPath();
        Reporter.log("tweet day: " + tweetHref);
        numberOfReTweets = followerPage.getNumberOfReTweets();
        Reporter.log("number of retweets 1st try: " + numberOfReTweets);
        if (numberOfReTweets != -1) {
            if (element != null) followerPage.click(element);
            reTweetConfirmPage.confirmReTweet();
        } else {
            numberOfReTweets = tweetPage.getNumberOfReTweets(tweetHref);
            Reporter.log("number of retweets 2nd try: " + numberOfReTweets);
            tweetPage.makeReTweet();
            reTweetConfirmPage.confirmReTweet();
        }
    }

    public void goToMainPage() {
        loginPage.open();
    }

    public void isReTweeted() {
        mainPage.waitForMenuLoad();
        Assert.assertTrue(mainPage.isReTweeted(tweetHref));
        Assert.assertTrue(mainPage.isSameDateOfTweetOnMyPage(date));
    }

    public void isReTweetedCounterIncrementCorrectly() {
        int reTweetCountAfter = tweetPage.getNumberOfReTweets(tweetHref);
        Reporter.log("get number of retweets after retweet: " + reTweetCountAfter);
        Assert.assertEquals(numberOfReTweets + 1, reTweetCountAfter);
    }

    public boolean isDateOnFollowerPageStillTheSame() {
        Reporter.log("check date of the tweet on follower page is still the same after retweet");
        driver.get(followerHref);
        return false;
    }
}