package testcases

import implementation.GistAssertion
import models.GistResponseDTO
import org.apache.http.HttpStatus
import org.testng.annotations.Test

class DeleteGist : GistAssertion() {
    private lateinit var response: GistResponseDTO

    @Test
    fun `Delete gist with valid id`() {
        withGivenData {
            response = createGistWithMandatoryFields()
            val gistId = getGistId(response)
            /** delete gist and check status code */
            deleteGist(gistId)
            /** try to get deleted gist */
            getGistResponseType(
                gistId = gistId,
                httpStatusCode = HttpStatus.SC_NOT_FOUND
            )
        }
    }

    /** ==== unhappy scenario === */
    @Test
    fun `Delete gist with invalid id`() {
        withGivenData {
            deleteGist(
                gistId = "invalid",
                httpStatusCode = HttpStatus.SC_NOT_FOUND
            )
        }
    }

    @Test
    fun `Delete gist with invalid authorization`() {
        withGivenData {
            deleteGist(
                gistId = "gistId",
                headers = noAuthorizationHeader(),
                httpStatusCode = HttpStatus.SC_NOT_FOUND
            )
        }
    }
}