package com.framework.driver;

import com.framework.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.time.Duration;

public final class DriverFactory {
    private DriverFactory() {
    }

    public static WebDriver createDriver() {
        String browser = System.getProperty("browser", ConfigReader.get("browser")).toLowerCase();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", ConfigReader.get("headless")));
        WebDriver driver;
        switch (browser) {
            case "chrome" -> {
                ChromeOptions options = new ChromeOptions();
                if (headless) options.addArguments("--headless=new");
                options.addArguments("--window-size=1440,1000", "--disable-notifications");
                driver = new ChromeDriver(options);
            }
            case "firefox" -> {
                FirefoxOptions options = new FirefoxOptions();
                if (headless) options.addArguments("-headless");
                driver = new FirefoxDriver(options);
            }
            case "edge" -> {
                EdgeOptions options = new EdgeOptions();
                if (headless) options.addArguments("--headless=new");
                driver = new EdgeDriver(options);
            }
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.getInt("page.load.timeout.seconds")));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(ConfigReader.getInt("script.timeout.seconds")));
        if (!headless) driver.manage().window().maximize();
        DriverManager.setDriver(driver);   //Stores the WebDriver for the current thread.
        return driver;
    }

    public static void quitDriver() {
        try {
            WebDriver driver = DriverManager.peekDriver();      //Returns that thread's driver, or throws an exception if none exists.
            if (driver != null) driver.quit();
        } finally {
            DriverManager.unload();   //Removes the current thread's stored reference.
        }
    }
}
