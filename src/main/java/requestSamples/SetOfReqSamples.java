/*
 * Класс для переиспользования шаблонных запросов.
 * */

package requestSamples;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.hamcrest.CoreMatchers.equalTo;

import static headers.SetOfHeaders.DEFAULT_HEADERS;
import static org.apache.http.HttpStatus.SC_ACCEPTED;

public class SetOfReqSamples {

    @Step("Выполнение post запроса")
    public static Response makePostRequest(String path, Object json) {
        return RestAssured.given()
                .headers(DEFAULT_HEADERS)
                .body(json)
                .post(path)
                .andReturn();
    }

    @Step("Выполнение delete запроса")
    public static Response makeDeleteRequest(String path, String token) {
        return RestAssured.given()
                .headers(DEFAULT_HEADERS)
                .header("Authorization", token)
                .delete(path)
                .then()
                .statusCode(SC_ACCEPTED)
                .and()
                .body("success", equalTo(true))
                .body("message", equalTo("User successfully removed"))
                .and()
                .extract()
                .response();
    }
}
