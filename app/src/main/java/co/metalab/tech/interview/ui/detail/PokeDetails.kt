package co.metalab.tech.interview.ui.detail

import co.metalab.tech.interview.data.Type

data class PokeDetails(
    val imageUrl: String?,
    val formattedId: String?,
    val name: String?,
    val types: List<Type>,
    val noEvolutionsText: String
)
