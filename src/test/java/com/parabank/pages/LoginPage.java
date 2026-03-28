package com.parabank.pages;

import com.parabank.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By usernameInput = By.name("username");
    private final By passwordInput = By.name("password");
    private final By loginButton = By.xpath("//input[@value='Log In']");
    private final By accountsOverviewLink = By.linkText("Accounts Overview");
    private final By errorMessage = By.cssSelector("p.error");
    private final By registerLink = By.linkText("Register");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        driver.get(ConfigReader.getBaseUrl() + "index.htm");
        waitForVisible(usernameInput);
        return this;
    }

    public LoginPage enterUsername(String username) {
        type(usernameInput, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        type(passwordInput, password);
        return this;
    }

    public LoginPage submitExpectingFailure() {
        click(loginButton);
        waitForVisible(errorMessage);
        return this;
    }

    public AccountsOverviewPage submitExpectingSuccess() {
        click(loginButton);
        return new AccountsOverviewPage(driver).waitUntilLoaded();
    }

    public AccountsOverviewPage login(String username, String password) {
        return enterUsername(username)
                .enterPassword(password)
                .submitExpectingSuccess();
    }

    public AccountsOverviewPage loginAsDefaultUser() {
        return login(ConfigReader.getUsername(), ConfigReader.getPassword());
    }

    public boolean isLoginFormDisplayed() {
        return isDisplayed(loginButton) && isDisplayed(registerLink);
    }

    public String getLoginErrorMessage() {
        return getText(errorMessage);
    }

    public String getUsernameValue() {
        return getValue(usernameInput);
    }
}
