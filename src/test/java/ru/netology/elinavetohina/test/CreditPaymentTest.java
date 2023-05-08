
package ru.netology.elinavetohina.test;


import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.elinavetohina.data.DataManager;
import ru.netology.elinavetohina.page.MainPage;

import static com.codeborne.selenide.Selenide.open;

public class CreditPaymentTest extends BaseTest {
    @Test
    @DisplayName("При оплате в кредит разрешенной картой, отображается сообщение об подтверждении банком")
    @Epic("Оплата в кредит")
    @Feature("Оплата разрешенной картой")
    @Story("Всплывающее окно со статусом операции")
    void ShouldSuccessMessageIfPaymentByCreditApprovedCard() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var creditPaymentPage = mainPage.paymentByCredit();

        creditPaymentPage.pay(DataManager.getApprovedCardInfo());

        creditPaymentPage.shouldNotificationSuccessMessage("Операция одобрена Банком.");
    }

    @Test
    @DisplayName("При оплате в кредит запрещенной картой отображается сообщение об отказе банком")
    @Epic("Оплата в кредит")
    @Feature("Оплата запрещенной картой")
    @Story("Всплывающее окно со статусом операции")
    void ShouldErrorMessageIfPaymentByCreditDeclinedCard() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var creditPaymentPage = mainPage.paymentByCredit();

        creditPaymentPage.pay(DataManager.getDeclinedCardInfo());

        creditPaymentPage.shouldNotificationSuccessMessage("Банк отказал в проведении операции");
    }

    @Test
    @DisplayName("При оплате в кредит картой отсутствующей в системе отображается сообщение об отказе банком")
    @Epic("Оплата в кредит")
    @Feature("Оплата картой отсутствующей в системе")
    @Story("Всплывающее окно со статусом операции")
    void ShouldErrorMsg() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var creditPaymentPage = mainPage.paymentByCredit();
        creditPaymentPage.pay(DataManager.getFiledCardInfo());

        creditPaymentPage.shouldNotificationErrorMessage("Банк отказал в проведении операции");
    }


}
