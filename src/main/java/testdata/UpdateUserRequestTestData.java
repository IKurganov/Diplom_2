package testdata;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import pojo.UpdateUserRequest;

public class UpdateUserRequestTestData {
    @Step("Составляем запрос на изменение данных пользователя")
    public static UpdateUserRequest getUserRequestAllUpdateRequiredField() {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setEmail(RandomStringUtils.randomAlphabetic(8) + "@proton.org");
        updateUserRequest.setPassword("password");
        updateUserRequest.setName(RandomStringUtils.randomAlphabetic(7));
        return updateUserRequest;
    }
}