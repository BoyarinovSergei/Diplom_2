/*
 * Класс для переиспользования шаблонных запросов.
 * */

package requestSamples;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static headers.SetOfHeaders.DEFAULT_HEADERS;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;

public class SetOfReqSamples {

    @Step("Выполнение post запроса без авторизации")
    public static Response makePostRequestWithNoAuthorization(String path, Object json) {
        return given()
                .headers(DEFAULT_HEADERS)
                .body(json)
                .post(path)
                .andReturn();
    }

    @Step("Выполнение post запроса с авторизацией")
    public static Response makePostRequestWithAuthorization(String path, Object json, String token) {
        return given()
                .headers(DEFAULT_HEADERS)
                .header("Authorization", token)
                .body(json)
                .post(path)
                .andReturn();
    }

    @Step("Выполнение patch запроса с авторизацией")
    public static Response makePatchRequestWithAuthorization(String path, Object json, String token) {
        return given()
                .headers(DEFAULT_HEADERS)
                .header("Authorization", token)
                .body(json)
                .patch(path)
                .andReturn();
    }

    @Step("Выполнение patch запроса без авторизации")
    public static Response makePatchRequestWithNoAuthorization(String path, Object json) {
        return given()
                .headers(DEFAULT_HEADERS)
                .body(json)
                .patch(path)
                .andReturn();
    }

    @Step("Выполнение get запроса")
    public static Response makeGetRequest(String path) {
        return given()
                .headers(DEFAULT_HEADERS)
                .get(path)
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
