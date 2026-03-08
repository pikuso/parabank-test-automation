package com.parabank.tests;

import com.parabank.pages.TransferFundsPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class TransferFundsTest extends BaseTest {

    @Test
    public void userCanTransferFunds() {

        loginAsDefaultUser();

        TransferFundsPage transferFundsPage = new TransferFundsPage(driver);

        transferFundsPage.openPage();
        transferFundsPage.enterAmount("100");
        transferFundsPage.selectFromAccount();
        transferFundsPage.selectToAccount();
        transferFundsPage.submitTransfer();

        Assertions.assertTrue(transferFundsPage.isTransferSuccessful());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "100", "500"})
    public void userCanTransferFundsWithDifferentAmounts(String amount) {

        loginAsDefaultUser();

        TransferFundsPage transferFundsPage = new TransferFundsPage(driver);

        transferFundsPage.openPage();
        transferFundsPage.enterAmount(amount);
        transferFundsPage.selectFromAccount();
        transferFundsPage.selectToAccount();
        transferFundsPage.submitTransfer();

        Assertions.assertTrue(transferFundsPage.isTransferSuccessful());
    }
}