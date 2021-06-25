package co.metalab.tech.interview.ui.detail

data class EvolutionItem(
    val pokeId: Int,
    val name: String,
    val imageUrl: String,
    val evolutionRelation: EvolutionRelation,
    val trigger: String
)

enum class EvolutionRelation { FROM, INTO }
