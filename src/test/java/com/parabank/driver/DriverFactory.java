package com.parabank.driver;

import com.parabank.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.Map;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver createDriver(String browserName) {
        String browser = browserName.toLowerCase();
        return switch (browser) {
            case "chrome" -> createChromeDriver();
            case "chrome-mobile", "mobile" -> createMobileChromeDriver();
            case "safari" -> createSafariDriver();
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };
    }

    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-search-engine-choice-screen");
        options.addArguments("--start-maximized");
        if (ConfigReader.isHeadless()) {
            options.addArguments("--headless=new", "--window-size=1920,1080");
        }
        return new ChromeDriver(options);
    }

    private static WebDriver createMobileChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-search-engine-choice-screen");
        Map<String, Object> deviceMetrics = Map.of(
                "width", ConfigReader.getMobileWidth(),
                "height", ConfigReader.getMobileHeight(),
                "pixelRatio", ConfigReader.getMobilePixelRatio());
        Map<String, Object> mobileEmulation = Map.of(
                "deviceMetrics", deviceMetrics,
                "userAgent", "Mozilla/5.0 (iPhone; CPU iPhone OS 16_0 like Mac OS X) AppleWebKit/605.1.15 "
                        + "(KHTML, like Gecko) Version/16.0 Mobile/15E148 Safari/604.1");
        options.setExperimentalOption("mobileEmulation", mobileEmulation);
        if (ConfigReader.isHeadless()) {
            options.addArguments("--headless=new");
        }
        return new ChromeDriver(options);
    }

    private static WebDriver createSafariDriver() {
        if (!System.getProperty("os.name").toLowerCase().contains("mac")) {
            throw new IllegalStateException("Safari execution is supported only on macOS hosts with safaridriver enabled");
        }
        SafariOptions options = new SafariOptions();
        options.setCapability(CapabilityType.PLATFORM_NAME, Platform.MAC);
        return new SafariDriver(options);
    }
}
