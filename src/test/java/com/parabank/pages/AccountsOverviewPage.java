package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountsOverviewPage extends BasePage {

    private final By pageTitle = By.cssSelector("h1.title");
    private final By accountTable = By.id("accountTable");
    private final By openNewAccountLink = By.linkText("Open New Account");
    private final By transferFundsLink = By.linkText("Transfer Funds");
    private final By billPayLink = By.linkText("Bill Pay");
    private final By accountsOverviewLink = By.linkText("Accounts Overview");

    public AccountsOverviewPage(WebDriver driver) {
        super(driver);
    }

    public AccountsOverviewPage waitUntilLoaded() {
        waitForVisible(accountTable);
        return this;
    }

    public String getTitle() {
        return getText(pageTitle);
    }

    public boolean isLoaded() {
        return isDisplayed(accountsOverviewLink) && isDisplayed(accountTable);
    }

    public OpenAccountPage openNewAccountPage() {
        click(openNewAccountLink);
        return new OpenAccountPage(driver).waitUntilLoaded();
    }

    public TransferFundsPage openTransferFundsPage() {
        click(transferFundsLink);
        return new TransferFundsPage(driver).waitUntilLoaded();
    }

    public BillPayPage openBillPayPage() {
        click(billPayLink);
        return new BillPayPage(driver).waitUntilLoaded();
    }
}
