package testdata;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import pojo.LoginRequest;
import pojo.UserRequest;

public class LoginRequestTestData {
    @Step("Авторизация зарегистрированного клиента с корректными данными")
    public static LoginRequest from(UserRequest userRequest) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(userRequest.getEmail());
        loginRequest.setPassword(userRequest.getPassword());
        return loginRequest;
    }

    @Step("Авторизация с некорректными данными")
    public static LoginRequest invalidLoginPassword() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(RandomStringUtils.randomAlphanumeric(5));
        loginRequest.setPassword(RandomStringUtils.randomAlphanumeric(5));
        return loginRequest;
    }
}