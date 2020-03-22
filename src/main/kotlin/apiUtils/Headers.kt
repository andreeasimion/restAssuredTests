package apiUtils

import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.specification.RequestSpecification

open class Headers {
    private fun getGistApiHeader(): Map<String, String> {
        return mutableMapOf("authorization" to "token 71c5ad3f756ba0408b71660fa01dd05014a02851")
    }

    fun authorizationHeader(): RequestSpecification? {
        return defaultRequest(GitApi.baseUrl)
            .addHeaders(getGistApiHeader())
            .build()
    }

    fun noAuthorizationHeader(): RequestSpecification? {
        return defaultRequest(GitApi.baseUrl)
            .build()
    }

    private fun defaultRequest(baseUrl: String): RequestSpecBuilder {
        return RequestSpecBuilder()
            .setBaseUri(baseUrl)
            .addFilter(ResponseLoggingFilter())
            .addFilter(RequestLoggingFilter())
    }
}