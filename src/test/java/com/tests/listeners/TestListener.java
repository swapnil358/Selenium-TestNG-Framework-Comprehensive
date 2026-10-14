
package com.tests.listeners;

import com.framework.driver.DriverManager;
import com.framework.utils.ScreenshotUtils;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TestListener implements ITestListener {

    private static final Logger log =
            LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        log.info("TEST START: {}", result.getName());

        Allure.getLifecycle().updateTestCase(testCase ->
                testCase.setName(result.getTestClass().getName()
                        + " :: " + result.getName()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("TEST PASS: {}", result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("TEST SKIPPED: {}", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("TEST FAIL: {}", result.getName(),
                result.getThrowable());

        if (result.getThrowable() != null) {
            Allure.addAttachment(
                    "Failure details",
                    "text/plain",
                    result.getThrowable().toString()
            );
        }

        try {
            if (DriverManager.peekDriver() != null) {
                String screenshotPath =
                        String.valueOf(ScreenshotUtils.capture(result.getName()));

                log.info("Screenshot saved: {}", screenshotPath);

                attachScreenshot(screenshotPath);
            }
        } catch (Exception e) {
            log.warn("Failure screenshot could not be attached", e);
        }
    }

    private void attachScreenshot(String screenshotPath)
            throws IOException {

        if (screenshotPath == null) {
            return;
        }

        File screenshot = new File(screenshotPath);

        if (screenshot.isFile()) {
            try (FileInputStream input =
                         new FileInputStream(screenshot)) {

                Allure.addAttachment(
                        "UI Failure Screenshot",
                        "image/png",
                        input,
                        ".png"
                );
            }
        }
    }
}
