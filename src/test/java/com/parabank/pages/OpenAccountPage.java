package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class OpenAccountPage extends BasePage {

    private By openAccountLink = By.linkText("Open New Account");
    private By accountTypeDropdown = By.id("type");
    private By fromAccountDropdown = By.id("fromAccountId");
    private By openAccountButton = By.xpath("//input[@value='Open New Account']");
    private By accountOpenedMessage = By.id("newAccountId");

    public OpenAccountPage(WebDriver driver) {
        super(driver);
    }

    public void openPage() {
        click(openAccountLink);
        waitForVisible(accountTypeDropdown);
    }

    public void selectAccountType(String type) {
        Select select = new Select(driver.findElement(accountTypeDropdown));
        select.selectByVisibleText(type);
    }

    public void selectFromAccount() {
        Select select = new Select(driver.findElement(fromAccountDropdown));
        select.selectByIndex(0);
    }

    public void submitNewAccount() {
        click(openAccountButton);
    }

    public boolean isAccountCreated() {
        return isDisplayed(accountOpenedMessage);
    }
}