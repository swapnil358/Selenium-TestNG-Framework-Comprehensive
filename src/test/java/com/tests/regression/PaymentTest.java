package com.tests.regression;

import com.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PaymentTest extends BaseTest {
    @Test(groups = {"regression"}, description = "Payment integration placeholder")
    public void paymentScenarioRequiresProjectSpecificTestEnvironment() {
        Assert.assertTrue(true, "Implement using a payment sandbox; never use live payment credentials in automation.");
    }
}
