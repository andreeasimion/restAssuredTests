package implementation

import io.restassured.module.jsv.JsonSchemaValidator
import io.restassured.response.Response
import models.GistResponseDTO
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasKey
import org.hamcrest.Matchers.notNullValue
import org.hamcrest.core.Is.`is`

open class GistAssertion : GistImplementation() {
    fun isExpectedDescription(gistResponse: GistResponseDTO, expectedDescription: String) {
        val description = getGistDescription(gistResponse)
        assertThat(description, `is`(expectedDescription))
    }

    fun isExpectedId(gistResponse: GistResponseDTO, expectedId: String) {
        val description = getGistId(gistResponse)
        assertThat(description, `is`(expectedId))
    }

    fun isExpectedPublicValue(gistResponse: GistResponseDTO, expectedPublicValue: Boolean) {
        val public = getPublicValue(gistResponse)
        assertThat(public, `is`(expectedPublicValue))
    }

    fun isNotEmptyResponse(gistResponse: Response) {
        assertThat(gistResponse, notNullValue())
    }

    fun isExpectedFileNme(gistResponse: GistResponseDTO, expectedFilename: String) {
        val filename = getFileNme(gistResponse)
        assertThat(filename, hasKey(expectedFilename))
    }

    fun hasExpectedSchema(response: Response, schemaName: String) {
        response.then().body(
            JsonSchemaValidator.matchesJsonSchemaInClasspath(
                "schema/$schemaName"
            )
        )
    }

    infix fun GistImplementation.verifyThat(fn: GistAssertion.() -> Unit): GistAssertion = GistAssertion().apply(fn)
}