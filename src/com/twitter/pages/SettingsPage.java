package com.twitter.pages;

import com.twitter.base.BasePage;
import com.twitter.utils.Reporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Created by Admin on 7/1/2015.
 */
public class SettingsPage extends BasePage {
    private By langDropBox = By.id("user_lang");
    private By settingsSaveButton = By.id("settings_save");

    public WebElement getSettingsSaveButton() {
        return getElement(settingsSaveButton);
    }

    /**
     * wait for language drop box
     */
    public void waitForLanguageDropBoxLoad() {
        Reporter.log("wait fo language dropboxload");
        waitForElementPresent(TimeoutSeconds, langDropBox);
    }

    public void clickLanguageCombobox() {
        click("click language combobox", getElement(langDropBox));
    }

    public List<WebElement> getAllLanguagesFromCombobox() {
        WebElement element = getElement(langDropBox);
        Select select = new Select(element);
        return select.getOptions();
    }

    public WebElement getLanguageItem(List<WebElement> elements,String lang) {
        for (int i = 0; i < elements.size(); i++) {
            String str = elements.get(i).getAttribute("value");
            if (str.equals(lang)) {
                return elements.get(i);
            }
        }
        return null;
    }

    public void clickLanguageItem(WebElement element) {
        mouseOverWithJS("move mouse over element with js",element);
        click("click language item", element);
        //clickWithJS("click language item", element);
    }
    /**
     * set new language
     *
     * @param language - contains language value which need to be set
     */
    public void setNewLanguage(String language) {

        //select.selectByValue(language);
    }

    /**
     * wait for save settings button to be clickable
     */
    public void waitForSaveSettingsButtonToBeClickable() {
        Reporter.log("wait for save settings button clickable");
        waitForElementBeClickable(TimeoutSeconds, settingsSaveButton);
    }

    /**
     * click save settings button
     */
    public void clickSaveSettingsButton() {
        click("click save settings button", settingsSaveButton);
    }

    public void setAttribute() {
        WebElement element = getElement(settingsSaveButton);
        setElementAttributeWithJS("disabled", "", element);
    }

}


