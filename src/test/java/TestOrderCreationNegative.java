/*
 * Раздел: Создание заказа:
 * Класс включает в себя проверки:
 * 4. без ингредиентов,
 * 5. с неверным хешем ингредиентов.
 * */

import commonClasses.SetDefaultURL;
import io.qameta.allure.Description;
import org.junit.Assert;
import org.junit.Test;
import pojo.commonErrorResponse.RespWrong;
import pojo.orderCreation.request.ReqOrderCreation;

import java.util.List;

import static helper.StringGenerator.generateString;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static requestSamples.SetOfReqSamples.makePostRequestWithNoAuthorization;
import static urlsAndAPIs.APIs.ORDER;

public class TestOrderCreationNegative extends SetDefaultURL {
    private static final String EXPECTED_MESSAGE = "Ingredient ids must be provided";

    @Test
    @Description("Создание заказа без ингредиентов и авторизации")
    public void createOrderWithNoIngredients() {
        RespWrong respWrong =
                makePostRequestWithNoAuthorization(ORDER, new ReqOrderCreation(List.of()))
                        .then()
                        .statusCode(SC_BAD_REQUEST)
                        .extract()
                        .as(RespWrong.class);

        Assert.assertFalse(respWrong.success);
        Assert.assertEquals(EXPECTED_MESSAGE, respWrong.getMessage());
    }

    @Test
    @Description("Создание заказа с несуществующим id ингредиента")
    public void createOrderWithWrongIngredientHash() {
        makePostRequestWithNoAuthorization(ORDER, new ReqOrderCreation(List.of(generateString(5))))
                .then()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }
}