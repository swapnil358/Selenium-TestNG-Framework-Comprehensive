package com.framework.components;

import com.framework.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HeaderComponent {
    private final WebDriver driver = DriverManager.getDriver();
    private final By title = By.cssSelector(".title");

    public String getPageTitle() {
        return driver.getTitle();
    }
}
