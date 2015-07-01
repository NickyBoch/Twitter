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
    private By loginButton = By.xpath("//button[contains(@class,'submit btn primary-btn')]");
    private By welcomeMessage = By.className("front-welcome-text");

    /**
     * type user login in login field
     *
     * @param login - String - user login
     */
    public void typeLogin(String login) {
        waitForElementPresent(emailField);
        type("type login: " + login, login, emailField);
    }

    /**
     * type user password in password field
     *
     * @param pass - String - user password
     */
    public void typePassword(String pass) {
        waitForElementPresent(passField);
        type("type secret password))", pass, passField);
    }

    /**
     * click login button
     */
    public void clickLoginButton() {
        waitForElementPresent(loginButton);
        click("click login button", loginButton);
    }

    /**
     * wait for login page load
     */
    public void waitForLoginPageLoad() {
        Reporter.log("Wait for main page load");
        waitForElementPresent(welcomeMessage);
    }

}