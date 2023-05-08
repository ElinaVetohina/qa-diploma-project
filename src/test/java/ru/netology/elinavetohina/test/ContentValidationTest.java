package ru.netology.elinavetohina.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.elinavetohina.page.MainPage;


import static com.codeborne.selenide.Selenide.open;

public class ContentValidationTest extends BaseTest {

    @Test
    @DisplayName("Заголовок страницы соответствуют содержанию")
    void ShouldPageTitle() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var title = mainPage.getPageTitle();
        var heading = mainPage.getPageHeading();

        Assertions.assertEquals(title, heading);
    }

    @Test
    @DisplayName("Корректность написания названия города")
    void VerifyCityName() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var cityTourName = mainPage.getCityTourName();

        Assertions.assertEquals("Марракеш", cityTourName);

    }

    @Test
    void shouldHaveRedColorInPaymentIfSelected() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var cardPaymentPage = mainPage.paymentByCard();

        cardPaymentPage.shouldHaveRedColorInPaymentButtonByCardIfSelected();
    }

    @Test
    void shouldHaveRedColorInPaymentByCreditButtonIfSelected() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var creditPaymentPage = mainPage.paymentByCredit();

        creditPaymentPage.shouldHaveRedColorInPaymentByCreditButtonIfSelected();
    }
}
