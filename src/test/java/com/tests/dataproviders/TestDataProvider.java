package com.tests.dataproviders;

import org.testng.annotations.DataProvider;

public class TestDataProvider {
    @DataProvider(name = "loginUsers", parallel = true)
    public static Object[][] loginUsers() {
        return new Object[][]{
                {"standard_user", "secret_sauce", true},
                {"standard_user", "invalid_password", false}
        };
    }
}
