package com.parabank.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import com.parabank.config.ConfigReader;

public class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(ConfigReader.getBaseUrl());
    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }
}