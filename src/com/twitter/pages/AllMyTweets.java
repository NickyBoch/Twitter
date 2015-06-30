package com.twitter.pages;

import com.twitter.base.BasePage;
import com.twitter.utils.Reporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.util.List;

/**
 * Created
 * by:   Admin
 * date: 18.06.2015
 * time: 17:46
 */
public class AllMyTweets extends BasePage {

    private By cancelReTweetButton = By.xpath(".//div[contains(@class,'ProfileTweet-action ProfileTweet-action--retweet js-toggleState js-toggleRt')]/button[2]");
    private By myTweetsPageLink = By.xpath("//a[contains(@class,'DashboardProfileCard-statLink u-textUserColor u-linkClean u-block')]");
    private By numberOfAllMyTweets = By.xpath("//ul[@class='ProfileNav-list']/li[1]/a/span[2]");
    private By allTweetItemsOnPage = By.xpath("//li[contains(@id,'stream-item-tweet')]");
    private By reTweetCount = By.xpath(".//div[contains(@class,'ProfileTweet-action ProfileTweet-action--retweet js-toggleState js-toggleRt')]/button[2]/span[3]/span");
    ///private By tweetDate = By.xpath("./div/div[2]/div[1]/small/a");
    private By tweetDate = By.xpath(".//a[contains(@class,'tweet-timestamp js-permalink js-nav js-tooltip')]");



    public By getReTweetCountLocator() {
        return reTweetCount;
    }

    public By getTweetDateLocator() {
        return tweetDate;
    }

    public By getAllTweetItemsOnPageLocator() {
        return allTweetItemsOnPage;
    }

    public String getMyTweetLink() {
        Reporter.log("Get my tweets link");
        return getElementAttribute(myTweetsPageLink, "href");
    }

    public int getCountOfAllTweetsOnMyPage() {
        Reporter.log("Get Number of Tweets on my all tweet page");
        return Integer.parseInt(getTextValueFromElement(numberOfAllMyTweets));
    }

    public void clickRemoveReTweetButton(WebElement element) {
        click("try to cancel retweet", element);
    }

    public WebElement getCancelReTweetButton(WebElement element) {
        Reporter.log("Get cancel retweet button");
        return element.findElement(cancelReTweetButton);
    }

}