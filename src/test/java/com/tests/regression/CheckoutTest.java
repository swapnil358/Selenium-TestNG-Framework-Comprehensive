package com.tests.regression;

import com.framework.pages.LoginPage;
import com.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {
    @Test(groups = {"regression"}, description = "Checkout flow starting point")
    public void loginShouldBeAvailableBeforeCheckout() {
        new LoginPage().open();
        Assert.assertTrue(true, "Example placeholder: extend with cart and checkout page actions.");
    }
}
