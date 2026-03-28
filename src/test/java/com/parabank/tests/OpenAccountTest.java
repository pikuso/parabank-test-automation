package com.parabank.tests;

import com.parabank.pages.OpenAccountPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class OpenAccountTest extends BaseTest {

    @Test
    void openAccountPageTitleIsCorrect() {
        OpenAccountPage openAccountPage = loginAsDefaultUser().openNewAccountPage();

        Assertions.assertEquals("Open New Account", openAccountPage.getTitle());
    }

    @ParameterizedTest
    @ValueSource(strings = {"SAVINGS", "CHECKING"})
    void userCanOpenNewAccountForSupportedTypes(String accountType) {
        OpenAccountPage openAccountPage = loginAsDefaultUser()
                .openNewAccountPage()
                .selectAccountType(accountType)
                .selectFirstFromAccount()
                .submitNewAccount();

        Assertions.assertTrue(openAccountPage.isAccountCreated());
    }

    @Test
    void createdAccountHasGeneratedIdentifier() {
        OpenAccountPage openAccountPage = loginAsDefaultUser()
                .openNewAccountPage()
                .selectAccountType("SAVINGS")
                .selectFirstFromAccount()
                .submitNewAccount();

        Assertions.assertFalse(openAccountPage.getNewAccountId().isBlank());
    }
}
