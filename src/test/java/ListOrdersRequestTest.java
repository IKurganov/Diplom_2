import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pageobject.OrderPageObject;
import pageobject.UserPageObject;
import pojo.UserRequest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static testdata.UserRequestTestData.getUserRequestAllRequiredField;

public class ListOrdersRequestTest {
    static UserPageObject userPageObject;
    static UserRequest userRequest;
    static String accessToken;

    @BeforeClass
    public static void createTestUser() {
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
    }

    @AfterClass
    public static void deleteUser() {
        if (accessToken != null) {
            userPageObject.delete(accessToken);
        }
    }

    @Test
    @DisplayName("Получение списка заказов авторизованным пользователем")
    @Description("Проверка получения списка заказов в теле ответа авторизованным юзером и того, что запрос возвращает код 200")
    public void checkSuccessGettingOrdersListWithAuth() {
        OrderPageObject orderPageObject = new OrderPageObject();
        Response response = orderPageObject.getOrdersUserWithAuth(accessToken);
        response.then().statusCode(200)
                .and()
                .assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Получение списка заказов без авторизации")
    @Description("Проверка получения заказов неавторизованным пользователем: запрос возвращает 401, а ещё корректный текст ошибки")
    public void checkFailureGettingOrdersListWithoutAuth() {
        OrderPageObject orderPageObject = new OrderPageObject();
        Response response = orderPageObject.getOrdersUserWithoutAuth();
        response.then().statusCode(401)
                .and()
                .assertThat().body("message", equalTo("You should be authorised"));
    }
}