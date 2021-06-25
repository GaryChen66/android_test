package co.metalab.tech.interview.data

data class Pokemon(
    val id: Int,
    val identifier: String,
    val image_url: String,
    val types: List<Type>
)
