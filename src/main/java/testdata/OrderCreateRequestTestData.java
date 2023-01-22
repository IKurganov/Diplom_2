package testdata;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import pojo.OrderCreateRequest;

public class OrderCreateRequestTestData {
    @Step("Создание заказа с ингредиентами")
    public static OrderCreateRequest getOrderWithIngredients(String[] ingredients) {
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
        orderCreateRequest.setIngredients(ingredients);
        return orderCreateRequest;
    }

    @Step("Создание заказа без ингредиентов")
    public static OrderCreateRequest getOrderWithoutIngredients() {
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
        orderCreateRequest.setIngredients(new String[]{});
        return orderCreateRequest;
    }

    @Step("Создание заказа с некорректными ингредиентами - рандомными значениями")
    public static OrderCreateRequest getOrderWithIncorrectIngredients() {
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
        orderCreateRequest.setIngredients(new String[]{"Incorrect-" + RandomStringUtils.randomAlphabetic(23), "Incorrect-" + RandomStringUtils.randomNumeric(25)});
        return orderCreateRequest;
    }
}