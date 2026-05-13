package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OpenAccountPage extends BasePage {

    private final By openAccountLink = By.linkText("Open New Account");
    private final By accountTypeDropdown = By.id("type");
    private final By fromAccountDropdown = By.id("fromAccountId");
    private final By openAccountButton = By.xpath("//input[@value='Open New Account']");
    private final By newAccountId = By.id("newAccountId");
    private final By pageTitle = By.cssSelector("h1.title");

    public OpenAccountPage(WebDriver driver) {
        super(driver);
    }

    public OpenAccountPage openPage() {
        click(openAccountLink);
        return waitUntilLoaded();
    }

    public OpenAccountPage waitUntilLoaded() {
        waitForVisible(accountTypeDropdown);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(fromAccountDropdown, By.tagName("option")));
        return this;
    }

    public OpenAccountPage selectAccountType(String type) {
        selectByVisibleText(accountTypeDropdown, type);
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
        return this;
    }

    public OpenAccountPage selectFirstFromAccount() {
        waitForVisible(fromAccountDropdown);
        selectByIndex(fromAccountDropdown, 0);
        return this;
    }

    public OpenAccountPage submitNewAccount() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(openAccountButton));

        click(openAccountButton);

        waitForVisible(newAccountId);
        return this;
    }

    public boolean isAccountCreated() {
        return isDisplayed(newAccountId);
    }

    public String getNewAccountId() {
        return getText(newAccountId);
    }

    public String getTitle() {
        return getText(pageTitle);
    }
}