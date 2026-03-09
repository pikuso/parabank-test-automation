package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BillPayPage extends BasePage {

    private By billPayLink = By.linkText("Bill Pay");

    private By payeeNameInput = By.name("payee.name");
    private By addressInput = By.name("payee.address.street");
    private By cityInput = By.name("payee.address.city");
    private By stateInput = By.name("payee.address.state");
    private By zipCodeInput = By.name("payee.address.zipCode");

    private By accountNumberInput = By.name("payee.accountNumber");
    private By verifyAccountInput = By.name("verifyAccount");
    private By amountInput = By.name("amount");

    private By sendPaymentButton = By.xpath("//input[@value='Send Payment']");
    private By successMessage = By.cssSelector("h1.title");
    private By phoneInput = By.name("payee.phoneNumber");

    public void enterPhone(String phone) {
        type(phoneInput, phone);
    }

    public BillPayPage(WebDriver driver) {
        super(driver);
    }

    public void openPage() {
        click(billPayLink);
        waitForVisible(payeeNameInput);
    }

    public void enterPayeeName(String name) {
        type(payeeNameInput, name);
    }

    public void enterAddress(String address) {
        type(addressInput, address);
    }

    public void enterCity(String city) {
        type(cityInput, city);
    }

    public void enterState(String state) {
        type(stateInput, state);
    }

    public void enterZipCode(String zip) {
        type(zipCodeInput, zip);
    }

    public void enterAccountNumber(String account) {
        type(accountNumberInput, account);
    }

    public void verifyAccountNumber(String account) {
        type(verifyAccountInput, account);
    }

    public void enterAmount(String amount) {
        type(amountInput, amount);
    }

    public void submitPayment() {
        click(sendPaymentButton);
    }

    public boolean isPaymentSuccessful() {
        return isDisplayed(successMessage);
    }
}