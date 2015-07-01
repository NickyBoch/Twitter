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
    private By tweetDate = By.xpath(".//a[contains(@class,'tweet-timestamp js-permalink js-nav js-tooltip')]");
    ///private By tweetDate = By.xpath("./div/div[2]/div[1]/small/a");

    /**
     * getter for locator
     *
     * @return locator for retweet counter
     */
    public By getReTweetCountLocator() {
        return reTweetCount;
    }

    /**
     * getter for locator
     *
     * @return locator for tweet date field
     */
    public By getTweetDateLocator() {
        return tweetDate;
    }

    /**
     * getter for locator
     *
     * @return locator for all tweets on page
     */
    public By getAllTweetItemsOnPageLocator() {
        return allTweetItemsOnPage;
    }

    /**
     * get element contains link to my tweets page
     *
     * @return - WebElement - with link to my tweets page
     */
    public WebElement getMyTweetLink() {
        Reporter.log("Get my tweets link");
        return super.getElement(myTweetsPageLink);
    }

    /**
     * get count of all tweets on my page
     *
     * @return - int - count of all tweets on my page
     */
    public int getCountOfAllTweetsOnMyPage() {
        Reporter.log("Get number of tweets on my all tweets page");
        return Integer.parseInt(getTextValueFromElement(numberOfAllMyTweets));
    }

    /**
     * click on button to remove retweet
     *
     * @param element - WebElement - button for removing the retweet
     */
    public void clickRemoveReTweetButton(WebElement element) {
        click("click remove retweet button", element);
    }

    /**
     * get element - button for cancelling retweet
     *
     * @param element - WebElement - contains all info about tweet
     * @return - WebElement - contains button for cancelling retweet
     */
    public WebElement getCancelReTweetButton(WebElement element) {
        Reporter.log("Get cancel retweet button");
        return element.findElement(cancelReTweetButton);
    }

    /**
     * click on element which contains link to my tweets page
     *
     * @param element - WebElement - contains link to my tweets page
     */
    public void clickMyTweetLink(WebElement element) {
        click("click my tweets page link", element);
    }
}