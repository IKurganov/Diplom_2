import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import pageobject.OrderPageObject;
import pageobject.UserPageObject;
import pojo.OrderCreateRequest;
import pojo.UserRequest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static testdata.OrderCreateRequestTestData.*;
import static testdata.UserRequestTestData.getUserRequestAllRequiredField;

public class OrderCreateRequestTest {
    UserPageObject userPageObject;
    UserRequest userRequest;
    String accessToken;

    @After
    public void deleteUser() {
        if (accessToken != null) {
            userPageObject.delete(accessToken);
        }
    }

    @Test
    @DisplayName("Создание заказа авторизованным юзером с ингредиентами")
    @Description("Проверка создания заказа под существующим пользователем и с ингредиентами: запрос возвращает код ответа 200")
    public void testCreateOrderWithAuthAndIngredients() {
        userPageObject = new UserPageObject();
        userRequest = getUserRequestAllRequiredField();
        accessToken = userPageObject.create(userRequest)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("accessToken", notNullValue())
                .extract()
                .path("accessToken");

        OrderCreateRequest orderCreateRequest = getOrderWithIngredients();
        OrderPageObject orderPageObject = new OrderPageObject();
        Response response = orderPageObject.createOrderWithAuth(orderCreateRequest, accessToken);
        response.then()
                .statusCode(200)
                .and()
                .assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа без авторизации и с ингредиентами")
    @Description("Проверка создания заказа под неавторизованным юзером, заказ с ингредиентами: запрос возвращает код ответа 200")
    public void testCreateOrderWithoutAuth() {
        OrderCreateRequest orderCreateRequest = getOrderWithIngredients();
        OrderPageObject orderPageObject = new OrderPageObject();
        Response response = orderPageObject.createOrderWithoutAuth(orderCreateRequest);
        response.then()
                .statusCode(200)
                .and()
                .assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("создания заказа под неавторизованным юзером, заказ без ингредиентов")
    @Description("Проверка того, что нельзя создать заказ без ингредиентов: запрос вернёт код 400 и сообщение: Ingredient ids must be provided")
    public void testCreateOrderWithoutIngredients() {
        userPageObject = new UserPageObject();
        userRequest = getUserRequestAllRequiredField();
        Response responseCreate = userPageObject.create(userRequest);
        accessToken = responseCreate
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("accessToken", notNullValue())
                .extract()
                .path("accessToken");

        OrderCreateRequest orderCreateRequest = getOrderWithoutIngredients();
        OrderPageObject orderPageObject = new OrderPageObject();
        Response response = orderPageObject.createOrderWithAuth(orderCreateRequest, accessToken);
        response.then()
                .statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Попытка создания заказа с некорректными ингредиентами")
    @Description("Проверка того, что нельзя создать заказ с некорректными ингредиентами")
    public void testCreateOrderWithIncorrectIngredients() {
        userPageObject = new UserPageObject();
        userRequest = getUserRequestAllRequiredField();
        Response responseCreate = userPageObject.create(userRequest);
        accessToken = responseCreate
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("accessToken", notNullValue())
                .extract()
                .path("accessToken");

        OrderCreateRequest orderCreateRequest = getOrderWithIncorrectIngredients();
        OrderPageObject orderPageObject = new OrderPageObject();
        Response response = orderPageObject.createOrderWithAuth(orderCreateRequest, accessToken);
        response.then()
                .statusCode(500);
    }
}