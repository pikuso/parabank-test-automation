package com.parabank.tests;

import com.parabank.pages.BillPayPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BillPayTest extends BaseTest {

    @Test
    public void userCanPayBill() {

        loginAsDefaultUser();

        BillPayPage billPayPage = new BillPayPage(driver);

        billPayPage.openPage();
        billPayPage.enterPayeeName("John Smith");
        billPayPage.enterAddress("Main Street 1");
        billPayPage.enterCity("New York");
        billPayPage.enterState("NY");
        billPayPage.enterZipCode("10001");
        billPayPage.enterPhone("123456789");
        billPayPage.enterAccountNumber("12345");
        billPayPage.verifyAccountNumber("12345");

        billPayPage.enterAmount("100");

        billPayPage.submitPayment();

        Assertions.assertTrue(billPayPage.isPaymentSuccessful());
    }
}