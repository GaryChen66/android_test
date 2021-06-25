package co.metalab.tech.interview.domain

import co.metalab.tech.interview.data.Pokemon
import co.metalab.tech.interview.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetPokemon {

    suspend fun execute(pokeId: Int): Result<Pokemon> = withContext(Dispatchers.IO) {
        return@withContext when (val result = GetPokemonList().execute(null)) {
            is Result.Success -> Result.Success(result.data.first { pokemon -> pokemon.id == pokeId })
            is Result.Failure -> Result.Failure<Pokemon>(result.exception)
        }
    }
}
