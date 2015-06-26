package com.twitter.Controls;

import com.twitter.pages.*;

/**
 * Created by Admin on 6/26/2015.
 */
public class PageControls {
    private static AllFollowPage allFollowPage;
    private static AllMyTweets allMyTweets;
    private static FollowPage followPage;
    private static LoginPage loginPage;
    private static LogoutPage logoutPage;
    private static MainPage mainPage;
    private static NewTweetDialogPage newMessagePage;
    private static ReTweetConfirmPage reTweetConfirmPage;
    private static TweetPage tweetPage;

    public static AllFollowPage getAllFollowPage() {
        return (allFollowPage != null) ? allFollowPage : new AllFollowPage();
    }

    public static AllMyTweets getAllMyTweets() {
        return (allMyTweets != null) ? allMyTweets : new AllMyTweets();
    }

    public static FollowPage getFollowPage() {
        return (followPage != null) ? followPage : new FollowPage();
    }

    public static LoginPage getLoginPage() {
        return (loginPage != null) ? loginPage : new LoginPage();
    }

    public static LogoutPage getLogoutPage() {
        return (logoutPage != null) ? logoutPage : new LogoutPage();
    }

    public static MainPage getMainPage() {
        return (mainPage != null) ? mainPage : new MainPage();
    }

    public static NewTweetDialogPage getNewMessagePage() {
        return (newMessagePage != null) ? newMessagePage : new NewTweetDialogPage();
    }

    public static ReTweetConfirmPage getReTweetConfirmPage() {
        return (reTweetConfirmPage != null) ? reTweetConfirmPage : new ReTweetConfirmPage();
    }

    public static TweetPage getTweetPage() {
        return (tweetPage != null) ? tweetPage : new TweetPage();
    }

}
