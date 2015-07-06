package com.twitter.pages;

import com.twitter.base.BasePage;
import com.twitter.utils.Reporter;
import org.openqa.selenium.By;

/**
 * Created by Admin on 7/6/2015.
 */
public class ErrorLoginPage extends BasePage {

    private By signInWrapperDiv =By.xpath("//div[contains(@class,'signin-wrapper')]");
    private By emailField=By.xpath("//input[contains(@class,'js-username-field email-input js-initial-focus')]");
    private By passField=By.xpath("//input[contains(@class,'js-password-field')]");
    private By loginSubmitButton=By.xpath("//button[contains(@class,'submit btn primary-btn')]");

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
        waitForElementPresent(loginSubmitButton);
        click("click login button", loginSubmitButton);
    }

    /**
     * wait for login page load
     */
    public void waitForErrorLoginPageLoad() {
        Reporter.log("Wait for error page load");
        waitForElementPresent(signInWrapperDiv);
    }

}
