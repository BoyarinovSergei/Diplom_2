/*
 * Раздел: Создание пользователя:
 * Класс включает в себя проверки:
 * 1. создать уникального пользователя;
 * 2. создать пользователя, который уже зарегистрирован;
 * */

import commonClasses.SetDefaultURL;
import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pojo.register.correctResponse.RespRegister;
import pojo.register.request.ReqRegister;
import pojo.commonerrorresponse.RespWrong;

import static helper.StringGenerator.generateString;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static requestSamples.SetOfReqSamples.makeDeleteRequest;
import static requestSamples.SetOfReqSamples.makePostRequestWithNoAuthorization;
import static urlsAndAPIs.APIs.USER;
import static urlsAndAPIs.APIs.USER_CREATION;

public class TestRegisterPositive extends SetDefaultURL {
    private static String email;
    private static String password;
    private static String name;
    private String bearerToken;
    private static final String EXPECTED_MESSAGE = "User already exists";

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
        RespRegister reqRegisterApi =
                makePostRequestWithNoAuthorization(USER_CREATION, new ReqRegister(email, password, name))
                        .then()
                        .statusCode(SC_OK)
                        .extract()
                        .as(RespRegister.class);

        bearerToken = reqRegisterApi.getAccessToken();

        Assert.assertTrue(reqRegisterApi.getSuccess());
        Assert.assertEquals(email, reqRegisterApi.getUser().getEmail());
        Assert.assertEquals(name, reqRegisterApi.getUser().getName());
        Assert.assertNotNull(reqRegisterApi.getAccessToken());
        Assert.assertNotNull(reqRegisterApi.getRefreshToken());
    }

    @Test
    @Description("Создание нового пользователя и затем создание пользователя с такими же данными с проверкой всех полей в ответе")
    public void signUpNewUserTwoTimes() {
        RespRegister reqRegisterApi =
                makePostRequestWithNoAuthorization(USER_CREATION, new ReqRegister(email, password, name))
                        .then()
                        .statusCode(SC_OK)
                        .extract()
                        .as(RespRegister.class);

        bearerToken = reqRegisterApi.getAccessToken();

        RespWrong respWrong =
                makePostRequestWithNoAuthorization(USER_CREATION, new ReqRegister(email, password, name))
                        .then()
                        .statusCode(SC_FORBIDDEN)
                        .extract()
                        .as(RespWrong.class);

        Assert.assertFalse(respWrong.getSuccess());
        Assert.assertEquals(EXPECTED_MESSAGE, respWrong.getMessage());
    }

    @After
    @Description("Удаление созданных пользоватлеей")
    public void shutDown() {
        makeDeleteRequest(USER, bearerToken);
    }
}
