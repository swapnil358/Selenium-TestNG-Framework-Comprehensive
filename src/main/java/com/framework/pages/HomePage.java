package com.framework.pages;

import com.framework.components.HeaderComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class HomePage extends BasePage {
    @FindBy(css = ".inventory_item")
    private List<WebElement> inventoryItems;
    private final HeaderComponent header;

    public HomePage() {
        super();
        PageFactory.initElements(driver, this);
        header = new HeaderComponent();
    }

    public String getHeading() {
        return header.getPageTitle();
    }

    public int getProductCount() {
        wait.until(ExpectedConditions.visibilityOfAllElements(inventoryItems));
        return inventoryItems.size();
    }

    public boolean isLoaded() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".inventory_list"))).isDisplayed();
    }
}
