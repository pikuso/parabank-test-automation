package com.parabank.tests;

import com.parabank.pages.TransferFundsPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class TransferFundsTest extends BaseTest {

    @Test
    void transferFundsPageTitleIsCorrect() {
        TransferFundsPage transferFundsPage = loginAsDefaultUser().openTransferFundsPage();

        Assertions.assertEquals("Transfer Funds", transferFundsPage.getTitle());
    }

    @Test
    void userCanTransferFunds() {
        TransferFundsPage transferFundsPage = loginAsDefaultUser()
                .openTransferFundsPage()
                .enterAmount("100")
                .selectFirstFromAccount()
                .selectFirstToAccount()
                .submitTransfer();

        Assertions.assertTrue(transferFundsPage.isTransferSuccessful());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "25", "100", "250"})
    void userCanTransferFundsWithDifferentAmounts(String amount) {
        TransferFundsPage transferFundsPage = loginAsDefaultUser()
                .openTransferFundsPage()
                .enterAmount(amount)
                .selectFirstFromAccount()
                .selectFirstToAccount()
                .submitTransfer();

        Assertions.assertTrue(transferFundsPage.isTransferSuccessful());
    }

    @Test
    void amountFieldStoresTypedTransferValue() {
        TransferFundsPage transferFundsPage = loginAsDefaultUser()
                .openTransferFundsPage()
                .enterAmount("77");

        Assertions.assertEquals("77", transferFundsPage.getAmountValue());
    }
}
