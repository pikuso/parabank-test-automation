package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private By usernameInput = By.name("username");
    private By passwordInput = By.name("password");
    private By loginButton = By.xpath("//input[@value='Log In']");
    private By accountsOverviewLink = By.linkText("Accounts Overview");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        click(loginButton);
    }

    public boolean isLoginSuccessful() {
        return isDisplayed(accountsOverviewLink);
    }
}