package com.parabank.tests;

import com.parabank.pages.AccountsOverviewPage;
import com.parabank.pages.BillPayPage;
import com.parabank.pages.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("mobile")
public class MobileSmokeTest extends BaseTest {

    @Override
    protected String resolveBrowser() {
        return "chrome-mobile";
    }

    @Test
    void mobileLoginFlowWorksWhenChromeEmulationIsEnabled() {
        AccountsOverviewPage accountsOverviewPage = loginPage().loginAsDefaultUser();

        Assertions.assertTrue(accountsOverviewPage.isLoaded());
    }

    @Test
    void mobileLoginPageStillShowsLoginForm() {
        LoginPage loginPage = loginPage();

        Assertions.assertTrue(loginPage.isLoginFormDisplayed());
    }

    @Test
    void mobileBillPayPageCanBeOpenedAfterLogin() {
        BillPayPage billPayPage = loginAsDefaultUser().openBillPayPage();

        Assertions.assertEquals("Bill Payment Service", billPayPage.getTitle());
    }
}
