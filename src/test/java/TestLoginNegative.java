/*
 * Раздел: Логин пользователя:
 * Класс включает в себя проверки:
 * 2. логин с неверным логином и паролем.
 * */

import commonClasses.SetDefaultURL;
import io.qameta.allure.Description;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pojo.commonerrorresponse.RespWrong;
import pojo.login.request.ReqLogin;

import static helper.StringGenerator.generateString;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static requestSamples.SetOfReqSamples.makePostRequestWithNoAuthorization;
import static urlsAndAPIs.APIs.USER_LOGIN;

public class TestLoginNegative extends SetDefaultURL {
    private static String email;
    private static String password;
    private static final String EXPECTED_MESSAGE = "email or password are incorrect";

    @Before
    @Description("Генерация тестовых данных и создание нового пользователя")
    public void generateData() {
        email = generateString(15) + "@yandex.ru";
        password = generateString(15);
    }

    @Test
    @Description("Авторизация под несуществующим пользователем")
    public void loginUsingNonexistentCredentials() {
        RespWrong respWrong =
                makePostRequestWithNoAuthorization(USER_LOGIN, new ReqLogin(email, password))
                        .then()
                        .statusCode(SC_UNAUTHORIZED)
                        .extract()
                        .as(RespWrong.class);

        Assert.assertFalse(respWrong.getSuccess());
        Assert.assertEquals(EXPECTED_MESSAGE, respWrong.getMessage());
    }
}