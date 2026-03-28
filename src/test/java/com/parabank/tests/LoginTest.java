package com.parabank.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.parabank.pages.AccountsOverviewPage;
import com.parabank.pages.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class LoginTest extends BaseTest {

    @Test
    void userCanLoginWithDefaultCredentials() {
        AccountsOverviewPage accountsOverviewPage = loginPage().loginAsDefaultUser();

        assertAll(
                () -> assertTrue(accountsOverviewPage.isLoaded()),
                () -> Assertions.assertEquals("Accounts Overview", accountsOverviewPage.getTitle()));
    }

    @ParameterizedTest
    @CsvSource({
            "john,wrong-password",
            "wrong-user,demo",
            "wrong-user,wrong-password"
    })
    void userCannotLoginWithInvalidCredentials(String username, String password) {
        LoginPage loginPage = loginPage()
                .enterUsername(username)
                .enterPassword(password)
                .submitExpectingFailure();

        Assertions.assertTrue(loginPage.getLoginErrorMessage().contains("could not be verified"));
    }

    @Test
    void loginFormIsVisibleOnHomePage() {
        Assertions.assertTrue(loginPage().isLoginFormDisplayed());
    }

    @Test
    void usernameFieldStoresEnteredValue() {
        LoginPage loginPage = loginPage().enterUsername("john");

        Assertions.assertEquals("john", loginPage.getUsernameValue());
    }
}
