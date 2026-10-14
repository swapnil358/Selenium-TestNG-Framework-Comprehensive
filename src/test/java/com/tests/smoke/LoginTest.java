package com.tests.smoke;

import com.framework.pages.HomePage;
import com.framework.pages.LoginPage;
import com.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    @Test(groups = {"smoke", "regression"}, description = "Valid user can sign in")
    public void validLoginShouldOpenHomePage() {
        new LoginPage().open().login("standard_user", "secret_sauce");
        HomePage home = new HomePage();
        Assert.assertTrue(home.isLoaded(), "Inventory should be visible");
        Assert.assertEquals(home.getHeading(), "Products");
        Assert.assertEquals(home.getProductCount(), 6, "Product count should be 6");
    }

    @Test(groups = {"regression"}, description = "Invalid password displays an error")
    public void invalidPasswordShouldShowError() {
        LoginPage page = new LoginPage().open();
        page.login("standard_user", "invalid_password");
        Assert.assertTrue(page.getErrorMessage().contains("Username and password do not match"));
    }
}
