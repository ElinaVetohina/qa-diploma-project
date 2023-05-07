package ru.netology.elinavetohina.data;

import com.github.javafaker.Faker;
import io.netty.util.internal.ThreadLocalRandom;
import lombok.Value;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class DataManager {
    private static final String APPROVED_CARD_NUMBER = "4444 4444 4444 4441";
    private static final String DECLINED_CARD_NUMBER = "4444 4444 4444 4442";
    private static YearMonth date;
    private static final Faker faker = new Faker(new Locale("EN"));
    private static final Faker fakerRu = new Faker(new Locale("RU"));

    private static String generateRandomCardNumber() {
        return faker.numerify("################");
    }

    public static CardInfo getApprovedCardInfo() {
        return new CardInfo(
                APPROVED_CARD_NUMBER,
                getRandomDate().getMonth(),
                getRandomDate().getYear(),
                generateRandomFullName(),
                generateRandomCVV()
        );
    }

    public static CardInfo getDeclinedCardInfo() {
        return new CardInfo(
                DECLINED_CARD_NUMBER,
                getRandomDate().getMonth(),
                getRandomDate().getYear(),
                generateRandomFullName(),
                generateRandomCVV()
        );
    }

    public static CardInfo getFiledCardInfo() {
        return new CardInfo(
                generateRandomCardNumber(),
                getRandomDate().getMonth(),
                getRandomDate().getYear(),
                generateRandomFullName(),
                generateRandomCVV()

        );
    }

    public static CardInfo getCurrentDatePlusMonths(long plusMonth) {
        return new CardInfo(
                APPROVED_CARD_NUMBER,
                getFutureDatePlusMonth(plusMonth).getMonth(),
                getFutureDatePlusMonth(plusMonth).getYear(),
                generateRandomFullName(),
                generateRandomCVV()
        );
    }

    public static CardInfo getZeroMonthCardInfo() {
        return new CardInfo(
                APPROVED_CARD_NUMBER,
                "00",
                getFutureDatePlusMonth(12).getYear(),
                generateRandomFullName(),
                generateRandomCVV()
        );
    }

    public static CardInfo getEmptyCardNumberCardNumberInfo() {
        return new CardInfo(
                "",
                getRandomDate().getMonth(),
                getRandomDate().getYear(),
                generateRandomFullName(),
                generateRandomCVV()
        );
    }

    public static CardInfo getEmptyMonthCardInfo() {
        return new CardInfo(
                DECLINED_CARD_NUMBER,
                "",
                getRandomDate().getYear(),
                generateRandomFullName(),
                generateRandomCVV()
        );
    }

    public static CardInfo getEmptyYearCardInfo() {
        return new CardInfo(
                DECLINED_CARD_NUMBER,
                getRandomDate().getMonth(),
                "",
                generateRandomFullName(),
                generateRandomCVV()
        );
    }

    public static CardInfo getEmptyHolderCardInfo() {
        return new CardInfo(
                DECLINED_CARD_NUMBER,
                getRandomDate().getMonth(),
                getRandomDate().getYear(),
                "",
                generateRandomCVV()
        );
    }

    public static CardInfo getEmptyCvcCardInfo() {
        return new CardInfo(
                DECLINED_CARD_NUMBER,
                getRandomDate().getMonth(),
                getRandomDate().getYear(),
                generateRandomFullName(),
                ""
        );
    }

    public static CardInfo getCyrillicHolderNameCardInfo() {
        return new CardInfo(
                APPROVED_CARD_NUMBER,
                getRandomDate().getMonth(),
                getRandomDate().getYear(),
                fakerRu.name().fullName(),
                generateRandomCVV()

        );
    }

    private static void generateRandomDate() {
        YearMonth now = YearMonth.now();
        int randomNumber = ThreadLocalRandom.current().nextInt(60);
        date = now.plusMonths(randomNumber);
    }

    public static DateInfo getRandomDate() {
        DateTimeFormatter month = DateTimeFormatter.ofPattern("MM");
        DateTimeFormatter year = DateTimeFormatter.ofPattern("yy");
        if (date == null) {
            generateRandomDate();
        }

        return new DateInfo(
                date.format(month),
                date.format(year)
        );
    }

    public static void generateCurrentDatePlusMonth(long plusMonth) {
        YearMonth now = YearMonth.now();
        date = now.plusMonths(plusMonth);
    }

    public static DateInfo getFutureDatePlusMonth(long plusMonth) {
        DateTimeFormatter month = DateTimeFormatter.ofPattern("MM");
        DateTimeFormatter year = DateTimeFormatter.ofPattern("yy");
        generateCurrentDatePlusMonth(plusMonth);

        return new DateInfo(
                date.format(month),
                date.format(year)
        );
    }

    private static String generateRandomFullName() {

        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String generateRandomCVV() {
        return faker.numerify("###");
    }


    @Value
    public static class CardInfo {
        String number;
        String month;
        String year;
        String holder;
        String cvc;
    }

    @Value
    public static class DateInfo {
        String month;
        String year;
    }


}
