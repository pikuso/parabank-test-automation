package com.parabank.pages;

import com.parabank.config.ConfigReader;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {

    protected final WebDriver driver;
    private final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitTimeoutSeconds()));
    }

    protected void waitForVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitForClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void click(By locator) {
        waitForClickable(locator);
        driver.findElement(locator).click();
    }

    protected void type(By locator, String text) {
        waitForVisible(locator);
        WebElement element = driver.findElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        waitForVisible(locator);
        return driver.findElement(locator).getText().trim();
    }

    protected String getValue(By locator) {
        waitForVisible(locator);
        return driver.findElement(locator).getAttribute("value");
    }

    protected boolean isDisplayed(By locator) {
        waitForVisible(locator);
        return driver.findElement(locator).isDisplayed();
    }

    protected void selectByVisibleText(By locator, String value) {
        waitForVisible(locator);
        new Select(driver.findElement(locator)).selectByVisibleText(value);
    }

    protected void selectByIndex(By locator, int index) {
        waitForVisible(locator);
        new Select(driver.findElement(locator)).selectByIndex(index);
    }
}
