package utils

import com.google.gson.Gson
import io.restassured.response.Response
import models.ErrorResponseDTO
import models.GistResponseDTO

object ParseResponse {
    private fun <T> fromJson(json: String, classOfT: Class<T>): T {
        return Gson().fromJson(json, classOfT)
    }

    fun parseCreateGistResponseDTO(response: Response): GistResponseDTO {
        return fromJson(response.asString(), GistResponseDTO::class.java)
    }

    fun parseErrorResponseDTO(response: Response): ErrorResponseDTO {
        return fromJson(response.asString(), ErrorResponseDTO::class.java)
    }


}