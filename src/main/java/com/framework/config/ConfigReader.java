package com.framework.config;

import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

public final class ConfigReader {
    private static final Properties PROPERTIES = new Properties();

    static {
        load("config/config.properties");
        String env = System.getProperty("env", "qa").trim().toLowerCase();
        if (!env.equals("default")) load("config/" + env + ".properties");
    }

    private ConfigReader() {
    }

    private static void load(String resource) {
        try (InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream(resource)) {
            if (in != null) PROPERTIES.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot load config: " + resource, e);
        }
    }

    public static String get(String key) {
        String sys = System.getProperty(key);
        if (sys != null && !sys.isBlank()) return sys.trim();
        String env = System.getenv(key.toUpperCase().replace('.', '_'));
        if (env != null && !env.isBlank()) return env.trim();
        String value = PROPERTIES.getProperty(key);
        if (value == null || value.isBlank()) throw new IllegalArgumentException("Missing config key: " + key);
        return value.trim();
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }
}
