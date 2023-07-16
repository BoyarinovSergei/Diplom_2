/*
 * Класс для переиспользования шаблонных запросов.
 * */

package requestSamples;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static headers.SetOfHeaders.DEFAULT_HEADERS;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.hamcrest.CoreMatchers.equalTo;

public class SetOfReqSamples {

    @Step("Выполнение post запроса")
    public static Response makePostRequest(String path, Object json) {
        return given()
                .headers(DEFAULT_HEADERS)
                .body(json)
                .post(path)
                .andReturn();
    }

    @Step("Выполнение delete запроса")
    public static void makeDeleteRequest(String path, String token) {
        given()
                .headers(DEFAULT_HEADERS)
                .header("Authorization", token)
                .delete(path)
                .then()
                .statusCode(SC_ACCEPTED)
                .and()
                .body("success", equalTo(true))
                .body("message", equalTo("User successfully removed"));
    }
}
