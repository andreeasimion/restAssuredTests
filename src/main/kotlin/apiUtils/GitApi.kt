package apiUtils

import java.util.*

object GitApi {
    private val environment: String = System.getProperty("env")
    private val environmentProperties = ResourceBundle.getBundle("environment/$environment")
    val baseUrl: String = environmentProperties.getString("BASE_URL")
    const val GISTS = "gists"
    private const val GIST_ID = "$GISTS/%s"

    fun gistIdPath(gistId: String): String =
        String.format(GIST_ID, gistId)
}