package ru.netology.elinavetohina.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;

public class MainPage {
    private MainPage() {
        $x("//h2[contains(class,heading)]").shouldBe(Condition.visible);
    }

    @FindBy(xpath = "//*[text()='Купить']")
    SelenideElement payOnCardButton;
    @FindBy(xpath = "//h2[contains(class,heading)]")
    SelenideElement heading;
    @FindBy(tagName = "h3")
    SelenideElement cityName;
    @FindBy(xpath = "//*[text()='Купить в кредит']")
    SelenideElement payCreditCardButton;

    @FindBy(tagName = "title")
    SelenideElement title;


    public CardPaymentPage paymentByCard() {
        payOnCardButton.click();
        return page(CardPaymentPage.class);
    }

    public CreditPaymentPage paymentByCredit() {
        payCreditCardButton.click();
        return page(CreditPaymentPage.class);
    }

    public String getPageTitle() {
        return title.getAttribute("innerText");
    }

    public String getPageHeading() {
        return heading.text();
    }

    public String getCityTourName() {
        return cityName.text();
    }
}
