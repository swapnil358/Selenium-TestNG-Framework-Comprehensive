package com.framework.utils;

import com.framework.driver.DriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public final class JavaScriptUtils {
    private JavaScriptUtils() {
    }

    public static void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) DriverManager.getDriver()).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", element);
    }

    public static Object execute(String script, Object... args) {
        return ((JavascriptExecutor) DriverManager.getDriver()).executeScript(script, args);
    }
}
