package pageobject;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.OrderCreateRequest;

import static config.Config.getBaseUri;
import static io.restassured.RestAssured.given;

public class OrderPageObject {
    @Step("Создание заказа с авторизацией")
    public Response createOrderWithAuth(OrderCreateRequest orderCreateRequest, String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .body(orderCreateRequest)
                .post("orders");
    }

    @Step("Создание заказа без авторизации")
    public Response createOrderWithoutAuth(OrderCreateRequest orderCreateRequest) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .body(orderCreateRequest)
                .post("orders");
    }

    @Step("Получение списка заказов с авторизацией")
    public Response getOrdersUserWithAuth(String accessToken) {
        return given()
                .baseUri(getBaseUri())
                .header("Authorization", accessToken)
                .header("Content-type", "application/json")
                .get("orders");
    }

    @Step("Получение списка заказов без авторизации")
    public Response getOrdersUserWithoutAuth() {
        return given()
                .baseUri(getBaseUri())
                .header("Content-type", "application/json")
                .get("orders");
    }
}