package com.parabank.config;

import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IllegalStateException("config.properties was not found on the classpath");
            }
            PROPERTIES.load(input);
        } catch (Exception exception) {
            throw new IllegalStateException("Cannot load config.properties", exception);
        }
    }

    private ConfigReader() {
    }

    public static String getBaseUrl() {
        return getRequiredProperty("base.url");
    }

    public static String getApiBaseUrl() {
        return getRequiredProperty("api.base.url");
    }

    public static String getUsername() {
        return getRequiredProperty("credentials.username");
    }

    public static String getPassword() {
        return getRequiredProperty("credentials.password");
    }

    public static String getBrowser() {
        return getProperty("browser", "chrome");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "false"));
    }

    public static String getMobileDeviceName() {
        return getProperty("mobile.device.name", "iPhone 12 Pro");
    }

    public static int getMobileWidth() {
        return Integer.parseInt(getProperty("mobile.width", "390"));
    }

    public static int getMobileHeight() {
        return Integer.parseInt(getProperty("mobile.height", "844"));
    }

    public static int getMobilePixelRatio() {
        return Integer.parseInt(getProperty("mobile.pixel.ratio", "3"));
    }

    public static long getExplicitTimeoutSeconds() {
        return Long.parseLong(getProperty("timeouts.explicit.seconds", "10"));
    }

    public static long getPageLoadTimeoutSeconds() {
        return Long.parseLong(getProperty("timeouts.page.load.seconds", "30"));
    }

    private static String getRequiredProperty(String key) {
        String value = getProperty(key, null);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing configuration key: " + key);
        }
        return value;
    }

    private static String getProperty(String key, String defaultValue) {
        return System.getProperty(key, PROPERTIES.getProperty(key, defaultValue));
    }
}
