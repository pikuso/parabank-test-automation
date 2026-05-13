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
        // 1. Открываем страницу и сохраняем в переменную
        TransferFundsPage transferFundsPage = loginAsDefaultUser().openTransferFundsPage();

        // 2. Ждем, чтобы сайт прогрузил счета в выпадающие списки
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }

        // 3. Выполняем действия по отдельности, чтобы не терять объект страницы
        transferFundsPage.enterAmount("100");
        transferFundsPage.selectFirstFromAccount();
        transferFundsPage.selectFirstToAccount();
        transferFundsPage.submitTransfer();

        // 4. Проверяем результат
        Assertions.assertTrue(transferFundsPage.isTransferSuccessful(), "Ошибка: перевод не был завершен успешно");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "25", "100", "250"})
    void userCanTransferFundsWithDifferentAmounts(String amount) {
        // 1. Открываем страницу
        TransferFundsPage transferFundsPage = loginAsDefaultUser().openTransferFundsPage();

        // 2. Ждем подгрузки данных
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }

        // 3. Выполняем шаги перевода
        transferFundsPage.enterAmount(amount);
        transferFundsPage.selectFirstFromAccount();
        transferFundsPage.selectFirstToAccount();
        transferFundsPage.submitTransfer();

        // 4. Проверка
        Assertions.assertTrue(transferFundsPage.isTransferSuccessful(), "Ошибка: перевод суммы " + amount + " не удался");
    }

    @Test
    void amountFieldStoresTypedTransferValue() {
        TransferFundsPage transferFundsPage = loginAsDefaultUser().openTransferFundsPage();

        transferFundsPage.enterAmount("77");

        Assertions.assertEquals("77", transferFundsPage.getAmountValue());
    }
}