package testcases

import implementation.GistAssertion
import models.GistResponseDTO
import org.apache.http.HttpStatus
import org.testng.annotations.AfterMethod
import org.testng.annotations.Test

private const val DESCRIPTION = "The Power of Positive Thinking ¶"
private const val FILE_NAME = "positiveThinking.txt"
private const val CONTENT = "It makes use of positive case histories and practical instructions ♩ ♪ ♫ ♬"
private const val PUBLIC = true

class CreateGistTest : GistAssertion() {
    //TODO create data provider to test boundary values for accepted params
    private lateinit var response: GistResponseDTO

    @Test
    fun `Create gist with optional fields`() {
        withGivenData {
            response = createGistWithMandatoryFields(
                description = DESCRIPTION,
                public = PUBLIC,
                fileName = FILE_NAME,
                content = CONTENT
            )
        }

        verifyThat {
            isExpectedDescription(
                gistResponse = response,
                expectedDescription = DESCRIPTION
            )
            isExpectedFileNme(
                gistResponse = response,
                expectedFilename = FILE_NAME
            )
            isExpectedPublicValue(
                gistResponse = response,
                expectedPublicValue = PUBLIC
            )
        }
    }

    @Test
    fun `Create gist only with mandatory fields`() {
        withGivenData {
            response = createGistWithMandatoryFields(
                fileName = FILE_NAME
            )
        }

        verifyThat {
            isExpectedPublicValue(gistResponse = response, expectedPublicValue = false)
        }
    }

    /** ==== unhappy scenarios === */

    @Test
    fun `Create gist without files`() {
        withGivenData {
            createGistWithoutFiles(
                httpStatusCode = HttpStatus.SC_UNPROCESSABLE_ENTITY
            )
        }
    }

    @Test
    fun `Create gist without authorization`() {
        withGivenData {
            createGistWithMandatoryFields(
                httpStatusCode = HttpStatus.SC_UNAUTHORIZED,
                headers = noAuthorizationHeader()
            )
        }
    }

    @AfterMethod(alwaysRun = true)
    fun afterMethod() {
        val id = getGistId(response)

        if (id == String()) {
            deleteGist(id)
        }
    }
}