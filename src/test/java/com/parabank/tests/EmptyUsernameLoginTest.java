package com.parabank.tests;

import com.parabank.pages.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmptyUsernameLoginTest extends BaseTest {

    @Test
    void userCannotLoginWithEmptyUsername() {
        LoginPage loginPage = loginPage()
                .enterUsername("")
                .enterPassword("demo")
                .submitExpectingFailure();

        String errorMessage = loginPage.getLoginErrorMessage();
        Assertions.assertFalse(errorMessage.isBlank());
    }
}
