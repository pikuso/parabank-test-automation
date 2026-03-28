package com.parabank.tests;

import com.parabank.pages.BillPayPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class BillPayTest extends BaseTest {

    @Test
    void billPayPageTitleIsCorrect() {
        BillPayPage billPayPage = loginAsDefaultUser().openBillPayPage();

        Assertions.assertEquals("Bill Payment Service", billPayPage.getTitle());
    }

    @Test
    void userCanPayBill() {
        BillPayPage billPayPage = loginAsDefaultUser()
                .openBillPayPage()
                .completePayment("100", "12345");

        Assertions.assertTrue(billPayPage.isPaymentSuccessful());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "15", "50", "125"})
    void userCanPayBillWithDifferentAmounts(String amount) {
        BillPayPage billPayPage = loginAsDefaultUser()
                .openBillPayPage()
                .completePayment(amount, "98765");

        Assertions.assertTrue(billPayPage.isPaymentSuccessful());
    }

    @Test
    void amountFieldStoresTypedBillPayValue() {
        BillPayPage billPayPage = loginAsDefaultUser()
                .openBillPayPage()
                .enterAmount("88");

        Assertions.assertEquals("88", billPayPage.getAmountValue());
    }
}
