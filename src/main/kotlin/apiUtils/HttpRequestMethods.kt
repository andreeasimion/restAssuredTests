package apiUtils

import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification

open class HttpRequestMethods : Headers() {
    fun get(
        path: String,
        headers: RequestSpecification? = null,
        httpStatusCode: Int
    ): Response {
        return RestAssured.given()
            .contentType(ContentType.JSON)
            .spec(headers)
            .`when`()
            .get(path)
            .then()
            .statusCode(httpStatusCode).extract().response()
    }

    fun post(
        path: String,
        headers: RequestSpecification? = null,
        body: Any,
        httpStatusCode: Int
    ): Response {
        return RestAssured.given()
            .contentType(ContentType.JSON)
            .spec(headers)
            .body(body)
            .`when`()
            .post(path)
            .then()
            .statusCode(httpStatusCode).extract().response()
    }

    fun put(
        path: String,
        headers: RequestSpecification? = null,
        body: Any,
        httpStatusCode: Int
    ): Response {
        return RestAssured.given()
            .contentType(ContentType.JSON)
            .spec(headers)
            .body(body)
            .`when`()
            .put(path)
            .then()
            .statusCode(httpStatusCode).extract().response()
    }

    fun patch(
        path: String,
        headers: RequestSpecification? = null,
        body: Any,
        httpStatusCode: Int
    ): Response {
        return RestAssured.given()
            .contentType(ContentType.JSON)
            .spec(headers)
            .body(body)
            .`when`()
            .patch(path)
            .then()
            .statusCode(httpStatusCode).extract().response()
    }

    fun put(
        path: String,
        headers: RequestSpecification,
        httpStatusCode: Int
    ): Response {
        return RestAssured.given()
            .contentType(ContentType.JSON)
            .spec(headers)
            .`when`()
            .put(path)
            .then()
            .statusCode(httpStatusCode).extract().response()
    }

    fun delete(
        path: String,
        headers: RequestSpecification? = null,
        httpStatusCode: Int
    ): Response {
        return RestAssured.given()
            .contentType(ContentType.JSON)
            .spec(headers)
            .`when`()
            .delete(path)
            .then()
            .statusCode(httpStatusCode).extract().response()
    }
}