package com.tests.smoke;

import com.framework.pages.HomePage;
import com.framework.pages.LoginPage;
import com.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest {
    @Test(groups = {"smoke"})
    public void inventoryProductsShouldBeVisibleAfterLogin() {
        new LoginPage()
                .open()
                .login("standard_user", "secret_sauce");

        Assert.assertTrue(new HomePage().getProductCount() > 0);
    }
}
