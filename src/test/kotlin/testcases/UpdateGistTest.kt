package testcases

import implementation.GistAssertion
import models.GistResponseDTO
import org.apache.http.HttpStatus
import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test

private const val FILE_NAME = "updated_file_name.txt"
private const val DESCRIPTION = "Á É Í Ó Ú Œ œ ♩ ♪ ♫ ♬ ♭ ♮ ♯"

class UpdateGistTest : GistAssertion() {
    //TODO create data provider to test boundary values for each field

    private lateinit var response: GistResponseDTO
    private var id = ""

    @BeforeClass
    fun `Create gist`() {
        response = createGistWithMandatoryFields()
        id = getGistId(response)
    }

    @Test
    fun `Update gist file name and description`() {
        withGivenData {
            response = updateGist(
                description = DESCRIPTION,
                fileName = FILE_NAME,
                gistId = id
            )
        }

        verifyThat {
            isExpectedFileNme(
                gistResponse = response,
                expectedFilename = FILE_NAME
            )
            isExpectedDescription(
                gistResponse = response,
                expectedDescription = DESCRIPTION
            )
        }
    }

    /** ==== unhappy scenarios === */

    @Test
    fun `Update description with null value`() {
        withGivenData {
            updateGist(
                description = null,
                gistId = id,
                httpStatusCode = HttpStatus.SC_UNPROCESSABLE_ENTITY
            )
        }

    }

    @Test
    fun `Update gist without authorization`() {
        withGivenData {
            updateGist(
                httpStatusCode = HttpStatus.SC_NOT_FOUND,
                headers = noAuthorizationHeader(),
                description = "test",
                gistId = id
            )
        }
    }

    @Test
    fun `Update gist using an invalid gist id`() {
        withGivenData {
            updateGist(
                description = "test",
                gistId = "invalid",
                httpStatusCode = HttpStatus.SC_NOT_FOUND
            )
        }
    }

    @AfterClass(alwaysRun = true)
    fun afterClassMethod() {
        deleteGist(getGistId(response))
    }
}
