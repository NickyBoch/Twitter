package com.twitter.utils;

import com.twitter.base.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;

/**
 * Created
 * by:   Admin
 * date: 15.06.2015
 * time: 17:39
 */
public class ScreenshotMaker extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult result) {
        String imageName = result.getName() + "-" + System.currentTimeMillis() + ".png";
        String destFile = System.getProperty("report.dir") + File.separatorChar + "html" +
                File.separatorChar + imageName;

        try {
            File scrFile = (BaseTest.driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(destFile));
            Reporter.log("Screenshot saved:<br /><a href = '" + imageName + "'><img src= '" + imageName + "' width='600' /></a>");
        } catch (Exception ex) {
            Reporter.log("Error saving screenshot!");
        }
    }
}
