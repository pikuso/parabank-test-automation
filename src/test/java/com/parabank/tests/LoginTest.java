package com.parabank.tests;

import com.parabank.pages.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class LoginTest extends BaseTest {

    @Test
    public void userCanLogin() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("john", "demo");
        Assertions.assertTrue(loginPage.isLoginSuccessful());
    }
}