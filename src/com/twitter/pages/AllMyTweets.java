package com.twitter.pages;

import com.twitter.base.BasePage;
import com.twitter.utils.Reporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created
 * by:   Admin
 * date: 18.06.2015
 * time: 17:46
 */
public class AllMyTweets extends BasePage {
    private String tweetHref = "";
    private String tweetDateString = "";

    private By numberOfTweets = By.xpath("//li[contains(@class,'ProfileNav-item ProfileNav-item--tweets is-active')]/a/span[2]");
    private By tweets = By.xpath("//li[contains(@id,'stream-item-tweet')]");
    private By tweetDate = By.xpath("./div/div[2]/div[1]/small/a");
    private By myTweetsHref=By.xpath("//a[contains(@class,'DashboardProfileCard-statLink u-textUserColor u-linkClean u-block')]");


    public String getMyTweetsHref() {
        String href=getDriver().findElement(myTweetsHref).getAttribute("href");
        return href;
    }

    public boolean isReTweeted(String tweetPath) {
        Reporter.log("check for retweet: " + tweetPath);
        waitForElementVisible(TimeoutSeconds, tweets);
        boolean bFlag = false;

        List<WebElement> listWebElements = getDriver().findElements(tweets);
        int count = listWebElements.size();
        Reporter.log("tweets count: " + count);
        for (int i = 0; i < count; i++) {
            WebElement element = listWebElements.get(i);
            WebElement elDate = element.findElement(tweetDate);

            tweetHref = elDate.getAttribute("href");
            Reporter.log("href tweet: " + tweetHref);
            tweetDateString = elDate.getText();
            bFlag = tweetHref.equals(tweetPath);
            if (bFlag == true) return bFlag;
        }
        return bFlag;
    }

    public boolean isSameDateOfTweetOnMyPage(String date) {
        Reporter.log("check is date the same on my page after retweet");
        return date.equals(tweetDateString);
    }

    public int getNumberOfTweets() {
        Reporter.log("Get Number of Tweets on my all tweets page");
        waitForElementPresent(TimeoutSeconds, numberOfTweets);
        WebElement element = getDriver().findElement(numberOfTweets);
        String str = element.getText();
        return Integer.decode(str);
    }
}
