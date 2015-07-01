package com.twitter.pages;

import com.twitter.base.BasePage;
import com.twitter.utils.Reporter;
import org.openqa.selenium.By;

/**
 * Created by Admin on 7/1/2015.
 */
public class PasswordInputDialogPage extends BasePage {
    private By passwordDialog = By.id("password_dialog");
    private By passwordInputField = By.id("auth_password");
    private By saveChangesButton = By.id("save_password");

    public void waitPasswordDialogVisibility() {
        Reporter.log("wait for password dialog visibility");
        waitForElementVisible(TimeoutSeconds, passwordDialog);
    }

    public void waitPasswordInputField() {
        Reporter.log("wait for password input field presence");
        waitForElementPresent(TimeoutSeconds, passwordInputField);
    }

    public void typePassword(String pass) {
        type("type secret password", pass, passwordInputField);
    }

    public void clickSaveChangesButton() {
        click("click save changes button", saveChangesButton);
    }

}
