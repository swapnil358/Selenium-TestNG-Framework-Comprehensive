package com.framework.driver;

import com.framework.config.ConfigReader;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;
import java.net.URL;
import java.time.Duration;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver createDriver() {

        String browser = System.getProperty("browser", ConfigReader.get("browser")).toLowerCase();

        boolean headless = Boolean.parseBoolean(System.getProperty("headless", ConfigReader.get("headless")));

        // If this URL is configured, use remote execution.
        // Otherwise, launch the browser locally.
        String remoteUrl = System.getProperty("selenium.remote.url", System.getenv("SELENIUM_REMOTE_URL"));

        WebDriver driver;

        try {
            switch (browser) {

                case "chrome" -> {
                    ChromeOptions options = new ChromeOptions();

                    if (headless) {
                        options.addArguments("--headless=new");
                    }

                    options.addArguments("--window-size=1440,1000", "--disable-notifications");

                    if (remoteUrl != null && !remoteUrl.isBlank()) {
                        driver = new RemoteWebDriver(URI.create(remoteUrl).toURL(), options);
                    } else {
                        driver = new ChromeDriver(options);
                    }
                }

                case "firefox" -> {
                    FirefoxOptions options = new FirefoxOptions();

                    if (headless) {
                        options.addArguments("-headless");
                    }

                    if (remoteUrl != null && !remoteUrl.isBlank()) {
                        driver = new RemoteWebDriver(URI.create(remoteUrl).toURL(), options);
                    } else {
                        driver = new FirefoxDriver(options);
                    }
                }

                case "edge" -> {
                    EdgeOptions options = new EdgeOptions();

                    if (headless) {
                        options.addArguments("--headless=new");
                    }

                    options.addArguments("--window-size=1440,1000");

                    if (remoteUrl != null && !remoteUrl.isBlank()) {
                        driver = new RemoteWebDriver(URI.create(remoteUrl).toURL(), options);
                    } else {
                        driver = new EdgeDriver(options);
                    }
                }

                default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to create WebDriver for browser: " + browser, e);
        }

        try {
            driver.manage().timeouts().implicitlyWait(Duration.ZERO);

            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.getInt("page.load.timeout.seconds")));

            driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(ConfigReader.getInt("script.timeout.seconds")));

            if (!headless) {
                driver.manage().window().maximize();
            }

            DriverManager.setDriver(driver);

            return driver;

        } catch (RuntimeException e) {
            try {
                driver.quit();
            } catch (Exception ignored) {
                // Preserve the original setup failure.
            }

            throw e;
        }
    }

    public static void quitDriver() {

        try {
            WebDriver driver = DriverManager.peekDriver();

            if (driver != null) {
                driver.quit();
            }

        } finally {
            DriverManager.unload();
        }
    }
}
