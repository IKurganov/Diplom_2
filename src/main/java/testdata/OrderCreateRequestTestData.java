package testdata;

import io.qameta.allure.Step;
import pojo.OrderCreateRequest;

public class OrderCreateRequestTestData {
    @Step("Создание заказа с корректными ингредиентами")
    public static OrderCreateRequest getOrderWithIngredients() {
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
        orderCreateRequest.setIngredients(new String[]{"61c0c5a71d1f82001bdaaa72", "61c0c5a71d1f82001bdaaa74", "61c0c5a71d1f82001bdaaa6c"});
        return orderCreateRequest;
    }

    @Step("Создание заказа без ингредиентов")
    public static OrderCreateRequest getOrderWithoutIngredients() {
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
        orderCreateRequest.setIngredients(new String[]{});
        return orderCreateRequest;
    }

    @Step("Создание заказа с некорректными ингредиентами")
    public static OrderCreateRequest getOrderWithIncorrectIngredients() {
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
        orderCreateRequest.setIngredients(new String[]{"Incorrect61c0c5a71d1f82001bdaaa72", "Incorrect61c0c5a71d1f82001bdaaa74", "Incorrect61c0c5a71d1f82001bdaaa6c"});
        return orderCreateRequest;
    }
}