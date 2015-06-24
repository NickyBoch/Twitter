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
 * dateOfTheTweet: 11.06.2015
 * time: 16:40
 */
public class GeneralActions {
    private RemoteWebDriver driver;
    //
    private AllFollowPage allFollowersPage;
    private AllFollowingPage allFollowingPage;
    private AllMyTweets allMyTweets;
    private FollowPage followPage;
    private LoginPage loginPage;
    private MainPage mainPage;
    private NewTweetDialogPage newMessagePage;
    private ReTweetConfirmPage reTweetConfirmPage;
    private TweetPage tweetPage;
    //
    private static String followerHref;
    private static String dateOfTheTweet;
    private static String tweetHref;
    private static int numberOfReTweetsBefore;
    private static int numberOfReTweetsAfter;
    private static WebElement element;
    private static String userName;

    //
    public String getDate() {
        return dateOfTheTweet;
    }

    public String getTweetHref() {
        return tweetHref;
    }

    public int getNumberOfReTweets() {
        return numberOfReTweetsBefore;
    }

    public GeneralActions(RemoteWebDriver driver) {
        this.driver = driver;
        allFollowersPage = new AllFollowPage();
        allFollowingPage = new AllFollowingPage();
        allMyTweets = new AllMyTweets();
        followPage = new FollowPage();
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
        this.userName = mainPage.getCurrentUserName();
        Assert.assertEquals(this.userName, userName);
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

    public int getNumberOfTweetsOnMainPage() {
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

            if (afterCount > beforeCount || stopwatch.getTime() >= 30 * 1000) {
                //Reporter.log("Stopwatch 5 sec warning");
                stopwatch.stop();
                break;
            } else {
                try {
                    Thread.sleep((long) 10);
                } catch (Exception ex) {
                    //System.out.println(ex.getMessage());
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
        while (true) {
            element = followPage.chooseTweetAndReturnRetweetButton();
            if (element != null) break;
        }

        dateOfTheTweet = followPage.getTweetDateString();
        Reporter.log("tweet dateOfTheTweet: " + dateOfTheTweet);
        tweetHref = followPage.getTweetPath();
        Reporter.log("tweet href: " + tweetHref);
        numberOfReTweetsBefore = followPage.getNumberOfReTweets();
        Reporter.log("number of retweets 1st try: " + numberOfReTweetsBefore);
        if (numberOfReTweetsBefore != -1) {
            if (element != null) {
                followPage.scrollIntoView(element);
                followPage.waitForElement(element);
                followPage.click(element);
                reTweetConfirmPage.waitForElement();
                reTweetConfirmPage.confirmReTweet();
            }
        } else {
            numberOfReTweetsBefore = tweetPage.getNumberOfReTweets(tweetHref);
            Reporter.log("number of retweets 2nd try: " + numberOfReTweetsBefore);
            followPage.scrollIntoView(element);
            tweetPage.makeReTweet();
            reTweetConfirmPage.confirmReTweet();
        }
    }

    public void getDataAboutTweet() {
        driver.get(tweetHref);
        numberOfReTweetsAfter = tweetPage.getNumberOfReTweets(tweetHref);
    }

    public void goToMainPage() {
        loginPage.open();
    }

    public boolean isReTweeted() {
        Reporter.log("assert is retweeted");
        return allMyTweets.isReTweeted(tweetHref);
    }

    public void isReTweetedCounterIncrementCorrectly() {
        int reTweetCountAfter = tweetPage.getNumberOfReTweets(tweetHref);
        Reporter.log("get number of retweets after retweet: " + reTweetCountAfter);
        Assert.assertEquals(numberOfReTweetsBefore + 1, reTweetCountAfter);
    }

    public boolean isDateOnFollowerPageStillTheSame() {
        Reporter.log("check date of the tweet on follower page is still the same after retweet");
        followPage.getDataFromTweet(followerHref,tweetHref);
        numberOfReTweetsBefore = tweetPage.getNumberOfReTweets(followerHref);
        String dateOnFollowPage = followPage.getTweetDateString();
        return dateOfTheTweet.equals(dateOnFollowPage);
    }

    public void openFollowingPage() {
        mainPage.openFollowingPage();
    }

    public void openMainPage() {
        loginPage.open();
    }

    public void goToMyTweetsPage() {
        String href = allMyTweets.getMyTweetsHref();
        allMyTweets.open(href);
    }

    public int getNumberOfTweetsOnAllMyTweetsPage() {
        return allMyTweets.getNumberOfTweets();
    }

    public void removeReTweet() {
        WebElement element = allMyTweets.findReTweetToRemove(tweetHref);
        Assert.assertNotNull(element);
        allMyTweets.removeReTweet(element);
        Reporter.log("update my tweets page");
        this.driver.navigate().refresh();
    }

    public boolean isReTweetExistOnMyPage() {
        Reporter.log("update my tweets page");
        this.driver.navigate().refresh();
        boolean bFlag = allMyTweets.checkTweetExistence(tweetHref);
        Reporter.log("update my tweets page");
        this.driver.navigate().refresh();
        return bFlag;
    }

    public boolean isTweetStillOnFollowingPage() {
        this.driver.get(followerHref);
        return followPage.checkTweetExistence(tweetHref);
    }

    public boolean isDateSameOnMyAllTweetsPage() {
        Reporter.log("is date of retweet still the same on my tweets page");
        return allMyTweets.isSameDateOfTweetOnMyPage(dateOfTheTweet);
    }

    public int getNumberOfReTweetsOnTweetPage() {
        return tweetPage.getNumberOfReTweets(tweetHref);
    }


}