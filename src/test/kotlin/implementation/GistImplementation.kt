package implementation

import apiUtils.GitApi.GISTS
import apiUtils.GitApi.gistIdPath
import apiUtils.HttpRequestMethods
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification
import models.*
import org.apache.http.HttpStatus
import utils.ParseResponse

open class GistImplementation : HttpRequestMethods() {
    fun getGistDescription(gistResponse: GistResponseDTO): String {
        return gistResponse.description
    }

    fun getGistId(gistResponse: GistResponseDTO): String {
        return gistResponse.id
    }

    fun getPublicValue(gistResponse: GistResponseDTO): Boolean {
        return gistResponse.public
    }


    fun getFileNme(gistResponse: GistResponseDTO): MutableMap<String, ContentDTO> {
        return gistResponse.files
    }

    fun createGistWithMandatoryFields(
        description: String = "The Power of Positive Thinking",
        fileName: String = "positiveThinking.txt",
        content: String = "It makes use of positive case histories and practical instructions",
        public: Boolean = false
    ): GistResponseDTO {
        val body = CreateGistDTO(
            description = description,
            public = public,
            files = mutableMapOf(
                fileName to ContentDTO(
                    content = content
                )
            )
        )
        val response = post(
            path = GISTS,
            headers = authorizationHeader(),
            body = body,
            httpStatusCode = HttpStatus.SC_CREATED
        )
        return ParseResponse.parseCreateGistResponseDTO(response)
    }

    fun createGistWithMandatoryFields(
        fileName: String = "positiveThinking.txt",
        httpStatusCode: Int = HttpStatus.SC_CREATED,
        headers: RequestSpecification? = authorizationHeader()
    ): GistResponseDTO {
        val body = CreateGistWithoutOptionalParamsDTO(
            files = mutableMapOf(
                fileName to ContentDTO(
                    content = "content"
                )
            )
        )
        val response = post(
            path = GISTS,
            headers = headers,
            body = body,
            httpStatusCode = httpStatusCode
        )
        return ParseResponse.parseCreateGistResponseDTO(response)
    }

    fun createGistWithoutFiles(
        description: String = "positiveThinking.txt",
        httpStatusCode: Int = HttpStatus.SC_CREATED
    ): ErrorResponseDTO {
        val body = CreateGistWithoutFilesDTO(
            description = description

        )
        val response = post(
            path = GISTS,
            headers = authorizationHeader(),
            body = body,
            httpStatusCode = httpStatusCode
        )
        return ParseResponse.parseErrorResponseDTO(response)
    }

    fun updateGist(
        description: String?,
        fileName: String = "positiveThinking.txt",
        content: String = "It makes use of positive case histories and practical instructions",
        gistId: String,
        httpStatusCode: Int = HttpStatus.SC_OK,
        headers: RequestSpecification? = authorizationHeader()
    ): GistResponseDTO {
        val body = CreateGistDTO(
            description = description,
            public = null,
            files = mutableMapOf(
                fileName to ContentDTO(
                    content = content
                )
            )
        )
        val response = patch(
            path = gistIdPath(gistId),
            headers = headers,
            body = body,
            httpStatusCode = httpStatusCode
        )
        return ParseResponse.parseCreateGistResponseDTO(response)
    }

    fun getGistResponseType(
        gistId: String,
        httpStatusCode: Int = HttpStatus.SC_OK
    ): Response {
        return get(
            path = gistIdPath(gistId),
            headers = noAuthorizationHeader(),
            httpStatusCode = httpStatusCode
        )

    }

    fun getGist(gistId: String): GistResponseDTO {
        val response = getGistResponseType(gistId)
        return ParseResponse.parseCreateGistResponseDTO(response)
    }

    fun deleteGist(
        gistId: String,
        headers: RequestSpecification? = authorizationHeader(),
        httpStatusCode: Int = HttpStatus.SC_NO_CONTENT
    ) {
        delete(
            path = gistIdPath(gistId),
            headers = headers,
            httpStatusCode = httpStatusCode
        )
    }

    fun withGivenData(fn: GistImplementation.() -> Unit): GistImplementation =
        GistImplementation().apply(fn)
}