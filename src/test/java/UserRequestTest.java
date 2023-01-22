import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import pageobject.UserPageObject;
import pojo.UserRequest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static testdata.UserRequestTestData.getUserRequestAllRequiredField;
import static testdata.UserRequestTestData.getUserRequestWithoutRequiredField;

public class UserRequestTest {
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
    @DisplayName("Успешная регистрация пользователя")
    @Description("Проверка успешной регистрации пользователя")
    public void checkSuccessCreateUser() {
        userPageObject = new UserPageObject();
        userRequest = getUserRequestAllRequiredField();
        accessToken = userPageObject.create(userRequest)
                .then()
                .statusCode(200)
                .and()
                .assertThat().body("success", equalTo(true))
                .and()
                .body("accessToken", notNullValue())
                .extract()
                .path("accessToken");
    }

    @Test
    @DisplayName("Попытка регистрации уже зарегистрированного пользователя")
    @Description("Проверка запрета на регистрацию дублирующего пользователя")
    public void checkFailureCreateDuplicateUser() {
        userPageObject = new UserPageObject();
        userRequest = getUserRequestAllRequiredField();
        accessToken = userPageObject.create(userRequest)
                .then()
                .statusCode(200)
                .and()
                .assertThat().body("success", equalTo(true))
                .and()
                .body("accessToken", notNullValue())
                .extract()
                .path("accessToken");

        Response response = userPageObject.create(userRequest);
        response.then()
                .statusCode(403)
                .and()
                .assertThat().body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Создание пользователя и незаполнение одного из обязательных полей")
    @Description("Проверка запрета регистрации пользователя при условии незаполнения необходимого поля")
    public void checkFailureCreateUserWithoutRequiredFields() {
        userPageObject = new UserPageObject();
        userRequest = getUserRequestWithoutRequiredField();
        Response response = userPageObject.create(userRequest);
        response.then()
                .statusCode(403)
                .and()
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }
}