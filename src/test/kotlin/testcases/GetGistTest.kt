package testcases

import implementation.GistAssertion
import io.restassured.response.Response
import models.GistResponseDTO
import org.apache.http.HttpStatus
import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeClass
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import utils.ParseResponse.parseCreateGistResponseDTO

class GetGistTest : GistAssertion() {
    private lateinit var response: GistResponseDTO
    private lateinit var getGistResponse: Response

    private var id = ""

    @DataProvider(name = "invalidGistId")
    fun differentGenderValues(): Array<Array<out Any>> {
        return arrayOf(
            arrayOf("invalid"),
            arrayOf("")
        )
    }

    @BeforeClass
    fun `Create gist`() {
        response = createGistWithMandatoryFields()
        id = getGistId(response)
    }

    @Test
    fun `Retrieve Gist by id`() {
        withGivenData {
            getGistResponse = getGistResponseType(id)
        }

        verifyThat {
            isExpectedId(gistResponse = parseCreateGistResponseDTO(getGistResponse),
                expectedId = id)
            hasExpectedSchema(response = getGistResponse,
                schemaName = "get_gist_schema.json")
        }
    }

    /** ==== unhappy scenarios === */

    @Test(dataProvider = "invalidGistId")
    fun `Retrieve Gist using invalid id`(gistId: String) {
        withGivenData {
            getGistResponse = getGistResponseType(
                gistId = gistId,
                httpStatusCode = HttpStatus.SC_NOT_FOUND)
        }

        verifyThat {
            isNotEmptyResponse(getGistResponse)
        }

    }

    @AfterClass(alwaysRun = true)
    fun afterClassMethod() {
        deleteGist(id)
    }
}