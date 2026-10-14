package com.framework.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class webDriverWait extends WebDriverWait {
    public webDriverWait(WebDriver driver, int timeoutSeconds) {
        super(driver, Duration.ofSeconds(timeoutSeconds));
    }


}
