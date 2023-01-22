import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import pageobject.UserPageObject;
import pojo.LoginRequest;
import pojo.UserRequest;
import testdata.LoginRequestTestData;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static testdata.UserRequestTestData.getUserRequestAllRequiredField;

public class LoginRequestTest {
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
    @DisplayName("Успешная авторизация под существующим юзером")
    @Description("Проверка авторизации под существующим юзером: возвращается код 200 и корректный текст")
    public void checkSuccessLoginWithExistingUser() {
        userPageObject = new UserPageObject();
        userRequest = getUserRequestAllRequiredField();
        userPageObject.create(userRequest);
        LoginRequest loginRequest = LoginRequestTestData.from(userRequest);
        accessToken = userPageObject.login(loginRequest)
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
    @DisplayName("Попытка авторизации под несуществующим юзером")
    @Description("Проверка ошибки при попытке авторизоваться с некорректными данными")
    public void checkFailureLoginWithInvalidData() {
        LoginRequest loginRequest = LoginRequestTestData.invalidLoginPassword();
        userPageObject = new UserPageObject();
        Response response = userPageObject.login(loginRequest);
        response.then()
                .statusCode(401)
                .and()
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }
}
