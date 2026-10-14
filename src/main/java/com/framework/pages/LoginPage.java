package com.framework.pages;

import com.framework.config.ConfigReader;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {
    @FindBy(id = "user-name")
    private WebElement username;
    @FindBy(id = "password")
    private WebElement password;
    @FindBy(id = "login-button")
    private WebElement loginButton;
    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    public LoginPage() {
        super();
        PageFactory.initElements(driver, this);
    }

    public LoginPage open() {
        driver.get(ConfigReader.get("base.url"));
        return this;
    }

    public void login(String user, String pass) {
        type(username, user);
        type(password, pass);
        click(loginButton);
    }

    public String getErrorMessage() {
        return text(errorMessage);
    }
}
