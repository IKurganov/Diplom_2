package pageobject;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.LoginRequest;
import pojo.UpdateUserRequest;
import pojo.UserRequest;

import static config.Config.getBaseUri;
import static io.restassured.RestAssured.given;

public class UserPageObject {
    @Step("Регистрация клиента")
    public Response create(UserRequest userRequest) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .body(userRequest)
                .post("auth/register");
    }

    @Step("Авторизация клиента")
    public Response login(LoginRequest loginRequest) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .body(loginRequest)
                .post("auth/login");
    }

    @Step("Удаление клиента")
    public void delete(String accessToken) {
        given()
                .header("Authorization", accessToken)
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .delete("auth/user");
    }

    @Step("Изменение данных клиента без авторизации")
    public Response updateWithAuth(UpdateUserRequest updateUserRequest, String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .body(updateUserRequest)
                .patch("auth/user");
    }
}