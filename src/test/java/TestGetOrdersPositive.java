/*
 * Раздел: Получение заказов конкретного пользователя:
 * Класс включает в себя проверки:
 * 1. авторизованный пользователь,
 * */

import commonClasses.SetDefaultURL;
import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pojo.getOrders.RespGetOrdersRoot;
import pojo.ingredients.RespIngredientsRoot;
import pojo.orderCreation.request.ReqOrderCreation;
import pojo.register.correctResponse.RespRegister;
import pojo.register.request.ReqRegister;

import java.util.List;

import static helper.StringGenerator.generateString;
import static org.apache.http.HttpStatus.SC_OK;
import static requestSamples.SetOfReqSamples.*;
import static urlsAndAPIs.APIs.*;

public class TestGetOrdersPositive extends SetDefaultURL {
    private String bearerToken;
    private RespIngredientsRoot respIngredientsRoot;

    @Before
    @Description("Генерация тестовых данных и создание нового пользователя с заказом")
    public void generateData() {
        String email = generateString(5) + "@yandex.ru";
        String password = generateString(5);
        String name = generateString(5);

        RespRegister reqRegisterApi =
                makePostRequestWithNoAuthorization(USER_CREATION, new ReqRegister(email, password, name))
                        .then()
                        .statusCode(SC_OK)
                        .extract()
                        .as(RespRegister.class);

        bearerToken = reqRegisterApi.accessToken;

        respIngredientsRoot = makeGetRequest(INGREDIENTS)
                .then()
                .statusCode(SC_OK)
                .extract()
                .as(RespIngredientsRoot.class);

        makePostRequestWithAuthorization(ORDER, new ReqOrderCreation(List.of(respIngredientsRoot.data.get(0)._id)), bearerToken)
                .then()
                .statusCode(SC_OK);
    }

    @Test
    @Description("Получение заказов у авторизованного пользователя с проверкой статуса ответа и id ингредиента")
    public void getOrdersBeingAuthorized() {
        RespGetOrdersRoot resp = makeGetRequestWithToken(ORDER, bearerToken)
                .then()
                .statusCode(SC_OK)
                .and()
                .extract()
                .as(RespGetOrdersRoot.class);

        Assert.assertTrue(resp.success);
        Assert.assertEquals(respIngredientsRoot.data.get(0)._id, resp.getOrders().get(0).getIngredients().get(0));
    }

    @After
    @Description("Удаление созданных пользоватлеей")
    public void shutDown() {
        makeDeleteRequest(USER, bearerToken);
    }
}