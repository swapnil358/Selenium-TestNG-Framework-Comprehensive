package com.framework.pages;

import com.framework.config.ConfigReader;
import com.framework.driver.DriverManager;
import com.framework.utils.webDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final webDriverWait wait;

    protected BasePage() {
        driver = DriverManager.getDriver();
        wait = new webDriverWait(driver, ConfigReader.getInt("explicit.wait.seconds"));
    }

    protected void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    protected void type(WebElement element, String value) {
        WebElement visible = wait.until(ExpectedConditions.visibilityOf(element));
        visible.clear();
        visible.sendKeys(value);
    }

    protected String text(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }
}
