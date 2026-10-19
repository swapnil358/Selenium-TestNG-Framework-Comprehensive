package com.framework.pages;

import com.framework.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(LoginPage.class);


    @FindBy(id = "userEmail")
    private WebElement username;
    @FindBy(id = "userPassword")
    private WebElement password;
    @FindBy(id = "login")
    private WebElement loginButton;
    @FindBy(xpath = "//div[@role='alert']")
    private WebElement errorMessage;

    By ByErrorMessage = By.xpath("//div[@role='alert']");

    public LoginPage() {
        super();
        PageFactory.initElements(driver, this);
    }

    public LoginPage open() {
        driver.get(ConfigReader.get("base.url"));
        return this;
    }

    public void login(String user, String pass) {
        logger.info("Starting login process");
        type(username, user);
        type(password, pass);
        click(loginButton);
        logger.info("Login process completed");
    }

    public String getErrorMessage() {
        return text(ByErrorMessage);
    }
}
