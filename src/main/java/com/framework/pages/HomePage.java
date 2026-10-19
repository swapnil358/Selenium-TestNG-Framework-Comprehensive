package com.framework.pages;

import com.framework.components.HeaderComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class HomePage extends BasePage {
    @FindBy(xpath = "//*[@class='card-body']")
    private List<WebElement> inventoryItems;
    By ByInventoryItems = By.xpath("//*[@class='card-body']");
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
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ByInventoryItems)).isDisplayed();
    }
}
