package co.metalab.tech.interview.domain

import co.metalab.tech.interview.data.Evolution
import co.metalab.tech.interview.data.PokemonRepository
import co.metalab.tech.interview.data.Result

class GetEvolutions {

    suspend fun execute(pokeId: Int): Result<List<Evolution>> = PokemonRepository.getEvolutions(pokeId)
}
