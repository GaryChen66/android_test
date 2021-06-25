package co.metalab.tech.interview.domain

import co.metalab.tech.interview.data.Evolution
import co.metalab.tech.interview.data.PokemonRepository
import co.metalab.tech.interview.data.Result
import kotlinx.coroutines.runBlocking

class GetEvolutions {

    suspend fun execute(pokeId: Int): Result<List<Evolution>> = runBlocking {
        return@runBlocking PokemonRepository.getEvolutions(pokeId)
    }
}
