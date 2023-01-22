import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pageobject.UserPageObject;
import pojo.UpdateUserRequest;
import pojo.UserRequest;

import static config.Config.getBaseUri;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static testdata.UpdateUserRequestTestData.getUserRequestAllUpdateRequiredField;
import static testdata.UserRequestTestData.getUserRequestAllRequiredField;

public class UpdateUserRequestTest {
    UpdateUserRequest updateUserRequest;
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
    @DisplayName("Успешное изменение данных юзера с авторизацией")
    @Description("Проверка изменения данных юзера с авторизацией: запрос вернёт код 200")
    public void checkSuccessUpdateUserDataWithAuth() {
        updateUserRequest = getUserRequestAllUpdateRequiredField();
        Response response = userPageObject.updateWithAuth(updateUserRequest, accessToken);
        response.then()
                .statusCode(200)
                .and()
                .assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Проверка запрета на изменение данных юзера без авторизации")
    @Description("Проверка 401 ошибки при попытке изменить данные без авторизации")
    public void checkFailureUpdateUserDataWithoutAuth() {
        updateUserRequest = getUserRequestAllUpdateRequiredField();
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .baseUri(getBaseUri())
                        .body(updateUserRequest)
                        .patch("auth/user");
        response.then()
                .statusCode(401)
                .and()
                .assertThat().body("message", equalTo("You should be authorised"));
    }
}