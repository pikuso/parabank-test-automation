package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TransferFundsPage extends BasePage {

    private final By transferFundsLink = By.linkText("Transfer Funds");
    private final By amountInput = By.id("amount");
    private final By fromAccountDropdown = By.id("fromAccountId");
    private final By toAccountDropdown = By.id("toAccountId");
    private final By transferButton = By.xpath("//input[@value='Transfer']");
    private final By successMessage = By.xpath("//h1[contains(text(),'Transfer Complete')]");
    private final By pageTitle = By.cssSelector("h1.title");

    public TransferFundsPage(WebDriver driver) {
        super(driver);
    }

    public TransferFundsPage openPage() {
        click(transferFundsLink);
        return waitUntilLoaded();
    }

    public TransferFundsPage waitUntilLoaded() {
        waitForVisible(amountInput);
        return this;
    }

    public TransferFundsPage enterAmount(String amount) {
        type(amountInput, amount);
        return this;
    }

    public TransferFundsPage selectFirstFromAccount() {
        selectByIndex(fromAccountDropdown, 0);
        return this;
    }

    public TransferFundsPage selectFirstToAccount() {
        selectByIndex(toAccountDropdown, 0);
        return this;
    }

    public TransferFundsPage submitTransfer() {
        click(transferButton);
        waitForVisible(successMessage);
        return this;
    }

    public boolean isTransferSuccessful() {
        return isDisplayed(successMessage);
    }

    public String getAmountValue() {
        return getValue(amountInput);
    }

    public String getTitle() {
        return getText(pageTitle);
    }
}
