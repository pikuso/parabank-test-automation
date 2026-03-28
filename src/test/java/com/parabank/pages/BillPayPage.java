package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BillPayPage extends BasePage {

    private final By billPayLink = By.linkText("Bill Pay");
    private final By payeeNameInput = By.name("payee.name");
    private final By addressInput = By.name("payee.address.street");
    private final By cityInput = By.name("payee.address.city");
    private final By stateInput = By.name("payee.address.state");
    private final By zipCodeInput = By.name("payee.address.zipCode");
    private final By phoneInput = By.name("payee.phoneNumber");
    private final By accountNumberInput = By.name("payee.accountNumber");
    private final By verifyAccountInput = By.name("verifyAccount");
    private final By amountInput = By.name("amount");
    private final By sendPaymentButton = By.xpath("//input[@value='Send Payment']");
    private final By successMessage = By.xpath("//h1[contains(text(),'Bill Payment Complete')]");
    private final By pageTitle = By.cssSelector("h1.title");

    public BillPayPage(WebDriver driver) {
        super(driver);
    }

    public BillPayPage openPage() {
        click(billPayLink);
        return waitUntilLoaded();
    }

    public BillPayPage waitUntilLoaded() {
        waitForVisible(payeeNameInput);
        return this;
    }

    public BillPayPage enterPayeeName(String name) {
        type(payeeNameInput, name);
        return this;
    }

    public BillPayPage enterAddress(String address) {
        type(addressInput, address);
        return this;
    }

    public BillPayPage enterCity(String city) {
        type(cityInput, city);
        return this;
    }

    public BillPayPage enterState(String state) {
        type(stateInput, state);
        return this;
    }

    public BillPayPage enterZipCode(String zipCode) {
        type(zipCodeInput, zipCode);
        return this;
    }

    public BillPayPage enterPhone(String phone) {
        type(phoneInput, phone);
        return this;
    }

    public BillPayPage enterAccountNumber(String accountNumber) {
        type(accountNumberInput, accountNumber);
        return this;
    }

    public BillPayPage verifyAccountNumber(String accountNumber) {
        type(verifyAccountInput, accountNumber);
        return this;
    }

    public BillPayPage enterAmount(String amount) {
        type(amountInput, amount);
        return this;
    }

    public BillPayPage submitPayment() {
        click(sendPaymentButton);
        waitForVisible(successMessage);
        return this;
    }

    public BillPayPage completePayment(String amount, String accountNumber) {
        return enterPayeeName("John Smith")
                .enterAddress("Main Street 1")
                .enterCity("New York")
                .enterState("NY")
                .enterZipCode("10001")
                .enterPhone("123456789")
                .enterAccountNumber(accountNumber)
                .verifyAccountNumber(accountNumber)
                .enterAmount(amount)
                .submitPayment();
    }

    public boolean isPaymentSuccessful() {
        return isDisplayed(successMessage);
    }

    public String getAmountValue() {
        return getValue(amountInput);
    }

    public String getTitle() {
        return getText(pageTitle);
    }
}
