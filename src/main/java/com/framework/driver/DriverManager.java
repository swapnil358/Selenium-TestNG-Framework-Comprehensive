package com.framework.driver;

import org.openqa.selenium.WebDriver;

public final class DriverManager {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() {
    }

    public static void setDriver(WebDriver driver) {
        DRIVER.set(driver);
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) throw new IllegalStateException("WebDriver is not initialised on this thread.");
        return driver;
    }

    public static WebDriver peekDriver() {
        return DRIVER.get();
    }

    public static void unload() {
        DRIVER.remove();
    }
}
