package com.twitter.pages;

import com.twitter.base.BasePage;
import com.twitter.utils.Reporter;
import org.openqa.selenium.By;

/**
 * Created by Admin on 6/24/2015.
 */
public class LogoutPage extends BasePage {
    private By mainPageLink = By.className("nav-logo-link");
    private By phoneInputField = By.id("phone_number");


    public void waitForLogoutComplete() {
        Reporter.log("Wait for logout complete");
        waitForElementVisible(TimeoutSeconds, mainPageLink);
        waitForElementVisible(TimeoutSeconds, phoneInputField);
    }
}
