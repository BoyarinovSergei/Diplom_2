/*
 * Раздел: Получение заказов конкретного пользователя:
 * Класс включает в себя проверки:
 * 1. неавторизованный пользователь.
 * */

import commonClasses.SetDefaultURL;
import io.qameta.allure.Description;
import org.junit.Assert;
import org.junit.Test;
import pojo.commonErrorResponse.RespWrong;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static requestSamples.SetOfReqSamples.makeGetRequest;
import static urlsAndAPIs.APIs.ORDER;

public class TestGetOrdersNegative extends SetDefaultURL {
    private static final String EXPECTED_MESSAGE = "You should be authorised";

    @Test
    @Description("Получение заказов без авторизации")
    public void getOrdersWithNoToken() {
        RespWrong respWrong = makeGetRequest(ORDER)
                .then()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .extract()
                .as(RespWrong.class);

        Assert.assertFalse(respWrong.success);
        Assert.assertEquals(EXPECTED_MESSAGE, respWrong.getMessage());
    }
}