package com.parabank.tests;

import com.parabank.config.ConfigReader;
import com.parabank.driver.DriverFactory;
import com.parabank.pages.AccountsOverviewPage;
import com.parabank.pages.LoginPage;
import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    public void setup() {
        driver = DriverFactory.createDriver(resolveBrowser());
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.getPageLoadTimeoutSeconds()));
        driver.get(ConfigReader.getBaseUrl());
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected String resolveBrowser() {
        return ConfigReader.getBrowser();
    }

    protected LoginPage loginPage() {
        return new LoginPage(driver).open();
    }

    protected AccountsOverviewPage loginAsDefaultUser() {
        return loginPage().loginAsDefaultUser();
    }
}
