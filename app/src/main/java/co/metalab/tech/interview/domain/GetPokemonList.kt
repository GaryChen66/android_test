package co.metalab.tech.interview.domain

import co.metalab.tech.interview.data.Pokemon
import co.metalab.tech.interview.data.PokemonRepository
import co.metalab.tech.interview.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetPokemonList {

    suspend fun execute(query: String?): Result<List<Pokemon>> = withContext(Dispatchers.IO) {
        val pokemonList = when (val result = PokemonRepository.getPokemonList()) {
            is Result.Success -> result.data.sortedBy { pokemon -> pokemon.id }
            is Result.Failure -> return@withContext result
        }

        if (query != null && query.isNotBlank()) return@withContext pokemonList.filter { pokemon ->
            pokemon.identifier.contains(query, false)
        }.let { list ->
            Result.Success(list)
        }

        return@withContext Result.Success(pokemonList)
    }
}
