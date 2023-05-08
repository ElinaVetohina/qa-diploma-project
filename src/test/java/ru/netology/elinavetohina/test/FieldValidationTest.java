package ru.netology.elinavetohina.test;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Features;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.elinavetohina.data.DataManager;
import ru.netology.elinavetohina.page.MainPage;

import static com.codeborne.selenide.Selenide.open;

public class FieldValidationTest extends BaseTest {

    @Test
    @DisplayName("При оплате картой и указании даты действия карты равной текущей - отображается сообщение об одобрении банком")
    @Epic("Валидация полей")
    @Features({@Feature("Поле года"), @Feature("Поле месяца")})
    @Story("Нижняя граница срока действия")
    void shouldSuccessMessageIfDateIsCurrent() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var cardPaymentPage = mainPage.paymentByCard();

        cardPaymentPage.pay(DataManager.getCurrentDatePlusMonths(0));

        cardPaymentPage.shouldNotificationSuccessMessage("Операция одобрена Банком.");
    }

    @Test
    @DisplayName("При оплата картой с указанием даты действия в верхней границе (текущая дата + 5 лет) - отображается сообщение об одобрении банком")
    @Epic("Валидация полей")
    @Features({@Feature("Поле Год"), @Feature("Поле Месяц")})
    @Story("Верхняя граница срока действия")
    void shouldErrorMessageIfDateInUpperBorder() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var cardPaymentPage = mainPage.paymentByCard();

        cardPaymentPage.pay(DataManager.getCurrentDatePlusMonths(60));

        cardPaymentPage.shouldNotificationSuccessMessage("Операция одобрена Банком.");
    }

    @Test
    @DisplayName("При оплате картой с указанием даты действия выше верхней границы (5 лет + 1 месяц) - заявка не отправляется, ошибка валидации поля 'Неверно указан срок действия карты' ")
    @Epic("Валидация полей")
    @Feature("Поле Год")
    @Story("Выше верхней границы срока действия")
    void shouldValidationErrorCardValidityDateFieldPlus61Month() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var cardPaymentPage = mainPage.paymentByCard();

        cardPaymentPage.pay(DataManager.getCurrentDatePlusMonths(61));

        cardPaymentPage.shouldYearFieldSubErrorMsg("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("При оплате картой и указании даты действия выше верхней границы (5 лет + 2 месяца) заявка не отправляется - ошибка валидации поля 'Неверно указан срок действия карты'")
    @Epic("Валидация полей")
    @Feature("Поле Год")
    @Story("Выше верхней  границы срока действия")
    void shouldValidationErrorCardValidityDateFieldPlus62Months() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var cardPaymentPage = mainPage.paymentByCard();

        cardPaymentPage.pay(DataManager.getCurrentDatePlusMonths(62));

        cardPaymentPage.shouldYearFieldSubErrorMsg("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("При оплате картой с указанием даты действия меньше текущей границы - заявка не отправляется, ошибка валидации поля 'Неверно указан срок действия карты'")
    @Epic("Валидация полей")
    @Feature("Поле месяца")
    @Story("Ниже нижней  границы срока действия")
    void shouldValidationErrorIfCardValidityDateLessCurrent() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var cardPaymentPage = mainPage.paymentByCard();

        cardPaymentPage.pay(DataManager.getCurrentDatePlusMonths(-1));

        cardPaymentPage.shouldMonthFieldSubErrorMsg("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("При оплате картой с нулевым месяцем - ошибка валидации поля месяца. 'Неверно указан срок действия карты'")
    @Epic("Валидация полей")
    @Feature("Поле месяца")
    @Story("Поле месяца со значением '00'")
    void shouldValidationErrorErrorIfValueOfTheMonthsIsZero() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var creditPaymentPage = mainPage.paymentByCredit();


        creditPaymentPage.pay(DataManager.getZeroMonthCardInfo());

        creditPaymentPage.shouldMonthFieldSubErrorMsg("Неверно указан срок действия карты");
    }


    @Test
    @DisplayName("При оплате в кредит с пустым полем номера карты - ошибка валидации поля, Поле обязательно для заполнения")
    @Epic("Валидация полей")
    @Feature("Поле номера карты")
    @Story("Пустое поле")
    void shouldValidationErrorCardNumberFieldIfValueIsEmpty() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var creditPaymentPage = mainPage.paymentByCredit();

        creditPaymentPage.pay(DataManager.getEmptyCardNumberCardNumberInfo());

        creditPaymentPage.shouldCardNumberFieldSubErrorMsg("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("При оплате картой с пустым полем месяца, ошибка валидации поля, 'Поле обязательно для заполнения'")
    @Epic("Валидация полей")
    @Feature("Поле месяца")
    @Story("Пустое поле")
    void shouldValidationErrorMonthFieldErrorIfValueIsEmpty() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var cardPaymentPage = mainPage.paymentByCard();

        cardPaymentPage.pay(DataManager.getEmptyMonthCardInfo());

        cardPaymentPage.shouldMonthFieldSubErrorMsg("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("При оплате картой с пустым полем года, ошибка валидации поля, Поле обязательно для заполнения")
    @Epic("Валидация полей")
    @Feature("Поле года")
    @Story("Пустое поле")
    void shouldValidationErrorIfYearFieldIsEmpty() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var cardPaymentPage = mainPage.paymentByCard();

        cardPaymentPage.pay(DataManager.getEmptyYearCardInfo());

        cardPaymentPage.shouldYearFieldSubErrorMsg("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("При оплате в кредит с пустым полем имени, ошибка валидации поля, Поле обязательно для заполнения")
    @Epic("Валидация полей")
    @Feature("Поле имени")
    @Story("Пустое поле")
    void shouldValidationErrorIfHolderFieldIsEmpty() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var creditPaymentPage = mainPage.paymentByCredit();

        creditPaymentPage.pay(DataManager.getEmptyHolderCardInfo());

        creditPaymentPage.shouldHolderFieldSubErrorMsg("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("При оплате картой с пустым полем CVC, ошибка валидации поля, Поле обязательно для заполнения")
    @Epic("Валидация полей")
    @Feature("Поле CVC")
    @Story("Пустое поле")
    void shouldValidationErrorIfCvvFieldIsEmpty() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var cardPaymentPage = mainPage.paymentByCard();

        cardPaymentPage.pay(DataManager.getEmptyCvcCardInfo());

        cardPaymentPage.shouldCvcFieldSubErrorMsg("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("При оплата с использованием имени кириллицей, ошибка валидация поля 'Неверный формат'")
    @Epic("Валидация полей")
    @Feature("Поле имени")
    @Story("Имя кириллицей")
    void ShouldValidationErrorIfHolderNameIsCyrillic() {
        var mainPage = open("http://localhost:8080", MainPage.class);
        var cardPaymentPage = mainPage.paymentByCard();

        cardPaymentPage.pay(DataManager.getCyrillicHolderNameCardInfo());

        cardPaymentPage.shouldHolderFieldSubErrorMsg("Неверный формат");
    }
}
