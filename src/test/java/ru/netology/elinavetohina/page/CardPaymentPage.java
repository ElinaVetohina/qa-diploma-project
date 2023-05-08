package ru.netology.elinavetohina.page;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$x;

public final class CardPaymentPage extends PaymentsPage {
    private CardPaymentPage() {
        $x("//h3[text()='Оплата по карте']").shouldBe(Condition.visible);
    }
}
