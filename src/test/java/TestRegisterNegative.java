/*
 * Раздел: Создание пользователя:
 * Класс включает в себя проверки:
 * 3. создать пользователя и не заполнить одно из обязательных полей.
 * */

import io.qameta.allure.Description;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pojo.register.request.ReqRegister;
import pojo.commonErrorResponse.RespWrong;

import static helper.StringGenerator.generateString;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static requestSamples.SetOfReqSamples.makePostRequestWithNoAuthorization;
import static urlsAndAPIs.APIs.USER_CREATION;

@RunWith(Parameterized.class)
public class TestRegisterNegative extends SetDefaultURL {
    private final ReqRegister reqRegister;
    private static final String EXPECTED_MESSAGE = "Email, password and name are required fields";

    public TestRegisterNegative(ReqRegister reqRegister) {
        this.reqRegister = reqRegister;
    }

    @Parameterized.Parameters
    public static Object[] setOfData() {
        return new Object[]{
                new ReqRegister(null, generateString(6), generateString(6)),
                new ReqRegister("wer34yandex.ru", null, generateString(6)),
                new ReqRegister(generateString(6) + "yandex.ru", generateString(6), null)
        };
    }

    @Test
    @Description("Проверка на обязательность заполнения всех полей")
    public void checkAllRequiredFields() {
        RespWrong respWrong =
                makePostRequestWithNoAuthorization(USER_CREATION, reqRegister)
                        .then()
                        .statusCode(SC_FORBIDDEN)
                        .extract()
                        .as(RespWrong.class);

        Assert.assertFalse(respWrong.success);
        Assert.assertEquals(EXPECTED_MESSAGE, respWrong.getMessage());
    }
}
