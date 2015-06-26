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

    //private String numberOfTheTweet = "";

    private By reTweetButton = By.xpath(".//div[contains(@class,'ProfileTweet-action ProfileTweet-action--retweet js-toggleState js-toggleRt')]/button[2]");
    private By myTweetsPageLink = By.xpath("//a[contains(@class,'DashboardProfileCard-statLink u-textUserColor u-linkClean u-block')]");
    private By numberOfAllMyTweets = By.xpath("//ul[@class='ProfileNav-list']/li[1]/a/span[2]");
    private By allTweetItemsOnPage = By.xpath("//li[contains(@id,'stream-item-tweet')]");
    private By reTweetCount = By.xpath(".//div[contains(@class,'ProfileTweet-action ProfileTweet-action--retweet js-toggleState js-toggleRt')]/button[2]/span[3]/span");

    public By getReTweetCountLocator() {
        return reTweetCount;
    }

    public By getTweetDateLocator() {
        return tweetDate;
    }

    private By tweetDate = By.xpath("./div/div[2]/div[1]/small/a");


    public By getNumberOfAllMyTweetsLocator() {
        return numberOfAllMyTweets;
    }

    public By getAllTweetItemsOnPageLocator() {
        return allTweetItemsOnPage;
    }

    public String getMyTweetLink() {
        return getDriver().findElement(myTweetsPageLink).getAttribute("href");
    }




    public boolean isTweetExists(List<WebElement> elements, String tweetLink) {
        Reporter.log("check for retweet: " + tweetLink);
        boolean bFlag = false;

        for (int i = 0; i < elements.size(); i++) {
            WebElement element = elements.get(i);
            WebElement elDate = element.findElement(tweetDate);
            tweetLink = elDate.getAttribute("link");
            Reporter.log("link tweet: " + tweetLink);
            bFlag = tweetLink.equals(tweetLink);
            if (bFlag == true) return bFlag;
        }
        return bFlag;
    }

    public int getCountOfAllTweetsOnMyPage() {
        Reporter.log("Get Number of Tweets on my all tweet page");
        waitForElementPresent(TimeoutSeconds, numberOfAllMyTweets);
        WebElement element = getDriver().findElement(numberOfAllMyTweets);
        String numberOfTheTweet = element.getText();
        return Integer.parseInt(numberOfTheTweet);
    }

    public void clickRemoveReTweetButton(WebElement element) {
        WebElement elem = element.findElement(reTweetButton);
        click("try to cancel retweet", elem);
    }

    private String[] convertDate(LocalDate localDate) {
        //Reporter.log("check is tweetDateString of tweet proper for retweet");
        String[] date = new String[2];
        date[0] = String.valueOf(localDate.getDayOfMonth());

        switch (localDate.getMonth()) {
            case JANUARY:
                date[1] = "янв";
                break;
            case FEBRUARY:
                date[1] = "фев";
                break;
            case MARCH:
                date[1] = "мар";
                break;
            case APRIL:
                date[1] = "апр";
                break;
            case MAY:
                date[1] = "мая";
                break;
            case JUNE:
                date[1] = "июн";
                break;
            case JULY:
                date[1] = "июл";
                break;
            case AUGUST:
                date[1] = "авг";
                break;
            case SEPTEMBER:
                date[1] = "сен";
                break;
            case OCTOBER:
                date[1] = "окт";
                break;
            case NOVEMBER:
                date[1] = "ноя";
                break;
            case DECEMBER:
                date[1] = "дек";
                break;
            default:
                break;
        }
        return date;
    }

}