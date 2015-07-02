package com.twitter.pages;

import com.twitter.base.BasePage;
import com.twitter.utils.Languages;
import com.twitter.utils.Reporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;

/**
 * Created by Admin on 7/1/2015.
 */
public class SettingsPage extends BasePage {

    private By langDropBox = By.id("user_lang");
    private By settingsSaveButton = By.id("settings_save");
    private By topDiv=By.xpath("//div[@class='content-inner no-stream-end']");

    /**
     * get combobox element
     * @return - Select - combobox element
     */
    private Select getComboBox() {
        Reporter.log("try get combobox element");
        WebElement element = getElement(langDropBox);
        return new Select(element);
    }

    /**
     * click on place on page without element to activate button 'Save changes'
     */
    public void clickOnPage() {
        click("click page div", topDiv);
    }

    /**
     * wait for language drop box
     */
    public void waitForLanguageDropBoxLoad() {
        Reporter.log("wait for language combobox load");
        waitForElementPresent(TimeoutSeconds, langDropBox);
    }

    /**
     * click on language combobox
     */
    public void clickLanguageComboBox() {
        click("click language combobox", langDropBox);
    }

    /**
     * get all language values from combobox
     * @return - List<WebElement> - collection with all language elements
     */
    public List<WebElement> getAllLanguagesFromComboBox() {
        Reporter.log("get all language items from combobox");
        Select select = getComboBox();
        return select.getOptions();
    }

    /**
     * get element with language info
     * @param elements - List<WebElement> - collection of language elements
     * @param lang - language to set
     * @return - WebElement - element with language info
     */
    public WebElement getLanguageItem(List<WebElement> elements,String lang) {
        Reporter.log("get language item from combobox");
        for (int i = 0; i < elements.size(); i++) {
            String str = elements.get(i).getAttribute("value");
            if (str.equals(lang)) {
                return elements.get(i);
            }
        }
        return null;
    }

    /**
     * get random language
     * @param currentLang - current UI language
     * @return - String - new language value for UI
     */
    public String getNewRandomLanguage(String currentLang)
    {
        Reporter.log("get random language item from combobox");
        Languages[] languages = Languages.values();
        Languages lan=null;
        boolean bFlag = false;
        do {
            Random rand = new Random();
            int randInt = rand.nextInt(languages.length - 1);
            lan = languages[randInt];
            if (lan.equals(currentLang)==false)
                bFlag = true;
        } while (!bFlag);
        return lan.toString();
    }

    /**
     * get element with language info
     * @param elements - List<WebElement> - collection of language elements
     * @param lang - language to set
     * @return - String - text from language element
     */
    public String getLanguageItemText(List<WebElement> elements, String lang) {
        Reporter.log("get language item text from combobox");
        for (int i = 0; i < elements.size(); i++) {
            String str = elements.get(i).getAttribute("value");
            if (str.equals(lang)) {
                //return elements.get(i).getText();
                return str;
            }
        }
        return null;
    }

    /**
     * get element with language info
     * @param elements - List<WebElement> - collection of language elements
     * @param lang - language to set
     * @return - String - text from language element
     */
    public int getLanguageItemIndex(List<WebElement> elements, String lang) {
        Reporter.log("get language item index from combobox");
        for (int i = 0; i < elements.size(); i++) {
            String str = elements.get(i).getAttribute("value");
            if (str.equals(lang)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * send new language value into combobox
     *
     * @param text - contains language value which need to be set
     */
    public void sendNewLanguageValueIntoComboBox(String text) {
        Select select = getComboBox();
        select.selectByValue(text);
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


    /**
     * get current UI language
     * @return - String - value of current UI language
     */
    public String getCurrentUILanguage() {
        Reporter.log("get current UI language");
        Select select = getComboBox();
        WebElement element=select.getFirstSelectedOption();
        return element.getAttribute("value");
    }

}