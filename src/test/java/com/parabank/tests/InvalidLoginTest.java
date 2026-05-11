package com.parabank.tests;

import com.parabank.pages.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InvalidLoginTest extends BaseTest {

    @Test
    void userCannotLoginWithInvalidCredentials() {
        LoginPage loginPage = loginPage()
                .enterUsername("wrong-user")
                .enterPassword("wrong-password")
                .submitExpectingFailure();

        Assertions.assertTrue(loginPage.getLoginErrorMessage().contains("could not be verified"));
    }
}
