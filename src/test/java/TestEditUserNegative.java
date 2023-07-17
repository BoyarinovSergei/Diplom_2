/*
 * Раздел: Изменение данных пользователя:
 * Класс включает в себя проверки:
 * 2. без авторизации,
 * */

import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pojo.commonErrorResponse.RespWrong;
import pojo.register.correctResponse.RespRegister;
import pojo.register.correctResponse.User;
import pojo.register.request.ReqRegister;
import pojo.userEdit.EditUser;

import static helper.StringGenerator.generateString;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static requestSamples.SetOfReqSamples.*;
import static urlsAndAPIs.APIs.USER;
import static urlsAndAPIs.APIs.USER_CREATION;

@RunWith(Parameterized.class)
public class TestEditUserNegative extends SetDefaultURL {
    private static final String EXPECTED_MESSAGE = "You should be authorised";
    private static String email;
    private static String name;
    private String bearerToken;
    private final EditUser editUser;

    public TestEditUserNegative(EditUser editUser) {
        this.editUser = editUser;
    }

    @Parameterized.Parameters
    public static Object[] testEditUserClasses() {
        return new Object[]{
                new EditUser(true, new User(generateString(5) + "@yandex.ru", name)),
                new EditUser(true, new User(email, generateString(5))),
                new EditUser(true, new User(generateString(5) + "@yandex.ru", generateString(5))),
                new EditUser(false, new User(generateString(5) + "@yandex.ru", generateString(5)))
        };
    }

    @Before
    @Description("Генерация тестовых данных и создание нового пользователя")
    public void generateData() {
        email = generateString(5) + "@yandex.ru";
        String password = generateString(5);
        name = generateString(5);

        bearerToken =
                makePostRequestWithNoAuthorization(USER_CREATION, new ReqRegister(email, password, name))
                        .then()
                        .statusCode(SC_OK)
                        .extract()
                        .as(RespRegister.class).accessToken;
    }

    @Test
    @Description("Проверка на возможность изменения полей success, email, name и проверка всех полей в ответе со статусом без токена")
    public void checkAllOptionalFieldsWithNoToken() {
        RespWrong respWrong =
                makePatchRequestWithNoAuthorization(USER, editUser)
                        .then()
                        .statusCode(SC_UNAUTHORIZED)
                        .and()
                        .extract()
                        .as(RespWrong.class);

        Assert.assertFalse(respWrong.success);
        Assert.assertEquals(EXPECTED_MESSAGE, respWrong.message);
    }

    @After
    @Description("Удаление созданных пользоватлеей")
    public void shutDown() {
        makeDeleteRequest(USER, bearerToken);
    }
}
