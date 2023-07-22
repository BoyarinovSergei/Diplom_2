/*
 * Раздел: Изменение данных пользователя:
 * Класс включает в себя проверки:
 * 1. с авторизацией,
 * */

import commonClasses.SetDefaultURL;
import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pojo.register.correctResponse.RespRegister;
import pojo.register.correctResponse.User;
import pojo.register.request.ReqRegister;
import pojo.userEdit.EditUser;

import static helper.StringGenerator.generateString;
import static org.apache.http.HttpStatus.SC_OK;
import static requestSamples.SetOfReqSamples.*;
import static urlsAndAPIs.APIs.USER;
import static urlsAndAPIs.APIs.USER_CREATION;

@RunWith(Parameterized.class)
public class TestEditUserPositive extends SetDefaultURL {
    private static String email;
    private static String name;
    private String bearerToken;
    private final EditUser editUser;

    public TestEditUserPositive(EditUser editUser) {
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
    @Description("Проверка на возможность изменения полей success, email, name и проверка всех полей в ответе со статусом с токеном")
    public void checkAllOptionalFieldsWithToken() {
        EditUser editUser1 =
                makePatchRequestWithAuthorization(USER, editUser, bearerToken)
                        .then()
                        .statusCode(SC_OK)
                        .and()
                        .extract()
                        .as(EditUser.class);

        Assert.assertTrue(editUser1.success);
        Assert.assertEquals(editUser1.user.getEmail(), email);
        Assert.assertEquals(editUser1.user.getName(), name);
    }

    @After
    @Description("Удаление созданных пользоватлеей")
    public void shutDown() {
        makeDeleteRequest(USER, bearerToken);
    }
}
