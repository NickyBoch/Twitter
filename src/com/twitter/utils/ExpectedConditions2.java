
package com.twitter.utils;

import org.openqa.selenium.*;
import com.twitter.base.BasePage;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.NoSuchElementException;


/**
 * Created by Admin on 7/3/2015.
 */

public class ExpectedConditions2 {

    public static ExpectedCondition<Boolean> invisibilityOfElementLocated(final By locator) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    WebElement element = driver.findElement(locator);
                    return !element.isDisplayed() || (element.getSize().getHeight() == 0) || (element.getSize().getWidth() == 0);

                } catch (StaleElementReferenceException var2) {
                    return false;
                }
            }

            @Override
            public String toString() {
                return "visibility of element located by " + locator;
            }
        };
    }

    public static ExpectedCondition<Boolean> visibilityOfElementLocated(final By locator) {
        return new ExpectedCondition<Boolean>() {

            @Override
            public Boolean apply(WebDriver driver) {
                WebElement element = driver.findElement(locator);
                return element.isDisplayed() || (element.getSize().getHeight() != 0) || (element.getSize().getWidth() != 0);
            }

            @Override
            public String toString() {
                return "visibility of element located by " + locator;
            }
        };
    }

    public static ExpectedCondition<Boolean> elementClickable(final By locator) {
        return new ExpectedCondition<Boolean>() {

            @Override
            public Boolean apply(WebDriver driver) {
                WebElement element = driver.findElement(locator);
                Point leftTop = element.getLocation();
                Point rightBottom = new Point(element.getSize().getWidth(), element.getSize().getHeight());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return element.isDisplayed() || (element.getSize().getHeight() != 0) || (element.getSize().getWidth() != 0)
                        && (leftTop == element.getLocation() && rightBottom == new Point(element.getSize().getWidth(), element.getSize().getHeight()));
            }

            @Override
            public String toString() {
                return "visibility of element located by " + locator;
            }
        };
    }

}


