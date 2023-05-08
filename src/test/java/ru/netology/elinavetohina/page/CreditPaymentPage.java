package ru.netology.elinavetohina.page;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$x;

public final class CreditPaymentPage extends PaymentsPage {
    private CreditPaymentPage() {
        $x("//h3[text()='Кредит по данным карты']").shouldBe(Condition.visible);
    }
}
