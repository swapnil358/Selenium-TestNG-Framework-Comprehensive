package com.framework.utils;

import com.framework.driver.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtils {
    private ScreenshotUtils() {
    }

    public static Path capture(String name) throws IOException {
        Path directory = Path.of("target", "screenshots");
        Files.createDirectories(directory);
        String stamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
        Path destination = directory.resolve(safe(name) + "_" + stamp + ".png");
        Path source = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE).toPath();
        return Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
    }

    private static String safe(String name) {
        return name.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
