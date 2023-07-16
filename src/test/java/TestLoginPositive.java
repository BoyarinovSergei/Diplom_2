/*
 * Раздел: Логин пользователя:
 * Класс включает в себя проверки:
 * 1. логин под существующим пользователем,
 * */

import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pojo.loginAPI.request.ReqLogin;
import pojo.loginAPI.response.RespLogin;
import pojo.registerAPI.correctResponse.RespRegister;
import pojo.registerAPI.request.ReqRegister;

import java.util.Locale;

import static helper.StringGenerator.generateString;
import static org.apache.http.HttpStatus.SC_OK;
import static requestSamples.SetOfReqSamples.makeDeleteRequest;
import static requestSamples.SetOfReqSamples.makePostRequest;
import static urlsAndAPIs.APIs.*;

public class TestLoginPositive extends SetDefaultURL {
    private static String email;
    private static String password;
    private static String name;
    private String bearerToken;

    @Before
    @Description("Генерация тестовых данных и создание нового пользователя")
    public void generateData() {
        email = generateString(5) + "@yandex.ru";
        password = generateString(5);
        name = generateString(5);

        bearerToken =
                makePostRequest(USER_CREATION, new ReqRegister(email, password, name))
                        .then()
                        .statusCode(SC_OK)
                        .extract()
                        .as(RespRegister.class).accessToken;
    }

    @Test
    @Description("Авторизация под существующим пользователем")
    public void loginUsingExistingCredentials() {
        RespLogin respLogin =
                makePostRequest(USER_LOGIN, new ReqLogin(email, password))
                        .then()
                        .statusCode(SC_OK)
                        .extract()
                        .as(RespLogin.class);

        Assert.assertTrue(respLogin.success);
        Assert.assertEquals(email.toLowerCase(Locale.ROOT), respLogin.user.email);
        Assert.assertEquals(name, respLogin.user.name);
        Assert.assertNotNull(respLogin.accessToken);
        Assert.assertNotNull(respLogin.refreshToken);
    }

    @After
    @Description("Удаление созданных пользоватлеей")
    public void shutDown() {
        makeDeleteRequest(DELETE_USER, bearerToken);
    }
}
