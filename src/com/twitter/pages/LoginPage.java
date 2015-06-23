package com.twitter.pages;

import com.twitter.base.BasePage;
import com.twitter.utils.Reporter;
import org.openqa.selenium.By;

/**
 * Created
 * by:   Admin
 * date: 11.06.2015
 * time: 17:19
 */
public class LoginPage extends BasePage {
    private By emailField = By.id("signin-email");
    private By passField = By.id("signin-password");
    private By submitButton = By.xpath("//button[contains(@class,'submit btn primary-btn')]");
    private By mainPageLink = By.className("nav-logo-link");


    public void typeLogin(String login) {
        type("type login: " + login, login, emailField);
    }

    public void typePassword(String pass) {
        type("type secret password))", pass, passField);
    }

    public void submitLogin() {
        click("click login button", submitButton);
    }

    public void waitForLoginPageLoad() {
        Reporter.log("Wait for main page load");
        waitForElementPresent(emailField);
    }

    public void waitForLogoutComplete() {
        Reporter.log("Wait for logout complete");
        waitForElementVisible(TimeoutSeconds, mainPageLink);
    }

}
