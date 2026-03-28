package com.parabank.tests;

import com.parabank.pages.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmptyPasswordLoginTest extends BaseTest {

    @Test
    void userCannotLoginWithEmptyPassword() {
        LoginPage loginPage = loginPage()
                .enterUsername("john")
                .enterPassword("")
                .submitExpectingFailure();

        String errorMessage = loginPage.getLoginErrorMessage();
        Assertions.assertFalse(errorMessage.isBlank());
    }
}
