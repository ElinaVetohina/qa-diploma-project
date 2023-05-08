package ru.netology.elinavetohina.test;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.elinavetohina.data.DataManager;
import ru.netology.elinavetohina.page.MainPage;

import static com.codeborne.selenide.Selenide.open;

public class CardPaymentTest extends BaseTest {
    @Test
    @DisplayName("При покупке разрешенной картой, отображается сообщение об одобрении банком")
    @Epic("Оплата картой")
    @Feature("Оплата разрешенной картой")
    @Story("Всплывающее окно со статусом операции")
    void shouldSuccessMessageIfPaymentOfCard() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var cardPaymentPage = mainPage.paymentByCard();

        cardPaymentPage.pay(DataManager.getApprovedCardInfo());
        cardPaymentPage.shouldNotificationSuccessMessage("Операция одобрена Банком.");
    }

    @Test
    @DisplayName("При покупке запрещенной картой отображается сообщение об отказе банком")
    @Epic("Оплата картой")
    @Feature("Оплата запрещенной картой")
    @Story("Всплывающее окно со статусом операции")
    void shouldErrorMessageIfPaymentOfDeclinedCard() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var cardPaymentPage = mainPage.paymentByCard();

        cardPaymentPage.pay(DataManager.getDeclinedCardInfo());

        cardPaymentPage.shouldNotificationErrorMessage("Банк отказал в проведении операции");
    }

    @Test
    @DisplayName("При покупке картой отсутствующей в системе отображается сообщение об отказе банком")
    @Epic("Оплата картой")
    @Feature("Оплата картой отсутствующей в системе")
    @Story("Всплывающее окно со статусом операции")
    void ShouldErrorMsg() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var cardPaymentPage = mainPage.paymentByCard();
        cardPaymentPage.pay(DataManager.getFiledCardInfo());

        cardPaymentPage.shouldNotificationErrorMessage("Банк отказал в проведении операции");
    }

}
