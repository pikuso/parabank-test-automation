package com.parabank.tests;

import com.parabank.pages.LoginPage;
import com.parabank.pages.OpenAccountPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OpenAccountTest extends BaseTest {

    @Test
    public void userCanOpenNewAccount() {

        loginAsDefaultUser();

        OpenAccountPage openAccountPage = new OpenAccountPage(driver);

        openAccountPage.openPage();
        openAccountPage.selectAccountType("SAVINGS");
        openAccountPage.selectFromAccount();
        openAccountPage.submitNewAccount();

        Assertions.assertTrue(openAccountPage.isAccountCreated());
    }
}