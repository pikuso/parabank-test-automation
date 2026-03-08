package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class TransferFundsPage extends BasePage {

    private By transferFundsLink = By.linkText("Transfer Funds");
    private By amountInput = By.id("amount");
    private By fromAccountDropdown = By.id("fromAccountId");
    private By toAccountDropdown = By.id("toAccountId");
    private By transferButton = By.xpath("//input[@value='Transfer']");
    private By successMessage = By.xpath("//h1[contains(text(),'Transfer Complete')]");

    public TransferFundsPage(WebDriver driver) {
        super(driver);
    }

    public void openPage() {
        click(transferFundsLink);
        waitForVisible(amountInput);
    }

    public void enterAmount(String amount) {
        type(amountInput, amount);
    }

    public void selectFromAccount() {

        waitForVisible(fromAccountDropdown);

        Select select = new Select(driver.findElement(fromAccountDropdown));
        select.selectByIndex(0);
    }

    public void selectToAccount() {

        waitForVisible(toAccountDropdown);

        Select select = new Select(driver.findElement(toAccountDropdown));
        select.selectByIndex(1);
    }

    public void submitTransfer() {
        click(transferButton);
    }

    public boolean isTransferSuccessful() {
        return isDisplayed(successMessage);
    }
}