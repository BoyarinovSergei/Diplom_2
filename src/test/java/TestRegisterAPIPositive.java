/*
 * Класс включает в себя проверки:
 * 1. создать уникального пользователя;
 * 2. создать пользователя, который уже зарегистрирован;
 * */

import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pojo.registerAPI.request.ReqRegisterApi;
import pojo.registerAPI.response.RespRegisterApi;

import java.util.Locale;

import static helper.StringGenerator.generateString;
import static org.apache.http.HttpStatus.SC_OK;
import static requestSamples.SetOfReqSamples.makeDeleteRequest;
import static requestSamples.SetOfReqSamples.makePostRequest;
import static urlsAndAPIs.APIs.DELETE_USER;
import static urlsAndAPIs.APIs.USER_CREATION;

public class TestRegisterAPIPositive extends SetDefaultURL {
    private static String email;
    private static String password;
    private static String name;

    private String bearerToken;

    @Before
    @Description("Генерация тестовых данных")
    public void generateData() {
        email = generateString(5) + "@yandex.ru";
        password = generateString(5);
        name = generateString(5);
    }


    @Test
    @Description("Создание уникального пользователя, проверка статуса ответа и ключевых полей")
    public void signUpNewUser() {
        RespRegisterApi reqRegisterApi =
                makePostRequest(USER_CREATION, new ReqRegisterApi(email, password, name))
                        .then()
                        .statusCode(SC_OK)
                        .extract()
                        .as(RespRegisterApi.class);

        bearerToken = reqRegisterApi.accessToken;

        Assert.assertTrue(reqRegisterApi.success);
        Assert.assertEquals(email.toLowerCase(Locale.ROOT), reqRegisterApi.user.email);
        Assert.assertEquals(name, reqRegisterApi.user.name);
        Assert.assertNotNull(reqRegisterApi.accessToken);
        Assert.assertNotNull(reqRegisterApi.refreshToken);
    }

    @After
    @Description("Удаление созданных пользоватлеей")
    public void shutDown() {
        makeDeleteRequest(DELETE_USER, bearerToken);
    }
}
