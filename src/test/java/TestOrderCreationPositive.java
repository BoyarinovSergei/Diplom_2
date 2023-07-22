/*
 * Раздел: Создание заказа:
 * Класс включает в себя проверки:
 * 1. с авторизацией,
 * 2. без авторизации,
 * 3. с ингредиентами,
 * */

import commonClasses.SetDefaultURL;
import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pojo.ingredients.RespIngredientsRoot;
import pojo.orderCreation.request.ReqOrderCreation;
import pojo.orderCreation.response.RespOrderCreationWithNoAuth;
import pojo.orderCreation.responseWithAuth.RespOrderCreationAuthRoot;
import pojo.register.correctResponse.RespRegister;
import pojo.register.request.ReqRegister;

import java.util.List;

import static helper.StringGenerator.generateString;
import static org.apache.http.HttpStatus.SC_OK;
import static requestSamples.SetOfReqSamples.*;
import static urlsAndAPIs.APIs.*;

public class TestOrderCreationPositive extends SetDefaultURL {
    private static String email;
    private static String name;
    private String bearerToken;
    private RespIngredientsRoot respIngredientsRoot;

    @Before
    @Description("Генерация тестовых данных и создание нового пользователя")
    public void generateData() {
        email = generateString(5) + "@yandex.ru";
        String password = generateString(5);
        name = generateString(5);

        RespRegister reqRegisterApi =
                makePostRequestWithNoAuthorization(USER_CREATION, new ReqRegister(email, password, name))
                        .then()
                        .statusCode(SC_OK)
                        .extract()
                        .as(RespRegister.class);

        bearerToken = reqRegisterApi.getAccessToken();

        respIngredientsRoot =
                makeGetRequest(INGREDIENTS).as(RespIngredientsRoot.class);
    }

    @Test
    @Description("Создание заказа с авторизацией, проверкой кода и тела ответа")
    public void createNewOrderWithToken() {
        RespOrderCreationAuthRoot resp =
                makePostRequestWithAuthorization(ORDER, new ReqOrderCreation(List.of(respIngredientsRoot.getData().get(0).get_id())), bearerToken)
                        .then()
                        .statusCode(SC_OK)
                        .extract()
                        .as(RespOrderCreationAuthRoot.class);

        Assert.assertTrue(resp.getSuccess());
        Assert.assertNotNull(resp.getName());
        Assert.assertEquals(email, resp.getOrder().getOwner().getEmail());
        Assert.assertEquals(name, resp.getOrder().getOwner().getName());
    }

    @Test
    @Description("Создание заказа без авторизации, проверкой кода и тела ответа")
    public void createNewOrderWithNoToken() {
        RespOrderCreationWithNoAuth resp =
                makePostRequestWithNoAuthorization(ORDER, new ReqOrderCreation(List.of(respIngredientsRoot.getData().get(0).get_id())))
                        .then()
                        .statusCode(SC_OK)
                        .extract()
                        .as(RespOrderCreationWithNoAuth.class);

        Assert.assertTrue(resp.getSuccess());
        Assert.assertNotNull(resp.getName());
        Assert.assertTrue(resp.getOrder().getNumber() > 0);
    }

    @After
    @Description("Удаление созданных пользоватлеей")
    public void shutDown() {
        makeDeleteRequest(USER, bearerToken);
    }
}