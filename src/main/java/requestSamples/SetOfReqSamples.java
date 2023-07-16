/*
 * Класс для переиспользования шаблонных запросов.
 * */

package requestSamples;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static headers.SetOfHeaders.DEFAULT_HEADERS;

public abstract class SetOfReqSamples {

    @Step("Make a Post request")
    public static Response makePostRequest(String path, Object json) {
        return RestAssured.given()
                .headers(DEFAULT_HEADERS)
                .body(json)
                .post(path)
                .andReturn();
    }
}
