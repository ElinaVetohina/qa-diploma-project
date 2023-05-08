package ru.netology.elinavetohina.test;

import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.elinavetohina.data.ApiManager;
import ru.netology.elinavetohina.data.DataManager;
import ru.netology.elinavetohina.data.DatabaseManager;


public class SqlApiTest extends BaseTest {
    @Test
    @DisplayName("При оплате разрешённой картой, статус в базе данных 'APPROVED'")
    @Epic("Оплата картой")
    @Feature("Оплата разрешенной картой")
    @Story("Статус операции в БД")
    void shouldSuccessStatusIfPaymentByApprovedCard() {
        var cardInfo = DataManager.getApprovedCardInfo();
        ApiManager.cardPayment(cardInfo);
        var paymentCardData = DatabaseManager.getPaymentsEntityData();

        Assertions.assertEquals("APPROVED", paymentCardData.getStatus());
    }

    @Test
    @DisplayName("При оплате запрещенной картой, статус в базе данных 'DECLINED'")
    @Epic("Оплата картой")
    @Feature("Оплата запрещенной картой")
    @Story("Статус операции в БД")
    void shouldFiledStatusIfPaymentByDeclinedCard() {
        var cardInfo = DataManager.getDeclinedCardInfo();
        ApiManager.cardPayment(cardInfo);
        var paymentCardData = DatabaseManager.getPaymentsEntityData();

        Assertions.assertEquals("DECLINED", paymentCardData.getStatus());
    }

    @Test
    @DisplayName("При оплате в кредит разрешенной картой, статус в базе данных APPROVED")
    @Epic("Оплата в кредит")
    @Feature("Оплата разрешенной картой")
    @Story("Статус операции в БД")
    void shouldFieldStatusIfPaymentByCreditApprovedCard() {
        var cardInfo = DataManager.getApprovedCardInfo();
        ApiManager.creditPayment(cardInfo);
        var paymentCreditData = DatabaseManager.getCreditRequestEntityData();

        Assertions.assertEquals("APPROVED", paymentCreditData.getStatus());
    }

    @Test
    @DisplayName("При оплате в кредит запрещенной картой, статус в базе данных DECLINED")
    @Epic("Оплата в кредит")
    @Feature("Оплата запрещенной картой")
    @Story("Статус опеоации в БД")
    void shouldFieldStatusIfPaymentByCreditDeclinedCard() {
        var cardInfo = DataManager.getDeclinedCardInfo();
        ApiManager.creditPayment(cardInfo);
        var paymentCreditData = DatabaseManager.getCreditRequestEntityData();

        Assertions.assertEquals("DECLINED", DatabaseManager.getCreditRequestEntityData().getStatus());
    }

    @Test
    @DisplayName("При оплате картой, в таблицу order_entity заносится ID транзакции в поле 'payment_id'")
    @Epic("Оплата картой")
    @Story("Корректность занесения данных в таблицу order_entity")
    void ShouldTransactionIdWrittenInPaymentIdField() {
        var cardInfo = DataManager.getApprovedCardInfo();
        ApiManager.cardPayment(cardInfo);

        var transactionId = DatabaseManager.getPaymentsEntityData().getTransaction_id();
        var orderEntityData = DatabaseManager.getOrderEntityData();

        Assertions.assertEquals(transactionId, orderEntityData.getPayment_id());
    }

    @Test
    @DisplayName("При оплате в кредит, в таблицу order_entity  в поле credit_id заносится значение из bank_id таблицы credit_request_entity)")
    @Epic("Оплата в кредит")
    @Story("Корректность занесения данных в таблицу order_entity")
    void ShouldFieldValueCreditIdWrittenInFieldCreditId() {
        var cardInfo = DataManager.getApprovedCardInfo();
        ApiManager.creditPayment(cardInfo);

        var bankId = DatabaseManager.getCreditRequestEntityData().getBank_id();
        var orderEntityData = DatabaseManager.getOrderEntityData();

        Assertions.assertEquals(bankId, orderEntityData.getCredit_id());
    }

    @Test
    @DisplayName("При оплате картой отсутствующей в системе, Данные не заносятся в БД")
    @Epic("Оплата картой")
    @Feature("Оплата картой отсутствующей в системе")
    @Story("Отсутствие новых данных в столицах ")
    void ShouldNotAmountRowsWhenPaymentFiledCard() {
        var cardInfo = DataManager.getFiledCardInfo();
        var startRowsAmountInPaymentEntity = DatabaseManager.getRowsAmountFromPaymentEntityTable();
        var startRowsAmountInOrderEntity = DatabaseManager.getRowAmountFromOrderEntityTable();

        ApiManager.cardPaymentWhenUsedFiledCard(cardInfo);

        var currentRowsAmountInPaymentEntity = DatabaseManager.getRowsAmountFromPaymentEntityTable();
        var currentRowsAmountInOrderEntity = DatabaseManager.getRowAmountFromOrderEntityTable();

        Assertions.assertEquals(startRowsAmountInPaymentEntity, currentRowsAmountInPaymentEntity);
        Assertions.assertEquals(startRowsAmountInOrderEntity, currentRowsAmountInOrderEntity);
    }

    @Test
    @DisplayName("При оплате в кредит по карте отсутствующей в системе, данные не заносятся в БД")
    @Epic("Оплата в кредит")
    @Feature("Оплата картой отсутствующей в системе")
    @Story("Отсутствие новых данных в столицах ")
    void ShouldNotAmountRowsWhenPaymentByCreditFiledCard() {
        var cardInfo = DataManager.getFiledCardInfo();
        var startRowsAmountInCreditRequestEntity = DatabaseManager.getRowAmountFromCreditRequestEntityTable();
        var startRowsAmountInOrderEntity = DatabaseManager.getRowAmountFromOrderEntityTable();


        ApiManager.creditPaymentWhenUsedFiledCard(cardInfo);

        var currentRowsAmountInCreditRequestEntity = DatabaseManager.getRowAmountFromCreditRequestEntityTable();
        var currentRowsAmountInOrderEntity = DatabaseManager.getRowAmountFromOrderEntityTable();


        Assertions.assertEquals(startRowsAmountInCreditRequestEntity, currentRowsAmountInCreditRequestEntity);
        Assertions.assertEquals(startRowsAmountInOrderEntity, currentRowsAmountInOrderEntity);
    }
}
