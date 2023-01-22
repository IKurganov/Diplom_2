package testdata;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import pojo.UserRequest;

public class UserRequestTestData {
    @Step("Заполнение всех полей в форме регистрации")
    public static UserRequest getUserRequestAllRequiredField() {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail(RandomStringUtils.randomAlphabetic(8) + "@proton.org");
        userRequest.setPassword(RandomStringUtils.randomAlphanumeric(8));
        userRequest.setName(RandomStringUtils.randomAlphabetic(10));
        return userRequest;
    }

    @Step("Заполнение необходимых полей в форме регистрации")
    public static UserRequest getUserRequestWithoutRequiredField() {
        UserRequest userRequest = new UserRequest();
        userRequest.setPassword(RandomStringUtils.randomAlphabetic(7));
        userRequest.setName(RandomStringUtils.randomAlphabetic(8));
        return userRequest;
    }
}