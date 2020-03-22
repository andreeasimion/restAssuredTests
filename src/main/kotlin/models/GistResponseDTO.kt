package models

data class GistResponseDTO(
    val id: String,
    val description: String,
    val public: Boolean,
    val files: MutableMap<String, ContentDTO>
)

