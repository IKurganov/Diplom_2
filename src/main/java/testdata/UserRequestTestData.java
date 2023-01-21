package testdata;

import io.qameta.allure.Step;
import pojo.UserRequest;

public class UserRequestTestData {
    @Step("Заполнение всех полей в форме регистрации")
    public static UserRequest getUserRequestAllRequiredField() {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("testovyicourier228@protonmail.org");
        userRequest.setPassword("parol");
        userRequest.setName("Zaklader");
        return userRequest;
    }

    @Step("Заполнение необходимых полей в форме регистрации")
    public static UserRequest getUserRequestWithoutRequiredField() {
        UserRequest userRequest = new UserRequest();
        userRequest.setPassword("Lorap");
        userRequest.setName("Enam");
        return userRequest;
    }
}