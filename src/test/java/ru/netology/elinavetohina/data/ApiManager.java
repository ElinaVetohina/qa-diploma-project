package ru.netology.elinavetohina.data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;;

public final class ApiManager {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(8080)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void cardPayment(DataManager.CardInfo info) {
        given()
                .spec(requestSpec)
                .body(info)
                .when()
                .post("/api/v1/pay")
                .then()
                .statusCode(200);
    }

    public static void cardPaymentWhenUsedFiledCard(DataManager.CardInfo info) {
        given()
                .spec(requestSpec)
                .body(info)
                .when()
                .post("/api/v1/pay")
                .then()
                .statusCode(500);
    }

    public static void creditPayment(DataManager.CardInfo info) {
        given()
                .spec(requestSpec)
                .body(info)
                .when()
                .post("api/v1/credit")
                .then()
                .statusCode(200);

    }

    public static void creditPaymentWhenUsedFiledCard(DataManager.CardInfo info) {
        given()
                .spec(requestSpec)
                .body(info)
                .when()
                .post("api/v1/credit")
                .then()
                .statusCode(500);
    }
}
