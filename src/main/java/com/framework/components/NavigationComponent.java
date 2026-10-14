package com.framework.components;

import com.framework.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationComponent {
    private final WebDriver driver = DriverManager.getDriver();
    private final By menuButton = By.id("react-burger-menu-btn");

    public void openMenu() {
        driver.findElement(menuButton).click();
    }
}
