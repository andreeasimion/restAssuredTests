package models

data class CreateGistDTO(
    val description: String?,
    val public: Boolean?,
    val files: MutableMap<String, ContentDTO>
)

data class CreateGistWithoutOptionalParamsDTO(
    val files: MutableMap<String, ContentDTO>
)

data class CreateGistWithoutFilesDTO(
    val description: String
)

data class ContentDTO(val content: String)
