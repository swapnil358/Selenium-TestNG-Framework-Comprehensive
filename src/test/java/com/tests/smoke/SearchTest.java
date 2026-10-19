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
                .login("anshika123@gmail.com", "Qwerty@12345");

        Assert.assertTrue(new HomePage().getProductCount() > 0);
    }
}
