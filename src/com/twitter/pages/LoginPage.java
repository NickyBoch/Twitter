package com.twitter.pages;

import com.twitter.base.BasePage;
import com.twitter.utils.Reporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

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
    private By dropDownMenu = By.className("dropdown-menu");
    private By logoutButton = By.xpath("//li[@class='js-signout-button']/button");

    public void open() {
        Reporter.log("open main page");
        getDriver().get("https://twitter.com/");
    }

    public void typeLogin(String login) {
        //WebElement el = getDriver().findElement(emailField);
        type("type login: " + login, login, emailField);
    }

    public void typePassword(String pass) {
        //WebElement el = getDriver().findElement(passField);
        type("type secret password))", pass, passField);
    }

    public void submitLogin() {
        click("click login button", submitButton);
    }

    public void waitForMainPageLoad() {
        waitForElementPresent(emailField);
    }

    public void waitForMenuLoad() {
        waitForElementPresent(dropDownMenu);
    }

    public void submitLogout() {
        WebElement element = getDriver().findElement(logoutButton);
        clickWithJS("submit logout", element);
        //click("click logout button", logoutButton);
    }

}
