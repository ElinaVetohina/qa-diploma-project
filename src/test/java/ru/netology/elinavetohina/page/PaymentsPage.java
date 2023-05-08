package ru.netology.elinavetohina.page;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.netology.elinavetohina.data.DataManager;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class PaymentsPage {

    @FindBy(xpath = "//*[text()='Купить']")
    SelenideElement payOnCardButton;
    @FindBy(xpath = "//*[text()='Купить в кредит']")
    SelenideElement payCreditCardButton;

    @FindBy(xpath = "//*[text()='Номер карты']/..//input")
    SelenideElement cardNumberField;

    @FindBy(xpath = "//*[text()='Номер карты']//..//*[@class='input__sub']")
    SelenideElement cardNumberFieldSub;
    @FindBy(xpath = "//*[text()='Месяц']/..//input")
    SelenideElement monthField;

    @FindBy(xpath = "//*[text()='Месяц']//..//*[@class='input__sub']")
    SelenideElement monthFieldSub;

    @FindBy(xpath = "//*[text()='Год']/..//input")
    SelenideElement yearField;

    @FindBy(xpath = "//*[text()='Год']//..//*[@class='input__sub']")
    SelenideElement yearFieldSub;

    @FindBy(xpath = "//*[text()='Владелец']/..//input")
    SelenideElement holderField;

    @FindBy(xpath = "//*[text()='Владелец']//..//*[@class='input__sub']")
    SelenideElement holderFieldSub;
    @FindBy(xpath = "//*[text()='CVC/CVV']/..//input")
    SelenideElement cvcField;

    @FindBy(xpath = "//*[text()='CVC/CVV']//..//*[@class='input__sub']")
    SelenideElement cvcFieldSub;
    @FindBy(xpath = "//*[text()='Продолжить']")
    SelenideElement continueButton;

    @FindBy(css = ".notification")
    SelenideElement notification;
    @FindBy(css = ".notification_status_ok")
    SelenideElement notificationSuccess;
    @FindBy(css = ".notification_status_error")
    SelenideElement notificationFailed;

    @FindBy(css = ".notification_status_error button")
    SelenideElement notificationFailedButton;
    @FindBy(css = ".notification button")
    SelenideElement notificationWindowCloseButton;

    public void pay(DataManager.CardInfo info) {
        cardNumberField.setValue(info.getNumber());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        holderField.setValue(info.getHolder());
        cvcField.setValue(info.getCvc());
        continueButton.click();
    }

    public void shouldNotificationSuccessMessage(String msg) {
        notificationSuccess.shouldBe(Condition.text(msg), Duration.ofSeconds(11));
        notificationWindowCloseButton.click();
        notificationFailed.shouldBe(Condition.not(Condition.visible));
    }

    public void shouldNotificationErrorMessage(String msg) {
        notificationFailed.shouldBe(Condition.text(msg), Duration.ofSeconds(11));
        notificationFailedButton.click();
        notificationSuccess.shouldBe(Condition.not(Condition.visible));
    }

    public void shouldCardNumberFieldSubErrorMsg(String subMsg) {
        cardNumberFieldSub.shouldHave(Condition.text(subMsg));
    }

    public void shouldMonthFieldSubErrorMsg(String subMsg) {
        monthFieldSub.shouldHave(Condition.text(subMsg));
    }

    public void shouldYearFieldSubErrorMsg(String subMsg) {
        yearFieldSub.shouldHave(Condition.text(subMsg));
    }

    public void shouldHolderFieldSubErrorMsg(String subMsg) {
        holderFieldSub.shouldHave(Condition.text(subMsg));
    }

    public void shouldCvcFieldSubErrorMsg(String subMsg) {
        cvcFieldSub.shouldHave(Condition.text(subMsg));
    }

    public void shouldHaveRedColorInPaymentButtonByCardIfSelected() {
        $x("//*[text()='Купить']/../..").shouldHave(Condition.attributeMatching("class", ".*button_view_extra.*"));
    }

    public void shouldHaveRedColorInPaymentByCreditButtonIfSelected() {
        $x("//*[text()='Купить в кредит']/../..").shouldHave(Condition.attributeMatching("class", ".*button_view_extra.*"));
    }


}
